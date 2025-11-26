import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.book.Cache;
import repository.sale.SaleRepository;
import repository.sale.SaleRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.sale.SaleService;
import service.sale.SaleServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();

        BookRepository bookRepo = new BookRepositoryMySQL(connection);
        SaleRepository saleRepo = new SaleRepositoryMySQL(connection);
        BookService bookService = new BookServiceImpl(bookRepo);
        SaleService saleService = new SaleServiceImpl(saleRepo,bookService, bookRepo);

        Book book = new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.now())
                .setStock(10L)
                .setPrice(50.0)
                .build();

        bookRepo.save(book);

        System.out.println("Book created!");
        Long bookId = bookRepo.findAll().get(0).getId();

        // Un user fictiv cu id = 1 (employee)
        Long userId = 1L;

        // 2. Facem o vânzare de 3 bucăți
        boolean ok = saleService.processSale(bookId, userId, 3L);

        if (ok) {
            System.out.println("Sale inserted and stock updated!");
        } else {
            System.out.println("Sale failed!");
        }

        bookRepo.findById(bookId).ifPresent(b ->
                System.out.println("New stock: " + b.getStock())
        );
    }

}