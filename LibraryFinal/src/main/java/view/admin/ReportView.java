package view.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ReportView {
    private TextField employeeIdField;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private Button generateReportButton;

    public ReportView(Stage stage) {
        stage.setTitle("Generate Report");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(new Label("Employee ID:"),0,0);
        employeeIdField = new TextField();
        gridPane.add(employeeIdField,1,0);

        gridPane.add(new Label("Start Date:"),0,1);
        startDatePicker = new DatePicker();
        gridPane.add(startDatePicker,1,1);

        gridPane.add(new Label("End Date:"),0,2);
        endDatePicker = new DatePicker();
        gridPane.add(endDatePicker,1,2);

        generateReportButton = new Button("Generate PDF");
        gridPane.add(generateReportButton,1,3);

        stage.setScene(new Scene(gridPane,400,250));
        stage.show();
    }
    public Long getEmployeeId() {
        return Long.parseLong(employeeIdField.getText());
    }

    public java.time.LocalDate getStartDate() {
        return startDatePicker.getValue();
    }

    public java.time.LocalDate getEndDate() {
        return endDatePicker.getValue();
    }

    public void addGenerateListener(javafx.event.EventHandler handler) {
        generateReportButton.setOnAction(handler);
    }

    public void showAlert(String title, String header, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
