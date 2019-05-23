package com.codecool.shop.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String billingAddress;
    private String shippingAddress;

    public User(final int id, final String name, final String email, final String password, final String phoneNumber, final String billingAddress, final String shippingAddress) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBillingAdress() {
        return billingAddress;
    }

    public void setBillingAdress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getShippingAdress() {
        return shippingAddress;
    }

    public void setShippingAdress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
}