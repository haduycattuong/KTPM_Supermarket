package com.hdct.supermarket.pojo;
public class Product {
    private int product_id;
    private String name;
    private double price;
    private String status;
    private int store_id;

    public Product(String name, double price, String status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public Product(String name) {
        this.name = name;
    }

    public Product(int product_id, String name, double price, String status) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.status = status;
    }
    public Product(int product_id, String name, double price, String status, int store_id) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.store_id = store_id;
    }

    public Product(String name, double price, String status, int store_id) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.store_id = store_id;
    }

    public Product() {

    }

    public Product(double price) {
        this.price = price;
    }

    public Product(int product_id, double price) {
        this.product_id = product_id;
        this.price = price;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
