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

public class ProductDaoJDBC extends DatabaseDao implements ProductDao {

    private static ProductDaoJDBC instance = null;
    private ProductCategoryDaoJDBC productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();
    private SupplierDaoJDBC supplierDaoJDBC = SupplierDaoJDBC.getInstance();

    private ProductDaoJDBC() {
    }

    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }


    @Override
    public void add(Product product) {
        String query = String.format("INSERT INTO products(name, default_price, currency, description, supplier, product_category, quantity)VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                product.getName(), product.getDefaultPrice(), product.getDefaultCurrency(), product.getDescription(), product.getSupplier(), product.getProductCategory(), product.getQuantity());
        try {
            executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        String query = String.format("SELECT * FROM products WHERE id ='%d';", id);

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            if (resultSet.next()) {
                return new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDaoJDBC.find(resultSet.getInt("product_category")),
                        supplierDaoJDBC.find(resultSet.getInt("supplier")));
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
        String query = String.format("DELETE FROM products WHERE id = '%d';", id);
        try {
            executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products;";

        List<Product> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                Product actualProduct = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDaoJDBC.find(resultSet.getInt("product_category")),
                        supplierDaoJDBC.find(resultSet.getInt("supplier")));
                resultList.add(actualProduct);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        String query = "SELECT * FROM products WHERE supplier ='" + supplier.getId() + "';";

        return getProducts(query);
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        String query = "SELECT * FROM products WHERE product_category ='" + productCategory.getId() + "';";

        return getProducts(query);
    }

    public List<Product> getProducts(String query) {
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
                        supplierDaoJDBC.find(resultSet.getInt("supplier")));
                resultList.add(product);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory, Supplier supplier) {
        String query = "SELECT * FROM products WHERE product_category ='" + productCategory.getId() + "'AND supplier ='" + supplier.getId() + "';";

        return getProducts(query);
    }

    public List<Product> getProducts(ProductDaoJDBC productDaoJDBC, Supplier supplier, ProductCategory productCategory, String selectedSupplierValue, String selectedProductValue) {
        if (!selectedSupplierValue.equals("None")) {
            if (!selectedProductValue.equals("None")) {
                return productDaoJDBC.getBy(productCategory, supplier);
            }
            return productDaoJDBC.getBy(supplier);

        } else if (!selectedProductValue.equals("None")) {
            return productDaoJDBC.getBy(productCategory);
        } else {
            return productDaoJDBC.getAll();
        }
    }
}
