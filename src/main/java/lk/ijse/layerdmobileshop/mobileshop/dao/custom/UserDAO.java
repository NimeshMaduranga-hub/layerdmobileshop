package lk.ijse.layerdmobileshop.mobileshop.dao.custom;

import lk.ijse.layerdmobileshop.mobileshop.dao.SuperDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.User;

import java.sql.SQLException;

public interface UserDAO extends SuperDAO {

     User login(String username, String password) throws SQLException, ClassNotFoundException;

}