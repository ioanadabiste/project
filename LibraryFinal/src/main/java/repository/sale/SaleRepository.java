package repository.sale;

import model.Sale;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository {
    boolean createSale(Sale sale);
    List<Sale> findAll();
    List<Sale> findSalesByEmployee(Long employeeId, LocalDateTime from, LocalDateTime to);
}
