package lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.dao.CRUDUtil;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.UserDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public User login(String username, String password) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM user WHERE username=? AND password=?";

        ResultSet rs = CRUDUtil.execute(sql, username, password);

        if (rs.next()) {
            return new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
            );
        }

        return null;
    }
}