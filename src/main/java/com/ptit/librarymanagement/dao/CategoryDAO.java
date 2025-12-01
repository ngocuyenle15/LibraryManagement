package com.ptit.librarymanagement.dao;

import com.ptit.librarymanagement.model.dto.BookDTO;
import com.ptit.librarymanagement.model.dto.CategoryDTO;


import java.sql.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CategoryDAO {
    private final Connection connection;

    public CategoryDAO(Connection connection) {
        this.connection = connection;
    }

    public Optional<CategoryDTO> getCagCategoryById (Integer id) {
        try (PreparedStatement statement = connection.prepareStatement("select * from category where id = ?")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(CategoryDTO.builder()
                                    .id(rs.getInt("id"))
                                    .name(rs.getString("name"))
                                    .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List<CategoryDTO> getAllCategory () {
        List<CategoryDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select  * from category")) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(CategoryDTO.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Integer insertCategory (CategoryDTO category) {
        Integer index = null;
        try (PreparedStatement statement = connection.prepareStatement("insert into category set name = ?", Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, category.getName());
            int i = statement.executeUpdate();
            if (i > 0) {
                try (var rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        index = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return index;

    }



    public List<CategoryDTO> getCategoryOfBook (BookDTO bookDTO) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        try (PreparedStatement statement = connection
                .prepareStatement("select * from category_book cb join category c on cb.category_id = c.id where cb.book_id = ?")) {
            statement.setInt(1, bookDTO.getId());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    categoryDTOList.add(
                            CategoryDTO.builder()
                                    .id(rs.getInt("c.id"))
                                    .name(rs.getString("c.name"))
                                    .build()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoryDTOList;
    }

    public Integer insertCategoryBook (CategoryDTO category, BookDTO book) {
        Integer index = null;
        try (PreparedStatement statement = connection
                .prepareStatement("insert into category_book (category_id, book_id) values (?, ?)", Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, category.getId());
            statement.setInt(2, book.getId());
            int i = statement.executeUpdate();
            if (i > 0) {
                try (var rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        index = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return index;

    }

    public void deleteBookInCategory (BookDTO bookDTO) {
        try (PreparedStatement statement = connection.prepareStatement("delete from `category_book` where book_id = ?")) {
            statement.setInt(1, bookDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void updateBookCategory (BookDTO bookDTO) {
        List<CategoryDTO> categoryNew = bookDTO.getCategories();
        List<CategoryDTO> categoryOld = this.getCategoryOfBook(bookDTO);
        Set<Integer> categoryIdOld = categoryOld.stream().map(category -> category.getId()).collect(Collectors.toSet());
        for (var category : categoryNew) {
            if (categoryIdOld.contains(category.getId()))
                categoryIdOld.remove(category.getId());
            else
                this.insertCategoryBook(category, bookDTO);
        }
        try (PreparedStatement statement = connection.prepareStatement("delete from `category_book` where `book_id` = ? and `category_id` = ?")) {
            for (var categoryId : categoryIdOld) {
                statement.setInt(1, bookDTO.getId());
                statement.setInt(2, categoryId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCategory (CategoryDTO category) {
        try (PreparedStatement statement = connection.prepareStatement("update category set name = ? where  id = ?")) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCategory (CategoryDTO category) {
        try (PreparedStatement statement = connection.prepareStatement("delete from category where id = ?")) {
            statement.setInt(1, category.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public Optional<CategoryDTO> getCategoryByName (String name) {
        try (PreparedStatement statement = connection.prepareStatement("select * from category where name = ?")) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return Optional.of(CategoryDTO.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, List<CategoryDTO>> loadCategoryOfBook (List<BookDTO> books) {
        HashMap<Integer, List<CategoryDTO>> result = new HashMap<>();
        String placeHolder = String.format("(%s)", books.stream().map(book -> "?").collect(Collectors.joining(", ")));
        String sql = "select * from category join category_book on category.id = category_book.category_id where category_book.book_id in ".concat(placeHolder);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < books.size(); i++) {
                statement.setInt(i + 1, books.get(i).getId());
            }
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Integer bookKey = rs.getInt("category_book.book_id");

                    CategoryDTO build = CategoryDTO.builder()
                            .id(rs.getInt("category.id"))
                            .name(rs.getString("category.name"))
                            .build();
                    if (result.containsKey(bookKey)) {
                        result.get(bookKey).add(build);
                    } else {
                        result.put(bookKey,((Function<CategoryDTO, List<CategoryDTO>>) categoryDTO -> {
                            ArrayList<CategoryDTO> categoryDTOS = new ArrayList<>();
                            categoryDTOS.add(categoryDTO);
                            return categoryDTOS;
                        }).apply(build));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;

    }


    public List<CategoryDTO> getCategoryByIdOrName (CategoryDTO category) {
        List<CategoryDTO> result = new ArrayList<>();
        String sql = "select * from category where (`id` = ? or ? is null) and (`name` like concat('%', ?, '%') or ? is null)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, category.getId());
            statement.setObject(2, category.getId());
            statement.setObject(3, category.getName());
            statement.setObject(4, category.getName());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(
                         category.builder()
                              .id(rs.getInt("id"))
                              .name(rs.getString("name"))
                              .build()

                    );
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }






}
