package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.jdbc.PurchasedDaoJDBC;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@WebServlet(urlPatterns = {"/purchasedProducts"})
public class PurchasedProducts extends HttpServlet {
    private static PurchasedDaoJDBC purchasedDaoJDBC = PurchasedDaoJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        HttpSession session = req.getSession(false);
        Integer userId = (Integer) session.getAttribute("id");

        context.setVariable("recipient", "World");
        context.setVariable("products", purchasedDaoJDBC.getAll(userId));
        context.setVariable("purchaseDao", purchasedDaoJDBC);
        context.setVariable("session", session);
        resp.setHeader("Content-type", "text/html; charset=utf-8");
        engine.process("product/purchasedProducts.html", context, resp.getWriter());
    }
}
