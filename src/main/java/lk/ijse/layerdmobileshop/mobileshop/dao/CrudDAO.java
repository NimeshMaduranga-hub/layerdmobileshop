package lk.ijse.layerdmobileshop.mobileshop.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {

    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    boolean save(T enity) throws SQLException, ClassNotFoundException;

    boolean update(T entity)throws SQLException,ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean isExist(String id) throws SQLException, ClassNotFoundException;

    T find(String id) throws SQLException,ClassNotFoundException;

    ArrayList<String> getAllId() throws SQLException, ClassNotFoundException;

}
