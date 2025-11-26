package view.model.builder;

import view.model.BookDTO;

public class BookDTOBuilder {

    private final BookDTO dto;

    public BookDTOBuilder() {
        dto = new BookDTO();
    }

    public BookDTOBuilder setId(Long id) {
        dto.setId(id);
        return this;
    }

    public BookDTOBuilder setTitle(String title) {
        dto.setTitle(title);
        return this;
    }

    public BookDTOBuilder setAuthor(String author) {
        dto.setAuthor(author);
        return this;
    }

    public BookDTOBuilder setStock(Long stock) {
        dto.setStock(stock);
        return this;
    }

    public BookDTOBuilder setPrice(Double price) {
        dto.setPrice(price);
        return this;
    }

    public BookDTO build() {
        return dto;
    }
}
