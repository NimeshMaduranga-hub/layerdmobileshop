package lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.bo.custom.LoginBO;
import lk.ijse.layerdmobileshop.mobileshop.dao.DAOFactory;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.UserDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.User;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {

    UserDAO userDAO =
            (UserDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.USER);

    @Override
    public User login(String username, String password) throws SQLException, ClassNotFoundException {

        return userDAO.login(username, password);
    }
}