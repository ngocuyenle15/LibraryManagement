package com.ptit.librarymanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.util.List;


public class ShelfDTO {
    private Integer id;

    @NotNull (message = "Vị trí không được để trống!")
    @NotBlank (message = "Vị trí không được để trống!")
    private String shelfName;

    @NotNull (message = "Địa điểm không được để trống!")
    @NotBlank (message = "Địa điểm không được để trống!")
    private String location;
    private List<BookDTO> books;

    public ShelfDTO() {
    }

    public ShelfDTO(Integer id, String shelfName, String location, List<BookDTO> books) {
        this.id = id;
        this.shelfName = shelfName;
        this.location = location;
        this.books = books;
    }
    public static ShelfDTOBuilder builder() {
        return new ShelfDTOBuilder();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return this.getShelfName();
    }

    public Object[] getRow () {
        return new Object[] {id, location, shelfName};
    }




    public static class ShelfDTOBuilder {
        private Integer id;
        private String shelfName;
        private String location;
        private List<BookDTO> books;

        ShelfDTOBuilder() {
        }

        public ShelfDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ShelfDTOBuilder shelfName(String shelfName) {
            this.shelfName = shelfName;
            return this;
        }

        public ShelfDTOBuilder location(String location) {
            this.location = location;
            return this;
        }

        public ShelfDTOBuilder books(List<BookDTO> books) {
            this.books = books;
            return this;
        }

        public ShelfDTO build() {
            return new ShelfDTO(this.id, this.shelfName, this.location, this.books);
        }

        public String toString() {
            Integer var10000 = this.id;
            return "ShelfDTO.ShelfDTOBuilder(id=" + var10000 + ", shelfName=" + this.shelfName + ", location=" + this.location + ", books=" + String.valueOf(this.books) + ")";
        }
    }
}
