package launcher;

import controller.EmployeeController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import model.User;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.sale.SaleRepository;
import repository.sale.SaleRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.admin.UserManagementService;
import service.admin.UserManagementServiceImpl;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.sale.SaleService;
import service.sale.SaleServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.EmployeeView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class EmployeeComponentFactory {

    private static EmployeeComponentFactory instance;

    private final BookRepository bookRepository;
    private final BookService bookService;

    private final SaleRepository saleRepository;
    private final SaleService saleService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final AuthenticationService authenticationService;
    private final UserManagementService userManagementService;

    private final EmployeeView employeeView;
    private final EmployeeController employeeController;

    public static EmployeeComponentFactory getInstance(Boolean componentsForTest,
                                                       Stage stage,
                                                       User loggedUser) {
        if (instance == null) {
            instance = new EmployeeComponentFactory(componentsForTest, stage, loggedUser);
        }
        return instance;
    }

    private EmployeeComponentFactory(Boolean componentsForTest, Stage stage, User loggedUser) {

        Connection connection =
                DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.saleRepository = new SaleRepositoryMySQL(connection);

        this.bookService = new BookServiceImpl(bookRepository);
        this.authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        this.userManagementService =
                new UserManagementServiceImpl(
                        userRepository,
                        rightsRolesRepository,
                        authenticationService
                );
        this.saleService = new SaleServiceImpl(saleRepository, bookService, bookRepository);
        List<BookDTO> dtos = BookMapper.convertBookListToBookDTOList(bookService.findAll());

        this.employeeView = new EmployeeView(
                stage,
                dtos,
                userRepository.findAll().stream()
                        .filter(u -> u.getRoles().stream()
                                .anyMatch(r -> r.getRole().equalsIgnoreCase("customer")))
                        .toList()
        );

        this.employeeController =
                new EmployeeController(employeeView, bookService, saleService, loggedUser);
    }
}
