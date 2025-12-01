package com.ptit.librarymanagement.dao;

import com.ptit.librarymanagement.model.dto.AccountDTO;
import com.ptit.librarymanagement.common.enums.Role;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AccountDAO {
    private final Connection connection;

    public AccountDAO(Connection connection) {
        this.connection = connection;
    }

    public List<AccountDTO> getAllAccount () {
        List<AccountDTO> accountDTOList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from `account`")) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    accountDTOList.add(
                            AccountDTO.builder()
                                    .id(rs.getInt("id"))
                                    .userName("user_name")
                                    .password(rs.getString("password"))
                                    .role(rs.getInt("role_id") == 1 ? Role.ADMIN : Role.STAFF)
                                    .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accountDTOList;
    }

    public Optional<AccountDTO> getAccountById (Integer id) {
        try (PreparedStatement statement = connection.prepareStatement("select * from account where id = ?")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(AccountDTO.builder()
                            .id(rs.getInt("id"))
                            .userName("user_name")
                            .password(rs.getString("password"))
                            .role(rs.getInt("role_id") == 1 ? Role.ADMIN : Role.STAFF)
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Integer addAccount (AccountDTO accountDTO) {
        Integer index = null;
        try (PreparedStatement statement = connection
                .prepareStatement("insert into `account` (`user_name`, `password`, `role_id`) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, accountDTO.getUserName());
            statement.setString(2, accountDTO.getPassword());
            statement.setInt(3, accountDTO.getRole().getId());
            int i = statement.executeUpdate();
            if (i > 0) {
               try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                   if (generatedKeys.next()) {
                       index = generatedKeys.getInt(1);
                   }
               }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return index;
    }

    // sai pass
    // ko tim thay user



    public Optional<AccountDTO> findAccountByUserName(AccountDTO accountDTO) {
        try (PreparedStatement statement = connection.prepareStatement("select * from account where user_name = ?")) {
            statement.setString(1, accountDTO.getUserName());
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(AccountDTO.builder()
                                .id(rs.getInt("id"))
                                .userName(rs.getString("user_name"))
                                .password(rs.getString("password"))
                                .role(rs.getInt("role_id") == 1 ? Role.ADMIN : Role.STAFF)
                                .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void editAccount (AccountDTO accountDTO) {
        try (PreparedStatement statement = connection
                .prepareStatement("update `account` set `user_name` = ?, `role_id` = ? where `id` = ?")) {
            statement.setString(1, accountDTO.getUserName());
         /*   statement.setString(2, BCrypt.hashpw(accountDTO.getPassword(),BCrypt.gensalt(10)));*/
            statement.setInt(2, accountDTO.getRole().getId());
            statement.setInt(3, accountDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void changePassword (AccountDTO account) {
        try (PreparedStatement statement = connection.prepareStatement("update account set password = ? where id = ?")) {
            statement.setString(1, account.getPassword());
            statement.setInt(2, account.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteAccount (AccountDTO accountDTO) {
        String sql = "delete from `account` where id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accountDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // chỉnh lại đăng nhập bằng email or username
    public boolean checkMail (String mail) {
        String sql = "select * from staff where email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, mail);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public void resetPassword (AccountDTO accountDTO) {
        String sql = "update account set password = ? where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountDTO.getPassword());
            statement.setInt(2, accountDTO.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<AccountDTO> getAccountByEmail (String email) {

        String sql = "select * from account join staff on account.id = staff.account_id where staff.email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(AccountDTO.builder()
                            .id(rs.getInt("account.id"))
                            .userName(rs.getString("account.user_name"))
                            .password(rs.getString("account.password"))
                            .role(rs.getInt("account.role_id") == 1 ? Role.ADMIN : Role.STAFF)
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
