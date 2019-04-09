package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/shoppingcart"})
public class ShoppingCart extends HttpServlet {
    static ArrayList<Product> cart = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("recipient", "World");
        context.setVariable("cart", cart);
        engine.process("product/shoppingcart.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("removeFromCart") != null) {
            cart.remove(ProductDaoMem.getInstance().find(Integer.parseInt(req.getParameter("removeFromCart"))));
            resp.sendRedirect("/shoppingcart");
        }
    }
}