package launcher;

import controller.LoginController;
import database.Constants;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactoryLog {
    private final LoginView loginView;
    private final LoginController loginController;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final BookRepository bookRepository;
   //static
    private static ComponentFactoryLog instance;
    //thread safe
    public static ComponentFactoryLog getInstance(Boolean componentsForTest, Stage stage) {
        if(instance == null){
            instance = new ComponentFactoryLog(componentsForTest, stage);
        }
        return instance;
    }
    private ComponentFactoryLog(Boolean componentForTest, Stage stage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentForTest).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        this.loginView = new LoginView(stage);
        this.loginController = new LoginController(loginView, authenticationService);
        this.bookRepository = new BookRepositoryMySQL(connection);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
    public UserRepository getUserRepository() {
        return userRepository;
    }
    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }
    public BookRepository getBookRepository() {
        return bookRepository;
    }
    public LoginView getLoginView() {
        return loginView;
    }
    public LoginController getLoginController() {
        return loginController;
    }
}
