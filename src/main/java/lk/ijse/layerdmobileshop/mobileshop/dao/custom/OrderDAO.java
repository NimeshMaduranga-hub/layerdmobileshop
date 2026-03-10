package lk.ijse.layerdmobileshop.mobileshop.dao.custom;

import lk.ijse.layerdmobileshop.mobileshop.dao.CrudDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Orders> {

    ArrayList<String> getAllId() throws SQLException, ClassNotFoundException;
    String genarateNewId() throws SQLException, ClassNotFoundException;

    }
