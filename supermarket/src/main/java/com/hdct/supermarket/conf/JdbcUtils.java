package com.hdct.supermarket.conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private String url = "jdbc:mysql://localhost:3306/supermarket";
    private String user = "root";
    private String pass = "Kuuhaku6112001";


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
    }



    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket", "root", "Kuuhaku6112001");
    }
}
