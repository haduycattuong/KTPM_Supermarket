package com.hdct.supermarket.controller;

import com.hdct.supermarket.App;
import com.hdct.supermarket.conf.JdbcUtils;
import com.hdct.supermarket.pojo.Admin;
import com.hdct.supermarket.pojo.GetData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SignInController {
    private static String isManager = "0";
    @FXML
    private Pane admin_form;

    @FXML
    private Pane employee_form;

    @FXML
    private Hyperlink admin_hyperlink;

    @FXML
    private Button admin_loginBtn;

    @FXML
    private PasswordField admin_password;

    @FXML
    private TextField admin_username;

    @FXML
    private TextField employee_username;

    @FXML
    private PasswordField employee_password;

    @FXML
    private Button employee_loginBtn;

    @FXML
    private Hyperlink employee_hyperlink;

    @FXML
    private CheckBox showPassword;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField employee_passwordTF;

    @FXML
    private CheckBox showPassword1;



    private ResultSet rs;
    private PreparedStatement stm;

//    public boolean validate() {
//        if(username.getText().isEmpty()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setContentText("Please fill in username");
//            return false;
//        } else if (password.getText().isEmpty()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setContentText("Please fill in password");
//            return false;
//        }
//        return true;
//    }
//    public void SignIn() throws SQLException, IOException {
//        if(validate()) {
//            String userName = username.getText();
//            String passWord = password.getText();
//
//            if(isEmployee(userName, passWord, isManager)) {
//                if(isManager == "1") {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Information Message");
//                    alert.setContentText("Successfully Login");
//                    alert.showAndWait();
//                    admin_loginBtn.getScene().getWindow().hide();
//                    Parent root = FXMLLoader.load(getClass().getResource("admin-view.fxml"));
//                }else {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Information Message");
//                    alert.setContentText("Successfully Login");
//                    alert.showAndWait();
//                    admin_loginBtn.getScene().getWindow().hide();
//                    Parent root = FXMLLoader.load(getClass().getResource("employee-view.fxml"));
//                }
//            }else {
//                Alert alert = new Alert((Alert.AlertType.ERROR));
//                alert.setContentText("Wrong username/password");
//            }
//        }
//    }
//
//    public boolean isEmployee(String username, String password, String role) throws SQLException {
//        Connection conn = JdbcUtils.getConn();
//        stm = conn.prepareStatement("SELECT * FROM employee WHERE username = ?");
//        stm.setString(1, username);
//        rs = stm.executeQuery();
//
//        if(rs.next()) {
//            String passAccess = rs.getString("password");
//            String managerAccess = rs.getString("role");
//            if(passAccess.equals(password) && Objects.equals(managerAccess, role)) {
//                return true;
//            }
//        }
//        return false;
//    }
    public void afterLogin(ActionEvent event) throws IOException {
        String username = employee_username.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-view.fxml"));
        Parent root = loader.load();

        EmployeeController employeeController = loader.getController();

    }


    public void adminLogin(ActionEvent event) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String  adminData = "SELECT * FROM admin WHERE username = ? and password = ? ";


        try{
            Alert alert;

            if(admin_username.getText().isEmpty()
                || admin_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please fill username and password fields");
                alert.showAndWait();
                }else {
                    stm = conn.prepareStatement(adminData);
                    stm.setString(1, admin_username.getText());
                    stm.setString(2, admin_password.getText());
                    rs = stm.executeQuery();

                    if(rs.next()) {
                    //Chuyen den admin view
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();
                    admin_loginBtn.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(SignInController.class.getResource("/com/hdct/supermarket/admin-view.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.show();
                    }else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Wrong Username/Password");
                        alert.showAndWait();
                    }
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }

    public void employeeLogin(ActionEvent event) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String  employeeData = "SELECT * FROM employee WHERE username = ? and password = ? ";


        try{
            Alert alert;

            if(employee_username.getText().isEmpty()
                    || employee_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please fill username and password fields");
                alert.showAndWait();
            }else {
                stm = conn.prepareStatement(employeeData);
                stm.setString(1, employee_username.getText());
                stm.setString(2, employee_password.getText());
                rs = stm.executeQuery();

                if(rs.next()) {
                    GetData.username = employee_username.getText();
                    //Chuyen den employee view
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();
//                    String username = employee_username.getText();
                    employee_loginBtn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(App.class.getResource("employee-view.fxml"));

//                    EmployeeController employeeController = loader.getController();
//                    employeeController.displayName(username);


                    //Parent root = FXMLLoader.load(getClass().getResource("employee-view.fxml"));

                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.show();
                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }




        public void switchForm(ActionEvent event) {
        if(event.getSource() == admin_hyperlink) {
            admin_form.setVisible(false);
            employee_form.setVisible(true);
        }else if(event.getSource() == employee_hyperlink) {
            admin_form.setVisible(true);
            employee_form.setVisible(false);
        }
    }

    public void showPassword(ActionEvent event) {
        if (showPassword.isSelected()) {
            passwordTextField.setText(admin_password.getText());
            passwordTextField.setVisible(true);
            admin_password.setVisible(false);
        }
        else{
            admin_password.setText(passwordTextField.getText());
            admin_password.setVisible(true);
            passwordTextField.setVisible(false);
        }
    }

    public void showPasswordEmployee(ActionEvent event) {
        if (showPassword1.isSelected()) {
            employee_passwordTF.setText(employee_password.getText());
            employee_passwordTF.setVisible(true);
            employee_password.setVisible(false);
        }
        else{
            employee_password.setText(employee_passwordTF.getText());
            employee_password.setVisible(true);
            employee_passwordTF.setVisible(false);
        }
    }

}

