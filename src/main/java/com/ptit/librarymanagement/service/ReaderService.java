package com.ptit.librarymanagement.service;

import com.ptit.librarymanagement.common.enums.BorrowState;
import com.ptit.librarymanagement.common.transactionmanager.Transactional;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.dao.BookDAO;
import com.ptit.librarymanagement.dao.BorrowCardDAO;
import com.ptit.librarymanagement.dao.ReaderDAO;
import com.ptit.librarymanagement.dto.BorrowCardDTO;
import com.ptit.librarymanagement.dto.ReaderDTO;

import java.sql.Connection;
import java.util.List;


public class ReaderService {
    private final ReaderDAO readerDAO;
    private final BookDAO bookDAO;
    private final BorrowCardDAO borrowCardDAO;


    public void removeReader (ReaderDTO readerDTO) {
        List<BorrowCardDTO> borrowCardByReader = borrowCardDAO.getBorrowCardByReader(readerDTO, BorrowState.RETURNED);
        if (!borrowCardByReader.isEmpty())
            throw new RuntimeException("Không thể xóa độc giả đang mượn sách");
        readerDAO.deleteReader(readerDTO);
    }

    public ReaderService (Connection connection) {
        this.readerDAO = new ReaderDAO(connection);
        this.bookDAO = new BookDAO(connection);
        this.borrowCardDAO = new BorrowCardDAO(connection);
    }

    public ReaderService (DAOFactory daoFactory) {
        this.readerDAO = daoFactory.getReaderDAO();
        this.bookDAO = daoFactory.getBookDAO();
        this.borrowCardDAO = daoFactory.getBorrowCardDAO();
    }

    public List<ReaderDTO> getAllReader () {
        List<ReaderDTO> list = readerDAO.getAllReader();
        for (var reader : list) {
            List<BorrowCardDTO> borrowCardByReader = borrowCardDAO.getBorrowCardByReader(reader);
            for (var bor : borrowCardByReader) {
                bor.setBooks(bookDAO.getBookByBorrowCard(bor));
            }
            reader.setBorrowCards(borrowCardByReader);
        }
        return list;
    }


    public List<ReaderDTO> getReaderByName (String name) {
        List<ReaderDTO> list = readerDAO.getReaderByName(name);
        for (var reader : list) {
            List<BorrowCardDTO> borrowCardByReader = borrowCardDAO.getBorrowCardByReader(reader);
            for (var bor : borrowCardByReader) {
                bor.setBooks(bookDAO.getBookByBorrowCard(bor));
            }
            reader.setBorrowCards(borrowCardByReader);
        }
        return list;
    }

    public Integer insertReader (ReaderDTO reader) {
        return readerDAO.insertReader(reader);
    }
    public void updateReader (ReaderDTO readerDTO) {
        readerDAO.updateReader(readerDTO);
    }


    @Transactional
    public void deleteReader (BorrowCardDTO borrowCardDTO) {

//        borrowCardDAO.deleteBorrowCardOfReader(borrowCardDTO);
//        readerDAO.deleteReader(borrowCardDTO);

    }

    public ReaderDTO getReaderById (Integer id) {
        return readerDAO.getReaderById(id).orElseThrow(() -> new RuntimeException("Ko tim thay"));

    }

    public List<ReaderDTO> searchReader (Integer id, String name) {
        return readerDAO.getReaderByIdOrName(id, name);
    }




}
