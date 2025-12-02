package service.report;

import model.Sale;
import repository.sale.SaleRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ReportServiceImpl implements ReportSevice{
    private final SaleRepository saleRepository;
    public ReportServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public boolean generateSalesReportForEmployee(Long employeeId, LocalDateTime start, LocalDateTime end) {
        List<Sale> sales = saleRepository.findSalesByEmployee(employeeId,start,end);
        try(PDDocument document = new PDDocument()){
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content=new PDPageContentStream(document,page);

            content.beginText();
            content.setFont(PDType1Font.TIMES_BOLD,18);
            content.newLineAtOffset(200,750);
            content.showText("Sales report for employee ID: "+ employeeId);
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_BOLD, 12);
            content.newLineAtOffset(50, 690);
            content.showText("Book ID   | Qty | Price   | Total    | Date");
            content.endText();


            content.setFont(PDType1Font.TIMES_ROMAN,12);

            int y = 660;
            double totalGeneral = 0;

            for (Sale s : sales) {

                double total = s.getQuantity() * s.getPrice();
                totalGeneral += total;

                content.beginText();
                content.newLineAtOffset(50, y);

                String line = String.format(
                        "%-9d | %-3d | %-7.2f | %-8.2f | %s",
                        s.getBookId(),
                        s.getQuantity(),
                        s.getPrice(),
                        total,
                        s.getSaleDate()
                );

                content.showText(line);
                content.endText();

                y -= 20;
            }


            content.beginText();
            content.setFont(PDType1Font.TIMES_BOLD, 14);
            content.newLineAtOffset(50, y-40);
            content.showText("TOTAL GENERAL: "+ totalGeneral);
            content.endText();

            content.close();

            document.save("D:\\proiect\\Raport\\Raport.pdf");
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }

    }
}
