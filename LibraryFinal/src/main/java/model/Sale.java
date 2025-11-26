package model;

import java.time.LocalDateTime;

public class Sale {
    private Long id;
    private Long bookId;
    private Long quantity;
    private Double price;
    private LocalDateTime saleDate;
    public Sale(){}

    public Sale(Long bookId, Long quantity, Double price) {
        this.bookId = bookId;
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
}
