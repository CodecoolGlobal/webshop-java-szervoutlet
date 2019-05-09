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

public class PurchasedDaoJDBC extends DatabaseDao implements ProductDao {
    private CartDaoJDBC cart = CartDaoJDBC.getInstance();
    private SupplierDaoJDBC supplierDaoJDBC = SupplierDaoJDBC.getInstance();
    private ProductCategoryDaoJDBC productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();

    private static PurchasedDaoJDBC instance;

    private PurchasedDaoJDBC() {
    }

    public static PurchasedDaoJDBC getInstance() {
        if (instance == null) {
            instance = new PurchasedDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(final Product product) {

    }

    public void addAllProduct(List<Product> products, int userid){
        for (final Product product : products) {
            int quantity = cart.getQuantity(product, userid);
            String query = String.format("INSERT INTO purchased_products(productid, quantity, userid) VALUES('%s', '%s', '%s');",product.getId(), quantity, userid);

            try {
                executeQuery(query);
            } catch (SQLException e) {
            }
        }
    }

    public int getQuantity(Product product, int userId){
        String query = String.format("SELECT quantity FROM purchased_products WHERE userid ='%d' AND productid = '%d';", userId, product.getId());

        int quantity = 0;

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                quantity = resultSet.getInt("quantity");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantity;
    }

    @Override
    public Product find(final int id) {
        return null;
    }

    @Override
    public void remove(final int id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    public List<Product> getAll(int userid){
        String query = String.format("SELECT * FROM products JOIN purchased_products ON products.id = purchased_products.productid WHERE purchased_products.userid = '%d';", userid);
        return getProducts(query);
    }

    @Override
    public List<Product> getBy(final Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(final ProductCategory productCategory) {
        return null;
    }

    @Override
    public List<Product> getBy(final ProductCategory productCategory, final Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getProducts(final String query) {
        List<Product> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
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
}

