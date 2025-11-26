package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import service.admin.UserManagementService;
import service.user.AuthenticationService;
import view.admin.AdminView;
import view.admin.ManageUsersView;
import view.admin.ManageStockView;
import service.book.BookService;

public class AdminController {

    private final AdminView adminView;
    private final UserManagementService userManagementService;
    private final BookService bookService;
    private final AuthenticationService authenticationService;

    public AdminController(AdminView adminView,
                           UserManagementService userManagementService,
                           BookService bookService,
                           AuthenticationService authenticationService) {

        this.adminView = adminView;
        this.userManagementService = userManagementService;
        this.bookService = bookService;
        this.authenticationService = authenticationService;

        adminView.addManageUsersListener(new ManageUsersHandler());
        adminView.addManageStockListener(new ManageStockHandler());
    }


    private class ManageUsersHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Stage stage = new Stage();

            ManageUsersView view =
                    new ManageUsersView(stage, userManagementService.findAll());

            new ManageUsersController(view, userManagementService,authenticationService);

            stage.show();
        }
    }

    private class ManageStockHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            Stage stage = new Stage();

            ManageStockView view = new ManageStockView(stage,
                    bookService.findAll()
                            .stream()
                            .map(mapper.BookMapper::convertBookToBookDTO)
                            .toList()
            );

            new ManageStockController(view, bookService);

        }
    }

}
