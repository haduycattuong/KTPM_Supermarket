import com.hdct.supermarket.conf.JdbcUtils;
import com.hdct.supermarket.pojo.Employee;
import com.hdct.supermarket.pojo.Product;
import com.hdct.supermarket.pojo.Product_Store;
import com.hdct.supermarket.pojo.Store;
import com.hdct.supermarket.service.StoreService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreTester {
    private static Connection conn;
    private static StoreService storeService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(StoreTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        storeService = new StoreService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(StoreTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    void testAddStore() {
        Store store = new Store("Cua hang Nguyen Trong Tuyen", "15 Nguyen Trong Tuyen P15, Q.Phu Nhuan");

        try {
            boolean actual = storeService.addStore(store);
            Assertions.assertTrue(actual);
            String sql = "SELECT * FROM store WHERE store_id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1,store.getStore_id());
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Cua hang Nguyen Trong Tuyen", rs.getString("name"));
            Assertions.assertEquals("15 Nguyen Trong TUyen P15, Q.Phu Nhuan", rs.getString("address"));
        } catch (SQLException ex) {
            Logger.getLogger(StoreTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    //FAIL: Khong them vao csdl dc
    @Test
    public void testAddEmployeeToStore() {
       Store store = new Store("Cua hang Nguyen Trong Tuyen1", "155 Nguyen Trong Tuyen P15, Q.Phu Nhuan");
       List<Employee> employees = new ArrayList<>();
       employees.add(new Employee("Duc Ha", "Nguyen", store.getStore_id()));
       employees.add(new Employee("Duc A", "Nguyen", store.getStore_id()));
       //employees.add(new Employee("Long An", "Pham", store.getStore_id()));

        try {
            boolean actual = storeService.addEmployeeToStore(store, employees);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM store WHERE store_id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, store.getStore_id());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Cua hang Nguyen Trong Tuyen1", rs.getString("name"));
            Assertions.assertEquals("155 Nguyen Trong Tuyen P15, Q.Phu Nhuan", rs.getString("address"));
        } catch (SQLException ex) {
            Logger.getLogger(StoreTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //FAIL: Khong them vao csdl dc
    @Test
    public void testAddProductToStore() {
        Store store = new Store("Cua hang Nguyen Trong Tuyen12", "15 Nguyen Trong Tuyen P15, Q.Phu Nhuan1");
        Product product = new Product("Cha lua", Double.parseDouble("200"), "In stock", store.getStore_id());
        List<Product_Store> product_stores = new ArrayList<>();
        //products.add(new Product("Cha lua", Double.parseDouble("200"), "In stock", store.getStore_id()));
        product_stores.add(new Product_Store(store.getStore_id(), product.getProduct_id()));
        //employees.add(new Employee("Long An", "Pham", store.getStore_id()));

        try {
            boolean actual = storeService.addProductToStore(store, product, product_stores);
            Assertions.assertTrue(actual);

            String sql1 = "SELECT * FROM store WHERE store_id=?";
            PreparedStatement stm = conn.prepareCall(sql1);
            stm.setInt(1, store.getStore_id());
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Cua hang Nguyen Trong Tuyen12", rs.getString("name"));
            Assertions.assertEquals("15 Nguyen Trong TUyen P15, Q.Phu Nhuan1", rs.getString("address"));

            String sql2 = "SELECT * FROM product WHERE product_id=?";
            PreparedStatement stm1 = conn.prepareCall(sql2);
            stm1.setInt(1, product.getProduct_id());
            ResultSet rs1 = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Cha lua", rs1.getString("name"));
            Assertions.assertEquals("200", rs1.getDouble("price"));
            Assertions.assertEquals("In stock", rs1.getString("status"));

        } catch (SQLException ex) {
            Logger.getLogger(StoreTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testDeleteStore() {
        int id = 5;
        try {
            boolean actual = storeService.deleteStore(id);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM store WHERE store_id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());

            sql = "SELECT * FROM employee WHERE store_id=?";
            stm = conn.prepareCall(sql);
            stm.setInt(1, id);

            rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(StoreTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
