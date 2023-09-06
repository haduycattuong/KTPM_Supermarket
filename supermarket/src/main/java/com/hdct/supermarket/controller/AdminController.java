package com.hdct.supermarket.controller;

import com.hdct.supermarket.pojo.Admin;
import com.hdct.supermarket.pojo.Employee;
import com.hdct.supermarket.pojo.Product;
import com.hdct.supermarket.App;
import com.hdct.supermarket.conf.JdbcUtils;
import com.hdct.supermarket.pojo.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

import static com.hdct.supermarket.conf.JdbcUtils.getConn;

public class AdminController implements Initializable {

    private static Connection conn;
    private static PreparedStatement stm;
    private static ResultSet rs;
    @FXML
    private Tab add_employee_tab;

    @FXML
    private Tab add_product_tab;

    @FXML
    private Tab add_store_tab;

    @FXML
    private Button employee_clear_btn;

    @FXML
    private Button employee_delete_btn;

    @FXML
    private TextField employee_employeeID;

    @FXML
    private TextField employee_firstname;

    @FXML
    private TableColumn<Employee, String> employee_firstname_col;

    @FXML
    private ComboBox<?> employee_gender_cb;

    @FXML
    private TableColumn<Employee, String> employee_gender_col;

    @FXML
    private TableColumn<Employee, Integer> employee_id_col;

    @FXML
    private TextField employee_lastname;

    @FXML
    private TableColumn<Employee, String> employee_lastname_col;

    @FXML
    private TextField employee_password;

    @FXML
    private TableColumn<Employee, String> employee_password_col;

    @FXML
    private Button employee_add_btn;

    @FXML
    private TableColumn<Employee, Integer> employee_store_col;

    @FXML
    private TableView<Employee> employee_table_view;

    @FXML
    private Button employee_update_btn;

    @FXML
    private TextField employee_username;

    @FXML
    private TableColumn<Employee, String> employee_username_col;

    @FXML
    private Button logOut_btn;

    @FXML
    private Button product_add_btn;

    @FXML
    private Button product_clear_btn;

    @FXML
    private Button product_delete_btn;

    @FXML
    private TableColumn<Product, Integer> product_id_col;

    @FXML
    private TableColumn<Product, String> product_name_col;

    @FXML
    private TableColumn<Product, Double> product_price_col;

    @FXML
    private TextField product_productID;

    @FXML
    private TextField product_productName;

    @FXML
    private TextField product_productPrice;

    @FXML
    private TextField product_productQuantity;

//    @FXML
//    private TableColumn<?, ?> product_quantity_col;

    @FXML
    private TextField product_search;

    @FXML
    private TableColumn<Product, String> product_status_col;

    @FXML
    private ComboBox<?> product_stt_cb;

    @FXML
    private TableView<Product> product_table_view;

    @FXML
    private Button product_update_btn;

    @FXML
    private TextField store_address;

    @FXML
    private TableColumn<Store, String> store_address_col;

    @FXML
    private Button store_clear_btn;

    @FXML
    private Button store_delete_btn;

    @FXML
    private TableColumn<Store, Integer> store_id_col;

    @FXML
    private TableColumn<Store, String> store_name_col;

    @FXML
    private Button store_save_btn;

    @FXML
    private TextField store_storeID;

    @FXML
    private TextField store_storename;

    @FXML
    private TableView<Store> store_table_view;

    @FXML
    private Button store_update_btn;

    @FXML
    private Pane username;

    @FXML
    private Label admin_username_lb;

    @FXML
    private TableColumn<Store, Integer> store_employee_id;
    @FXML
    private TableColumn<Store, Integer> store_product_id;
    @FXML
    private TextField store_employeeID;
    @FXML
    private TextField store_productID;


    private static ObservableList<Employee> employeeList;
    public static ObservableList<Employee> getEmployeeList() throws SQLException {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();

        conn = getConn();
        String sql = "SELECT * FROM employee";

        try {
            Employee employee;
            stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                employee = new Employee(rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("gender"));
                employeeList.add(employee);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }
    public void showEmployees() throws SQLException {
        employeeList = getEmployeeList();
        employee_id_col.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        employee_firstname_col.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        employee_lastname_col.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        employee_username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        employee_password_col.setCellValueFactory(new PropertyValueFactory<>("password"));
        employee_gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));

        employee_table_view.setItems(employeeList);
    }

    public static ObservableList<Product> getProductList() throws SQLException {
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

    private static ObservableList<Product> productList;
    public void showProducts() throws SQLException {
        productList = getProductList();
        product_id_col.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        product_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        product_price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        product_status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
        product_table_view.setItems(productList);
    }

    public void displayUsername() {
        Admin admin = new Admin();
        admin_username_lb.setText(admin.getUsername());
    }
    private String[] statusList = {"In stock", "Not Available"};
    public void selectProductsStatus() {
        List<String> stt = new ArrayList<>();

        for(String data: statusList) {
            stt.add(data);
        }
        ObservableList statusData = FXCollections.observableArrayList(stt);
        product_stt_cb.setItems(statusData);
    }

    private String[] genderList = {"Male", "Female", "Other"};
    public void selectEmployeesGender() {
        List<String> gender = new ArrayList<>();

        for(String data: genderList) {
            gender.add(data);
        }
        ObservableList genderData = FXCollections.observableArrayList(gender);
        employee_gender_cb.setItems(genderData);
    }
    public void selectProducts() {
        Product product = product_table_view.getSelectionModel().getSelectedItem();
        int num = product_table_view.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1) {
            return;
        }

        product_productID.setText(String.valueOf(product.getProduct_id()));
        product_productName.setText(product.getName());
        product_productPrice.setText(String.valueOf(product.getPrice()));

    }
    public void selectEmployees() {
        Employee employee = employee_table_view.getSelectionModel().getSelectedItem();
        int num = employee_table_view.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1) {
            return;
        }
        employee_employeeID.setText(String.valueOf(employee.getEmployee_id()));
        employee_firstname.setText(employee.getFirst_name());
        employee_lastname.setText(employee.getLast_name());
        employee_username.setText(employee.getUsername());
        employee_password.setText(employee.getPassword());
    }
    public void selectStores() {
        Store store = store_table_view.getSelectionModel().getSelectedItem();
        int num = store_table_view.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1) {
            return;
        }
        store_storeID.setText(String.valueOf(store.getStore_id()));
        store_storename.setText(store.getName());
        store_address.setText(store.getAddress());
    }
    public void searchProduct() {
       FilteredList<Product> filter = new FilteredList<>(productList, b -> true);
       product_search.textProperty().addListener((observable, oldValue, newValue) -> {
           filter.setPredicate(predicateProduct -> {
               if(newValue == null || newValue.isEmpty()) {
                   return true;
               }
               String searchKey = newValue.toLowerCase();
               if(predicateProduct.getName().toLowerCase().contains(searchKey)) {
                   return true;
               }else if(predicateProduct.getStatus().toLowerCase().contains(searchKey)) {
                   return true;
               }else return false;
           });
       });
        SortedList<Product> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(product_table_view.comparatorProperty());
        product_table_view.setItems(sortList);
    }

    public void addProducts(ActionEvent event) throws SQLException {
        String sql = "INSERT INTO product(product_id, name, price, status) VALUES(?, ?, ?, ?)";
        Connection conn = JdbcUtils.getConn();

        try {
            Alert alert;
            if(product_productID.getText().isEmpty() || product_productName.getText().isEmpty()
                    || product_productPrice.getText().isEmpty()
                    || product_stt_cb.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            }else if(!isProductPrice(String.valueOf(product_productPrice.getText()))) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please input the right format for product price");
                alert.showAndWait();
            }else if(!isProductName(String.valueOf(product_productName.getText()))) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please input at least 5 and max 45 character");
                alert.showAndWait();
            }else {
                String check = "SELECT product_id FROM product WHERE product_id = '"
                        + product_productID.getText()+ "'";
                Statement statement = conn.createStatement();
                rs = statement.executeQuery(check);
                //Neu product_id da ton tai thi k them dc
                if(rs.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Product ID: " + product_productID.getText() + " was already exist");
                    alert.showAndWait();
                }

                stm = conn.prepareCall(sql);
                stm.setString(1, product_productID.getText());
                stm.setString(2, product_productName.getText());
                stm.setString(3, String.valueOf(product_productPrice.getText()));
                stm.setString(4, String.valueOf(product_stt_cb.getSelectionModel().getSelectedItem()));

                stm.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setContentText("Successful Added!");
                alert.showAndWait();

                //Update table view sau khi insert
                showProducts();
                //clear product sau khi insert
                clearProduct();
            }

        }catch (Exception e) {e.printStackTrace();}
    }

    public boolean isProductPrice(String price) {
        Pattern p = Pattern.compile("^[0-9]+$");
        if(p.matcher(price).find())
            return true;
        return false;
    }

    public boolean isProductName(String name) {
        Pattern p = Pattern.compile("^[a-zA-Z]{5,45}$");
        if(p.matcher(name).find())
            return true;
        return false;
    }

    public void clearProduct() {
        product_productID.setText("");
        product_productName.setText("");
        product_productPrice.setText("");
        product_stt_cb.getSelectionModel().clearSelection();
    }

    public void updateProduct() throws SQLException {
        String update = "UPDATE product SET name = '"
                + product_productName.getText() + "', price = '"
                + product_productPrice.getText() + "', status = '"
                + product_stt_cb.getSelectionModel().getSelectedItem() + "' WHERE product_id = '"
                + product_productID.getText() + "'";
        try {
                Alert alert;
                if(product_productID.getText().isEmpty() || product_productName.getText().isEmpty()
                        || product_productPrice.getText().isEmpty()
                    || product_stt_cb.getSelectionModel().getSelectedItem() == null) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Please fill all the fields");
                    alert.showAndWait();
                }else if(!isProductPrice(String.valueOf(product_productPrice.getText()))) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Please input the right format for product price");
                    alert.showAndWait();
                }else if(!isProductName(String.valueOf(product_productName.getText()))) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Please input at least 5 and max 45 character");
                    alert.showAndWait();
                }else {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setContentText("Are you sure you want to update Product ID: " + product_productID.getText() + "?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if(option.get().equals(ButtonType.OK)) {
                        Statement statement = conn.createStatement();
                        statement.executeUpdate(update);
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setContentText("Successfully updated!");
                        alert.showAndWait();

                        //Update table view sau khi insert
                        showProducts();
                        //clear product sau khi insert
                        clearProduct();
                    }else return;
                }
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct() throws SQLException {
        String delete = "DELETE FROM product WHERE product_id = '"
                + product_productID.getText() + "'";

        conn = JdbcUtils.getConn();
        try {
            Alert alert;
            if(product_productID.getText().isEmpty() || product_productName.getText().isEmpty()
                    || product_productPrice.getText().isEmpty()
                    || product_stt_cb.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();

            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setContentText("Are you sure you want to delete Product ID: " + product_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)) {
                    Statement statement = conn.createStatement();
                    statement.executeUpdate(delete);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully deleted!");
                    alert.showAndWait();

                    //Update table view sau khi insert
                    showProducts();
                    //clear product sau khi insert
                    clearProduct();
                }else return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearEmployee() {
        employee_employeeID.setText("");
        employee_firstname.setText("");
        employee_lastname.setText("");
        employee_username.setText("");
        employee_password.setText("");
        employee_gender_cb.getSelectionModel().clearSelection();
    }

    public void addEmployees() throws SQLException {
        String sql = "INSERT INTO employee(employee_id, first_name, last_name, username, password, gender) VALUES(?, ?, ?, ?, ?, ?)";
        Connection conn = JdbcUtils.getConn();

        try {
            Alert alert;
            if (employee_employeeID.getText().isEmpty() || employee_username.getText().isEmpty()
                    || employee_lastname.getText().isEmpty()
                    || employee_username.getText().isEmpty()
                    || employee_password.getText().isEmpty()
                    || employee_gender_cb.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            }else if(!isUsername(employee_username.getText())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Please input username at least 6 and max 18 character");
                    alert.showAndWait();
            }else if(!isPassword(employee_password.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please input password at least 6 and max 18 character");
                alert.showAndWait();
            }else if(!isLastName(employee_lastname.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please input last name at least 1 and max 6 character");
                alert.showAndWait();
            }else if(!isFirstName(employee_firstname.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please input first name at least 3 and max 16 character");
                alert.showAndWait();
            }else if(!isID(employee_employeeID.getText())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Employee ID can be add just with number");
                    alert.showAndWait();
                }else {
                String check = "SELECT employee_id FROM employee WHERE employee_id = '"
                        + employee_employeeID.getText() + "'";
                Statement statement = conn.createStatement();
                rs = statement.executeQuery(check);
                //Neu product_id da ton tai thi k them dc
                if (rs.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Employee ID: " + employee_employeeID.getText() + " was already exist");
                    alert.showAndWait();
                }
                String check1 = "SELECT username FROM employee WHERE username = '" + employee_username.getText() + "'";
                rs = statement.executeQuery(check1);
                //Neu username da ton tai thi k them dc
                if (rs.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Employee username: " + employee_username.getText() + " was already exist");
                    alert.showAndWait();
                }

                stm = conn.prepareCall(sql);
                stm.setString(1, employee_employeeID.getText());
                stm.setString(2, employee_firstname.getText());
                stm.setString(3, employee_lastname.getText());
                stm.setString(4, employee_username.getText());
                stm.setString(5, employee_password.getText());
                stm.setString(6, String.valueOf(employee_gender_cb.getSelectionModel().getSelectedItem()));

                stm.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setContentText("Successful Added!");
                alert.showAndWait();

                //Update table view sau khi insert
                showEmployees();
                //clear employee sau khi insert
                clearEmployee();
            }
        }catch (Exception e) {e.printStackTrace();}
    }

    public boolean isUsername(String name) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9 ]{6,18}+$");
        if (p.matcher(name).find())
            return true;
        return false;
    }
    public boolean isPassword(String password) {
        Pattern p = Pattern.compile(("^[a-zA-Z0-9 ]{6,18}+$"));
        if (p.matcher(password).find())
            return true;
        return false;
    }
    public boolean isLastName(String lastname) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9 ]{1,6}+$");
        if(p.matcher(lastname).find())
            return true;
        return false;
    }
    public boolean isFirstName(String firstname) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9 ]{3,16}+$");
        if(p.matcher(firstname).find())
            return true;
        return false;
    }
    public boolean isID(String phone) {
        Pattern p = Pattern.compile("^[0-9]+$");
        if(p.matcher(phone).find())
            return true;
        return false;
    }

    public void updateEmployee() {

        String update = "UPDATE employee SET first_name = '"
                + employee_firstname.getText() + "', last_name = '"
                + employee_lastname.getText() + "', username = '"
                + employee_username.getText() + "', password = '"
                + employee_password.getText() + "', gender = '"
                + employee_gender_cb.getSelectionModel().getSelectedItem() + "' WHERE employee_id = '"
                + employee_employeeID.getText() + "'";

        try {
            Alert alert;
            if (employee_employeeID.getText().isEmpty() || employee_username.getText().isEmpty()
                    || employee_lastname.getText().isEmpty()
                    || employee_username.getText().isEmpty()
                    || employee_password.getText().isEmpty()
                    || employee_gender_cb.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            }else if(!isUsername(employee_username.getText())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Please input username at least 6 and max 18 character");
                    alert.showAndWait();
            }else if(!isPassword(employee_password.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please input password at least 6 and max 18 character");
                alert.showAndWait();
            }else if(!isLastName(employee_lastname.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please input last name at least 1 and max 6 character");
                alert.showAndWait();
            }else if(!isFirstName(employee_firstname.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please input first name at least 3 and max 16 character");
                alert.showAndWait();
            }else if(!isID(employee_employeeID.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Employee ID can be add just with number");
                alert.showAndWait();
                }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setContentText("Are you sure you want to update Employee ID: " + employee_employeeID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)) {
                    Statement statement = conn.createStatement();
                    statement.executeUpdate(update);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully updated!");
                    alert.showAndWait();

                    //Update table view sau khi insert
                    showEmployees();
                    //clear product sau khi insert
                    clearEmployee();
                }else return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee() throws SQLException {
        String delete = "DELETE FROM employee WHERE employee_id = '"
                + employee_employeeID.getText() + "'";

        conn = JdbcUtils.getConn();
        try {
            Alert alert;
            if (employee_employeeID.getText().isEmpty() || employee_username.getText().isEmpty()
                    || employee_lastname.getText().isEmpty()
                    || employee_username.getText().isEmpty()
                    || employee_password.getText().isEmpty()
                    || employee_gender_cb.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();

            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setContentText("Are you sure you want to delete Employee ID: " + employee_employeeID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)) {
                    Statement statement = conn.createStatement();
                    statement.executeUpdate(delete);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully updated!");
                    alert.showAndWait();

                    //Update table view sau khi insert
                    showEmployees();
                    //clear product sau khi insert
                    clearEmployee();
                }else return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ObservableList<Store> storeList;
    public static ObservableList<Store> getStoreList() throws SQLException {
        ObservableList<Store> storeList = FXCollections.observableArrayList();

        conn = getConn();
        String sql = "SELECT * FROM store";

        try {
            Store store;
            stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                store = new Store(rs.getInt("store_id"),
                        rs.getString("name"),
                        rs.getString("address"));
                storeList.add(store);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return storeList;
    }

    public void showStores() throws SQLException {
        storeList = getStoreList();
        store_id_col.setCellValueFactory(new PropertyValueFactory<>("store_id"));
        store_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        store_address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        store_table_view.setItems(storeList);
    }

    public void clearStore() {
        store_storeID.setText("");
        store_storename.setText("");
        store_address.setText("");
    }
    public void addStores() throws SQLException {
        String sql = "INSERT INTO store(store_id, name, address) VALUES(?, ?, ?)";
        Connection conn = JdbcUtils.getConn();

        try {
            Alert alert;
            if(store_storeID.getText().isEmpty() || store_storename.getText().isEmpty()
                    || store_address.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            }else if(!isStoreName(store_storename.getText())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Please input store name at least 10 and max 50 character");
                    alert.showAndWait();
            }else if(!isAddress(store_address.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please input address at least 20 and max 100 character");
                alert.showAndWait();
            }else {
                String check = "SELECT store_id FROM store WHERE store_id = '"
                        + store_storeID.getText()+ "'";
                Statement statement = conn.createStatement();
                rs = statement.executeQuery(check);
                //Neu product_id da ton tai thi k them dc
                if(rs.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText(" Store ID: " + store_storeID.getText() + " was already exist");
                    alert.showAndWait();
                }

                stm = conn.prepareCall(sql);
                stm.setString(1, store_storeID.getText());
                stm.setString(2, store_storename.getText());
                stm.setString(3, store_address.getText());

                stm.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setContentText("Successful Added!");
                alert.showAndWait();

                //Update table view sau khi insert
                showStores();
                //clear store sau khi insert
                clearStore();
            }

        }catch (Exception e) {e.printStackTrace();}
    }

    public boolean isStoreName(String name) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9 ]{10,50}+$");
        if(p.matcher(name).find())
            return true;
        return false;
    }
    public boolean isAddress(String address) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9 ]{20,100}+$");
        if(p.matcher(address).find())
            return true;
        return false;
    }

    public void updateStore() {
        String update = "UPDATE store SET name = '"
                + store_storename.getText() + "', address = '"
                + store_address.getText() + "' WHERE store_id = '"
                + store_storeID.getText() + "'";
        try {
            Alert alert;
            if(store_storeID.getText().isEmpty() || store_storename.getText().isEmpty()
                    || store_address.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            }else if(!isStoreName(store_storename.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please input store name at least 10 and max 50 character");
                alert.showAndWait();
            }else if(!isAddress(store_address.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please input address at least 20 and max 100 character");
                alert.showAndWait();
            }else{
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setContentText("Are you sure you want to update Store ID: " + store_storeID.getText() + "?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if(option.get().equals(ButtonType.OK)) {
                        Statement statement = conn.createStatement();
                        statement.executeUpdate(update);
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setContentText("Successfully updated!");
                        alert.showAndWait();

                        //Update table view sau khi insert
                        showStores();
                        //clear store sau khi insert
                        clearStore();
                    }else return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void deleteStore() throws SQLException {
        String delete = "DELETE FROM store WHERE store_id = '"
                + store_storeID.getText() + "'";

        conn = JdbcUtils.getConn();
        try {
            Alert alert;
            if(store_storeID.getText().isEmpty() || store_storename.getText().isEmpty()
                    || store_address.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setContentText("Are you sure you want to update Store ID: " + store_storeID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)) {
                    Statement statement = conn.createStatement();
                    statement.executeUpdate(delete);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully updated!");
                    alert.showAndWait();

                    //Update table view sau khi delete
                    showStores();
                    //clear store sau khi delete
                    clearStore();
                }else return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logOut() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {

            logOut_btn.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(App.class.getResource("signin-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showProducts();
            displayUsername();
            selectProductsStatus();
            showEmployees();
            selectEmployeesGender();
            showStores();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
