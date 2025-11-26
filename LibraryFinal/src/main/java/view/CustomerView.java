package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.model.BookDTO;

import java.util.List;

public class CustomerView {

    private TableView<BookDTO> table;
    private final ObservableList<BookDTO> books;

    public CustomerView(Stage stage, List<BookDTO> booksList) {

        stage.setTitle("Books in Stock");

        books = FXCollections.observableArrayList(booksList);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25));

        TableColumn<BookDTO, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookDTO, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        table = new TableView<>();
        table.getColumns().addAll(titleCol, authorCol);
        table.setItems(books);

        grid.add(table, 0, 0);

        stage.setScene(new Scene(grid, 600, 400));
        stage.show();
    }

    public TableView<BookDTO> getTable() {
        return table;
    }
}
