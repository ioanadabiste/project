package model;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String titlu;
    private String author;
    private LocalDate publishedDate;
    private Double price;
    private Long stock;


    public void setId(Long id) {
        this.id = id;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitlu() {
        return titlu;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getAuthor() {
        return author;
    }

    public Double getPrice() {
        return price;
    }
    public Long getStock() {
        return stock;
    }
    public void setStock(Long stock) {
        this.stock = stock;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Book id=" + id + " Title=" + titlu + " Author=" + author+ " PublishedDate=" + publishedDate;
    }
}
