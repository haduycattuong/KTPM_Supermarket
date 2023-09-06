package com.hdct.supermarket.pojo;

public class Product_Store {
    private int id;
    private int store_id;
    private int employee_id;

    public Product_Store(int id, int store_id, int employee_id) {
        this.id = id;
        this.store_id = store_id;
        this.employee_id = employee_id;
    }

    public Product_Store(int store_id, int employee_id) {
        this.store_id = store_id;
        this.employee_id = employee_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
