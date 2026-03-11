package lk.ijse.layerdmobileshop.mobileshop.dao.custom;

import lk.ijse.layerdmobileshop.mobileshop.dao.CrudDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.OrderDetails;

import java.sql.SQLException;

public interface OrderDetailsDAO extends CrudDAO<OrderDetails> {
    String genarateNewId() throws SQLException, ClassNotFoundException;
}
