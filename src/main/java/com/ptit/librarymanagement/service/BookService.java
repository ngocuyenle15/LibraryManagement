package com.ptit.librarymanagement.service;

import com.ptit.librarymanagement.common.paging.Pageable;
import com.ptit.librarymanagement.common.transactionmanager.Transactional;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.dao.*;
import com.ptit.librarymanagement.dto.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class BookService {
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final ShelfDAO shelfDAO;
    private final CategoryDAO categoryDAO;
    private final PublisherDAO publisherDAO;
    private final BorrowCardDAO borrowCardDAO;
    public BookService (Connection connection) {
        this.bookDAO = new BookDAO(connection);
        this.shelfDAO = new ShelfDAO(connection);
        this.authorDAO = new AuthorDAO(connection);
        this.categoryDAO = new CategoryDAO(connection);
        this.publisherDAO = new PublisherDAO(connection);
        this.borrowCardDAO = new BorrowCardDAO(connection);
    }

    public BookService (DAOFactory daoFactory) {
        this.bookDAO = daoFactory.getBookDAO();
        this.shelfDAO = daoFactory.getShelfDAO();
        this.authorDAO = daoFactory.getAuthorDAO();
        this.categoryDAO = daoFactory.getCategoryDAO();
        this.publisherDAO = daoFactory.getPublisherDAO();
        this.borrowCardDAO = daoFactory.getBorrowCardDAO();
    }


    public List<BookDTO> searchBookByTitle (String title, Boolean delete) {
        List<BookDTO> result = bookDAO.searchBookByTitle(title, delete);
        for (var book : result) {
            book.setPublisher(publisherDAO.getPublisherByBook(book).orElseThrow(RuntimeException::new));
            book.setAuthors(authorDAO.getAuthorOfBook(book));
            book.setShelf(shelfDAO.getShelfOfBook(book).orElseThrow(RuntimeException::new));
            book.setCategories(categoryDAO.getCategoryOfBook(book));
        }
        return result;
    }


    public BookDTO getBookById (Integer bookId) {
        BookDTO bookDTO = bookDAO.getBookById(bookId).orElseThrow(() -> new RuntimeException("Không có giá trị!"));
        bookDTO.setPublisher(publisherDAO.getPublisherByBook(bookDTO).orElseThrow(() -> new RuntimeException("Không có giá trị!")));
        bookDTO.setAuthors(authorDAO.getAuthorOfBook(bookDTO));
        bookDTO.setShelf(shelfDAO.getShelfOfBook(bookDTO).orElseThrow(() -> new RuntimeException("Không có giá trị!")));
        bookDTO.setCategories(categoryDAO.getCategoryOfBook(bookDTO));
        return bookDTO;
    }

    public BookDTO getBookById (Integer bookId, Boolean delete) {
        BookDTO bookDTO = bookDAO.getBookById(bookId, delete).orElseThrow(() -> new RuntimeException("Không có giá trị!"));
        bookDTO.setPublisher(publisherDAO.getPublisherByBook(bookDTO).orElseThrow(() -> new RuntimeException("Không có giá trị!")));
        bookDTO.setAuthors(authorDAO.getAuthorOfBook(bookDTO));
        bookDTO.setShelf(shelfDAO.getShelfOfBook(bookDTO).orElseThrow(() -> new RuntimeException("Không có giá trị!")));
        bookDTO.setCategories(categoryDAO.getCategoryOfBook(bookDTO));
        return bookDTO;
    }

    public List<BookDTO> getAllBook (int size, int offset, boolean delete) {
        List<BookDTO> list = bookDAO.getAllBook(size, offset, delete);
        for (var book : list) {
            book.setPublisher(publisherDAO.getPublisherByBook(book).orElseThrow(() -> new RuntimeException()));
            book.setAuthors(authorDAO.getAuthorOfBook(book));
            book.setShelf(shelfDAO.getShelfOfBook(book).orElseThrow(() -> new RuntimeException()));
            book.setCategories(categoryDAO.getCategoryOfBook(book));
        }
        return list;
    }


    public List<BookDTO> getAllBook (boolean delete) {
        List<BookDTO> list = bookDAO.getAllBook(delete);
        for (var book : list) {
            book.setPublisher(publisherDAO.getPublisherByBook(book).orElseThrow(() -> new RuntimeException()));
            book.setAuthors(authorDAO.getAuthorOfBook(book));
            book.setShelf(shelfDAO.getShelfOfBook(book).orElseThrow(() -> new RuntimeException()));
            book.setCategories(categoryDAO.getCategoryOfBook(book));
        }
        return list;
    }

    public List<BookDTO> getBookCanBorrow () {
        List<BookDTO> list = bookDAO.getBookCanBorrow();
        for (var book : list) {
            book.setPublisher(publisherDAO.getPublisherByBook(book).orElseThrow(() -> new RuntimeException()));
            book.setAuthors(authorDAO.getAuthorOfBook(book));
            book.setShelf(shelfDAO.getShelfOfBook(book).orElseThrow(() -> new RuntimeException()));
            book.setCategories(categoryDAO.getCategoryOfBook(book));
        }
        return list;
    }

    @Transactional /* todo: view phải đảm bảo tất cả obj đưa xuống đều có trong db */
    public void insertBook (BookDTO bookDTO) {
        Integer bookId = bookDAO.insertBook(bookDTO);
        bookDTO.setId(bookId);
        for (var author : bookDTO.getAuthors()) {
            authorDAO.insertAuthorBook(author, bookDTO);
        }
        for (var cate : bookDTO.getCategories()) {
            categoryDAO.insertCategoryBook(cate, bookDTO);
        }
    }

    public List<BookDTO> searchBookByName (String title, int size, int offset, boolean delete) {
        List<BookDTO> list = bookDAO.searchBookByTitle(title ,size, offset, delete);
        for (var book : list) {
            book.setPublisher(publisherDAO.getPublisherByBook(book).orElseThrow(() -> new RuntimeException()));
            book.setAuthors(authorDAO.getAuthorOfBook(book));
            book.setShelf(shelfDAO.getShelfOfBook(book).orElseThrow(() -> new RuntimeException()));
            book.setCategories(categoryDAO.getCategoryOfBook(book));
        }
        return list;
    }

    public void softDelete (BookDTO bookDTO, Boolean delete) {
        bookDAO.softDelete(bookDTO, delete);
    }
    @Transactional
    public void deletePermanently (BookDTO bookDTO) {
        categoryDAO.deleteBookInCategory(bookDTO);
        authorDAO.deleteBookOfAuthor(bookDTO);
        borrowCardDAO.deleteBookInBorrowCard(bookDTO);
        bookDAO.deletePermanently(bookDTO);
    }

    @Transactional
    public void updateBook (BookDTO bookDTO) {
        // update table book - cover nang dung ham rieng
        bookDAO.updateBook(bookDTO);
        if (bookDTO.getCover() != null) {
            bookDAO.updateBookCover(bookDTO);
        }
        authorDAO.updateBookAuthor(bookDTO);
        categoryDAO.updateBookCategory(bookDTO);
    }

    public void updateCover (BookDTO bookDTO)  {
        bookDAO.updateBookCover(bookDTO);
    }


    public List<BookDTO> loadAllBook (Pageable pageable, Boolean delete) {
        List<BookDTO> result = bookDAO.loadBook(pageable, delete);
        if (result.isEmpty()) return result;
        Map<Integer, List<CategoryDTO>> categoryLoader = categoryDAO.loadCategoryOfBook(result);
        Map<Integer, List<AuthorDTO>> authorLoader = authorDAO.loadAllAuthorOfBook(result);
        for (var book : result) {
            book.setCategories(categoryLoader.get(book.getId()));
            book.setAuthors(authorLoader.get(book.getId()));
        }
        return result;
    }

    public Pageable createPageableLoadAllBook(int pageSize) {
        return new Pageable(bookDAO.getTotalBook(), pageSize);
    }

    public Pageable createPageableSearchBook (int pageSize, BookDTO book, Boolean deleted) {
        return new Pageable(bookDAO.countRecordForSearch(book, deleted), pageSize);
    }

    public List<BookDTO> searchBook (BookDTO bookDTO, Pageable pageable, Boolean delete) {
        List<BookDTO> result = bookDAO.searchBoobByIdOrTitle(bookDTO, pageable, delete);
        if (result.isEmpty()) return result;
        Map<Integer, List<CategoryDTO>> categoryLoader = categoryDAO.loadCategoryOfBook(result);
        Map<Integer, List<AuthorDTO>> authorLoader = authorDAO.loadAllAuthorOfBook(result);
        for (var book : result) {
            book.setCategories(categoryLoader.get(book.getId()));
            book.setAuthors(authorLoader.get(book.getId()));
        }
        return result;
    }

    public List<BookDTO> searchBook (BookDTO bookDTO, Boolean delete) {
        List<BookDTO> result = bookDAO.searchBoobByIdOrTitle(bookDTO, delete);
        if (result.isEmpty()) return result;
        Map<Integer, List<CategoryDTO>> categoryLoader = categoryDAO.loadCategoryOfBook(result);
        Map<Integer, List<AuthorDTO>> authorLoader = authorDAO.loadAllAuthorOfBook(result);
        for (var book : result) {
            book.setCategories(categoryLoader.get(book.getId()));
            book.setAuthors(authorLoader.get(book.getId()));
        }
        return result;
    }

    public List<BookDTO> getBookByReader (Integer id) {
        return bookDAO.getBookByReader(id);
    }

}
