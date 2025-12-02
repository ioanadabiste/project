package view.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdminView {
    private Button manageUsersButton;
    private Button manageBooksButton;
    private Button generateReportButton;

    public AdminView(Stage stage) {
        stage.setTitle("Admin Panel");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(20);
        grid.setVgap(20);

        manageUsersButton = new Button("Manage Users");
        manageBooksButton = new Button("Manage Stock");
        generateReportButton = new Button("Generate Sale Report");

        grid.add(manageUsersButton, 0, 0);
        grid.add(manageBooksButton, 0, 1);
        grid.add(generateReportButton, 0,2);

        stage.setScene(new Scene(grid, 400, 300));
        stage.show();
    }
    public void addManageUsersListener(javafx.event.EventHandler handler) {
        manageUsersButton.setOnAction(handler);
    }

    public void addManageStockListener(javafx.event.EventHandler handler) {
        manageBooksButton.setOnAction(handler);
    }

    public void addGenerateReportListener(javafx.event.EventHandler handler) {generateReportButton.setOnAction(handler); }
}
