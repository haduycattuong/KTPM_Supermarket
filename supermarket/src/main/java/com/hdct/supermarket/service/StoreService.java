package com.hdct.supermarket.service;

import com.hdct.supermarket.conf.JdbcUtils;
import com.hdct.supermarket.pojo.Employee;
import com.hdct.supermarket.pojo.Product;
import com.hdct.supermarket.pojo.Product_Store;
import com.hdct.supermarket.pojo.Store;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreService {
    private static Connection conn;
    private static PreparedStatement stm;
    private static ResultSet rs;
    private static Statement statement;


    public List<Store> getStores() throws SQLException {
        List<Store> store = new ArrayList<>();

        try {
            conn = JdbcUtils.getConn();
            String sql = "SELECT * FROM store";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                int store_id = rs.getInt("store_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                store.add(new Store(store_id, name, address));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }

    public boolean addEmployeeToStore(Store store, List<Employee> employeeList) throws SQLException {

        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String insertStore = "INSERT INTO store(store_id, name, address) VALUES(?, ?, ?)";
            stm = conn.prepareCall(insertStore);
            stm.setInt(1, store.getStore_id());
            stm.setString(2, store.getName());
            stm.setString(3, store.getAddress());
            int r = stm.executeUpdate();
            while (r > 0) {
                String insertEmployee = "INSERT INTO employee(employee_id, first_name, last_name, store_id) VALUES(?, ?, ?, ?)";
                PreparedStatement stm1 = conn.prepareCall(insertEmployee);
                for (Employee e : employeeList) {
                    stm1.setInt(1, e.getEmployee_id());
                    stm1.setString(2, e.getFirst_name());
                    stm1.setString(3, e.getLast_name());
                    stm1.setInt(4, store.getStore_id());
                    stm1.executeUpdate();
                }
            }
            try {
                conn.commit();
                return true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }
        }
    }


    public boolean addProductToStore(Store store, Product product, List<Product_Store> product_storeList) throws SQLException {

        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String insertStore = "INSERT INTO store(store_id, name, address) VALUES(?, ?, ?)";
            stm = conn.prepareStatement(insertStore);
            stm.setInt(1, store.getStore_id());
            stm.setString(2, store.getName());
            stm.setString(3, store.getAddress());
            int r = stm.executeUpdate();
            while (r > 0) {
                String insertProduct = "INSERT INTO product(product_id, name, price, status) VALUES(?, ?, ?, ?)";
                PreparedStatement stm1 = conn.prepareStatement(insertProduct);
                stm1.setInt(1, product.getProduct_id());
                stm1.setString(2, product.getName());
                stm1.setDouble(3, product.getPrice());
                stm1.setString(4, product.getStatus());
                int t = stm1.executeUpdate();
                while (t > 0) {
                    String insertProductStore = "INSERT INTO product_store(id, product_id, store_id) VALUES(?, ?, ?)";
                    PreparedStatement stm2 = conn.prepareStatement(insertProductStore);
                    for (Product_Store ps : product_storeList) {
                        stm2.setInt(1, ps.getId());
                        stm2.setInt(2, product.getProduct_id());
                        stm2.setInt(3, store.getStore_id());
                        stm2.executeUpdate();
                    }
                }
            }
        }
            try {
                conn.commit();
                return true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }
        }

    public boolean deleteStore(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM store WHERE store_id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, String.valueOf(id));

            return stm.executeUpdate() > 0;
        }
    }
    public boolean addStore(Store store) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO store(store_id, name, address) VALUES(?, ?, ?)";

            stm = conn.prepareCall(sql);
            stm.setInt(1,store.getStore_id());
            stm.setString(2, store.getName());
            stm.setString(3, store.getAddress());

            stm.executeUpdate();
            try {
                conn.commit();
                return true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }
        }
    }


}


