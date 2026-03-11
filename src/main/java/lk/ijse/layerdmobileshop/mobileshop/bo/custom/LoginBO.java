package lk.ijse.layerdmobileshop.mobileshop.bo.custom;

import lk.ijse.layerdmobileshop.mobileshop.bo.SuperBO;
import lk.ijse.layerdmobileshop.mobileshop.entity.User;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {

    User login(String username, String password) throws SQLException, ClassNotFoundException;

}