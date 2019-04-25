package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.DatabaseDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJDBC extends DatabaseDao implements SupplierDao {

    private static SupplierDaoJDBC instance = null;

    private SupplierDaoJDBC() {
    }

    public static SupplierDaoJDBC getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        String query = String.format("INSERT INTO supplier(name, description)VALUES ('%s', '%s');", supplier.getName(), supplier.getDescription());
        try {
            executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {
        String query = String.format("SELECT * FROM supplier WHERE id ='%d';", id);

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            if (resultSet.next()){
                return new Supplier(resultSet.getInt("id"),
                        resultSet.getString("name"), resultSet.getString("description"));
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(int id) {
        String query = String.format("DELETE FROM supplier WHERE id = '%d';", id);
        try {
            executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() {
        String query = "SELECT * FROM supplier;";

        List<Supplier> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()){
                Supplier actualSupplier = new Supplier(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("description"));
                resultList.add(actualSupplier);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public Supplier getByName(String supplierName) {
        String query = String.format("SELECT * FROM supplier WHERE name LIKE '%s';", supplierName);

        Supplier result = null;

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()){
                result = new Supplier(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("description"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
