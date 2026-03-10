package lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.dao.CRUDUtil;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.ItemDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Item;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rst = CRUDUtil.execute("SELECT * FROM Item");

        ArrayList<Item> itemList = new ArrayList<>();

        while(rst.next()){

            String id = rst.getString("code");
            String description = rst.getString("description");
            BigDecimal price = rst.getBigDecimal("unitPrice");
            int qtyOnHand = rst.getInt("qtyOnHand");

            Item itemTM = new Item(id, description, price, qtyOnHand);
            itemList.add(itemTM);
        }

        return itemList;

    }

    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)";
        return CRUDUtil.execute(sql ,item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand());
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
        return CRUDUtil.execute(sql, item.getDescription(), item.getUnitPrice(), item.getQtyOnHand(), item.getCode());
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM Item WHERE code=?";
        return CRUDUtil.execute(sql, code);

    }

    @Override
    public boolean isExist(String code) throws SQLException, ClassNotFoundException {

        ResultSet rst =  CRUDUtil.execute("SELECT code FROM Item WHERE code=?", code);
        return rst.next();
    }

    @Override
    public String genarateNewId() throws SQLException, ClassNotFoundException {

        ResultSet rst = CRUDUtil.execute("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");

        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }

    }

    @Override
    public ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {

        ResultSet rst = CRUDUtil.execute("SELECT * FROM Item");

        ArrayList<String> idList = new ArrayList<>();

        while (rst.next()){
            idList.add(rst.getString("code"));
        }

        return idList;
    }

    @Override
    public Item find(String code) throws SQLException, ClassNotFoundException {

        ResultSet rst = CRUDUtil.execute("SELECT * FROM Item WHERE code=?", code);
        rst.next();
        return new Item(code, rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
    }
}
