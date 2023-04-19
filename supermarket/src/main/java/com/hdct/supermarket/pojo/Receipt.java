package com.hdct.supermarket.pojo;

import java.util.Date;

public class Receipt {
    private int receipt_id;
    private int employee_id;
    private int store_id;
    private int tableOrder_id;
    private double total_price;
    private Date date;
    private double customer_cash;
    private double customer_change;

    public Receipt(int receipt_id, int tableOrder_id, double total_price) {
        this.receipt_id = receipt_id;
        this.tableOrder_id = tableOrder_id;
        this.total_price = total_price;
    }

    public Receipt(int receipt_id, int employee_id, int store_id, int tableOrder_id, double total_price) {
        this.receipt_id = receipt_id;
        this.employee_id = employee_id;
        this.store_id = store_id;
        this.tableOrder_id = tableOrder_id;
        this.total_price = total_price;
    }

    public Receipt(int receipt_id, int employee_id, int store_id, int tableOrder_id, double total_price, Date date) {
        this.receipt_id = receipt_id;
        this.employee_id = employee_id;
        this.store_id = store_id;
        this.tableOrder_id = tableOrder_id;
        this.total_price = total_price;
        this.date = date;
    }

    public Receipt(double customer_cash, double customer_change, double total_price) {
        this.customer_cash = customer_cash;
        this.customer_change = customer_change;
        this.total_price= total_price;
    }

    public double getCustomer_cash() {
        return customer_cash;
    }

    public void setCustomer_cash(double customer_cash) {
        this.customer_cash = customer_cash;
    }

    public double getCustomer_change() {
        return customer_change;
    }

    public void setCustomer_change(double customer_change) {
        this.customer_change = customer_change;
    }

    public int getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(int receipt_id) {
        this.receipt_id = receipt_id;
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

    public int getTableOrder_id() {
        return tableOrder_id;
    }

    public void setTableOrder_id(int tableOrder_id) {
        this.tableOrder_id = tableOrder_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
