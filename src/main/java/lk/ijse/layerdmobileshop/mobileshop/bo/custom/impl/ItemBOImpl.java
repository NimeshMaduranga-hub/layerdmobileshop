package lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.bo.custom.ItemBO;
import lk.ijse.layerdmobileshop.mobileshop.dao.DAOFactory;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.CustomerDAO;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.ItemDAO;
import lk.ijse.layerdmobileshop.mobileshop.dto.ItemDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {


    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDTO> itemDtoList = new ArrayList<>();

        for(Item i : items){
            itemDtoList.add(new ItemDTO(i.getCode(),i.getDescription(), i.getUnitPrice(), i.getQtyOnHand()));
        }
        return itemDtoList;
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Item item = new Item(itemDTO.getCode(),itemDTO.getDescription(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand());
        return itemDAO.save(item);
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Item item = new Item(itemDTO.getCode(),itemDTO.getDescription(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand());
        return itemDAO.update(item);
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public boolean isExist(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.isExist(code);
    }

    @Override
    public String genarateNewId() throws SQLException, ClassNotFoundException {
        return itemDAO.genarateNewId();
    }
}
