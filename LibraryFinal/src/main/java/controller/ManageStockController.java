package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import service.book.BookService;
import view.admin.ManageStockView;
import view.model.BookDTO;
import mapper.BookMapper;
import view.model.builder.BookDTOBuilder;

public class ManageStockController {

    private final ManageStockView view;
    private final BookService bookService;

    public ManageStockController(ManageStockView view, BookService bookService) {
        this.view = view;
        this.bookService = bookService;

        view.addSaveListener(new SaveHandler());
        view.addDeleteListener(new DeleteHandler());
       // view.addUpdateStockListener(new UpdateStockHandler());
    }

     private class SaveHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                String title=view.getTitle();
                String author=view.getAuthor();
                Long stock=view.getStock();
                Double price=view.getPrice();
                if(title.isEmpty() || author.isEmpty() || stock.equals(0)||price.equals(0))
                {
                    view.showAlert("Save Error","Problem at Author or Title", "Cannot have an empty Title or Author field");
                }
                else {
                    BookDTO bookDTO=new BookDTOBuilder()
                            .setAuthor(author)
                            .setPrice(price)
                            .setTitle(title)
                            .setStock(stock)
                            .build();
                    boolean ok = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                    if (ok) {
                        view.addBookToList(bookDTO);
                        view.showAlert("Success", "Book added", "The book was saved.");
                    }
                    else{
                        view.showAlert("Error", "Book could not be saved", "The book could not be saved.");
                    }
                }
            } catch (Exception e) {
                view.showAlert("Error", "Invalid input", "Check fields again.");
            }
        }
    }

    private class DeleteHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            BookDTO bookDTO = view.getSelectedBook();
            if (bookDTO == null) {
                view.showAlert("Error", "No selection", "Select a book first.");
                return;
            }

            boolean deleted = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));

            if (deleted) {
                view.showAlert("Success", "Book deleted", "Book removed!");

                view.refreshList(
                        bookService.findAll()
                                .stream()
                                .map(BookMapper::convertBookToBookDTO)
                                .toList()
                );
            } else {
                view.showAlert("Error", "Delete failed", "Could not delete the book.");
            }
        }
    }

}
