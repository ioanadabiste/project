package controller;

import service.book.BookService;
import view.CustomerView;

public class CustomerController {

    private final CustomerView view;
    private final BookService bookService;

    public CustomerController(CustomerView view, BookService bookService) {
        this.view = view;
        this.bookService = bookService;
    }
}
