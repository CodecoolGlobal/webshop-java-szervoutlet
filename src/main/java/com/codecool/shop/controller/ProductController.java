package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.jdbc.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.jdbc.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.jdbc.SupplierDaoJDBC;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private String selectedProductValue = "None";
    private String selectedSupplierValue = "None";
    private List<Product> products;

    private ProductDaoJDBC productDaoJDBC = ProductDaoJDBC.getInstance();
    private ProductCategoryDaoJDBC productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();
    private SupplierDaoJDBC supplierDaoJDBC = SupplierDaoJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Supplier supplier = supplierDaoJDBC.getByName(selectedSupplierValue);
        ProductCategory productCategory = productCategoryDaoJDBC.getByName(selectedProductValue);

        try {
            productDaoJDBC.getProducts(productDaoJDBC, supplier, productCategory, selectedSupplierValue, selectedProductValue);
        } catch (Exception ignored) {
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        setVariables(resp, engine, context);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        selectedProductValue = req.getParameter("orderByProductCategory");
        selectedSupplierValue = req.getParameter("orderBySupplier");

        Supplier supplier = supplierDaoJDBC.getByName(selectedSupplierValue);
        ProductCategory productCategory = productCategoryDaoJDBC.getByName(selectedProductValue);

        products = productDaoJDBC.getProducts(productDaoJDBC, supplier, productCategory, selectedSupplierValue, selectedProductValue);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        if (productCategoryDaoJDBC.getAll().contains(productCategory)) {
            context.setVariable("currentCategory", productCategory);
        }
        setVariables(resp, engine, context);

    }

    private void setVariables(HttpServletResponse resp, TemplateEngine engine, WebContext context) throws IOException {
        context.setVariable("selectedProduct", selectedProductValue);
        context.setVariable("selectedSupplier", selectedSupplierValue);
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDaoJDBC.getAll());
        context.setVariable("supplier", supplierDaoJDBC.getAll());
        context.setVariable("products", products);
        engine.process("product/index.html", context, resp.getWriter());
    }
}