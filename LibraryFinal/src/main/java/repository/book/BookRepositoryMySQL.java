package repository.book;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository {

    private final Connection connection;
    public BookRepositoryMySQL( Connection connection ) {
        this.connection = connection;
    }
    @Override
    public List<Book> findAll() {
        String sql="SELECT * FROM book;";
        List<Book> books = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {String sql = "SELECT * FROM book WHERE id = ?";
        Optional<Book> book = Optional.empty();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    book = Optional.of(getBookFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public boolean save(Book book) {
        String sql = "INSERT INTO book (author, title, publishedDate, stock, price) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, book.getAuthor());
            ps.setString(2, book.getTitlu());
            ps.setTimestamp(3, Timestamp.valueOf(book.getPublishedDate().atStartOfDay()));
            ps.setLong(4, book.getStock());
            ps.setDouble(5, book.getPrice());
            int rowsInserted = ps.executeUpdate();

            return (rowsInserted != 1) ? false : true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Book book) {
        String sql = "DELETE FROM book WHERE author = ? AND title = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getAuthor());
            statement.setString(2, book.getTitlu());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public void removeAll() {
        String sql = "DELETE FROM book WHERE id >= 0;";
        try{
            Statement statement=connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();

        }

    }
    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException{
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setPrice(resultSet.getDouble("price"))
                .setStock(resultSet.getLong("stock"))
                .build();
    }
    public boolean updateStock(Long id, Long newStock) {
        String sql = "UPDATE book SET stock = ? WHERE id = ?;";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,newStock);
            ps.setLong(2,id);
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
