package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.User;
import view.model.BookDTO;

import java.util.List;

public class EmployeeView {

    private TableView<BookDTO> bookTableView;
    private ObservableList<BookDTO> booksObservableList;

    private TextField titleField;
    private TextField authorField;
    private TextField stockField;
    private TextField priceField;

    private ComboBox<User> customerCombo;

    private Button saveButton;
    private Button deleteButton;
    private Button sellButton;

    public EmployeeView(Stage primaryStage, List<BookDTO> books, List<User> customers) {

        primaryStage.setTitle("Employee Panel");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 900, 550);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);

        initTable(gridPane);
        initForm(gridPane);
        initCustomerCombo(gridPane, customers);

        primaryStage.show();
    }

    // ---------------- GRID SETUP ----------------
    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(12);
        gridPane.setVgap(12);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
    }

    // ---------------- TABLE ----------------
    private void initTable(GridPane gridPane) {
        bookTableView = new TableView<>();

        TableColumn<BookDTO, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BookDTO, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookDTO, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookDTO, Long> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<BookDTO, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        bookTableView.getColumns().addAll(idCol, titleCol, authorCol, stockCol, priceCol);
        bookTableView.setItems(booksObservableList);
        bookTableView.setPrefHeight(300);

        gridPane.add(bookTableView, 0, 0, 6, 1);
    }

    // ---------------- FORM ----------------
    private void initForm(GridPane gridPane) {

        gridPane.add(new Label("Title:"), 0, 1);
        titleField = new TextField();
        gridPane.add(titleField, 1, 1);

        gridPane.add(new Label("Author:"), 2, 1);
        authorField = new TextField();
        gridPane.add(authorField, 3, 1);

        gridPane.add(new Label("Stock:"), 0, 2);
        stockField = new TextField();
        gridPane.add(stockField, 1, 2);

        gridPane.add(new Label("Price:"), 2, 2);
        priceField = new TextField();
        gridPane.add(priceField, 3, 2);

        saveButton = new Button("Save");
        deleteButton = new Button("Delete");
        sellButton = new Button("Sell");

        HBox buttonBox = new HBox(10, saveButton, deleteButton, sellButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        gridPane.add(buttonBox, 0, 4, 4, 1);
    }

    // ---------------- CUSTOMER COMBO ----------------
    private void initCustomerCombo(GridPane gridPane, List<User> customers) {
        gridPane.add(new Label("Customer:"), 0, 3);

        customerCombo = new ComboBox<>();
        customerCombo.setPromptText("Select customer");
        customerCombo.getItems().addAll(customers);

        gridPane.add(customerCombo, 1, 3);
    }

    // ---------------- GETTERS -------------------

    public String getTitle() { return titleField.getText(); }
    public String getAuthor() { return authorField.getText(); }
    public Long getStock() { return Long.parseLong(stockField.getText()); }
    public Double getPrice() { return Double.parseDouble(priceField.getText()); }

    public BookDTO getSelectedBook() {
        return bookTableView.getSelectionModel().getSelectedItem();
    }

    public User getSelectedCustomer() {
        return customerCombo.getValue();
    }

    // ---------------- LIST UPDATE -------------------

    public void addBookToList(BookDTO dto) { booksObservableList.add(dto); }

    public void removeBookFromList(BookDTO dto) { booksObservableList.remove(dto); }

    public void refreshList(List<BookDTO> newList) {
        booksObservableList.setAll(newList);
    }

    // ---------------- LISTENERS -------------------

    public void addSaveListener(javafx.event.EventHandler<ActionEvent> l) { saveButton.setOnAction(l); }
    public void addDeleteListener(javafx.event.EventHandler<ActionEvent> l) { deleteButton.setOnAction(l); }
    public void addSellListener(javafx.event.EventHandler<ActionEvent> l) { sellButton.setOnAction(l); }

    // ---------------- ALERT -------------------

    public void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
