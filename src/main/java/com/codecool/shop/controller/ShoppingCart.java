package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.jdbc.CartDaoJDBC;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = {"/shoppingcart"})
public class ShoppingCart extends HttpServlet {
    private static CartDaoJDBC cart = CartDaoJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        HttpSession session = req.getSession(false);
        Integer userId = (Integer) session.getAttribute("id");

        context.setVariable("recipient", "World");
        context.setVariable("cart", cart.getAll(userId));
        context.setVariable("cartDao", cart);
        float sumOfProductPrices = cart.getSumOfProductPrices(userId);
        context.setVariable("session", session);
        context.setVariable("sumOfProductValues", sumOfProductPrices);
        resp.setHeader("Content-type", "text/html; charset=utf-8");
        engine.process("product/shoppingcart.html", context, resp.getWriter());

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        int userId = (int) session.getAttribute("id");

        if (isRemoveAllButtonClicked(req)) {
            removeChoosenProductFromCart(req);
        } else cart.getCartProductQuantity(req, userId, false);
        resp.sendRedirect("/shoppingcart");
    }

    private boolean isRemoveAllButtonClicked(HttpServletRequest req) {
        return req.getParameter("removeFromCart") != null;
    }

    private void removeChoosenProductFromCart(HttpServletRequest req) {
        cart.remove(Integer.parseInt(req.getParameter("removeFromCart")));
    }
}