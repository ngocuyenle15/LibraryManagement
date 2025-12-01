package com.ptit.librarymanagement.service;

import com.ptit.librarymanagement.common.transactionmanager.Transactional;
import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.dao.AccountDAO;
import com.ptit.librarymanagement.dao.StaffDAO;
import com.ptit.librarymanagement.model.dto.AccountDTO;
import com.ptit.librarymanagement.model.dto.StaffDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.util.List;

public class StaffService {
    private final StaffDAO staffDAO;
    private final AccountDAO accountDAO;

    public StaffDTO getStaffById (Integer id) {
        return staffDAO.getStaffById(id).orElseThrow();
    }

    public StaffService (Connection connection) {
        this.staffDAO = new StaffDAO(connection);
        this.accountDAO = new AccountDAO(connection);
    }

    public StaffService (DAOFactory daoFactory) {
        this.staffDAO = daoFactory.getStaffDAO();
        this.accountDAO = daoFactory.getAccountDAO();
    }


    public List<StaffDTO> getAllStaff () {
        return staffDAO.getAllStaff();
    }

    @Transactional
    public void deleteStaffAccount (StaffDTO staffDTO) {
        staffDAO.deleteStaff(staffDTO);
        accountDAO.deleteAccount(staffDTO.getAccount());
    }


    @Transactional
    public StaffDTO CreateStaffAccount (StaffDTO staff){
        AccountDTO account = staff.getAccount();
        account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(10)));
        Integer i = accountDAO.addAccount(account);
        account.setId(i);
        staff.setAccount(account);
        staffDAO.insertStaff(staff);
        return staff;
    }

    @Transactional
    public void updateStaffAndAccount(StaffDTO staff) {
        staffDAO.updateStaff(staff);
        accountDAO.editAccount(staff.getAccount());
    }

    public List<StaffDTO> search (Integer staffId, String staffName, String userName) {
        return staffDAO.searchStaff(staffId, staffName, userName);

    }





}
