package repository;

import model.Book;

import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository {

    @Override
    public List<Book> findAll() {
        return List.of();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean save(Book book) {
        return false;
    }

    @Override
    public boolean delete(Book book) {
        return false;
    }

    @Override
    public void removeAll() {

    }
}
