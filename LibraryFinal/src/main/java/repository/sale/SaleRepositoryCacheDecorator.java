package repository.sale;

import model.Book;
import model.Sale;
import repository.Cache;
import repository.book.BookRepository;

import java.time.LocalDateTime;
import java.util.List;

public class SaleRepositoryCacheDecorator extends SaleRepositoryDecorator {
    private Cache<Sale> cache;

    public SaleRepositoryCacheDecorator(SaleRepository saleRepository, Cache<Sale> cache) {
        super(saleRepository);
        this.cache=cache;

    }

    @Override
    public boolean createSale(Sale sale) {
        cache.invalidateCache();
        return decoratorSaleRepository.createSale(sale);
    }

    @Override
    public List<Sale> findAll() {
        if(cache.hasResult())
        {
            return cache.load();
        }
        List<Sale> sale=decoratorSaleRepository.findAll();
        cache.save(sale);
        return sale;
    }

    @Override
    public List<Sale> findSalesByEmployee(Long employeeId, LocalDateTime from, LocalDateTime to) {
        if (cache.hasResult()) {
            return cache.load().stream()
                    .filter(it -> it.getUserId()==employeeId
                            && !it.getSaleDate().isBefore(from)
                            && !it.getSaleDate().isAfter(to))
                    .toList();
        }
        List<Sale> result = decoratorSaleRepository.findSalesByEmployee(employeeId, from, to);
        cache.save(result);
        return result;
    }

}




