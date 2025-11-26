package service.sale;

import model.Book;
import model.Sale;
import repository.sale.SaleRepository;
import repository.book.BookRepository;
import service.book.BookService;

public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final BookService bookService;
    private final BookRepository bookRepository;

    public SaleServiceImpl(SaleRepository saleRepository,
                           BookService bookService,
                           BookRepository bookRepository) {
        this.saleRepository = saleRepository;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean processSale(Long bookId, Long quantity) {
        Book book = bookService.findById(bookId);

        if (book.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }

        Sale sale = new Sale(bookId, quantity, book.getPrice());
        if (!saleRepository.createSale(sale)) {
            return false;
        }

        Long newStock = book.getStock() - quantity;
        return bookRepository.updateStock(bookId, newStock);
    }
}
