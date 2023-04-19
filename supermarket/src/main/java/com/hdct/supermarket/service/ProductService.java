package com.hdct.supermarket.service;

import com.hdct.supermarket.conf.JdbcUtils;
import com.hdct.supermarket.pojo.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.logging.Logger;

import static com.hdct.supermarket.conf.JdbcUtils.getConn;

public class ProductService {
    private static Connection conn;
    private static PreparedStatement stm;





    public boolean addProduct(Product product) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO product(product_id, name, price, status) VALUES(?, ?, ?, ?)";
            stm = conn.prepareCall(sql);
            stm.setInt(1, product.getProduct_id());
            stm.setString(2, product.getName());
            stm.setDouble(3, product.getPrice());
            stm.setString(4, product.getStatus());
            try {
                conn.commit();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public Product findProductByID(int id) throws SQLException {
        conn = JdbcUtils.getConn();
        String sql = "SELECT * from product WHERE product_id = ?";
        stm = conn.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        while(rs.next()) {
            int ID = rs.getInt("product_id");
            String name = rs.getString("name");
            Double price = rs.getDouble("price");
            String status = rs.getString("status");
            Product product = new Product(ID, name, price, status);
            return product;
        }

        return null;
    }
    public boolean findProductByName(String name) throws SQLException {
        conn = JdbcUtils.getConn();
        String sql = "SELECT * from product WHERE name = ?";
        stm = conn.prepareStatement(sql);
        stm.setString(1, name);
        ResultSet rs = stm.executeQuery();
        while(rs.next()) {
            int ID = rs.getInt("product_id");
            String name1 = rs.getString("name");
            Double price = rs.getDouble("price");
            String status = rs.getString("status");
            return true;
        }
        return false;
    }
    public Product updateProduct(Product product) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "UPDATE product SET name = ?, price = ?,  status = ? WHERE product_id = ?";
            stm = conn.prepareCall(sql);
            stm.setString(1, product.getName());
            stm.setDouble(2, product.getPrice());
            stm.setString(3, product.getStatus());
            try {
                conn.commit();
                return product;
            }catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    public boolean deleteProduct(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM product WHERE product_id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, String.valueOf(id));
            return stm.executeUpdate() > 0;
        }
    }
    public boolean resetTableOrder() throws SQLException {
        try(Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "TRUNCATE TABLE tableOrder";

            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            try{
                conn.commit();
                return true;
            }catch(SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }

        }
    }

    public double getPrice(String name) throws SQLException {
        String getPrice = "SELECT price FROM product WHERE name = '" + name + "'";
        double price = 0;

        try(Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(getPrice);


            if (rs.next())
                price = rs.getDouble("price");


        }catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        return price;
    }

    public String searchProductName(String name) throws SQLException {
        String searchPN = "SELECT * FROM product WHERE name = '" + name + "'";
        try(Connection conn = JdbcUtils.getConn()){
            stm = conn.prepareStatement(searchPN);
            ResultSet rs  = stm.executeQuery();
            while(rs.next()) {
                name = rs.getString("name");
            }
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
        return name;
    }



//    public void searchProductName() throws SQLException {
//        String searchPN = "SELECT * FROM product WHERE name = '"
//                + employee_productName.getText() + "' and status = 'In stock'";
//
//        conn = getConn();
//        try{
//            stm = conn.prepareStatement(searchPN);
//            rs = stm.executeQuery();
//
//            ObservableList listProduct = FXCollections.observableArrayList();
//            while(rs.next()) {
//                listProduct.add(rs.getString("name"));
//            }
//            product_name_cb.setItems(listProduct);
//        }catch(Exception e) {e.printStackTrace();}
//    }
    public ObservableList<Product> getProductList(String name) throws SQLException {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        conn = getConn();
        String sql = "SELECT * FROM product";
        try {
            Product product;
            stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                product = new Product(rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("status"));
                productList.add(product);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }
}
