import com.hdct.supermarket.conf.JdbcUtils;
import com.hdct.supermarket.pojo.Employee;
import com.hdct.supermarket.pojo.Store;
import com.hdct.supermarket.service.EmployeeService;
import com.hdct.supermarket.service.StoreService;
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

public class EmployeeTester {
    private static Connection conn;
    private static EmployeeService employeeService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        employeeService = new EmployeeService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    public void testAddEmployee() {
        Employee employee = new Employee("Duy Phong", "Ha", "Male");
        try {
            boolean actual = employeeService.addEmployee(employee);
            Assertions.assertTrue(actual);
            String sql = "SELECT * FROM employee WHERE employee_id =?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, employee.getEmployee_id());
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Duy Phong", rs.getString("first_name"));
            Assertions.assertEquals("Ha", rs.getString("last_name"));
            Assertions.assertEquals("Male", rs.getString("gender"));
        } catch (SQLException ex) {
            Logger.getLogger(StoreTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testAddEmployeeAccount() {
        Employee employee = new Employee("duyphong", "1234567");
        try {
            boolean actual = employeeService.addEmployeeAccount(employee);
            Assertions.assertTrue(actual);
            String sql = "SELECT * FROM employee WHERE employee_id =?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, employee.getEmployee_id());
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("duyphong", rs.getString("username"));
            Assertions.assertEquals("123", rs.getString("password"));
        } catch (SQLException ex) {
            Logger.getLogger(StoreTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
