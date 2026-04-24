package lk.ijse.layerdmobileshop.mobileshop.dao.custom;

import lk.ijse.layerdmobileshop.mobileshop.dao.SuperDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends SuperDAO {

     // ================= GET ALL =================
     ArrayList<User> getAll() throws SQLException, ClassNotFoundException;

     // ================= SAVE USER =================
     boolean save(User user) throws SQLException, ClassNotFoundException;

     // ================= UPDATE PASSWORD =================
     boolean update(User user) throws SQLException, ClassNotFoundException;

     // ================= DELETE USER =================
     boolean delete(String username) throws SQLException, ClassNotFoundException;

     User login(String username, String password) throws SQLException, ClassNotFoundException;

}