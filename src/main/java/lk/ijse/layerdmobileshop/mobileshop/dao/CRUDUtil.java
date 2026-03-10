package lk.ijse.layerdmobileshop.mobileshop.dao;

import lk.ijse.layerdmobileshop.mobileshop.db.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUDUtil {
    public static <T> T execute(String sql, Object... ob) throws SQLException, ClassNotFoundException {
        Connection connection = DBconnection.getdBconnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < ob.length; i++) {
            preparedStatement.setObject(i + 1, ob[i]);
        }
        if (sql.startsWith("SELECT")) {
            return (T) preparedStatement.executeQuery();
        } else {
            return (T) (Boolean) (preparedStatement.executeUpdate() > 0);
        }
    }
}
