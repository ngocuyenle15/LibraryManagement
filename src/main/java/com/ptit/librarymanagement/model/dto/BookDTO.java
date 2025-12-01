package com.ptit.librarymanagement.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookDTO {
    private Integer id;

    @NotNull (message = "Tiêu đề không được để trống!")
    @NotBlank (message = "Tiêu đề không được để trống!")
    private String title;

    private byte[] cover = null;
    private String description = "";

    @NotNull (message = "Số lượng không được để trống!")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0!")
    private Integer quantity;

    private Boolean delete = false;

    @NotNull (message = "Nhà xất bản không được để trống!")
    private PublisherDTO publisher;

    @NotNull (message = "Vị trí không được để trống!")
    private ShelfDTO shelf;

    @NotEmpty (message = "Danh sách tác giả không được để trống!")
    private List<AuthorDTO> authors = new ArrayList<>();

    @NotEmpty (message = "Danh mục không được để trống!")
    private List<CategoryDTO> categories = new ArrayList<>();

    public BookDTO(Integer id, String title, byte[] cover, String description, Integer quantity, Boolean delete, PublisherDTO publisher, ShelfDTO shelf, List<AuthorDTO> authors, List<CategoryDTO> categories) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.description = description;
        this.quantity = quantity;
        this.delete = delete;
        this.publisher = publisher;
        this.shelf = shelf;
        this.authors = authors;
        this.categories = categories;
    }

    public BookDTO() {
    }
    public static BookDTOBuilder builder() {
        return new BookDTOBuilder();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
    }

    public ShelfDTO getShelf() {
        return shelf;
    }

    public void setShelf(ShelfDTO shelf) {
        this.shelf = shelf;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public Object[] getRow () {
        return new Object[] {
                id,
                title,
                quantity,
                publisher.getName(),
                authors == null ? new ArrayList<AuthorDTO>() : authors
                        .stream()
                        .map(author -> author.getFullName())
                        .collect(Collectors.joining(", ")),
                categories == null ? new ArrayList<CategoryDTO>() :
                        categories.stream()
                        .map(category -> category.getName())
                        .collect(Collectors.joining(", ")),
                String.format("%s - %s", this.shelf.getLocation(),this.shelf.getShelfName())
        };
    }

    public void returnBook () {
        ++this.quantity;
    }

    public static class BookDTOBuilder {
        private Integer id;
        private String title;
        private byte[] cover;
        private String description;
        private Integer quantity;
        private Boolean delete;
        private PublisherDTO publisher;
        private ShelfDTO shelf;
        private List<AuthorDTO> authors;
        private List<CategoryDTO> categories;

        BookDTOBuilder() {
        }

        public BookDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public BookDTOBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookDTOBuilder cover(byte[] cover) {
            this.cover = cover;
            return this;
        }

        public BookDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BookDTOBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public BookDTOBuilder delete(Boolean delete) {
            this.delete = delete;
            return this;
        }

        public BookDTOBuilder publisher(PublisherDTO publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookDTOBuilder shelf(ShelfDTO shelf) {
            this.shelf = shelf;
            return this;
        }

        public BookDTOBuilder authors(List<AuthorDTO> authors) {
            this.authors = authors;
            return this;
        }

        public BookDTOBuilder categories(List<CategoryDTO> categories) {
            this.categories = categories;
            return this;
        }

        public BookDTO build() {
            return new BookDTO(this.id, this.title, this.cover, this.description, this.quantity, this.delete, this.publisher, this.shelf, this.authors, this.categories);
        }

        public String toString() {
            Integer var10000 = this.id;
            return "BookDTO.BookDTOBuilder(id=" + var10000 + ", title=" + this.title + ", cover=" + Arrays.toString(this.cover) + ", description=" + this.description + ", quantity=" + this.quantity + ", delete=" + this.delete + ", publisher=" + String.valueOf(this.publisher) + ", shelf=" + String.valueOf(this.shelf) + ", authors=" + String.valueOf(this.authors) + ", categories=" + String.valueOf(this.categories) + ")";
        }
    }


}

