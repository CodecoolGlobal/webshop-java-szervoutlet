package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.util.List;

public interface UserDao {

    void add(String name, String email, String password, int phoneNumber, String shippingAddress, String billingAddress);
    User find(String email, String password);
    void remove(int id);

    List<User> getAll();

    User getByName(String userName);
}
