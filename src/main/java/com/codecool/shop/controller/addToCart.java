package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.jdbc.CartDaoJDBC;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/addToCart"})
public class addToCart extends HttpServlet {

    private static CartDaoJDBC cart = CartDaoJDBC.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        cart.setCartProductQuantity(req);
        resp.sendRedirect("/");
    }
}