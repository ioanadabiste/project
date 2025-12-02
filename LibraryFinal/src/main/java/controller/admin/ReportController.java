package controller.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import service.report.ReportSevice;
import view.admin.ReportView;

import java.time.LocalDateTime;

public class ReportController {
    private final ReportView reportView;
    private final ReportSevice reportService;

    public ReportController(ReportView reportView, ReportSevice reportService) {
        this.reportView = reportView;
        this.reportService = reportService;
        reportView.addGenerateListener(new GenerateHandler());
    }
    private class GenerateHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try{
                Long employeeId = reportView.getEmployeeId();
                LocalDateTime start = reportView.getStartDate().atTime(23,59,59);
                LocalDateTime end = reportView.getEndDate().atTime(23,59,59);

                boolean ok = reportService.generateSalesReportForEmployee(employeeId, start, end);
                if(ok){
                    reportView.showAlert("Succes", "PDF generated","The report generated successfully!");
                }
                else{
                    reportView.showAlert("Error", "PDF failed", "Report could not be generated.");

                }

            }catch(Exception e){
                reportView.showAlert("Error", "PDF failed", "The report could not be generated.");
            }
        }
    }
}
