package lk.ijse.layerdmobileshop.mobileshop.dao.custom;

import lk.ijse.layerdmobileshop.mobileshop.dao.CrudDAO;
import lk.ijse.layerdmobileshop.mobileshop.dto.CustomerDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Attendance;
import lk.ijse.layerdmobileshop.mobileshop.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer> {
     boolean isExist(String id) throws SQLException, ClassNotFoundException;

     String genarateNewId() throws SQLException, ClassNotFoundException;
    }
