package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import mapper.BookMapper;
import service.book.BookService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

public class BookController {
    private final BookView bookView;
    //in niciun ca bookRepository
    private final BookService bookService;
    public BookController(BookView bookView, BookService bookService) {
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
    }
    private class SaveButtonListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            String title=bookView.getTitle();
            String author=bookView.getAuthor();

            if(title.isEmpty() || author.isEmpty())
            {
                bookView.addDisplayAlertMessage("Save Error","Problem at Author or Title", "Cannot have an empty Title or Author field");
            }
            else{
                BookDTO bookDTO=new BookDTOBuilder().setTitle(title).setAuthor(author).build();
                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if(savedBook){
                    bookView.addDisplayAlertMessage("Saved successful","Book Added", "Book was successfully added to library");
                    bookView.addBookToObservableList(bookDTO);
                }
                else{
                    bookView.addDisplayAlertMessage("Save Error","Problem at adding Book", "There was a problem at adding the book to the database. please try again!");

                }
            }

        }
    }
    private class DeleteButtonListener implements javafx.event.EventHandler {

        @Override
        public void handle(Event event) {
            BookDTO bookDTO=(BookDTO)bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO!=null){
                boolean deleteSuccessful= bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if(deleteSuccessful){
                    bookView.addDisplayAlertMessage("Book deleted","Book Deleted", "Book was successfully deleted");
                    bookView.deleteBookFromObservableList(bookDTO);
                }
                else{
                    bookView.addDisplayAlertMessage("Delete Error","Problem at deleting book", "There was a problem with the database. Please try again");

                }
            }
            else{
                bookView.addDisplayAlertMessage("Delete Error","Problem at deleting Book", "You must select a book before pressing the delete button");
            }
        }
    }
}
