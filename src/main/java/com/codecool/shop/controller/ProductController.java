package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.jdbc.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.jdbc.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.jdbc.SupplierDaoJDBC;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    private Supplier supplier = supplierDaoJDBC.getByName(selectedSupplierValue);
    private ProductCategory productCategory = productCategoryDaoJDBC.getByName(selectedProductValue);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if(session!=null) {
            String name=(String)session.getAttribute("name") ;
        } else resp.sendRedirect("/login");


        try {
            getProducts(productDaoJDBC, supplier, productCategory);
        } catch (Exception ignored) { }

        resp.setHeader("Content-type", "text/html; charset=utf-8");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        if (productCategoryDaoJDBC.getAll().contains(productCategory)) context.setVariable("currentCategory", productCategory);
        setVariables(context);
        context.setVariable("session", session);
        engine.process("product/index.html", context, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        selectedProductValue = req.getParameter("orderByProductCategory");
        selectedSupplierValue = req.getParameter("orderBySupplier");

        supplier = supplierDaoJDBC.getByName(selectedSupplierValue);
        productCategory = productCategoryDaoJDBC.getByName(selectedProductValue);

        getProducts(productDaoJDBC, supplier, productCategory);

        resp.sendRedirect("/");
    }

    private void setVariables(WebContext context) throws IOException {
        context.setVariable("selectedProduct", selectedProductValue);
        context.setVariable("selectedSupplier", selectedSupplierValue);
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDaoJDBC.getAll());
        context.setVariable("supplier", supplierDaoJDBC.getAll());
        context.setVariable("products", products);
    }

    private void getProducts(ProductDaoJDBC productDaoJDBC, Supplier supplier, ProductCategory productCategory) {
        if (!selectedSupplierValue.equals("None")) {
            products = productDaoJDBC.getBy(supplier);
            if (!selectedProductValue.equals("None")) {
                products = productDaoJDBC.getBy(productCategory, supplier);
            }
        } else if (!selectedProductValue.equals("None")) {
            products = productDaoJDBC.getBy(productCategory);
        } else {
            products = productDaoJDBC.getAll();
        }
    }
}