package lk.ijse.layerdmobileshop.mobileshop.bo.custom;

import lk.ijse.layerdmobileshop.mobileshop.bo.SuperBO;
import lk.ijse.layerdmobileshop.mobileshop.dto.CustomerDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.ItemDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.OrderDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {

    String genarateNewId() throws SQLException, ClassNotFoundException;

    boolean isExist(String orderId) throws SQLException, ClassNotFoundException;

    boolean itemIsExist(String itemId) throws SQLException, ClassNotFoundException;

    boolean customerIsExist(String customerId) throws SQLException, ClassNotFoundException;

    boolean saveOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;

    boolean saveOrderDetails(OrderDetailDTO detail) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    CustomerDTO findCustomer(String id) throws SQLException, ClassNotFoundException;

    ItemDTO findItem(String id) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllCusId() throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllItemId() throws SQLException, ClassNotFoundException;
}
