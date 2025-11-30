package com.ptit.librarymanagement.service;

import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.dao.BookDAO;
import com.ptit.librarymanagement.dao.PublisherDAO;
import com.ptit.librarymanagement.dto.BookDTO;
import com.ptit.librarymanagement.dto.PublisherDTO;

import java.sql.Connection;
import java.util.List;

public class PublisherService {
    private final BookDAO bookDAO;
    private final PublisherDAO publisherDAO;

    public PublisherService(Connection connection) {
        this.bookDAO = new BookDAO(connection);
        this.publisherDAO = new PublisherDAO(connection);
    }
    public PublisherService(DAOFactory daoFactory) {
        this.bookDAO = daoFactory.getBookDAO();
        this.publisherDAO = daoFactory.getPublisherDAO();
    }


    public List<PublisherDTO> getAllPublisher () {
        return publisherDAO.getAllPublisher();
    }

    public PublisherDTO getPublisherById (Integer id) {
        return publisherDAO.getPublisherById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy nhà xuất bản"));
    }

    public Integer insertPublisher (PublisherDTO publisher) {
        return publisherDAO.insertPublisher(publisher);
    }

    public void updatePublisher (PublisherDTO publisher) {
        publisherDAO.updatePublisher(publisher);
    }

    public void deletePublisher (PublisherDTO publisherDTO) {
        publisherDAO.deletePublisher(publisherDTO);
    }

    public List<BookDTO> getBookOfPublisher (Integer id) {
        return bookDAO.getBookOfPublisher(id);
    }



    public List<PublisherDTO> getPublisherByIdOrName (PublisherDTO publisher) {
        return publisherDAO.getPublisherByIdOrName(publisher);
    }

}
