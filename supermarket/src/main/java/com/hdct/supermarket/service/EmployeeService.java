package com.hdct.supermarket.service;

import com.hdct.supermarket.conf.JdbcUtils;
import com.hdct.supermarket.pojo.Employee;
import com.hdct.supermarket.pojo.TableOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeService {
    private static Connection conn;
    private static PreparedStatement stm;

    public boolean addEmployee(Employee employee) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO employee(employee_id, first_name, last_name, gender) VALUES(?, ?, ?, ?)";

            stm = conn.prepareCall(sql);
            stm.setInt(1, employee.getEmployee_id());
            stm.setString(2, employee.getFirst_name());
            stm.setString(3, employee.getLast_name());
            stm.setString(4, employee.getGender());

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

    public boolean addEmployeeAccount(Employee employee) throws SQLException {

        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO employee(username, password) VALUES(?, ?)";

            stm = conn.prepareCall(sql);
            stm.setString(1, employee.getUsername());
            stm.setString(2, employee.getPassword());

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
