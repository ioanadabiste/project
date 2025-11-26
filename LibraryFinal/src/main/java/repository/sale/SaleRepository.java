package repository.sale;

import model.Sale;

import java.util.List;

public interface SaleRepository {
    boolean createSale(Sale sale);
    List<Sale> findAll();
}
