package lk.ijse.layerdmobileshop.mobileshop.dao.custom;

import lk.ijse.layerdmobileshop.mobileshop.dao.CrudDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item> {
    ArrayList<String> getAllId() throws SQLException, ClassNotFoundException;

    String genarateNewId() throws SQLException, ClassNotFoundException;

    Item find(String code) throws SQLException, ClassNotFoundException;

    boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException;

}
