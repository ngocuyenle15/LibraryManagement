package com.ptit.librarymanagement.dao;

import com.ptit.librarymanagement.model.dto.BookDTO;
import com.ptit.librarymanagement.model.dto.PublisherDTO; // Cần thiết để tạo BookDTO
import com.ptit.librarymanagement.model.dto.ShelfDTO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ShelfDAO {
    private final Connection connection;

    public ShelfDAO(Connection connection) {
        this.connection = connection;
    }

    public void deleteBookInShelf (BookDTO bookDTO) {
        try (PreparedStatement statement = connection.prepareStatement("delete from shelf_book where book_id = ?")) {
            statement.setInt(1, bookDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<ShelfDTO> getShelfOfBook (BookDTO book) {
        try (PreparedStatement statement = connection.prepareStatement("select * from shelf s join book b on s.id = b.shelf_id where b.id = ?")) {
            statement.setInt(1, book.getId());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next())
                    return Optional.of(ShelfDTO.builder()
                            .id(rs.getInt("s.id"))
                            .shelfName(rs.getString("s.shelf_name"))
                            .location(rs.getString("s.location"))
                            .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all shelves",e);
        }
        return Optional.empty();
    }

    public Integer insertShelf (ShelfDTO shelfDTO) {
        Integer index = null;
        try (PreparedStatement statement = connection.prepareStatement("insert into shelf (shelf_name, location) values (?,?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, shelfDTO.getShelfName());
            statement.setString(2, shelfDTO.getLocation());
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
    public Optional<ShelfDTO> getShelfById(Integer id) {
        String sql = "select * from shelf where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(ShelfDTO.builder()
                            .id(rs.getInt("id"))
                            .shelfName(rs.getString("shelf_name"))
                            .location(rs.getString("location"))
                            .books(null)
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting shelf by ID", e);
        }
        return Optional.empty();
    }

    public Optional<ShelfDTO> getShelfByIdWithBooks(int id) {
        // 1. Lấy thông tin cơ bản của Shelf
        Optional<ShelfDTO> shelfOptional = getShelfById(id);

        if (shelfOptional.isPresent()) {
            ShelfDTO shelf = shelfOptional.get();

            // 2. Tự truy vấn sách theo Shelf ID ngay tại ShelfDAO
            List<BookDTO> booksOnShelf = getBooksByShelfId(shelf.getId());

            // 3. Set danh sách sách vào DTO và trả về
            shelf.setBooks(booksOnShelf);
            return Optional.of(shelf);
        }
        return Optional.empty();
    }

    private List<BookDTO> getBooksByShelfId(int shelfId) {
        List<BookDTO> lists = new ArrayList<>();
        // Logic truy vấn sách tương tự như BookDAO, nhưng đặt trong ShelfDAO
        String sql = "select * from book where shelf_id = ? and `delete` = false";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, shelfId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    lists.add(BookDTO.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .cover(rs.getBytes("cover"))
                            .description(rs.getString("description"))
                            .quantity(rs.getInt("quantity"))
                            .delete(rs.getBoolean("delete"))
                            // Bạn phải tạo các DTO con nếu BookDTO yêu cầu
                            .publisher(PublisherDTO.builder().id(rs.getInt("publisher_id")).build())
                            .shelf(ShelfDTO.builder().id(rs.getInt("shelf_id")).build())
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching books by shelf ID within ShelfDAO", e);
        }
        return lists;
    }

    public List<ShelfDTO> getAllShelves() {
        List<ShelfDTO> shelves = new ArrayList<>();
        String sql = "select * from shelf";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                shelves.add(ShelfDTO.builder()
                        .id(rs.getInt("id"))
                        .shelfName(rs.getString("shelf_name"))
                        .location(rs.getString("location"))
                        .books(null)
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all shelves", e);
        }
        return shelves;
    }

    public boolean updateShelf(ShelfDTO shelfDTO) {
        String sql = "update shelf set shelf_name = ?, location = ? where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, shelfDTO.getShelfName());
            statement.setString(2, shelfDTO.getLocation());
            statement.setInt(3, shelfDTO.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating shelf", e);
        }
    }

    public boolean deleteShelf(int id) {
        String sql = "delete from shelf where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting shelf (Check foreign key constraints)", e);
        }
    }


    public List<ShelfDTO> getShelfByIdOrNameOrLocation (ShelfDTO shelfDTO) {
        List<ShelfDTO> result = new ArrayList<>();
        String sql = "select * from shelf where (id = ? or ? is null) and (shelf_name like concat('%', ?, '%') or ? is null) and (location like concat('%', ?, '%') or ? is null)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, shelfDTO.getId());
            statement.setObject(2, shelfDTO.getId());
            statement.setObject(3, shelfDTO.getShelfName());
            statement.setObject(4, shelfDTO.getShelfName());
            statement.setObject(5, shelfDTO.getLocation());
            statement.setObject(6, shelfDTO.getLocation());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(ShelfDTO.builder()
                            .id(rs.getInt("id"))
                            .shelfName(rs.getString("shelf_name"))
                            .location(rs.getString("location"))
                            .books(null)
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}