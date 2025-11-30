package com.ptit.librarymanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

    public Object[] getRow () {
        return new Object[] {
                id,
                title,
                quantity,
                publisher.getName(),
                authors == null ? new ArrayList<AuthorDTO>() : authors
                        .stream()
                        .map(author -> author.getFirstName() + " " + author.getLastName())
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


}

