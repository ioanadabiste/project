package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import model.Book;
import repository.BookRepository;
import repository.BookRepositoryMySQL;
import service.BookService;
import service.BookServiceImpl;
import view.BookView;
import view.model.BookDTO;


import java.sql.Connection;
import java.util.List;

//SINGLETON
//cu lazy load
public class ComponentFactory {
    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static ComponentFactory instance;

    public static ComponentFactory getInstance(Boolean componentTest, Stage primaryStage){
        //lipseste ceva aici
            if (instance==null){
                instance= new ComponentFactory(componentTest,primaryStage);
            }
            return instance;


    }
    //e public s ar putea sa fie o pb aici
    public ComponentFactory(Boolean componentTest, Stage primaryStage){
        //arbore de dependinte
        Connection connection=DatabaseConnectionFactory.getConnectionWrapper(componentTest).getConnection();
        this.bookRepository=new BookRepositoryMySQL(connection);
        this.bookService=new BookServiceImpl(bookRepository);

        List<BookDTO> bookDTOs= BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.bookView=new BookView(primaryStage,bookDTOs);
        //in controler interactionam DOAR cu service nu cu repository
        //no business logic in controller doar ub Model
        this.bookController=new BookController(bookView,bookService);
    }

    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }
}
