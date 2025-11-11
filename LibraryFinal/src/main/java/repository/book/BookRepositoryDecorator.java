package repository.book;

//decorator design pattern
public abstract class BookRepositoryDecorator implements BookRepository {
    protected BookRepository decoratorBookRepository;

    public BookRepositoryDecorator(BookRepository bookRepository) {
        decoratorBookRepository = bookRepository;
    }
}
