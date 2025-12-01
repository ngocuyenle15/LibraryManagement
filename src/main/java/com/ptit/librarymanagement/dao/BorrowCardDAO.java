package com.ptit.librarymanagement.dao;

import com.ptit.librarymanagement.model.dto.BookDTO;
import com.ptit.librarymanagement.model.dto.BorrowCardDTO;

import com.ptit.librarymanagement.model.dto.ReaderDTO;
import com.ptit.librarymanagement.common.enums.BorrowState;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BorrowCardDAO {

    private final Connection connection;

    public BorrowCardDAO(Connection connection) {
        this.connection = connection;
    }

    //get reader by id
    public List<BorrowCardDTO> getBorrowCardByReader (ReaderDTO reader) {
        List<BorrowCardDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from borrow_card where reader_id = ?")) {
            statement.setInt(1, reader.getId());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(BorrowCardDTO.builder()
                            .id(rs.getInt("id"))
                            .startDate(rs.getDate("start_date"))
                            .returnDate(rs.getDate("return_date"))
                            .state(rs.getString("state"))
                            .reader(ReaderDTO.builder().id(rs.getInt("reader_id")).build())
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<BorrowCardDTO> getBorrowCardByReader (ReaderDTO reader, BorrowState borrowState) {
        List<BorrowCardDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from borrow_card where reader_id = ? and state != ?")) {
            statement.setInt(1, reader.getId());
            statement.setString(2, borrowState.getState());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(BorrowCardDTO.builder()
                            .id(rs.getInt("id"))
                            .startDate(rs.getDate("start_date"))
                            .returnDate(rs.getDate("return_date"))
                            .state(rs.getString("state"))
                            .reader(ReaderDTO.builder().id(rs.getInt("reader_id")).build())
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

//    insert card
//    public void CreateBorrowCardOfReader (BorrowCardDTO borrowCard) {
//        String sql = "INSERT INTO borrow_card (start_date, return_date, status, reader_id, status_create_id) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setDate(1, borrowCard.getStartDate());
//            statement.setDate(2, borrowCard.getReturnDate());
//            statement.setString(3, borrowCard.getState());
//            statement.setInt(4, borrowCard.getReader().getId());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }

//    get all card
//    public List<BorrowCardDTO> getAllBorrowCard(BorrowCardDTO borrowCard, PagingDTO pa) {
//        String sql = "select * from borrow_card limit ?, offset ?";
//        List<BorrowCardDTO> list = new ArrayList<>();
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, pa.getLimit());
//            statement.setInt(2, pa.getOffset());
//            try (ResultSet rs = statement.executeQuery()) {
//                while (rs.next()) {
//                    list.add(BorrowCardDTO.builder()
//                            .id(rs.getInt("id"))
//                            .startDate(rs.getDate("start_date"))
//                            .returnDate(rs.getDate("return_date"))
//                            .state(rs.getString("state"))
//                            .reader(ReaderDTO.builder().id(rs.getInt("reader_id")).build())
//                            .punishment(rs.getString("punishment"))
//                            .build());
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
//
//    }



//    update date
    public void updateBorrowCardOfReader(BorrowCardDTO borrowCard) {
        String sql = "UPDATE borrow_card SET return_date = ?, punishment = ?, `state`= ?  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, borrowCard.getReturnDate());
            statement.setString(2, borrowCard.getPunishment());
            statement.setString(3, borrowCard.getState());
            statement.setInt(4, borrowCard.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    get borrow by id
    public BorrowCardDTO getBorrowCardById (Integer id) {
        try (PreparedStatement statement = connection.prepareStatement("select * from borrow_card where id = ?")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return BorrowCardDTO.builder()
                            .id(rs.getInt("id"))
                            .startDate(rs.getDate("start_date"))
                            .returnDate(rs.getDate("return_date"))
                            .state(rs.getString("state"))
                            .reader(ReaderDTO.builder().id(rs.getInt("reader_id")).build())
                            .punishment(rs.getString("punishment"))
                            .build();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void deleteBookInBorrowCard (BookDTO bookDTO) {
        try (PreparedStatement statement = connection.prepareStatement("delete from book_borrow_card where book_id = ?")) {
        statement.setInt(1, bookDTO.getId());
        statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public List<BorrowCardDTO> getAllBorrowCard() {
        String sql = "select * from borrow_card \n" +
                "join reader on borrow_card.reader_id = reader.id";
        List<BorrowCardDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(BorrowCardDTO.builder()
                            .id(rs.getInt("borrow_card.id"))
                            .startDate(rs.getDate("borrow_card.start_date"))
                            .returnDate(rs.getDate("borrow_card.return_date"))
                            .state(rs.getString("borrow_card.state"))
                            .punishment(rs.getString("punishment"))
                            .reader(ReaderDTO.builder()
                                    .id(rs.getInt("reader.id"))
                                    .firstName(rs.getString("reader.first_name"))
                                    .lastName(rs.getString("reader.last_name"))
                                    .gender(rs.getBoolean("reader.gender"))
                                    .location(rs.getString("reader.location"))
                                    .birth(rs.getDate("reader.birth"))
                                    .phone(rs.getString("reader.phone"))
                                    .email(rs.getString("reader.email"))
                                    .build())

                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }

    public List<BorrowCardDTO> getBorrowCardByIdOrNameReader(Integer id, String readerName) {
        String sql = "select * from borrow_card\n" +
                "join reader on borrow_card.reader_id = reader.id\n" +
                "where (borrow_card.id = ? or ? is null) \n" +
                "and (concat(reader.first_name, ' ', reader.last_name) like concat('%', ?, '%') or  ? is null)";
        List<BorrowCardDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            statement.setObject(2, id);
            statement.setObject(3, readerName);
            statement.setObject(4, readerName);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(BorrowCardDTO.builder()
                            .id(rs.getInt("borrow_card.id"))
                            .startDate(rs.getDate("borrow_card.start_date"))
                            .returnDate(rs.getDate("borrow_card.return_date"))
                            .state(rs.getString("borrow_card.state"))
                            .punishment(rs.getString("punishment"))
                            .reader(ReaderDTO.builder()
                                    .id(rs.getInt("reader.id"))
                                    .firstName(rs.getString("reader.first_name"))
                                    .lastName(rs.getString("reader.last_name"))
                                    .gender(rs.getBoolean("reader.gender"))
                                    .location(rs.getString("reader.location"))
                                    .birth(rs.getDate("reader.birth"))
                                    .phone(rs.getString("reader.phone"))
                                    .email(rs.getString("reader.email"))
                                    .build())

                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }
    
    




    public void deleteBookInBorrowCard (BorrowCardDTO cardDTO) {
        String sql = "delete from book_borrow_card where borrow_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cardDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteBorrowCard (BorrowCardDTO borrowCard) {
        String sql = "delete from borrow_card where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, borrowCard.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void insertBookBorrowCard (Integer borrowCardId ,Integer bookId) {
        String sql = "insert into book_borrow_card (borrow_id, book_id) VALUE (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, borrowCardId);
            statement.setInt(2, bookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer insertBorrowCard (BorrowCardDTO borrowCard) {
        Integer index = null;
        String sql = "insert into borrow_card (start_date, return_date, reader_id, punishment) value (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, borrowCard.getStartDate());
            statement.setDate(2, borrowCard.getReturnDate());
            statement.setInt(3, borrowCard.getReader().getId());
            statement.setString(4, borrowCard.getPunishment());
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


    public List<BorrowCardDTO> getBorrowCardByState (BorrowState state) {
        String sql = "select * from borrow_card join reader on borrow_card.reader_id = reader.id where borrow_card.state = ?";
        List<BorrowCardDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, state.getState());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(BorrowCardDTO.builder()
                            .id(rs.getInt("borrow_card.id"))
                            .startDate(rs.getDate("borrow_card.start_date"))
                            .returnDate(rs.getDate("borrow_card.return_date"))
                            .state(rs.getString("borrow_card.state"))
                            .punishment(rs.getString("punishment"))
                            .reader(ReaderDTO.builder()
                                    .id(rs.getInt("reader.id"))
                                    .firstName(rs.getString("reader.first_name"))
                                    .lastName(rs.getString("reader.last_name"))
                                    .gender(rs.getBoolean("reader.gender"))
                                    .location(rs.getString("reader.location"))
                                    .birth(rs.getDate("reader.birth"))
                                    .phone(rs.getString("reader.phone"))
                                    .email(rs.getString("reader.email"))
                                    .build())

                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;



    }



    public void loadStateAllBorrowCard () {
        String sql = "update borrow_card set state = 'Quá hạn' where state = 'Đang mượn' && return_date < CURDATE()";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }





}
