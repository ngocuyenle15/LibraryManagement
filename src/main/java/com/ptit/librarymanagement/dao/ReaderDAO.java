package com.ptit.librarymanagement.dao;

import com.ptit.librarymanagement.model.dto.ReaderDTO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ReaderDAO {
    private final Connection connection;

    public ReaderDAO(Connection connection) {
        this.connection = connection;
    }

    // CRUD reader

    public Optional<ReaderDTO> getReaderById (Integer id) {
        String sql = "select * from reader where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(
                            ReaderDTO.builder()
                                    .id(rs.getInt("id"))
                                    .firstName(rs.getString("first_name"))
                                    .lastName(rs.getString("last_name"))
                                    .gender(rs.getBoolean("gender"))
                                    .location(rs.getString("location"))
                                    .phone(rs.getString("phone"))
                                    .email(rs.getString("email"))
                                    .note(rs.getString("note"))
                                    .birth(rs.getDate("birth"))
                                    .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List<ReaderDTO> getAllReader () {
        List<ReaderDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from reader")) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(ReaderDTO.builder()
                            .id(rs.getInt("id"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .gender(rs.getBoolean("gender"))
                            .location(rs.getString("location"))
                            .phone(rs.getString("phone"))
                            .email(rs.getString("email"))
                            .note(rs.getString("note"))
                            .birth(rs.getDate("birth"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<ReaderDTO> getReaderByName (String name) {
        List<ReaderDTO> list = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement("select * from reader where concat(first_name, ' ', last_name) like concat('%', ?,'%')")) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(ReaderDTO.builder()
                            .id(rs.getInt("id"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .gender(rs.getBoolean("gender"))
                            .location(rs.getString("location"))
                            .phone(rs.getString("phone"))
                            .email(rs.getString("email"))
                            .note(rs.getString("note"))
                            .birth(rs.getDate("birth"))
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Integer insertReader (ReaderDTO reader) {
        Integer key = null;
        try (PreparedStatement statement = connection.
                prepareStatement("insert into reader (first_name, last_name, gender, location, phone, email, note, birth) value (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);) {

            statement.setString(1, reader.getFirstName());
            statement.setString(2, reader.getLastName());
            statement.setBoolean(3, reader.getGender());
            statement.setString(4, reader.getLocation());
            statement.setString(5, reader.getPhone());
            statement.setString(6, reader.getEmail());
            statement.setString(7, reader.getNote());
            statement.setDate(8, reader.getBirth());
            int i = statement.executeUpdate();
            if (i > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    key = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    public void updateReader (ReaderDTO reader) {
        try (PreparedStatement statement = connection.
                prepareStatement("update reader set first_name = ? , last_name = ? , gender = ?, location = ?, phone = ? , email = ? , note = ? , birth = ? where id = ?")) {
                statement.setString(1, reader.getFirstName());
                statement.setString(2, reader.getLastName());
                statement.setBoolean(3, reader.getGender());
                statement.setString(4, reader.getLocation());
                statement.setString(5, reader.getPhone());
                statement.setString(6, reader.getEmail());
                statement.setString(7, reader.getNote());
                statement.setDate(8, reader.getBirth());
                statement.setInt(9, reader.getId());
                statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteReader (ReaderDTO reader) {
        try (PreparedStatement statement = connection.prepareStatement("delete from reader where id = ?")) {
            statement.setInt(1, reader.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<ReaderDTO> getReaderByIdOrName (Integer id, String name) {
        List<ReaderDTO> result = new ArrayList<>();
        String sql = "select * from reader where (id = ? or ? is null) and (concat(first_name,' ', last_name) like concat('%', ?, '%') or ? is null)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            statement.setObject(2, id);
            statement.setObject(3, name);
            statement.setObject(4, name);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(ReaderDTO.builder()
                            .id(rs.getInt("id"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .gender(rs.getBoolean("gender"))
                            .location(rs.getString("location"))
                            .phone(rs.getString("phone"))
                            .email(rs.getString("email"))
                            .note(rs.getString("note"))
                            .birth(rs.getDate("birth"))
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return result;
    }

}
