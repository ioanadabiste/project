package service.report;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ReportSevice {
    boolean generateSalesReportForEmployee(Long employeeId, LocalDateTime start, LocalDateTime end);

}
