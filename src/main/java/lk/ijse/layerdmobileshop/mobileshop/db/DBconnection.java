package lk.ijse.layerdmobileshop.mobileshop.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    private static DBconnection dBconnection;

    private final Connection connection;


    private DBconnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobileshoplayerd", "root", "ijse@123456789");
    }

    public static DBconnection getdBconnection() throws SQLException, ClassNotFoundException {
        return dBconnection == null ? dBconnection = new DBconnection() : dBconnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
