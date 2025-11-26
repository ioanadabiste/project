package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sale {
    private Long id;
    private Long bookId;
    private Long userId;
    private Long quantity;
    private Double price;
    private LocalDateTime saleDate;
    public Sale(){}

    public Sale(Long id, Long bookId, Long userId, Long quantity, Double price, LocalDate saleDate) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.saleDate = LocalDateTime.now();

    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public LocalDateTime getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDateTime saleDate) { this.saleDate = saleDate; }

    public void setUserId(Long userId) {this.userId = userId;}

    public long getUserId() {return userId;}
}
