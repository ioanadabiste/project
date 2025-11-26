package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.User;
import model.validation.Notification;
import service.admin.UserManagementService;
import service.user.AuthenticationService;
import view.admin.ManageUsersView;

import java.util.Collections;


public class ManageUsersController {

    private final ManageUsersView view;
    private final UserManagementService userManagementService;
    private final AuthenticationService authenticationService;

    public ManageUsersController(ManageUsersView view,
                                 UserManagementService userManagementService,
                                 AuthenticationService authenticationService) {

        this.view = view;
        this.userManagementService = userManagementService;
        this.authenticationService = authenticationService;


        view.addSaveButtonListener(new SaveHandler());
        view.addDeleteButtonListener(new DeleteHandler());
        view.addUpdateButtonListener(new UpdateHandler());
    }

    class SaveHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String username = view.getUsername();
            String password = view.getPassword();
            String role = view.getSelectedRole();

            Notification<Boolean> notif = userManagementService.createUser(username, password, role);
            if (notif.hasError()) {
                view.showAlert("Error", "Cannot save user", notif.getFormattedErrors());
                return;
            }
            view.refreshList(userManagementService.findAll());
            view.showAlert("Success", "User added", "User saved successfully.");
        }
    }

    class DeleteHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            User selected = view.getSelectedUser();
            if (selected == null) {
                view.showAlert("Error", "No user selected", "Please select a user first.");
                return;
            }
            Notification<Boolean> notif =
                    userManagementService.deleteUser(selected.getId());
            if (notif.hasError()) {
                view.showAlert("Error", "Cannot delete", notif.getFormattedErrors());
                return;
            }
            view.refreshList(userManagementService.findAll());
            view.showAlert("Success", "User deleted", "User removed.");
        }
    }
    class UpdateHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            User selected = view.getSelectedUser();
            if (selected == null) {
                view.showAlert("Error", "No user selected", "Select a user from the table first!");
                return;
            }
            String newUsername = view.getUsername();
            String newPassword = view.getPassword();
            String newRole = view.getSelectedRole();

            if (newUsername == null || newUsername.trim().isEmpty()) {
                newUsername = selected.getUsername();
            }

            selected.setUsername(newUsername);
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                String hashed = authenticationService.encodePassword(newPassword);
                selected.setPassword(hashed);
            }
            selected.setRoles(
                    Collections.singletonList(
                            userManagementService.findRoleByName(newRole)
                    )
            );

            Notification<Boolean> result = userManagementService.update(selected);

            if (result.hasError()) {
                view.showAlert("Error", "Update failed", String.join("\n", result.getErrors()));
            } else {
                view.showAlert("Success", "User updated", "Changes saved successfully!");
                view.refreshList(userManagementService.findAll());
            }
        }
    }


}
