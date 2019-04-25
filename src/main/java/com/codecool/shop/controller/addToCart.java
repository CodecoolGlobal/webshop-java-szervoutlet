package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;

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
            cart.remove(ProductDaoMem.getInstance().find(Integer.parseInt(req.getParameter("removeFromCart"))));
        }
        else {
            getCartProductQuantity(req);
        }
        resp.sendRedirect("/");
    }

    static void getCartProductQuantity(HttpServletRequest req) {
        if (!req.getParameter("quantity").isEmpty()) {
            Product product = ProductDaoMem.getInstance().find(Integer.parseInt(req.getParameter("itemId")));
            if (Integer.parseInt(req.getParameter("quantity")) == 0) {
                cart.remove(product);
            } else {
                cart.put(product, Integer.parseInt(req.getParameter("quantity")));
            }
        }
    }
}