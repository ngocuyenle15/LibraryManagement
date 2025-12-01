package com.ptit.librarymanagement.dao;

import com.ptit.librarymanagement.common.paging.Pageable;
import com.ptit.librarymanagement.model.dto.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class BookDAO {
    private final Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    public Optional<BookDTO> getBookById (Integer id) {
        try (PreparedStatement statement = connection
                .prepareStatement("select * from book where id = ? and `delete` = false")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    BookDTO bookDTO = BookDTO.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .cover(rs.getBytes("cover"))
                            .description(rs.getString("description"))
                            .quantity(rs.getInt("quantity"))
                            .delete(rs.getBoolean("delete"))
                            .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                            .build();
                    return Optional.of(bookDTO);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();

    }

    public Optional<BookDTO> getBookById (Integer id, boolean delete) {
        try (PreparedStatement statement = connection
                .prepareStatement("select * from book where id = ? and `delete` = ?")) {
            statement.setInt(1, id);
            statement.setBoolean(2, delete);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    BookDTO bookDTO = BookDTO.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .cover(rs.getBytes("cover"))
                            .description(rs.getString("description"))
                            .quantity(rs.getInt("quantity"))
                            .delete(rs.getBoolean("delete"))
                            .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                            .build();
                    return Optional.of(bookDTO);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();

    }

    public List<BookDTO> getBookOfShelf (Integer id) {
        List<BookDTO> result = new ArrayList<>();
        String sql = "select * from book where shelf_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(BookDTO.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .cover(rs.getBytes("cover"))
                            .description(rs.getString("description"))
                            .quantity(rs.getInt("quantity"))
                            .delete(rs.getBoolean("delete"))
                            .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                            .shelf(ShelfDTO.builder().id(rs.getInt("shelf_id")).build())
                            .build());

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    public List<BookDTO> getAllBook (int size, int offset, boolean delete) {
        ArrayList<BookDTO> lists = new ArrayList<>();
        try (PreparedStatement statement = connection
                .prepareStatement("select * from book where `delete` = ? limit ? offset ? ")) {
            statement.setBoolean(3, delete);
            statement.setInt(1, size);
            statement.setInt(2, offset);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    lists.add(BookDTO.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .cover(rs.getBytes("cover"))
                            .description(rs.getString("description"))
                            .quantity(rs.getInt("quantity"))
                            .delete(rs.getBoolean("delete"))
                            .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                            .shelf(ShelfDTO.builder().id(rs.getInt("shelf_id")).build())
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lists;
    }

    public List<BookDTO> getAllBook (boolean delete) {
        ArrayList<BookDTO> lists = new ArrayList<>();
        try (PreparedStatement statement = connection
                .prepareStatement("select * from book where `delete` = ?")) {
            statement.setBoolean(1, delete);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    lists.add(BookDTO.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .cover(rs.getBytes("cover"))
                            .description(rs.getString("description"))
                            .quantity(rs.getInt("quantity"))
                            .delete(rs.getBoolean("delete"))
                            .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                            .shelf(ShelfDTO.builder().id(rs.getInt("shelf_id")).build())
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lists;
    }

    public List<BookDTO> getBookCanBorrow () {
        ArrayList<BookDTO> lists = new ArrayList<>();
        try (PreparedStatement statement = connection
                .prepareStatement("select * from book where `delete` = false and quantity > 0")) {
//            statement.setBoolean(1, delete);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    lists.add(BookDTO.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .cover(rs.getBytes("cover"))
                            .description(rs.getString("description"))
                            .quantity(rs.getInt("quantity"))
                            .delete(rs.getBoolean("delete"))
                            .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                            .shelf(ShelfDTO.builder().id(rs.getInt("shelf_id")).build())
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lists;
    }

    public List<BookDTO> searchBookByTitle(String title, int size, int offset, boolean delete) {
        List<BookDTO> lists = new ArrayList<>();
        try (PreparedStatement statement = connection
                .prepareStatement("select * from `book` where `title` like concat('%', ?, '%') and `delete` = ? limit ? offset ?")) {
            statement.setString(1, title);
            statement.setBoolean(4, delete);
            statement.setInt(2, size);
            statement.setInt(3, offset);
            try (var rs = statement.executeQuery()){
                while (rs.next()) {
                    lists.add(BookDTO.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .cover(rs.getBytes("cover"))
                            .description(rs.getString("description"))
                            .quantity(rs.getInt("quantity"))
                            .delete(rs.getBoolean("delete"))
                            .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                            .shelf(ShelfDTO.builder().id(rs.getInt("shelf_id")).build())
                            .build());
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lists;
    }

    public List<BookDTO> searchBookByTitle(String title, boolean delete) {
        List<BookDTO> lists = new ArrayList<>();
        try (PreparedStatement statement = connection
                .prepareStatement("select * from `book` where `title` like concat('%', ?, '%') and `delete` = ?")) {
            statement.setString(1, title);
            statement.setBoolean(2, delete);
            try (var rs = statement.executeQuery()){
                while (rs.next()) {
                    lists.add(BookDTO.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .cover(rs.getBytes("cover"))
                            .description(rs.getString("description"))
                            .quantity(rs.getInt("quantity"))
                            .delete(rs.getBoolean("delete"))
                            .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                            .shelf(ShelfDTO.builder().id(rs.getInt("shelf_id")).build())
                            .build());
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lists;
    }

    public Integer insertBook (BookDTO book) {
        Integer index = null;
        try (PreparedStatement statement = connection
                .prepareStatement("insert into book (title, cover, `description`, quantity, `delete`, publisher_id, shelf_id) values (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, book.getTitle());
            statement.setBytes(2, book.getCover());
            statement.setString(3, book.getDescription());
            statement.setInt(4, book.getQuantity());
            statement.setBoolean(5, false);
            statement.setInt(6, book.getPublisher().getId());
            statement.setInt(7, book.getShelf().getId());
            int i = statement.executeUpdate();
            if (i > 0) {
                try (var rs = statement.getGeneratedKeys()) {
                    if (rs.next())
                        index = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return index;
    }



    public void softDelete (BookDTO bookDTO, Boolean delete) {
        try (PreparedStatement statement = connection
                .prepareStatement("update book set `delete` = ? where id = ?")) {
            bookDTO.setDelete(delete);
            statement.setInt(2, bookDTO.getId());
            statement.setBoolean(1, delete);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deletePermanently (BookDTO bookDTO) {
        try (PreparedStatement statement = connection
                .prepareStatement("delete from `book` where id = ?")) {
            statement.setInt(1, bookDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateBookCover (BookDTO bookDTO) {
        try (PreparedStatement statement = connection
                .prepareStatement("update `book` set `cover` = ? where id = ?")) {
            statement.setBytes(1, bookDTO.getCover());
            statement.setInt(2, bookDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void updateQuantityBook (Integer id, Integer quantity) {
        String sql = "update book set quantity = ? where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, quantity);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateBook (BookDTO book) {
        try (PreparedStatement statement = connection
                .prepareStatement("update book set title = ?, description = ?, quantity = ?, publisher_id = ?, shelf_id = ? where id = ?")) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getDescription());
            statement.setInt(3, book.getQuantity());
            statement.setInt(4, book.getPublisher().getId());
            statement.setInt(5, book.getShelf().getId());
            statement.setInt(6, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookDTO> getBookByBorrowCard (BorrowCardDTO borrowCardDTO) {
        List<BookDTO> list = new ArrayList<>();
        String sql = "select * from book_borrow_card bb join book b on bb.book_id = b.id where bb.borrow_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, borrowCardDTO.getId());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(BookDTO.builder()
                            .id(rs.getInt("b.id"))
                            .title(rs.getString("b.title"))
                            .cover(rs.getBytes("b.cover"))
                            .description(rs.getString("b.description"))
                            .quantity(rs.getInt("b.quantity"))
                            .delete(rs.getBoolean("b.delete"))
                            .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                            .shelf(ShelfDTO.builder().id(rs.getInt("shelf_id")).build())
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }

    public List<BookDTO> getBookOfCategory (CategoryDTO category) {
        List<BookDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from category_book cb join book b on cb.book_id = b.id where  cb.category_id = ?")) {
            statement.setInt(1, category.getId());
            try (ResultSet rs = statement.executeQuery()) {
               while (rs.next()) {
                   list.add(BookDTO.builder()
                           .id(rs.getInt("id"))
                           .title(rs.getString("title"))
                           .cover(rs.getBytes("cover"))
                           .description(rs.getString("description"))
                           .quantity(rs.getInt("quantity"))
                           .delete(rs.getBoolean("delete"))
                           .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                           .shelf(ShelfDTO.builder().id(rs.getInt("shelf_id")).build())
                           .build());
               }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }



    public List<BookDTO> getBookOfAuthor (Integer authorId) {
        List<BookDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from book_author au join book b on au.book_id = b.id where au.author_id = ?")){
            statement.setInt(1, authorId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(BookDTO.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .cover(rs.getBytes("cover"))
                            .description(rs.getString("description"))
                            .quantity(rs.getInt("quantity"))
                            .delete(rs.getBoolean("delete"))
                            .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                            .shelf(ShelfDTO.builder().id(rs.getInt("shelf_id")).build())
                            .build());
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }





    public List<BookDTO> getBookOfPublisher (Integer id) {
        List<BookDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from book b join publisher p on b.publisher_id = p.id where p.id = ?")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
               while (rs.next()) {
                   list.add(BookDTO.builder()
                           .id(rs.getInt("id"))
                           .title(rs.getString("title"))
                           .cover(rs.getBytes("cover"))
                           .description(rs.getString("description"))
                           .quantity(rs.getInt("quantity"))
                           .delete(rs.getBoolean("delete"))
                           .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                           .shelf(ShelfDTO.builder().id(rs.getInt("shelf_id")).build())
                           .build());
               }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public List<BookDTO> loadBook (Pageable pageable, Boolean delete) {
        List<BookDTO> result = new ArrayList<>();
        String sql = "select * from book \n" +
                "join publisher on book.publisher_id = publisher.id\n" +
                "join shelf on book.shelf_id = shelf.id \n" +
                "where book.delete = ? limit ? offset ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, delete);
            statement.setInt(2, pageable.getPageSize());
            statement.setInt(3, (pageable.getCurrentPage() - 1) * pageable.getPageSize());

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(
                            BookDTO.builder()
                                    .id(rs.getInt("book.id"))
                                    .title(rs.getString("book.title"))
                                    .cover(rs.getBytes("book.cover"))
                                    .description(rs.getString("book.description"))
                                    .delete(rs.getBoolean("book.delete"))
                                    .quantity(rs.getInt("book.quantity"))
                                    .publisher(PublisherDTO.builder()
                                            .id(rs.getInt("publisher.id"))
                                            .name(rs.getString("publisher.name"))
                                            .location(rs.getString("publisher.location"))
                                            .phone(rs.getString("publisher.phone"))
                                            .email(rs.getString("publisher.phone"))
                                            .build())
                                    .shelf(ShelfDTO.builder()
                                            .id(rs.getInt("shelf.id"))
                                            .shelfName(rs.getString("shelf.shelf_name"))
                                            .location(rs.getString("shelf.location"))
                                            .build())
                                    .authors(new ArrayList<>())
                                    .categories(new ArrayList<>())
                                    .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return result;

    }

    public int getTotalBook () {
        String sql = "select count(*) from book;";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery())
        {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;

    }

    public int countRecordForSearch (BookDTO bookDTO, Boolean deleted) {
        String  sql = "select count(*) from book join publisher on book.publisher_id = publisher.id \n" +
                "join shelf on book.shelf_id = shelf.id\n" +
                "where (book.id = ? or ? is null)\n" +
                "and (book.title like concat('%',?,'%') or ? is null)\n" +
                "and book.`delete` = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, bookDTO.getId());
            statement.setObject(2, bookDTO.getId());
            statement.setObject(3, bookDTO.getTitle());
            statement.setObject(4, bookDTO.getTitle());
            statement.setObject(5, deleted);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;

    }

    public List<BookDTO> searchBoobByIdOrTitle(BookDTO book , Pageable pageable, Boolean delete) {
        List<BookDTO> result = new ArrayList<>();
        String sql = "select * from book\n" +
                "join publisher on book.publisher_id = publisher.id\n" +
                " join shelf on book.shelf_id = shelf.id\n" +
                "where (book.id = ? or ? is null) and (book.title like concat('%',?,'%') or ? is null) and book.`delete` = ?\n" +
                "limit ? offset ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, book.getId());
            statement.setObject(2, book.getId());
            statement.setObject(3, book.getTitle());
            statement.setObject(4, book.getTitle());
            statement.setBoolean(5, delete);
            statement.setInt(6, pageable.getPageSize());
            statement.setInt(7, (pageable.getCurrentPage() - 1) * pageable.getPageSize());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(
                            BookDTO.builder()
                                    .id(rs.getInt("book.id"))
                                    .title(rs.getString("book.title"))
                                    .cover(rs.getBytes("book.cover"))
                                    .description(rs.getString("book.description"))
                                    .delete(rs.getBoolean("book.delete"))
                                    .quantity(rs.getInt("book.quantity"))
                                    .publisher(PublisherDTO.builder()
                                            .id(rs.getInt("publisher.id"))
                                            .name(rs.getString("publisher.name"))
                                            .location(rs.getString("publisher.location"))
                                            .phone(rs.getString("publisher.phone"))
                                            .email(rs.getString("publisher.phone"))
                                            .build())
                                    .shelf(ShelfDTO.builder()
                                            .id(rs.getInt("shelf.id"))
                                            .shelfName(rs.getString("shelf.shelf_name"))
                                            .location(rs.getString("shelf.location"))
                                            .build())
                                    .authors(new ArrayList<>())
                                    .categories(new ArrayList<>())
                                    .build());
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    public List<BookDTO> searchBoobByIdOrTitle(BookDTO book , Boolean delete) {
        List<BookDTO> result = new ArrayList<>();
        String sql = "select * from book\n" +
                "join publisher on book.publisher_id = publisher.id\n" +
                " join shelf on book.shelf_id = shelf.id\n" +
                "where (book.id = ? or ? is null) and (book.title like concat('%',?,'%') or ? is null) and book.`delete` = ?\n";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, book.getId() == null ? null : book.getId() );
            statement.setObject(2, book.getId() == null ? null : book.getId());
            statement.setObject(3, book.getTitle() == null ? null : book.getTitle());
            statement.setObject(4, book.getTitle() == null ? null : book.getTitle());
            statement.setBoolean(5, delete);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(
                            BookDTO.builder()
                                    .id(rs.getInt("book.id"))
                                    .title(rs.getString("book.title"))
                                    .cover(rs.getBytes("book.cover"))
                                    .description(rs.getString("book.description"))
                                    .delete(rs.getBoolean("book.delete"))
                                    .quantity(rs.getInt("book.quantity"))
                                    .publisher(PublisherDTO.builder()
                                            .id(rs.getInt("publisher.id"))
                                            .name(rs.getString("publisher.name"))
                                            .location(rs.getString("publisher.location"))
                                            .phone(rs.getString("publisher.phone"))
                                            .email(rs.getString("publisher.phone"))
                                            .build())
                                    .shelf(ShelfDTO.builder()
                                            .id(rs.getInt("shelf.id"))
                                            .shelfName(rs.getString("shelf.shelf_name"))
                                            .location(rs.getString("shelf.location"))
                                            .build())
                                    .authors(new ArrayList<>())
                                    .categories(new ArrayList<>())
                                    .build());
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }





    public List<BookDTO> getBookByReader (Integer id) {
        List<BookDTO> result = new ArrayList<>();
        String sql = "select * from borrow_card\n" +
                "join reader on borrow_card.reader_id = reader.id\n" +
                "join book_borrow_card on borrow_card.id = book_borrow_card.borrow_id\n" +
                "join book on book_borrow_card.book_id = book.id\n" +
                "where borrow_card.state != 'Đã trả' and reader.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(
                            BookDTO.builder()
                                    .id(rs.getInt("book.id"))
                                    .title(rs.getString("book.title"))
                                    .cover(rs.getBytes("book.cover"))
                                    .description(rs.getString("book.description"))
                                    .quantity(rs.getInt("book.quantity"))
                                    .delete(rs.getBoolean("book.delete"))
                                    .publisher(PublisherDTO.builder().id(rs.getInt("book.publisher_id")).build())
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
