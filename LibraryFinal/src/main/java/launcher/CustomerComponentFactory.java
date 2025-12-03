package launcher;

import controller.CustomerController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.Cache;
import service.book.BookService;
import service.book.BookServiceImpl;
import view.CustomerView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class CustomerComponentFactory {
    private static CustomerComponentFactory instance;

    private final BookRepository bookRepository;
    private final BookService bookService;

    private final CustomerView customerView;
    private final CustomerController customerController;

    public static CustomerComponentFactory getInstance(Boolean componentsForTest) {
        if (instance == null) {
            instance = new CustomerComponentFactory(componentsForTest);
        }
        return instance;
    }

    private CustomerComponentFactory(Boolean componentsForTest) {

        Connection connection =
                DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();

        this.bookRepository = new BookRepositoryCacheDecorator(
                new BookRepositoryMySQL(connection),
                new Cache<>()
        );

        this.bookService = new BookServiceImpl(bookRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.customerView = new CustomerView(new Stage(), bookDTOs);
        this.customerController = new CustomerController(customerView, bookService);
    }
}
