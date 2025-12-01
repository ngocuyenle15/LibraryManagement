package com.ptit.librarymanagement.model.dto;

import com.ptit.librarymanagement.common.validation.customvalidation.datevaildation.ReturnDateValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


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
        return new Object[] {id, reader.getFullName(),startDate, returnDate, state};
    }

    public BorrowCardDTO() {
    }
    public static BorrowCardDTOBuilder builder() {
        return new BorrowCardDTOBuilder();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ReaderDTO getReader() {
        return reader;
    }

    public void setReader(ReaderDTO reader) {
        this.reader = reader;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public String getPunishment() {
        return punishment;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
    }

    public BorrowCardDTO(Integer id, Date startDate, Date returnDate, String state, ReaderDTO reader, List<BookDTO> books, String punishment) {
        this.id = id;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.state = state;
        this.reader = reader;
        this.books = books;
        this.punishment = punishment;
    }

    public static class BorrowCardDTOBuilder {
        private Integer id;
        private Date startDate;
        private Date returnDate;
        private String state;
        private ReaderDTO reader;
        private List<BookDTO> books;
        private String punishment;

        BorrowCardDTOBuilder() {
        }

        public BorrowCardDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public BorrowCardDTOBuilder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public BorrowCardDTOBuilder returnDate(Date returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public BorrowCardDTOBuilder state(String state) {
            this.state = state;
            return this;
        }

        public BorrowCardDTOBuilder reader(ReaderDTO reader) {
            this.reader = reader;
            return this;
        }

        public BorrowCardDTOBuilder books(List<BookDTO> books) {
            this.books = books;
            return this;
        }

        public BorrowCardDTOBuilder punishment(String punishment) {
            this.punishment = punishment;
            return this;
        }

        public BorrowCardDTO build() {
            return new BorrowCardDTO(this.id, this.startDate, this.returnDate, this.state, this.reader, this.books, this.punishment);
        }

        public String toString() {
            Integer var10000 = this.id;
            return "BorrowCardDTO.BorrowCardDTOBuilder(id=" + var10000 + ", startDate=" + String.valueOf(this.startDate) + ", returnDate=" + String.valueOf(this.returnDate) + ", state=" + this.state + ", reader=" + String.valueOf(this.reader) + ", books=" + String.valueOf(this.books) + ", punishment=" + this.punishment + ")";
        }
    }


}