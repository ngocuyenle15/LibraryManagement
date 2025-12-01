package com.ptit.librarymanagement.service;

import com.ptit.librarymanagement.common.transactionmanager.Transactional;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.dao.BookDAO;
import com.ptit.librarymanagement.dao.BorrowCardDAO;
import com.ptit.librarymanagement.dao.ReaderDAO;
import com.ptit.librarymanagement.model.dto.BookDTO;
import com.ptit.librarymanagement.model.dto.BorrowCardDTO;
import com.ptit.librarymanagement.common.enums.BorrowState;

import java.sql.Connection;
import java.util.List;


public class BorrowCardService {
    private final BorrowCardDAO borrowCardDAO;
    private final BookDAO bookDAO;
    private final ReaderDAO readerDAO;



    public BorrowCardService (Connection connection) {
        this.borrowCardDAO = new BorrowCardDAO(connection);
        this.bookDAO = new BookDAO(connection);
        this.readerDAO = new ReaderDAO(connection);

    }

    public BorrowCardService (DAOFactory daoFactory) {
        this.borrowCardDAO = daoFactory.getBorrowCardDAO();
        this.bookDAO = daoFactory.getBookDAO();
        this.readerDAO = daoFactory.getReaderDAO();
    }

    public List<BorrowCardDTO> getAllBorrowCard () {
        return borrowCardDAO.getAllBorrowCard();
    }

    public List<BorrowCardDTO> getBorrowCardByIdOrNameReader (Integer id, String name) {
        return borrowCardDAO.getBorrowCardByIdOrNameReader(id, name);
    }

    public BorrowCardDTO getBorrowCardById (Integer id) {

        BorrowCardDTO cardDTO = borrowCardDAO.getBorrowCardById(id);
        cardDTO.setBooks(bookDAO.getBookByBorrowCard(cardDTO));
        cardDTO.setReader(readerDAO.getReaderById(cardDTO.getReader().getId()).get());
        return cardDTO;
    }


    public List<BookDTO> getBookOfBorrowCard (BorrowCardDTO cardDTO) {
        return bookDAO.getBookByBorrowCard(cardDTO);
    }
//    @Transactional
//    public void returnBook (BorrowCardDTO card) {
//        for (BookDTO book : bookDAO.getBookByBorrowCard(card)) {
//            book.returnBook();
//            bookDAO.updateBook(book);
//        }
//        borrowCardDAO.deleteBookInBorrowCard(card);
//        borrowCardDAO.deleteBorrowCard(card);
//
//    }


    @Transactional
    public void returnBooks (BorrowCardDTO card) {
        card.setState("Đã trả");
//        borrowCardDAO.deleteBookInBorrowCard(card);
        borrowCardDAO.updateBorrowCardOfReader(card);
        for (BookDTO book : bookDAO.getBookByBorrowCard(card)) {
            bookDAO.updateQuantityBook(book.getId(), book.getQuantity() + 1);
        }
    }



    public void deleteBorrowCard (BorrowCardDTO card) {
        for (BookDTO book : bookDAO.getBookByBorrowCard(card)) {
            book.returnBook();
            bookDAO.updateBook(book);
        }
        card.setState("Đã trả");
        borrowCardDAO.deleteBookInBorrowCard(card);
        borrowCardDAO.deleteBorrowCard(card);

    }


    public void update(BorrowCardDTO cardDTO) {
        borrowCardDAO.updateBorrowCardOfReader(cardDTO);

    }

    public void createBorrowCard (BorrowCardDTO borrowCard) {
        Integer i = borrowCardDAO.insertBorrowCard(borrowCard);
        borrowCard.setId(i);
        for (BookDTO book : borrowCard.getBooks()) {
            bookDAO.updateQuantityBook(book.getId(), book.getQuantity() - 1);
            borrowCardDAO.insertBookBorrowCard(borrowCard.getId(), book.getId());
        }
    }


    public List<BorrowCardDTO> getAllBorrowCardByState (BorrowState borrowState) {
        return borrowCardDAO.getBorrowCardByState(borrowState);
    }


    public void loadStateAllBorrowCard () {
        borrowCardDAO.loadStateAllBorrowCard();
    }








}
