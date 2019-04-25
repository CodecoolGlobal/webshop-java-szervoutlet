package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.DatabaseDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC extends DatabaseDao implements ProductCategoryDao {

    private static ProductCategoryDaoJDBC instance = null;

    private ProductCategoryDaoJDBC() {
    }

    public static ProductCategoryDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        String query = String.format("INSERT INTO product_category(name, department, description)VALUES ('%s', '%s', '%s');", category.getName(), category.getDepartment(), category.getDescription());
        try {
            executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(String type) {
        String query = String.format("SELECT * FROM product_category WHERE type ='%s';", type);

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            if (resultSet.next()) {
                return new ProductCategory(resultSet.getString("name"), resultSet.getString("department"),
                        resultSet.getString("description"));
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(String type) {
        String query = String.format("DELETE FROM product_category WHERE type = '%s';", type);
        try {
            executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        String query = "SELECT * FROM product_category;";

        List<ProductCategory> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                ProductCategory actualProductCategory = new ProductCategory(resultSet.getString("name"), resultSet.getString("department"),
                        resultSet.getString("description"));
                resultList.add(actualProductCategory);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public ProductCategory getByName(String productCategoryName) {
        return null;
    }
}
