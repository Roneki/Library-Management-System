package lk.ijse.cmjd.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    public static Connection getConnection() throws SQLException {
        if (connection==null){
connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarysystem","root","bunty");
        }
        return connection;
    }
}
