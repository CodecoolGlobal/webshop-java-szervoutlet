package com.codecool.shop.dao;

import java.sql.PreparedStatement;

public class databaseDao {
    public void add(Object object, String table){
        PreparedStatement statement = conn.createStatement("INSERT INTO ${table} VALUES()");

    }
}
