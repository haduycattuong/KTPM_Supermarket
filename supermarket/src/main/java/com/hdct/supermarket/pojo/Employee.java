package com.hdct.supermarket.pojo;

public class Employee {
    private int employee_id;
    private String first_name;
    private String last_name;
    private int phone;
    private String address;
    private String username;
    private String password;
    private String birth_date;
    private String gender;
    private int store_id;


    public Employee(){}


    public Employee(int employee_id, String first_name, String last_name, String username, String password, String gender) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public Employee(int employee_id, String first_name, String last_name, String username, String password, String gender, int store_id) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.store_id = store_id;
    }

    public Employee(int customer_id, String first_name, String last_name, String phone, String rank) {
    }

    public Employee(String first_name, String last_name, int store_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.store_id = store_id;
    }

    public Employee(int employee_id, String first_name, String last_name, int store_id) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.store_id = store_id;
    }

    public Employee(int employee_id, String first_name, String last_name, String gender) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }

    public Employee(String first_name, String last_name, String gender) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }
}
