package model.builder;

import model.Sale;
import java.time.LocalDateTime;

public class SaleBuilder {

    private Sale sale;

    public SaleBuilder() {
        sale = new Sale();
    }

    public SaleBuilder setId(Long id) {
        sale.setId(id);
        return this;
    }

    public SaleBuilder setBookId(Long bookId) {
        sale.setBookId(bookId);
        return this;
    }

    public SaleBuilder setQuantity(Long quantity) {
        sale.setQuantity(quantity);
        return this;
    }

    public SaleBuilder setPrice(Double price) {
        sale.setPrice(price);
        return this;
    }

    public SaleBuilder setSaleDate(LocalDateTime saleDate) {
        sale.setSaleDate(saleDate);
        return this;
    }

    public Sale build() {
        return sale;
    }
}
