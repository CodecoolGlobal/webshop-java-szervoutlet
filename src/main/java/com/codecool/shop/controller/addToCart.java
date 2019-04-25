package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.jdbc.ProductDaoJDBC;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.codecool.shop.controller.ShoppingCart.cart;


@WebServlet(urlPatterns = {"/addToCart"})
public class addToCart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("removeFromCart") != null) {
            cart.remove(ProductDaoJDBC.getInstance().find(Integer.parseInt(req.getParameter("removeFromCart"))));
        }
        else ShoppingCart.getQuantity(req);
        resp.sendRedirect("/");
    }
}