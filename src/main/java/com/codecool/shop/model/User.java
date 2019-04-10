package com.codecool.shop.model;

class User {
    private String name;
    private String email;
    private int phoneNumber;
    private String billingAddress;
    private String shippingAddress;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setBillingAdress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingAdress() {
        return billingAddress;
    }

    public void setShippingAdress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAdress() {
        return shippingAddress;
    }
}