package lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.dao.CRUDUtil;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.UserDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    // ================= GET ALL =================
    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rst = CRUDUtil.execute("SELECT * FROM user");

        ArrayList<User> users = new ArrayList<>();

        while (rst.next()) {
            users.add(new User(
                    rst.getString("username"),
                    rst.getString("password"),
                    rst.getString("role")
            ));
        }

        return users;
    }

    // ================= SAVE USER =================
    @Override
    public boolean save(User user) throws SQLException, ClassNotFoundException {

        return CRUDUtil.execute(
                "INSERT INTO user (username, password, role) VALUES (?,?,?)",
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }

    // ================= UPDATE PASSWORD =================
    @Override
    public boolean update(User user) throws SQLException, ClassNotFoundException {

        return CRUDUtil.execute(
                "UPDATE user SET password=?, role=? WHERE username=?",
                user.getPassword(),
                user.getRole(),
                user.getUsername()
        );
    }

    // ================= DELETE USER =================
    @Override
    public boolean delete(String username) throws SQLException, ClassNotFoundException {

        return CRUDUtil.execute(
                "DELETE FROM user WHERE username=?",
                username
        );
    }

    // ================= LOGIN =================
    @Override
    public User login(String username, String password) throws SQLException, ClassNotFoundException {

        ResultSet rs = CRUDUtil.execute(
                "SELECT * FROM user WHERE username=? AND password=?",
                username,
                password
        );

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