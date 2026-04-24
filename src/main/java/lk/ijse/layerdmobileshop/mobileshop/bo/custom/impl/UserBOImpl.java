package lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.bo.custom.UserBO;
import lk.ijse.layerdmobileshop.mobileshop.dao.DAOFactory;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.UserDAO;
import lk.ijse.layerdmobileshop.mobileshop.dto.UserDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.USER);

    // ================= LOGIN =================
    @Override
    public User login(String username, String password)
            throws SQLException, ClassNotFoundException {

        return userDAO.login(username, password);
    }

    // ================= GET ALL USERS =================
    @Override
    public ArrayList<UserDTO> getAllUser()
            throws SQLException, ClassNotFoundException {

        ArrayList<User> users = userDAO.getAll();
        ArrayList<UserDTO> dtos = new ArrayList<>();

        for (User u : users) {
            dtos.add(new UserDTO(
                    null,               // id (optional or empId later)
                    u.getUsername(),
                    u.getPassword(),
                    u.getRole()
            ));
        }

        return dtos;
    }

    // ================= SAVE USER =================
    @Override
    public boolean saveUser(UserDTO dto)
            throws SQLException, ClassNotFoundException {

        return userDAO.save(new User(
                dto.getUsername(),
                dto.getPassword(),
                dto.getRole()
        ));
    }

    // ================= UPDATE USER =================
    @Override
    public boolean updateUser(UserDTO dto)
            throws SQLException, ClassNotFoundException {

        return userDAO.update(new User(
                dto.getUsername(),
                dto.getPassword(),
                dto.getRole()
        ));
    }

    // ================= DELETE USER =================
    @Override
    public boolean deleteUser(String username)
            throws SQLException, ClassNotFoundException {

        return userDAO.delete(username);
    }
}