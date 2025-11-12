import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.book.Cache;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        System.out.println("Hello World!");
//        Book book = new BookBuilder()
//                .setTitle("Ion")
//                .setAuthor("Liviu Rebreanu")
//                .setPublishedDate(LocalDate.of(1910,10,20))
//                .build();
//
//        System.out.println(book);
//    //bookRepository.save(new BookBuilder().setTitle("Moara cu noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1920,2,10)).build());
//        BookRepository bookRepository = new BookRepositoryMock();
//
//        bookRepository.save(book);
//        bookRepository.save(new BookBuilder().setTitle("Moara cu noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1920,2,10)).build());
//        System.out.println(bookRepository.findAll());
//        bookRepository.removeAll();
//        System.out.println(bookRepository.findAll());
//        bookService.save(bookMoaraCuNoroc);
//        System.out.println(bookService.findAll());
//        bookService.delete(bookMoaraCuNoroc);
//        bookService.delete(book);
//        bookService.save(book);
//        System.out.println(bookService.findAll());

        Connection connection=DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();

        BookRepository bookRepository=new BookRepositoryCacheDecorator(
                new BookRepositoryMySQL(connection),
                new Cache<>());
        BookService bookService=new BookServiceImpl(bookRepository);
//        Book bookMoaraCuNoroc = new BookBuilder().setTitle("Moara cu noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1920, 2, 10)).build();

        System.out.println("Hello word!");
        RightsRolesRepository rightsRolesRepository=new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        AuthenticationService authenticationService=new AuthenticationServiceImpl(userRepository,rightsRolesRepository);

        if(userRepository.existsByUsername("ioana")){
            System.out.println("Username already exists");
        }
        else{
            authenticationService.register("ioana","parola123!");
        }
        System.out.println(authenticationService.login("ioana","parola123!"));

    }

}