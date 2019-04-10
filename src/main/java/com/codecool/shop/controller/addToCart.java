package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.ProductDaoMem;
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
        if (req.getParameter("addToCart") != null) {
            cart.merge(ProductDaoMem.getInstance().find(Integer.parseInt(req.getParameter("addToCart"))), 1, Integer::sum);
            resp.sendRedirect("/");
        }
    }
}