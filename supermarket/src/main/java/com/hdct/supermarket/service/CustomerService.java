package com.hdct.supermarket.service;

import com.hdct.supermarket.conf.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerService {
    private static Connection conn;
    private static PreparedStatement stm;


    public boolean findCustomerByFirstName(String name) throws SQLException {
        conn = JdbcUtils.getConn();
        String sql = "SELECT * from customer WHERE first_name = ?";
        stm = conn.prepareStatement(sql);
        stm.setString(1, name);
        ResultSet rs = stm.executeQuery();
        while(rs.next()) {
            int ID = rs.getInt("customer_id");
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            int phone = rs.getInt("phone");
            String rank = rs.getString("rank");
            return true;
        }
        return false;
    }

    public boolean findCustomerByPhone(int phoneNumber) throws SQLException {
        conn = JdbcUtils.getConn();
        String sql = "SELECT * from customer WHERE phone = ?";
        stm = conn.prepareStatement(sql);
        stm.setInt(1, phoneNumber);
        ResultSet rs = stm.executeQuery();
        while(rs.next()) {
            int ID = rs.getInt("customer_id");
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            int phone = rs.getInt("phone");
            String rank = rs.getString("rank");
            return true;
        }
        return false;
    }

    public boolean findCustomerByRank(String rank) throws SQLException {
        conn = JdbcUtils.getConn();
        String sql = "SELECT * from customer WHERE rank = ?";
        stm = conn.prepareStatement(sql);
        stm.setString(1, rank);
        ResultSet rs = stm.executeQuery();
        while(rs.next()) {
            int ID = rs.getInt("customer_id");
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            int phone = rs.getInt("phone");
            String rank1 = rs.getString("rank");
            return true;
        }
        return false;
    }
}
