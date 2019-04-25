package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.DatabaseDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoJDBC extends DatabaseDao implements ProductDao {

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


    public void add(Product product, int quantity) {
        String query = String.format("UPDATE products SET quantity = '%s' WHERE id = '%d';", quantity, product.getId());
        String orderingQuery = "SELECT * FROM products ORDER BY products.id";

        try {
            executeUpdate(query);
            executeQuery(orderingQuery);
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
            executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> resultList = new ArrayList<>(productDaoJDBC.getAll());
        resultList.removeIf( product -> product.getQuantity() == 0);
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

    public boolean isEmpty() {
        return getAll().size() == 0;
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
                    add(product, Integer.parseInt(req.getParameter("quantity")));
                }
            }
        }
    }
}
