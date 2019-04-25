package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.jdbc.ProductDaoJDBC;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = {"/shoppingcart"})
public class ShoppingCart extends HttpServlet {
    static HashMap<Product, Integer> cart = new HashMap<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("recipient", "World");
        context.setVariable("cart", cart);
        float sumOfProductPrices = 0.0f;
        for(Map.Entry<Product, Integer> entry: cart.entrySet()){
            sumOfProductPrices += entry.getKey().getDefaultPrice()*entry.getValue();
        }
        context.setVariable("sumOfProductValues", sumOfProductPrices);
        engine.process("product/shoppingcart.html", context, resp.getWriter());

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (isRemoveAllButtonClicked(req)) {
            removeChoosenProductFromCart(req);
        }
        else {
            getQuantity(req);
        }
        resp.sendRedirect("/shoppingcart");
    }

    static void getQuantity(HttpServletRequest req) {
        if (!req.getParameter("quantity").isEmpty()) {
            Product product = ProductDaoJDBC.getInstance().find(Integer.parseInt(req.getParameter("itemId")));
            if (Integer.parseInt(req.getParameter("quantity")) == 0) {
                cart.remove(product);
            } else {
                cart.put(product, Integer.parseInt(req.getParameter("quantity")));
            }
        }
    }

    private boolean isRemoveAllButtonClicked(HttpServletRequest req) {
        return req.getParameter("removeFromCart") != null;
    }

    private void removeChoosenProductFromCart(HttpServletRequest req) {
        cart.remove(ProductDaoJDBC.getInstance().find(Integer.parseInt(req.getParameter("removeFromCart"))));
    }
}