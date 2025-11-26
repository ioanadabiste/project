package service.sale;

public interface SaleService {
    boolean processSale(Long bookId, Long userId, Long quantity);
}
