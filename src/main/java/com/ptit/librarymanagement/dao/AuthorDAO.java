package com.ptit.librarymanagement.dao;

import com.ptit.librarymanagement.model.dto.AuthorDTO;
import com.ptit.librarymanagement.model.dto.BookDTO;



import java.sql.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class AuthorDAO {
    private final Connection connection;

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }

    public List<AuthorDTO> getAllAuthor () {
        List<AuthorDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from author")) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(AuthorDTO
                            .builder()
                            .id(rs.getInt("id"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .gender(rs.getBoolean("gender"))
                            .yearOfBirth(rs.getString("year_of_birth"))
                            .description(rs.getString("description"))
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public List<AuthorDTO> getAuthorOfBook (BookDTO bookDTO) {
        ArrayList<AuthorDTO> authors = new ArrayList<>();
        try (PreparedStatement statement = connection
                .prepareStatement("select * from book_author ba join author a on ba.author_id = a.id where ba.book_id = ?")) {
            statement.setInt(1, bookDTO.getId());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    authors.add(
                            AuthorDTO.builder()
                                    .id(rs.getInt("ba.author_id"))
                                    .firstName(rs.getString("a.first_name"))
                                    .lastName(rs.getString("a.last_name"))
                                    .gender(rs.getBoolean("a.gender"))
                                    .description(rs.getString("a.description"))
                                    .yearOfBirth("a.year_of_birth")
                                    .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }

    public Integer insertAuthor (AuthorDTO author) {
        Integer index = null;
        try (PreparedStatement statement = connection.prepareStatement("insert into author (first_name, last_name, gender, year_of_birth, description) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());
            statement.setBoolean(3, author.getGender());
            statement.setString(4, author.getYearOfBirth());
            statement.setString(5, author.getDescription());
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

    public Integer insertAuthorBook (AuthorDTO author, BookDTO book) {
        Integer index = null;
        try (PreparedStatement statement = connection.prepareStatement("insert into book_author (author_id, book_id) values (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, author.getId());
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

    public void deleteBookOfAuthor (BookDTO bookDTO) {
        try (PreparedStatement statement = connection.prepareStatement("delete from `book_author` where book_id = ?")) {
            statement.setInt(1, bookDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBookAuthor(BookDTO bookDTO) {
        List<AuthorDTO> authorsNew = bookDTO.getAuthors();
        List<AuthorDTO> authorsOld = this.getAuthorOfBook(bookDTO);
        Set<Integer> authorIdOld = authorsOld.stream().map(author -> author.getId()).collect(Collectors.toSet());
        for (var author : authorsNew) {
            if (authorIdOld.contains(author.getId()))
                authorIdOld.remove(author.getId());
            else
                this.insertAuthorBook(author, bookDTO);
        }
        try (PreparedStatement statement = connection
                .prepareStatement("delete from `book_author` where `book_id` = ? and `author_id` = ?")){
            for (var authorId : authorIdOld) {
                statement.setInt(1, bookDTO.getId());
                statement.setInt(2, authorId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAuthor (AuthorDTO author) {
        try (PreparedStatement statement = connection.prepareStatement("delete from author where id = ?")){
            statement.setInt(1, author.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAuthor (AuthorDTO author) {
        try (PreparedStatement statement = connection
                .prepareStatement("update author set first_name = ?, last_name= ?, gender = ?, year_of_birth = ? , description = ? where id = ?")) {
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());
            statement.setBoolean(3, author.getGender());
            statement.setString(4, author.getYearOfBirth());
            statement.setString(5, author.getDescription());
            statement.setInt(6, author.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<AuthorDTO> getAuthorById (Integer id) {
        try (PreparedStatement statement = connection.prepareStatement("select * from author where id = ?")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next())
                    return Optional.of(AuthorDTO.builder()
                            .id(rs.getInt("id"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .gender(rs.getBoolean("gender"))
                            .description(rs.getString("description"))
                            .yearOfBirth(rs.getString("year_of_birth"))
                            .build());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public Map<Integer, List<AuthorDTO>> loadAllAuthorOfBook (List<BookDTO> books) {
        Map<Integer, List<AuthorDTO>> result = new HashMap<>();
        String placeHolder = String.format("(%s)", books.stream().map(book -> "?").collect(Collectors.joining(", ")));
        String sql = "select * from author join book_author on author.id = book_author.author_id where book_author.book_id in ".concat(placeHolder);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < books.size(); i++) {
                statement.setInt(i + 1, books.get(i).getId());
            }
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {


                    Integer bookKey = rs.getInt("book_author.book_id");
                    AuthorDTO author = AuthorDTO.builder()
                            .id(rs.getInt("author.id"))
                            .firstName(rs.getString("author.first_name"))
                            .lastName(rs.getString("author.last_name"))
                            .gender(rs.getBoolean("author.gender"))
                            .yearOfBirth(rs.getString("author.year_of_birth"))
                            .description(rs.getString("author.description"))
                            .build();

                    if (result.containsKey(bookKey)) {
                        result.get(bookKey).add(author);
                    } else {
                        result.put(bookKey,((Function<AuthorDTO, List<AuthorDTO>>) e -> {
                            ArrayList<AuthorDTO> authors = new ArrayList<>();
                            authors.add(author);
                            return authors;
                        }).apply(author));
                    }
                }
            }






        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;


    }

    public List<AuthorDTO> getAuthorByIdOrName (Integer id, String name) {
        List<AuthorDTO> result = new ArrayList<>();
        String sql = "select * from author where (id = ? or ? is null) and (concat(first_name, ' ', last_name) like concat('%', ?, '%')  or ? is null)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            statement.setObject(2, id);
            statement.setObject(3, name);
            statement.setObject(4, name);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(AuthorDTO.builder()
                                        .id(rs.getInt("author.id"))
                                        .firstName(rs.getString("author.first_name"))
                                        .lastName(rs.getString("author.last_name"))
                                        .gender(rs.getBoolean("author.gender"))
                                        .yearOfBirth(rs.getString("author.year_of_birth"))
                                        .description(rs.getString("author.description"))
                                        .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }





}
