package com.ptit.librarymanagement.dao;

import com.ptit.librarymanagement.model.dto.BookDTO;
import com.ptit.librarymanagement.model.dto.PublisherDTO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublisherDAO {
    private final Connection connection;

    public PublisherDAO(Connection connection) {
        this.connection = connection;
    }

    public Optional<PublisherDTO> getPublisherByBook(BookDTO bookDTO) {

        try (PreparedStatement statement = connection.prepareStatement("select * from publisher where id = ?")) {
            statement.setInt(1, bookDTO.getPublisher().getId());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(PublisherDTO
                            .builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .email(rs.getString("email"))
                            .phone(rs.getString("phone"))
                            .location(rs.getString("location"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

/*    public Optional<PublisherDTO> findByName (String name) {
        return Optional.empty();
    }*/




    public Integer insertPublisher (PublisherDTO publisher) {
        Integer index = null;
        try (PreparedStatement statement = connection.prepareStatement("insert into publisher (name,location,phone, email) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, publisher.getName());
            statement.setString(2, publisher.getLocation());
            statement.setString(3, publisher.getPhone());
            statement.setString(4, publisher.getEmail());
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

    public void updatePublisher (PublisherDTO publisher) {
        try (PreparedStatement statement = connection.prepareStatement("update publisher set name = ?, location = ?, phone = ? , email = ? where id = ?")){
            statement.setString(1, publisher.getName());
            statement.setString(2, publisher.getLocation());
            statement.setString(3, publisher.getPhone());
            statement.setString(4, publisher.getEmail());
            statement.setInt(5, publisher.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePublisher (PublisherDTO publisher) {
        try (PreparedStatement statement = connection.prepareStatement("delete from publisher where id = ?")){
            statement.setInt(1, publisher.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<PublisherDTO> getPublisherById (Integer id) {
        try (PreparedStatement statement = connection.prepareStatement("select * from publisher where id = ?") ){

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(PublisherDTO
                            .builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .email(rs.getString("email"))
                            .phone(rs.getString("phone"))
                            .location(rs.getString("location"))
                            .build());
                }
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List<PublisherDTO> getAllPublisher () {
        List <PublisherDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from publisher")) {
            try (ResultSet rs = statement.executeQuery()) {
               while (rs.next()) {
                   list.add(PublisherDTO
                           .builder()
                           .id(rs.getInt("id"))
                           .name(rs.getString("name"))
                           .email(rs.getString("email"))
                           .phone(rs.getString("phone"))
                           .location(rs.getString("location"))
                           .build());
               }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public List<PublisherDTO> getPublisherByIdOrName (PublisherDTO publisher) {
        List<PublisherDTO> result = new ArrayList<>();
        String sql = "select * from publisher where (id = ? or ? is null) and (name like concat('%', ?, '%') or ? is null)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, publisher.getId());
            statement.setObject(2, publisher.getId());
            statement.setObject(3, publisher.getName());
            statement.setObject(4, publisher.getName());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(PublisherDTO
                            .builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .email(rs.getString("email"))
                            .phone(rs.getString("phone"))
                            .location(rs.getString("location"))
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}
