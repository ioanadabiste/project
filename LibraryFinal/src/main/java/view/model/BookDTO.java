package view.model;

import javafx.beans.property.*;
import model.Book;

public class BookDTO {

    private LongProperty id = new SimpleLongProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty author = new SimpleStringProperty();
    private LongProperty stock = new SimpleLongProperty();
    private DoubleProperty price = new SimpleDoubleProperty();

    public Long getId() { return id.get(); }
    public void setId(Long id) { this.id.set(id); }
    public LongProperty idProperty() { return id; }

    public String getTitle() { return title.get(); }
    public void setTitle(String title) { this.title.set(title); }
    public StringProperty titleProperty() { return title; }

    public String getAuthor() { return author.get(); }
    public void setAuthor(String author) { this.author.set(author); }
    public StringProperty authorProperty() { return author; }

    public Long getStock() { return stock.get(); }
    public void setStock(Long stock) { this.stock.set(stock); }
    public LongProperty stockProperty() { return stock; }

    public Double getPrice() { return price.get(); }
    public void setPrice(Double price) { this.price.set(price); }
    public DoubleProperty priceProperty() { return price; }
}
