package com.ptit.librarymanagement.service;

import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.dao.AuthorDAO;
import com.ptit.librarymanagement.dao.BookDAO;
import com.ptit.librarymanagement.model.dto.AuthorDTO;
import com.ptit.librarymanagement.model.dto.BookDTO;

import java.sql.Connection;
import java.util.List;

public class AuthorService {
    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;

    public AuthorService (Connection connection) {
        this.authorDAO = new AuthorDAO(connection);
        this.bookDAO = new BookDAO(connection);
    }

    public AuthorService (DAOFactory daoFactory) {
        this.authorDAO = daoFactory.getAuthorDAO();
        this.bookDAO = daoFactory.getBookDAO();
    }


    public List<AuthorDTO>getAllAuthor () {
        return authorDAO.getAllAuthor();
    }

    public Integer insertAuthor (AuthorDTO authorDTO) {
        return authorDAO.insertAuthor(authorDTO);
    }

    public void deleteAuthor (AuthorDTO author) {
        authorDAO.deleteAuthor(author);
    }

    public void updateAuthor (AuthorDTO authorDTO) {
        authorDAO.updateAuthor(authorDTO);
    }

    public List<BookDTO> getBookOfAuthor (Integer authorId) {
        return bookDAO.getBookOfAuthor(authorId);
    }

    public AuthorDTO getAuthorById (Integer id) {
        return authorDAO.getAuthorById(id).orElseThrow(() -> new RuntimeException("Khong tim thay author"));
    }



    public List<AuthorDTO> getAuthorByIdOrName (Integer id, String name) {
        return authorDAO.getAuthorByIdOrName(id, name);
    }






}
