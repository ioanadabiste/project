package mapper;
import model.Book;
import model.builder.BookBuilder;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookDTO convertBookToBookDTO(Book book) {
        return new BookDTOBuilder()
                .setId(book.getId())
                .setTitle(book.getTitlu())
                .setAuthor(book.getAuthor())
                .setStock(book.getStock())
                .setPrice(book.getPrice())
                .build();
    }

    public static Book convertBookDTOToBook(BookDTO bookDTO) {
        return new BookBuilder()
                .setId(bookDTO.getId())
                .setTitle(bookDTO.getTitle())
                .setAuthor(bookDTO.getAuthor())
                .setStock(bookDTO.getStock())
                .setPrice(bookDTO.getPrice())
                .setPublishedDate(LocalDate.of(2010,1,1))
                .build();
    }

    public static List<BookDTO> convertBookListToBookDTOList(List<Book> books) {
        return books.stream()
                .map(BookMapper::convertBookToBookDTO)
                .collect(Collectors.toList());
    }

    public static List<Book> convertBookDTOListToBookList(List<BookDTO> booksDTO) {
        return booksDTO.stream()
                .map(BookMapper::convertBookDTOToBook)
                .collect(Collectors.toList());
    }
}
