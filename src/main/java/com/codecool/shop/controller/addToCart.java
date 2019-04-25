package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.jdbc.CartDaoJDBC;
import com.codecool.shop.dao.implementation.jdbc.ProductDaoJDBC;
import com.codecool.shop.model.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/addToCart"})
public class addToCart extends HttpServlet {

    static CartDaoJDBC cart = CartDaoJDBC.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (req.getParameter("removeFromCart") != null) {
            cart.remove(Integer.parseInt(req.getParameter("removeFromCart")));
        }
        else {
            getCartProductQuantity(req);
        }
        resp.sendRedirect("/");
    }

    static void getCartProductQuantity(HttpServletRequest req) {
        if (!req.getParameter("quantity").isEmpty()) {
            Product product = ProductDaoJDBC.getInstance().find(Integer.parseInt(req.getParameter("itemId")));
            if (Integer.parseInt(req.getParameter("quantity")) == 0) {
                cart.remove(Integer.parseInt(req.getParameter("itemId")));
            } else {
                cart.add(product, Integer.parseInt(req.getParameter("quantity")));
            }
        }
    }


}