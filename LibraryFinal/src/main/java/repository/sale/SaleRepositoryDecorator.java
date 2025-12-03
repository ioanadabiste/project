package repository.sale;

import model.Sale;

import java.time.LocalDateTime;
import java.util.List;

public abstract class SaleRepositoryDecorator implements SaleRepository{
   protected SaleRepository decoratorSaleRepository;
   public SaleRepositoryDecorator(SaleRepository decoratorSaleRepository){ this.decoratorSaleRepository = decoratorSaleRepository;}
}
