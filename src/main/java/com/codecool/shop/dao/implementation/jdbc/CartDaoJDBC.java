package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.DatabaseDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartDaoJDBC extends DatabaseDao implements ProductDao {

    ProductCategoryDaoJDBC productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();
    SupplierDaoJDBC supplierDaoJDBC = SupplierDaoJDBC.getInstance();

    private static CartDaoJDBC instance = null;


    private CartDaoJDBC() {
    }

    public static CartDaoJDBC getInstance() {
        if (instance == null) {
            instance = new CartDaoJDBC();
        }
        return instance;
    }



    public void add(Product product, int quantity) {
        String query = String.format("UPDATE products SET quantity = '%s' WHERE id = '%d';", quantity, product.getId());
        try {
            executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {
        String query = String.format("DELETE FROM products WHERE id = '%d';", id);
        try {
            executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products WHERE quantity > 0;";

        List<Product> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDaoJDBC.find(resultSet.getInt("product_category")),
                        supplierDaoJDBC.find(resultSet.getInt("supplier")),
                        resultSet.getInt("quantity"));
                resultList.add(product);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory, Supplier supplier) {
        return null;
    }

    public boolean isEmpty(){
        return getAll().size() == 0;
    }
}
