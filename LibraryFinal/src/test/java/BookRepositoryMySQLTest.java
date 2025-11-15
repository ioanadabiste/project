//
//import model.Book;
//import model.builder.BookBuilder;
//import org.junit.jupiter.api.*;
//import repository.book.BookRepositoryMySQL;
//
//import java.sql.*;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class BookRepositoryMySQLTest {
//
//    private Connection connection;
//    private BookRepositoryMySQL bookRepository;
//
//    @BeforeAll
//    void setupDatabase() throws SQLException {
//        connection = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/test_library?allowMultiQueries=true",
//                "root", "root"
//        );
//
//        Statement statement = connection.createStatement();
//        statement.execute("CREATE TABLE IF NOT EXISTS book (" +
//                "id BIGINT NOT NULL AUTO_INCREMENT, " +
//                "author VARCHAR(255) NOT NULL, " +
//                "title VARCHAR(255) NOT NULL, " +
//                "publishedDate DATE, " +
//                "PRIMARY KEY (id)" +
//                ");");
//
//        bookRepository = new BookRepositoryMySQL(connection);
//    }
//
//    @BeforeEach
//    void cleanTable() throws SQLException {
//        Statement statement = connection.createStatement();
//        statement.execute("DELETE FROM book;");
//    }
//
//    @Test
//    void testSaveAndFindAll() {
//        Book book = new BookBuilder()
//                .setAuthor("Colleen Hoover")
//                .setTitle("It Ends With Us")
//                .setPublishedDate(LocalDate.of(2016, 8, 2))
//                .build();
//
//        assertTrue(bookRepository.save(book));
//
//        List<Book> books = bookRepository.findAll();
//        assertEquals(1, books.size());
//        assertEquals("Colleen Hoover", books.get(0).getAuthor());
//    }
//
//    @Test
//    void testFindById() {
//        Book book = new BookBuilder()
//                .setAuthor("Veronica Roth")
//                .setTitle("Divergent")
//                .setPublishedDate(LocalDate.of(2011, 4, 25))
//                .build();
//
//        assertTrue(bookRepository.save(book));
//
//        List<Book> all = bookRepository.findAll();
//        Long id = all.get(0).getId();
//
//        Optional<Book> found = bookRepository.findById(id);
//        assertTrue(found.isPresent());
//        assertEquals("Veronica Roth", found.get().getAuthor());
//        assertEquals("Divergent", found.get().getTitlu());
//    }
//
//    @Test
//    void testDelete() {
//        Book book = new BookBuilder()
//                .setAuthor("Suzanne Collins")
//                .setTitle("The Hunger Games")
//                .setPublishedDate(LocalDate.of(2008, 9, 14))
//                .build();
//
//        assertTrue(bookRepository.save(book));
//        assertEquals(1, bookRepository.findAll().size());
//
//        assertTrue(bookRepository.delete(book));
//        assertEquals(0, bookRepository.findAll().size());
//    }
//
//    @AfterAll
//    void tearDown() throws SQLException {
//        Statement statement = connection.createStatement();
//        statement.execute("DROP TABLE IF EXISTS book;");
//        connection.close();
//    }
//}
