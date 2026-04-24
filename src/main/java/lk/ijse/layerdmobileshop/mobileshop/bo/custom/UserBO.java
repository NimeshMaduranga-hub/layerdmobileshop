package lk.ijse.layerdmobileshop.mobileshop.bo.custom;

import lk.ijse.layerdmobileshop.mobileshop.bo.SuperBO;
import lk.ijse.layerdmobileshop.mobileshop.dto.UserDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {

    // ================= LOGIN =================
    User login(String username, String password)
            throws SQLException, ClassNotFoundException;

    // ================= GET ALL USERS =================
    ArrayList<UserDTO> getAllUser()
            throws SQLException, ClassNotFoundException;

    // ================= SAVE USER =================
    boolean saveUser(UserDTO dto)
            throws SQLException, ClassNotFoundException;

    // ================= UPDATE USER =================
    boolean updateUser(UserDTO dto)
            throws SQLException, ClassNotFoundException;

    // ================= DELETE USER =================
    boolean deleteUser(String username)
            throws SQLException, ClassNotFoundException;
}