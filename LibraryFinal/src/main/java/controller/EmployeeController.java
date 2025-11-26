package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import mapper.BookMapper;
import model.User;
import service.admin.UserManagementService;
import service.admin.UserManagementServiceImpl;
import service.book.BookService;
import service.sale.SaleService;
import view.EmployeeView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.util.List;
import java.util.Optional;

public class EmployeeController {

        private final EmployeeView view;
        private final BookService bookService;
        private final SaleService saleService;
        private final User loggedUser;

        public EmployeeController(EmployeeView view,
                                  BookService bookService,
                                  SaleService saleService,
                                  User loggedUser) {

            this.view = view;
            this.bookService = bookService;
            this.saleService = saleService;
            this.loggedUser = loggedUser;

            view.addSaveListener(new SaveHandler());
            view.addDeleteListener(new DeleteHandler());
            view.addSellListener(new SellHandler());
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
    class DeleteHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            BookDTO bookDTO =(BookDTO)view.getSelectedBook();
            if (bookDTO == null) {
                view.showAlert("Error", "No selection", "Select a book first.");
                return;
            }

            boolean ok = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));

            if (ok) {
                view.removeBookFromList(bookDTO);
                view.showAlert("Success", "Book deleted", "The book was removed.");
            }
            else{
                view.showAlert("Error", "Book could not be removed", "The book could not be removed.");
            }
        }
    }

    class SellHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = view.getSelectedBook();

            if (bookDTO == null) {
                view.showAlert("Sell Error", "No book selected", "Select a book first.");
                return;
            }

            if (view.getSelectedCustomer() == null) {
                view.showAlert("Sell Error", "No customer selected", "Select a customer to sell to.");
                return;
            }

            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Sell Book");
            dialog.setHeaderText("Sell: " + bookDTO.getTitle());
            dialog.setContentText("Quantity:");

            Optional<String> result = dialog.showAndWait();
            if (result.isEmpty()) return;

            Long qty;
            try {
                qty = Long.parseLong(result.get());
                if (qty <= 0) throw new NumberFormatException();
            } catch (Exception ex) {
                view.showAlert("Error", "Invalid quantity", "Enter a positive number.");
                return;
            }

            if (qty > bookDTO.getStock()) {
                view.showAlert("Error", "Not enough stock",
                        "Requested " + qty + " but only " + bookDTO.getStock() + " available.");
                return;
            }

            boolean ok = saleService.processSale(
                    bookDTO.getId(),
                    view.getSelectedCustomer().getId(),
                    qty
            );

            if (ok) {
                view.showAlert("Success", "Sale complete", "Book sold.");
                bookDTO.setStock(bookDTO.getStock() - qty);
                view.refreshList(bookService.findAll()
                        .stream()
                        .map(BookMapper::convertBookToBookDTO)
                        .toList());
            }
        }
    }

}
