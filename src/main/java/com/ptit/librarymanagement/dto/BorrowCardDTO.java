package com.ptit.librarymanagement.dto;

import com.ptit.librarymanagement.common.validation.customvalidation.comomdatevaildation.DateValid;
import com.ptit.librarymanagement.common.validation.customvalidation.comomdatevaildation.ReturnDateValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.CheckReturnValue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BorrowCardDTO {
    private Integer id;

    @NotNull (message = "Vui lòng nhập ngày mượn hợp lệ!")
    private Date startDate;

    @ReturnDateValid (message = "Ngày trả không hợp lệ!")
    private Date returnDate;

    @NotNull
    @NotBlank
    private String state;

    @NotNull (message = "Vui lòng chọn độc giả hợp lệ!")
    private ReaderDTO reader;

    @NotEmpty (message = "Vui lòng chọn sách!")
    private List<BookDTO> books = new ArrayList<>();

    private String punishment = "";


    public Object[] getRow () {
        return new Object[] {id, String.format("%s %s", reader.getFirstName(), reader.getLastName()),startDate, returnDate, state};
    }


}