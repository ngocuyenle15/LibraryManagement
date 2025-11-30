package com.ptit.librarymanagement.dao;

import com.ptit.librarymanagement.dto.AccountDTO;
import com.ptit.librarymanagement.common.enums.Role;
import com.ptit.librarymanagement.dto.StaffDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaffDAO {
    private final Connection connection;
    public StaffDAO(Connection connection) {
        this.connection = connection;
    }

    public Optional<StaffDTO> getStaffById (Integer id) {
        String sql = "select * from staff s join account c on s.account_id = c.id where s.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(StaffDTO.builder()
                            .id(rs.getInt("s.id"))
                            .firstName(rs.getString("s.first_name"))
                            .lastName(rs.getString("s.last_name"))
                            .gender(rs.getBoolean("s.gender"))
                            .location(rs.getString("s.location"))
                            .salary(rs.getDouble("s.salary"))
                            .birth(rs.getDate("s.birth"))
                            .phone(rs.getString("s.phone"))
                            .email(rs.getString("s.email"))
                            .account(AccountDTO.
                                    builder().
                                    id(rs.getInt("c.id"))
                                    .userName(rs.getString("c.user_name"))
                                    .role(rs.getInt("c.role_id") == 1 ? Role.ADMIN : Role.STAFF)
                                    .password(rs.getString("c.password"))
                                    .build())
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List<StaffDTO> getAllStaff () {
        List<StaffDTO> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from staff s join account c on s.account_id = c.id")) {
             try (ResultSet rs = statement.executeQuery()) {
                 while (rs.next()) {
                     list.add(StaffDTO.builder()
                             .id(rs.getInt("s.id"))
                             .firstName(rs.getString("s.first_name"))
                             .lastName(rs.getString("s.last_name"))
                             .gender(rs.getBoolean("s.gender"))
                             .location(rs.getString("s.location"))
                             .salary(rs.getDouble("s.salary"))
                             .birth(rs.getDate("s.birth"))
                             .phone(rs.getString("s.phone"))
                             .email(rs.getString("s.email"))
                             .account(AccountDTO.
                                     builder().
                                     id(rs.getInt("c.id"))
                                     .userName(rs.getString("c.user_name"))
                                     .role(rs.getInt("c.role_id") == 1 ? Role.ADMIN : Role.STAFF)
                                     .password(rs.getString("c.password"))
                                     .build())
                             .build());
                 }
             }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Integer insertStaff (StaffDTO staff) {
        Integer keyGen = null;
        try (PreparedStatement statement = connection.prepareStatement("insert into staff (first_name, last_name, gender, location, birth, phone, email, account_id, salary) values (?,?,?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, staff.getFirstName());
            statement.setString(2, staff.getLastName());
            statement.setBoolean(3, staff.getGender());
            statement.setString(4, staff.getLocation());
            statement.setDate(5, staff.getBirth());
            statement.setString(6, staff.getPhone());
            statement.setString(7, staff.getEmail());
            statement.setInt(8, staff.getAccount().getId());
            statement.setDouble(9, staff.getSalary());
            int i = statement.executeUpdate();
            if (i > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        keyGen = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return keyGen;

    }

    // todo: set lai cascade trong cac bang co link voi staff
    public void updateStaff (StaffDTO staff) {
        try (PreparedStatement statement = connection.prepareStatement("update staff set first_name = ?, last_name = ?, gender = ? , location = ?, birth = ? , phone = ?, email = ?, salary = ? where id = ?")) {
            statement.setString(1, staff.getFirstName());
            statement.setString(2, staff.getLastName());
            statement.setBoolean(3, staff.getGender());
            statement.setString(4, staff.getLocation());
            statement.setDate(5, staff.getBirth());
            statement.setString(6, staff.getPhone());
            statement.setString(7, staff.getEmail());
            statement.setDouble(8, staff.getSalary());
            statement.setInt(9, staff.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStaff (StaffDTO staff) {
        try (PreparedStatement statement = connection.prepareStatement("delete from staff where id = ?")) {
            statement.setInt(1, staff.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<StaffDTO> searchStaff (Integer staffId, String staffName, String userName) {
        List<StaffDTO> result = new ArrayList<>();
        String sql = "select * from staff join account on staff.account_id = account.id\n" +
                "where (staff.id = ? or ? is null)\n" +
                "and (concat(staff.first_name, ' ', staff.last_name) like concat('%' ,? , '%')  or ? is null)\n" +
                "and (account.user_name like concat('%' , ? , '%') or ? is null)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, staffId);
            statement.setObject(2,staffId);
            statement.setObject(3, staffName);
            statement.setObject(4, staffName);
            statement.setObject(5, userName);
            statement.setObject(6, userName);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(StaffDTO.builder()
                            .id(rs.getInt("staff.id"))
                            .firstName(rs.getString("staff.first_name"))
                            .lastName(rs.getString("staff.last_name"))
                            .gender(rs.getBoolean("staff.gender"))
                            .location(rs.getString("staff.location"))
                            .salary(rs.getDouble("staff.salary"))
                            .birth(rs.getDate("staff.birth"))
                            .phone(rs.getString("staff.phone"))
                            .email(rs.getString("staff.email"))
                            .account(AccountDTO.
                                    builder().
                                    id(rs.getInt("account.id"))
                                    .userName(rs.getString("account.user_name"))
                                    .role(rs.getInt("account.role_id") == 1 ? Role.ADMIN : Role.STAFF)
                                    .password(rs.getString("account.password"))
                                    .build())
                            .build());
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;

    }










}
