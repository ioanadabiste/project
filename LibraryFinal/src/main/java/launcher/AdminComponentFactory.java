package launcher;

import controller.AdminController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import repository.book.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.admin.UserManagementService;
import service.admin.UserManagementServiceImpl;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.admin.AdminView;

import java.sql.Connection;

public class AdminComponentFactory {

    private static AdminComponentFactory instance;

    private final UserRepository userRepository;
    private final BookRepositoryMySQL bookRepository;
    private final RightsRolesRepository rightsRolesRepository;

    private final AuthenticationService authenticationService;
    private final UserManagementService userManagementService;
    private final BookService bookService;

    private final AdminView adminView;
    private final AdminController adminController;

    public static AdminComponentFactory getInstance(Boolean componentsForTest) {
        if (instance == null) {
            instance = new AdminComponentFactory(componentsForTest);
        }
        return instance;
    }

    private AdminComponentFactory(Boolean componentsForTests) {

        Connection connection =
                DatabaseConnectionFactory.getConnectionWrapper(componentsForTests).getConnection();

        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        this.userManagementService = new UserManagementServiceImpl(userRepository, rightsRolesRepository, authenticationService);
        this.bookService = new BookServiceImpl(bookRepository);
        this.adminView = new AdminView(new Stage());
        this.adminController =
                new AdminController(
                        adminView,
                        userManagementService,
                        bookService,
                        authenticationService
                );
    }
}
