package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.jdbc.CartDaoJDBC;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = {"/AddToCart"})
public class AddToCart extends HttpServlet {

    private static CartDaoJDBC cart = CartDaoJDBC.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        int userId = (int)session.getAttribute("id");

        cart.getCartProductQuantity(req, userId, true);
        resp.sendRedirect("/");
    }
}