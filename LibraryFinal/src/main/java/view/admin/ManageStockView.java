package view.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.model.BookDTO;

import java.util.List;

public class ManageStockView {

    private TableView<BookDTO> bookTable;
    private ObservableList<BookDTO> booksObservableList;

    private TextField titleField;
    private TextField authorField;
    private TextField stockField;
    private TextField priceField;

    private Button saveButton;
    private Button deleteButton;

    public ManageStockView(Stage stage, List<BookDTO> books) {

        stage.setTitle("Manage Stock");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        Scene scene = new Scene(gridPane, 900, 550);
        stage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);

        initTable(gridPane);
        initForm(gridPane);

        stage.show();
    }

    private void initTable(GridPane gridPane) {
        bookTable = new TableView<>();

        TableColumn<BookDTO, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(250);

        TableColumn<BookDTO, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorCol.setPrefWidth(200);

        TableColumn<BookDTO, Long> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<BookDTO, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        bookTable.getColumns().addAll(titleCol, authorCol, stockCol, priceCol);
        bookTable.setItems(booksObservableList);
        bookTable.setPrefHeight(300);

        gridPane.add(bookTable, 0, 0, 4, 1);
    }

    private void initForm(GridPane gridPane) {
        // TITLE
        gridPane.add(new Label("Title:"), 0, 1);
        titleField = new TextField();
        gridPane.add(titleField, 1, 1);

        // AUTHOR
        gridPane.add(new Label("Author:"), 2, 1);
        authorField = new TextField();
        gridPane.add(authorField, 3, 1);

        // STOCK
        gridPane.add(new Label("Stock:"), 0, 2);
        stockField = new TextField();
        gridPane.add(stockField, 1, 2);

        // PRICE
        gridPane.add(new Label("Price:"), 2, 2);
        priceField = new TextField();
        gridPane.add(priceField, 3, 2);

        saveButton = new Button("Save");
        deleteButton = new Button("Delete");

        HBox buttons = new HBox(10, saveButton, deleteButton);
        buttons.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(buttons, 1, 3, 3, 1);
    }

    public String getTitle() { return titleField.getText(); }
    public String getAuthor() { return authorField.getText(); }

    public Long getStock() {
        return Long.parseLong(stockField.getText());
    }

    public Double getPrice() {
        return Double.parseDouble(priceField.getText());
    }

    public BookDTO getSelectedBook() {
        return bookTable.getSelectionModel().getSelectedItem();
    }

    public void addBookToList(BookDTO dto) {
        booksObservableList.add(dto);
    }

    public void removeBookFromList(BookDTO dto) {
        booksObservableList.remove(dto);
    }

    public void refreshList(List<BookDTO> newList) {
        booksObservableList.setAll(newList);
    }

    public void addSaveListener(EventHandler<ActionEvent> l) { saveButton.setOnAction(l); }
    public void addDeleteListener(EventHandler<ActionEvent> l) { deleteButton.setOnAction(l); }
    public void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
