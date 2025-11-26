package view.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.util.List;

public class ManageUsersView {

    private TableView<User> userTableView;
    private ObservableList<User> usersObservableList;

    private TextField usernameField;
    private PasswordField passwordField;
    private ComboBox<String> roleComboBox;

    private Button saveButton;
    private Button updateButton;
    private Button deleteButton;

    private Text actionTarget;

    public ManageUsersView(Stage stage, List<User> users) {

        stage.setTitle("Manage Users");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 800, 500);
        stage.setScene(scene);

        initTable(gridPane, users);
        initForm(gridPane);

        stage.show();
    }

    // ------------------- GRID PANE SETUP -------------------

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
    }

    // ------------------- TABLE VIEW -------------------

    private void initTable(GridPane gridPane, List<User> users) {

        usersObservableList = FXCollections.observableArrayList(users);
        userTableView = new TableView<>(usersObservableList);

        userTableView.setPrefHeight(250);

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameCol.setPrefWidth(300);

        userTableView.getColumns().add(usernameCol);

        gridPane.add(userTableView, 0, 0, 3, 1);

        // 🔥 POPULARE AUTOMATĂ A CÂMPURILOR LA SELECTARE
        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                usernameField.setText(newVal.getUsername());
                passwordField.setText(""); // Nu afișăm parola hash

                if (!newVal.getRoles().isEmpty()) {
                    roleComboBox.setValue(newVal.getRoles().get(0).getRole());
                }
            }
        });
    }

    // ------------------- FORM -------------------

    private void initForm(GridPane gridPane) {

        Label usernameLabel = new Label("Username:");
        gridPane.add(usernameLabel, 0, 1);

        usernameField = new TextField();
        gridPane.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        gridPane.add(passwordLabel, 0, 2);

        passwordField = new PasswordField();
        gridPane.add(passwordField, 1, 2);

        Label roleLabel = new Label("Role:");
        gridPane.add(roleLabel, 0, 3);

        roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("employee", "customer");
        roleComboBox.setValue("customer");
        gridPane.add(roleComboBox, 1, 3);

        saveButton = new Button("Save");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.getChildren().addAll(saveButton, updateButton, deleteButton);

        gridPane.add(buttonBox, 1, 4);

        actionTarget = new Text();
        gridPane.add(actionTarget, 1, 5);
    }

    // ------------------- GETTERS -------------------

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getSelectedRole() {
        return roleComboBox.getValue();
    }

    public User getSelectedUser() {
        return userTableView.getSelectionModel().getSelectedItem();
    }

    // ------------------- TABLE REFRESH -------------------

    public void refreshList(List<User> newList) {
        usersObservableList.setAll(newList);
    }

    // ------------------- BUTTON LISTENERS -------------------

    public void addSaveButtonListener(javafx.event.EventHandler<javafx.event.ActionEvent> listener) {
        saveButton.setOnAction(listener);
    }

    public void addUpdateButtonListener(javafx.event.EventHandler<javafx.event.ActionEvent> listener) {
        updateButton.setOnAction(listener);
    }

    public void addDeleteButtonListener(javafx.event.EventHandler<javafx.event.ActionEvent> listener) {
        deleteButton.setOnAction(listener);
    }

    // ------------------- ALERTS -------------------

    public void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
