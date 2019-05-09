package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.DatabaseDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartDaoJDBC extends DatabaseDao implements ProductDao {
    private ProductCategoryDaoJDBC productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();
    private SupplierDaoJDBC supplierDaoJDBC = SupplierDaoJDBC.getInstance();
    ProductDaoJDBC productDaoJDBC = ProductDaoJDBC.getInstance();

    private static CartDaoJDBC instance = null;


    private CartDaoJDBC() {
    }

    public static CartDaoJDBC getInstance() {
        if (instance == null) {
            instance = new CartDaoJDBC();
        }
        return instance;
    }


    public void add(Product product, int quantity, int userId, boolean isAdd) {
        String query = String.format("SELECT * FROM products JOIN shoppingcart on products.id = shoppingcart.productid WHERE shoppingcart.userid = '%d';", userId);
        List<Product> products = getProducts(query);
        products.removeIf(product1 -> product1.getId() != product.getId());

        if(!products.isEmpty()){
                if(isAdd){
                    query = String.format("UPDATE shoppingcart SET quantity = '%s' WHERE productid = '%d' AND userid = '%d';", quantity + getQuantity(product, userId), product.getId(), userId);
                } else {
                    query = String.format("UPDATE shoppingcart SET quantity = '%s' WHERE productid = '%d' AND userid = '%d';", quantity, product.getId(), userId);
                }
        } else {
            query = String.format("INSERT INTO shoppingcart(productid, quantity, purchased, userid) VALUES('%s', '%s', '%s', '%s');", product.getId(), quantity, false, userId);
        }

        try {
            executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Product product) {

    }

    public int getQuantity(Product product, int userId){
        String query = String.format("SELECT quantity FROM shoppingcart WHERE userid ='%d' AND productid = '%d';", userId, product.getId());

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
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {
        String query = String.format("DELETE FROM shoppingcart WHERE productid = '%d';", id);
        try {
            executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        return null;
    }


    public List<Product> getAll(int userId) {
        String query = String.format("SELECT * FROM products JOIN shoppingcart ON products.id = shoppingcart.productid WHERE shoppingcart.userid = '%d';",userId);
        return getProducts(query);
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

    public List<Product> getProducts(String query) {
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

    public boolean isEmpty(int userid) {
        return getAll(userid).size() == 0;
    }

    public float getSumOfProductPrices(){
        float sumOfProductPrices = 0.0f;
        for(Product product: getAll()) sumOfProductPrices += product.getDefaultPrice() * product.getQuantity();
        return sumOfProductPrices;
    }

    public void setCartProductQuantity(HttpServletRequest req) {
        if (req.getParameter("removeFromCart") != null) {
            remove(Integer.parseInt(req.getParameter("removeFromCart")));
        } else {
            if (!req.getParameter("quantity").isEmpty()) {
                Product product = ProductDaoJDBC.getInstance().find(Integer.parseInt(req.getParameter("itemId")));
                if (Integer.parseInt(req.getParameter("quantity")) == 0) {
                    remove(Integer.parseInt(req.getParameter("itemId")));
                } else {
                    add(product, Integer.parseInt(req.getParameter("quantity")), 1, false);
                }
            }
        }
    }


    public void clearShoppingCart(final int userId) {
        String query = String.format("DELETE FROM shoppingcart WHERE userid = '%s';",userId);

        try {
            executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
