package lk.ijse.layerdmobileshop.mobileshop.dao.custom;

import lk.ijse.layerdmobileshop.mobileshop.dao.SuperDAO;

import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {
    void getAllOrdersByCustomer() throws SQLException;

}
