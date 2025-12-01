package com.ptit.librarymanagement.service;

import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.dao.BookDAO;
import com.ptit.librarymanagement.dao.CategoryDAO;
import com.ptit.librarymanagement.model.dto.BookDTO;
import com.ptit.librarymanagement.model.dto.CategoryDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CategoryService {
    private final CategoryDAO categoryDAO;
    private final BookDAO bookDAO;
    public CategoryService (Connection connection) {
        this.categoryDAO = new CategoryDAO(connection);
        this.bookDAO = new BookDAO(connection);
    }

    public CategoryService (DAOFactory daoFactory) {
        this.categoryDAO = daoFactory.getCategoryDAO();
        this.bookDAO = daoFactory.getBookDAO();
    }

    public CategoryDTO getCategoryById (Integer id) {
        Optional<CategoryDTO> cagCategoryById = categoryDAO.getCagCategoryById(id);
        CategoryDTO categoryDTO = cagCategoryById.orElseThrow();
        return categoryDTO;


    }

    public List<CategoryDTO> getAllCategory () {
        return categoryDAO.getAllCategory();
    }

    public void insertCategory (CategoryDTO categoryDTO) {
        categoryDAO.insertCategory(categoryDTO);
    }


    public void updateCategory (CategoryDTO categoryDTO) {
        categoryDAO.updateCategory(categoryDTO);
    }

    public void deleteCategory (CategoryDTO category) {
        categoryDAO.deleteCategory(category);
    }

    public List<BookDTO> getBookOfCategory (CategoryDTO category) {
        return bookDAO.getBookOfCategory(category);
    }

    public CategoryDTO findCategoryByName (String name) {
        return categoryDAO.getCategoryByName(name).orElseThrow(() -> new RuntimeException("Danh muc khong ton tai"));
    }

    public List<CategoryDTO> getCategoryByIdOrName (CategoryDTO category) {
        return categoryDAO.getCategoryByIdOrName(category);
    }



}
