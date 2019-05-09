package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.jdbc.CartDaoJDBC;
import com.codecool.shop.dao.implementation.jdbc.ProductDaoJDBC;
import com.codecool.shop.model.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = {"/AddToCart"})
public class AddToCart extends HttpServlet {

    static CartDaoJDBC cart = CartDaoJDBC.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        int userId = (int)session.getAttribute("id");


        getCartProductQuantity(req, userId, true);
        resp.sendRedirect("/");
    }

    static void getCartProductQuantity(HttpServletRequest req, int userId, boolean isAdd) {
        if (!req.getParameter("quantity").isEmpty()) {
            Product product = ProductDaoJDBC.getInstance().find(Integer.parseInt(req.getParameter("itemId")));
            if (Integer.parseInt(req.getParameter("quantity")) == 0) {
                cart.remove(Integer.parseInt(req.getParameter("itemId")));
            } else {
                cart.add(product, Integer.parseInt(req.getParameter("quantity")), userId, isAdd);
            }
        }
    }
}