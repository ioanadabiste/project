import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.BookRepository;
import repository.BookRepositoryMock;
import repository.BookRepositoryMySQL;
import service.BookService;
import service.BookServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        System.out.println("Hello World!");
        Book book = new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1910,10,20))
                .build();

        System.out.println(book);
//    //bookRepository.save(new BookBuilder().setTitle("Moara cu noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1920,2,10)).build());
//        BookRepository bookRepository = new BookRepositoryMock();
//
//        bookRepository.save(book);
//        bookRepository.save(new BookBuilder().setTitle("Moara cu noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1920,2,10)).build());
//        System.out.println(bookRepository.findAll());
//        bookRepository.removeAll();
//        System.out.println(bookRepository.findAll());

        Connection connection=DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
        BookRepository bookRepository=new BookRepositoryMySQL(connection);
        BookService bookService=new BookServiceImpl(bookRepository);


        bookService.save(book);
        System.out.println(bookService.findAll());
        Book bookMoaraCuNoroc=new BookBuilder().setTitle("Moara cu noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1920,2,10)).build();
        bookService.save(bookMoaraCuNoroc);
        System.out.println(bookService.findAll());
        bookService.delete(bookMoaraCuNoroc);
        bookService.delete(book);
        bookService.save(book);
        System.out.println(bookService.findAll());

    }
}