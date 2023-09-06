package com.hdct.supermarket.pojo;

import java.util.Date;

public class TableOrder {
    private int tableOrder_id;
    private String product_name;
    private double product_quantity;
    private double product_price;
    private double total;
    private Date date;
    private int orderID;
    private int employee_id;
    private int store_id;
    private double customer_cash;
    private double customer_change;

    public TableOrder(int tableOrder_id, String product_name, double product_quantity, double product_price, double total, int employee_id, int store_id, Date date) {
        this.tableOrder_id = tableOrder_id;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.total = total;
        this.employee_id = employee_id;
        this.store_id = store_id;
        this.date = date;
    }
    public TableOrder(int tableOrder_id, String product_name, double product_quantity, double product_price, double total, int employee_id, int store_id) {
        this.tableOrder_id = tableOrder_id;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.total = total;
        this.employee_id = employee_id;
        this.store_id = store_id;
    }

    public TableOrder(int tableOrder_id, String product_name, double product_quantity, double product_price, double total, int employee_id) {
        this.tableOrder_id = tableOrder_id;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.total = total;
        this.employee_id = employee_id;
    }
    public TableOrder(int tableOrder_id, String product_name, double product_quantity, double product_price, double total, int employee_id, Date date) {
        this.tableOrder_id = tableOrder_id;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.total = total;
        this.employee_id = employee_id;
        this.date = date;
    }

    public TableOrder(int tableOrder_id, String product_name, double product_quantity, double product_price, double total) {
        this.tableOrder_id = tableOrder_id;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.total = total;
    }
    public TableOrder(int tableOrder_id, String product_name, double product_quantity, double product_price, double total, Date date) {
        this.tableOrder_id = tableOrder_id;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.total = total;
        this.date = date;
    }

    public TableOrder() {

    }

    public double getCustomer_change() {
        return customer_change;
    }

    public void setCustomer_change(double customer_change) {
        this.customer_change = customer_change;
    }

    public int getTableOrder_id() {
        return tableOrder_id;
    }

    public void setTableOrder_id(int tableOrder_id) {
        this.tableOrder_id = tableOrder_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(double product_quantity) {
        this.product_quantity = product_quantity;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public double getCustomer_cash() {
        return customer_cash;
    }

    public void setCustomer_cash(double customer_cash) {
        this.customer_cash = customer_cash;
    }
}
