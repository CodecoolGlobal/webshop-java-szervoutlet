package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.jdbc.CartDaoJDBC;
import com.codecool.shop.dao.implementation.jdbc.PurchasedDaoJDBC;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/orderdetails"})
public class OrderDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("recipient", "World");
        engine.process("product/orderdetails.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false);
        int userId = (int) session.getAttribute("id");

        CartDaoJDBC cart = CartDaoJDBC.getInstance();
        PurchasedDaoJDBC purchased = PurchasedDaoJDBC.getInstance();

        List<Product> products = cart.getAll(userId);
        purchased.addAllProduct(products, userId);

        cart.clearShoppingCart(userId);


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("recipient", "World");
        resp.sendRedirect("/purchasedProducts");
    }
}