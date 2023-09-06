import com.hdct.supermarket.conf.JdbcUtils;
import com.hdct.supermarket.pojo.Customer;
import com.hdct.supermarket.pojo.Product;
import com.hdct.supermarket.service.CustomerService;
import com.hdct.supermarket.service.ProductService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerTester {
    private static Connection conn;
    private static CustomerService customerService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        customerService = new CustomerService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }



    @Test
    public void testSearchCustomerFirstName() {
        String name = "Van A";
        Customer customer = new Customer();
        try {
            boolean actual = customerService.findCustomerByFirstName(name);
            Assertions.assertTrue(actual);
            String sql = "SELECT * FROM customer WHERE customer_id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, customer.getCustomer_id());
            ResultSet rs = stm.executeQuery();
            Assertions.assertEquals(name, rs.getString("first_name"));
        } catch (SQLException e) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    public void testSearchCustomerFirstNameShouldFail() {
        String name = "Van B";
        Customer customer = new Customer();
        try {
            boolean actual = customerService.findCustomerByFirstName(name);
            Assertions.assertTrue(actual);
            String sql = "SELECT * FROM customer WHERE customer_id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, customer.getCustomer_id());
            ResultSet rs = stm.executeQuery();
            Assertions.assertEquals(name, rs.getString("first_name"));
        } catch (SQLException e) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @Test
    public void testSearchCustomerPhoneNumber() {
        int phone = 12345678;
        Customer customer = new Customer();
        try {
            boolean actual = customerService.findCustomerByPhone(phone);
            Assertions.assertTrue(actual);
            String sql = "SELECT * FROM customer WHERE customer_id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, customer.getCustomer_id());
            ResultSet rs = stm.executeQuery();
            Assertions.assertEquals(phone, rs.getString("phone"));
        } catch (SQLException e) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    public void testSearchCustomerPhoneNumberShouldFail() {
        int phone = 123456789;
        Customer customer = new Customer();
        try {
            boolean actual = customerService.findCustomerByPhone(phone);
            Assertions.assertFalse(actual);
            String sql = "SELECT * FROM customer WHERE customer_id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, customer.getCustomer_id());
            ResultSet rs = stm.executeQuery();
            Assertions.assertEquals(phone, rs.getString("phone"));
        } catch (SQLException e) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
