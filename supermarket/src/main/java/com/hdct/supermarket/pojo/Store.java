package com.hdct.supermarket.pojo;

public class Store {
    private int store_id;
    private String name;
    private String address;

    public Store(){}
    public Store(int store_id, String name, String address) {
        this.store_id = store_id;
        this.name = name;
        this.address = address;
    }

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
