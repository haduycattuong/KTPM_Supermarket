import com.hdct.supermarket.conf.JdbcUtils;
import com.hdct.supermarket.pojo.Employee;
import com.hdct.supermarket.pojo.Product;
import com.hdct.supermarket.service.EmployeeService;
import com.hdct.supermarket.service.ProductService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductTester {
    private static Connection conn;
    private static ProductService productService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        productService = new ProductService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    @Test
    public void testAddProduct() {
        Product product = new Product("Bi ngo", 35, "In stock");
        try {
            boolean actual = productService.addProduct(product);
            Assertions.assertTrue(actual);
            String sql = "SELECT * FROM product WHERE product_id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, product.getProduct_id());
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Bi ngo", rs.getString("name"));
            Assertions.assertEquals("35", rs.getString("price"));
            Assertions.assertEquals("In stock", rs.getString("status"));
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    //FAIL: Khong sua thanh cong vao csdl
    @Test
    public void testUpdateProduct() {
       Product product = new Product(8, 45);
       try {
           Product actual = productService.updateProduct(product);
           String sql = "SELECT * FROM product WHERE product_id = ?";
           PreparedStatement stm = conn.prepareCall(sql);
           stm.setInt(1, product.getProduct_id());
           ResultSet rs = stm.executeQuery();
           Assertions.assertTrue(rs.next());
           Assertions.assertEquals(45, actual.getPrice());

       } catch (SQLException e) {
           Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, e);
       }
    }

    @Test
    public void testDeleteProduct() {
        int id = 9;
        try {
            boolean actual = productService.deleteProduct(id);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM product WHERE product_id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetPrice() {
       String name = "Ca hoi";
        try {
           double actual = productService.getPrice(name);
           Assertions.assertEquals(300, actual);
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }


    @Test
    public void testSearchProductName() {
        String name = "Ca hoi";
        Product product = new Product();
        try {
            boolean actual = productService.findProductByName(name);
            Assertions.assertTrue(actual);
            String sql = "SELECT * FROM product WHERE product_id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, product.getProduct_id());
            ResultSet rs = stm.executeQuery();
            Assertions.assertEquals(name, rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSearchProductNameShouldFail() {
        String name = "Ca thu";
        Product product = new Product();
        try {
            boolean actual = productService.findProductByName(name);
            Assertions.assertFalse(actual);
            String sql = "SELECT * FROM product WHERE product_id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, product.getProduct_id());
            ResultSet rs = stm.executeQuery();
            Assertions.assertEquals(name, rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testResetOrder() {
        try(Connection conn = JdbcUtils.getConn()) {

            boolean actual = productService.resetTableOrder();
            Assertions.assertTrue(actual);
        }catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
