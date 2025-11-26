package controller;

import database.Constants;
import database.DatabaseConnectionFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import launcher.AdminComponentFactory;
import launcher.CustomerComponentFactory;
import launcher.EmployeeComponentFactory;
import launcher.LoginComponentFactory;
import model.User;
import model.validation.Notification;
import repository.sale.SaleRepositoryMySQL;
import service.sale.SaleService;
import service.sale.SaleServiceImpl;
import service.user.AuthenticationService;
import view.LoginView;

public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }
    private class LoginButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasError()) {
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            } else {
                User user = loginNotification.getResult();
                loginView.setActionTargetText("Login successful!");

                LoginComponentFactory.getStage().close();

                if (hasRole(user, Constants.Roles.ADMINISTRATOR)) {
                    AdminComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests());
                }
                else if (hasRole(user, Constants.Roles.EMPLOYEE)) {
                    EmployeeComponentFactory factory =
                            EmployeeComponentFactory.getInstance(
                                    LoginComponentFactory.getComponentsForTests(),
                                    new Stage(),
                                    user
                            );
                }

                else if (hasRole(user, Constants.Roles.CUSTOMER)) {
                    CustomerComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests());
                }


            }
        }
    }
    private boolean hasRole(User user, String roleName) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getRole().equalsIgnoreCase(roleName));
    }
    private class RegisterButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification=authenticationService.register(username,password);

            if (registerNotification.hasError()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());

            } else {
                loginView.setActionTargetText("Register successful!");
            }
        }
    }


}
