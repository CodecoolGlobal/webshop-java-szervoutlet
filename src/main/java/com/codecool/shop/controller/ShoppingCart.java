package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.jdbc.CartDaoJDBC;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet(urlPatterns = {"/shoppingcart"})
public class ShoppingCart extends HttpServlet {
    private static CartDaoJDBC cart = CartDaoJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        float sumOfProductPrices = 0.0f;
        for(Product product: cart.getAll()) sumOfProductPrices += product.getDefaultPrice() * product.getQuantity();

        context.setVariable("recipient", "World");
        context.setVariable("cart", cart.getAll());
        context.setVariable("sumOfProductValues", sumOfProductPrices);
        engine.process("product/shoppingcart.html", context, resp.getWriter());

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (isRemoveAllButtonClicked(req)) {
            removeChoosenProductFromCart(req);
        }
        else cart.setCartProductQuantity(req);
        resp.sendRedirect("/shoppingcart");
    }

    private boolean isRemoveAllButtonClicked(HttpServletRequest req) {
        return req.getParameter("removeFromCart") != null;
    }

    private void removeChoosenProductFromCart(HttpServletRequest req) {
        cart.remove(Integer.parseInt(req.getParameter("removeFromCart")));
    }
}