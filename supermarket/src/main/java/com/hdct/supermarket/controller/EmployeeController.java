package com.hdct.supermarket.controller;

import com.hdct.supermarket.App;
import com.hdct.supermarket.conf.JdbcUtils;
import com.hdct.supermarket.pojo.*;
import com.mysql.cj.xdevapi.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Date;

import static com.hdct.supermarket.conf.JdbcUtils.getConn;

public class EmployeeController implements Initializable {

    @FXML
    private Label Sale;

    @FXML
    private Label TotalAfterSale;

    @FXML
    private Label TotalBeforeSale;

    @FXML
    private Label change;

    @FXML
    private TextField employee_customer_cash;

    @FXML
    private TextField employee_productName;

    @FXML
    private TextField employee_productQuantity;

    @FXML
    private TableView<TableOrder> order_detail;

    @FXML
    private TableColumn<TableOrder, String> product_name_column;

    @FXML
    private TableColumn<TableOrder, Double> product_price_column;

    @FXML
    private TableColumn<TableOrder, Double> product_quantity_column;

    @FXML
    private TableColumn<TableOrder, Integer> stt_column;

    @FXML
    private TableColumn<TableOrder, Double> total_column;

    @FXML
    private Pane username;

    @FXML
    private Label employee_username;

    @FXML
    private ComboBox product_name_cb;

    @FXML
    private TextField employee_stt;

    @FXML
    private Button employee_bill_btn;

    @FXML
    private Pane order_form;

   private static Connection conn;
   private static PreparedStatement stm;
   private static Statement statement;
   private static ResultSet rs;

   //-----------------CUSTOMER----------------
    @FXML
    private TextField searchCustomer;
    @FXML
    private TableColumn customer_first_name_col;
    @FXML
    private TableColumn customer_last_name_col;
    @FXML
    private TableColumn customer_phone_col;
    @FXML
    private TableColumn customer_birth_date_col;
    @FXML
    private TableColumn customer_rank_col;
    @FXML
    private TableView customer_table_view;


    private static ObservableList<Customer> customerList;
    public static ObservableList<Customer> getCustomerList() throws SQLException {
        ObservableList<Customer> customerList= FXCollections.observableArrayList();

        conn = getConn();
        String sql = "SELECT * FROM customer";

        try {
            Customer customer;
            stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                customer = new Customer(rs.getInt("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone"),
                        rs.getString("rank"));
                customerList.add(customer);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }
    public void showCustomers() throws SQLException {
        customerList = getCustomerList();
        customer_first_name_col.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        customer_last_name_col.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        customer_phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customer_rank_col.setCellValueFactory(new PropertyValueFactory<>("rank"));
        customer_table_view.setItems(customerList);
    }

    public void selectCustomers() {
        Customer customer = (Customer) customer_table_view.getSelectionModel().getSelectedItem();
        int num = customer_table_view.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1) {
            return;
        }

        customer_first_name_col.setText(customer.getFirst_name());
        customer_last_name_col.setText(customer.getLast_name());
        customer_phone_col.setText(customer.getPhone());
        customer_birth_date_col.setText(customer.getBirth_date());
        customer_rank_col.setText(customer.getRank());
    }

    //Search theo sdt vs first name
    public void searchCustomer() {
        FilteredList<Customer> filter = new FilteredList<>(customerList, b -> true);
        searchCustomer.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateCustomer -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if(predicateCustomer.getPhone().contains(searchKey)) {
                    return true;
                }else if(predicateCustomer.getFirst_name().toLowerCase().contains(searchKey)) {
                    return true;
                }else return false;
            });
        });
        SortedList<Customer> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(customer_table_view.comparatorProperty());
        customer_table_view.setItems(sortList);
    }



   //------------------BILL-------------------

    @FXML
    private Pane bill_form;
    @FXML
    private TableView bill_table_view;
    @FXML
    private TableColumn bill_product_name_col;
    @FXML
    private TableColumn bill_product_price_col;
    @FXML
    private TableColumn bill_product_quantity_col;
    @FXML
    private TableColumn bill_total_column;
    @FXML
    private Label bill_date;
    @FXML
    private Label bill_total;
    @FXML
    private Label bill_customer_cash;
    @FXML
    private Label bill_customer_change;
    @FXML
    private Button bill_btn;
    @FXML
    private Button employee_thanhtoan;

//    private static ObservableList<Receipt> receiptLists;

//    public static ObservableList<Receipt> getReceiptList() throws SQLException {
//        ObservableList<Receipt> receiptList = FXCollections.observableArrayList();
//        String sql = "SELECT * FROM receipt";
//        conn = getConn();
//        try {
//            Receipt receipt;
//            stm = conn.prepareStatement(sql);
//            rs = stm.executeQuery();
//
//            while(rs.next()) {
//                receipt = new Receipt(rs.getDouble("customer_cash"),
//                        rs.getDouble("customer_change"),
//                        rs.getDouble("total_price"));
//                receiptList.add(receipt);
//            }
//        }catch (Exception e) {e.printStackTrace();}
//        return receiptList;
//    }


    public void showBillTable() throws SQLException {
        tableOrderLists = getTableOrderList();
        bill_product_name_col.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        bill_product_quantity_col.setCellValueFactory(new PropertyValueFactory<>("product_quantity"));
        bill_product_price_col.setCellValueFactory(new PropertyValueFactory<>("product_price"));
        bill_total_column.setCellValueFactory(new PropertyValueFactory<>("total"));
        bill_table_view.setItems(tableOrderLists);
        bill_total.setText(totalPrice + "00 VND");
        bill_customer_cash.setText(String.valueOf(customerCash + "00 VND"));
        bill_customer_change.setText(String.valueOf(changes + "00 VND"));
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date1 = sdf.format(date);
        bill_date.setText(String.valueOf(date1));
    }

//    public void showBillTable1() throws SQLException {
//        receiptLists = getReceiptList();
//        bill_customer_cash.setText(customerCash + "00 VND");
//        bill_customer_change.setText(changes + "00 VND");
//        bill_table_view.setItems(receiptLists);
//    }



    public void switchForm(ActionEvent event) throws SQLException {
        if(event.getSource() == employee_bill_btn) {
            showBillTable();
            resetOrder();
            order_form.setVisible(false);
            bill_form.setVisible(true);
        }else if (event.getSource() == bill_btn) {
            order_form.setVisible(true);
            bill_form.setVisible(false);
        };
    }

    //-------------------------------------------
   public static ObservableList<TableOrder> getTableOrderList() throws SQLException {
       //employeeIdInTableOrder();
       ObservableList<TableOrder> tableOrderList = FXCollections.observableArrayList();

       String sql = "SELECT * FROM tableOrder";
        //+ " WHERE employee_id = '" + employeeID + "'";
       conn = getConn();

       try{
           TableOrder table;
           stm = conn.prepareStatement(sql);
           rs = stm.executeQuery();

           while(rs.next()) {
               table = new TableOrder(rs.getInt("tableOrder_id"),
                       rs.getString("product_name"),
                       rs.getDouble("product_quantity"),
                       rs.getDouble("product_price"),
                       rs.getDouble("total"));
               tableOrderList.add(table);
           }

       }catch (Exception e) {e.printStackTrace();}
       return tableOrderList;
   }

   private static ObservableList<TableOrder> tableOrderLists;

   public void showTableOrder() throws SQLException {
       tableOrderLists = getTableOrderList();

       stt_column.setCellValueFactory(new PropertyValueFactory<>("tableOrder_id"));
       product_name_column.setCellValueFactory(new PropertyValueFactory<>("product_name"));
       product_quantity_column.setCellValueFactory(new PropertyValueFactory<>("product_quantity"));
       product_price_column.setCellValueFactory(new PropertyValueFactory<>("product_price"));
       total_column.setCellValueFactory(new PropertyValueFactory<>("total"));

       order_detail.setItems(tableOrderLists);
   }
   private int employeeID;
   //Chua dc
   public void employeeIdInTableOrder() throws SQLException {
       String sql = "SELECT employee_id FROM employee";
       conn = getConn();

       stm = conn.prepareStatement(sql);
       rs = stm.executeQuery();

       while(rs.next()) {
           employeeID = rs.getInt("employee_id");
       }

//       int checkNum = 0;
//       String checkemployeeID = "SELECT employee_id FROM receipt";
//       statement = conn.createStatement();
//       rs = statement.executeQuery(checkemployeeID);
//
//       while(rs.next()) {
//           checkNum = rs.getInt("employee_id");
//       }
//
//       if(employeeID == 0) {
//           employeeID += 1;
//       }else if(checkNum == employeeID) {
//           employeeID += 1;
//       }
   }


    //Tim name cua bang product
   public void searchProductName() throws SQLException {
       String searchPN = "SELECT * FROM product WHERE name = '"
                + employee_productName.getText() + "' and status = 'In stock'";

       conn = getConn();
       try{
           stm = conn.prepareStatement(searchPN);
           rs = stm.executeQuery();

           ObservableList listProduct = FXCollections.observableArrayList();
           while(rs.next()) {
               listProduct.add(rs.getString("name"));
           }
           product_name_cb.setItems(listProduct);
       }catch(Exception e) {e.printStackTrace();}
   }

   public void displayUsername() {
       employee_username.setText(GetData.getUsername());
   }
   private String productName;
   private double productQuantity;
   public void deleteTableOrder() throws SQLException {
        String delete = "DELETE FROM tableOrder WHERE tableOrder_id = '"
                + employee_stt.getText() + "'";

        conn = JdbcUtils.getConn();
        try {
            Alert alert;
            if (employee_productQuantity.getText().isEmpty() || employee_productName.getText().isEmpty()
                    || product_name_cb.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();

            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setContentText("Are you sure you want to delete product : " + employee_stt.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)) {
                    Statement statement = conn.createStatement();
                    statement.executeUpdate(delete);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully updated!");
                    alert.showAndWait();

                    //Update table view sau khi insert
                    showTableOrder();
                    //clear product sau khi insert
                    clearOrders();
                }else return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   public void addTableOrder() throws SQLException {
       employeeIdInTableOrder();
       //getEmployeeIDInSignIn();
       getPrice();
       String insertProduct = "INSERT INTO tableOrder "
               + "(employee_id, product_name, product_quantity, product_price, date, total)"
               + "VALUES(?, ?, ?, ?, ?, ?)";
       conn = getConn();
       try{
           Alert alert;
           Date date = new Date();
           java.sql.Date sqlDate = new java.sql.Date(date.getTime());
           if(employee_productName.getText().isEmpty() || product_name_cb.getSelectionModel().getSelectedItem() == null
            || employee_productQuantity.getText().isEmpty()) {
               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error Message");
               alert.setContentText("Hãy nhập sản phẩm trước");
               alert.showAndWait();
           }

           stm = conn.prepareStatement(insertProduct);
           stm.setString(1, String.valueOf(employeeID));
           stm.setString(2, (String)product_name_cb.getSelectionModel().getSelectedItem());
           stm.setDouble(3, Double.parseDouble(employee_productQuantity.getText()));
           stm.setString(4,String.valueOf(price));
           stm.setString(5, String.valueOf(sqlDate));
           total = Double.parseDouble(employee_productQuantity.getText()) * price;
           stm.setString(6, String.valueOf(total));
           //stm.setDouble(7, Double.parseDouble(employee_customer_cash.getText()));

           stm.executeUpdate();

//           productName = employee_productName.getText();
//           productQuantity = Double.parseDouble(employee_productQuantity.getText());

           //Update tableview
           showTableOrder();
           displayTotalPayment();
//           displayChange();
           clearOrders();
       }catch (Exception e){e.printStackTrace();}
   }
   private double total = 0;
   private double price = 0;
   public void getPrice() throws SQLException {
       String getPrice = "SELECT price FROM product WHERE name = '"
               + product_name_cb.getSelectionModel().getSelectedItem() + "'";
       conn = getConn();

       try{
           statement = conn.createStatement();
           rs = statement.executeQuery(getPrice);


           if(rs.next()) {
               price = rs.getDouble("price");

           }

       }catch (Exception e) {e.printStackTrace();}
   }

   public void clearOrders() {
       employee_productName.setText("");
       //employee_customer_cash.setText("");
       product_name_cb.getSelectionModel().clearSelection();
       employee_productQuantity.setText("1");
   }

   public void selectOrder() {
       TableOrder tableOrder = order_detail.getSelectionModel().getSelectedItem();
       int num = order_detail.getSelectionModel().getSelectedIndex();
       if((num - 1) < -1) {
           return;
       }

       employee_stt.setText(String.valueOf(tableOrder.getTableOrder_id()));
       employee_productName.setText(tableOrder.getProduct_name());
       employee_productQuantity.setText(String.valueOf(tableOrder.getProduct_quantity()));
   }

   private double totalPrice = 0;

   public void displayTotalPayment() throws SQLException {
       employeeIdInTableOrder();
       String sql = "SELECT SUM(total) FROM tableOrder WHERE employee_id = '" + employeeID + "'";
       conn = JdbcUtils.getConn();
       try{
           statement = conn.createStatement();
           rs = statement.executeQuery(sql);

           if(rs.next()) {
               totalPrice = rs.getDouble("SUM(total)");
           }
           TotalAfterSale.setText(String.valueOf(totalPrice) + "00 VND");
//           changes = Double.parseDouble(employee_customer_cash.getText()) - totalPrice;
//           change.setText(String.valueOf(changes) + "00 VND");

       }catch (Exception e) {e.printStackTrace();}

   }

   private double changes = 0;
   public void displayChange() throws SQLException {
       employeeIdInTableOrder();
       String sql = "SELECT SUM(total) FROM tableOrder WHERE employee_id = '" + employeeID + "'";
       conn = JdbcUtils.getConn();
       try{
           statement = conn.createStatement();
           rs = statement.executeQuery(sql);

           if(rs.next()) {
               totalPrice = rs.getDouble("SUM(total)");
           }
           inputCustomerCash();
          // changes = Double.parseDouble(employee_customer_cash.getText()) - totalPrice;
           changes = customerCash - totalPrice;
           change.setText(String.valueOf(changes) + "00 VND");


       }catch (Exception e) {e.printStackTrace();}
   }
   private double customerCash = 0;
   public void inputCustomerCash() throws SQLException {
//       String sql = "INSERT INTO tableOrder(customer_cash) VALUES(?)";
//       conn = JdbcUtils.getConn();

       try{
           if(employee_customer_cash.getText().isEmpty()) {
               Alert alert;
               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error Message");
               alert.setContentText("Chua nhap tien khach hang dua");
               alert.showAndWait();
           }else {
//               stm = conn.prepareStatement(sql);
//               stm.setString(1, employee_customer_cash.getText());
//               stm.executeUpdate();
               customerCash = Double.parseDouble(employee_customer_cash.getText());
           }
       }catch (Exception e){e.printStackTrace();}
   }

   public void payOrder() throws SQLException {
       employeeIdInTableOrder();
       //getEmployeeIDInSignIn();
       displayTotalPayment();
//       inputCustomerCash();
       displayChange();
       String insertReceipt = "INSERT INTO receipt (employee_id, total_price, date, customer_cash, customer_change)"
                + "VALUES(?, ?, ?, ?, ?)";
       String insertTableOrder = "INSERT INTO tableOrder(customer_cash, customer_change) VALUES(?, ?)";
       conn = JdbcUtils.getConn();
       try {
           Date date = new Date();
           java.sql.Date sqlDate = new java.sql.Date(date.getTime());
           Alert alert;
           if(order_detail.getItems().isEmpty()) {
               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error Message");
               alert.setContentText("No order to start the payment");
               alert.showAndWait();
           }else {
               alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Confirmation Message");
               alert.setContentText("Are you sure to go to payment session?");
               Optional<ButtonType> option = alert.showAndWait();
               if(option.get().equals(ButtonType.OK)) {
                   stm = conn.prepareStatement(insertReceipt);
                   stm.setString(1, String.valueOf(employeeID));
                   stm.setString(2, String.valueOf(totalPrice));
                   stm.setString(3, String.valueOf(sqlDate));
                   stm.setString(4, String.valueOf(customerCash));
                   stm.setString(5, String.valueOf(changes));

                   stm.executeUpdate();

                   PreparedStatement stm1 = conn.prepareStatement(insertTableOrder);
                   stm1.setDouble(1, customerCash);
                   stm1.setDouble(2, changes);
                   stm1.executeUpdate();
                   alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Information Message");
                   alert.setContentText("Successful!");
                   alert.showAndWait();


               }else return;
           }
       }catch (Exception e) {e.printStackTrace();}
   }

   public void resetOrder() throws SQLException {
       employeeIdInTableOrder();
       String resetID = "TRUNCATE TABLE tableOrder";
       conn = JdbcUtils.getConn();

       try{
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle("Confirmation Message");
           alert.setContentText("Are you sure you want to reset?");

           Optional<ButtonType> option = alert.showAndWait();
           if(option.get().equals(ButtonType.OK)) {
               statement = conn.createStatement();
               statement.executeUpdate(resetID);

               clearOrders();
           }else return;

       }catch (Exception e) {e.printStackTrace();}
       showTableOrder();
       clearOrders();

   }

//   private int employee_ID;
//   private int store_ID;
//   public void getEmployeeIDInSignIn() throws SQLException {
//       String sql = "SELECT * FROM employee WHERE username = '" + GetData.getUsername() + "'";
//       conn = JdbcUtils.getConn();
//       try {
//           stm = conn.prepareStatement(sql);
//           rs = stm.executeQuery();
//           if(rs.next()) {
//               employeeID = rs.getInt("employee_id");
//               store_ID = rs.getInt("store_id");
//           }
//
//       }catch (Exception e) {e.printStackTrace();}
//   }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
           showTableOrder();
           displayUsername();
           displayTotalPayment();


           //Bill
            showBillTable();
            //Customer
            showCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

