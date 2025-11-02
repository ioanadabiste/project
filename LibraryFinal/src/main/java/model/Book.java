package model;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String titlu;
    private String author;
    private LocalDate publishedDate;


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

    @Override
    public String toString() {
        return "Book id=" + id + " Title=" + titlu + " Author=" + author+ " PublishedDate=" + publishedDate;
    }
}
