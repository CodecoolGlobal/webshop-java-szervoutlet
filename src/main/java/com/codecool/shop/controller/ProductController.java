package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
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
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private String selectedProductValue = "None";
    private String selectedSupplierValue = "None";
    private List<Product> products;

    ProductDaoJDBC productDaoJDBC = ProductDaoJDBC.getInstance();
    ProductCategoryDaoJDBC productCategoryDaoJDBC = ProductCategoryDaoJDBC.getInstance();
    SupplierDaoJDBC supplierDaoJDBC = SupplierDaoJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        Supplier supplier = supplierDataStore.getByName(selectedSupplierValue);
        ProductCategory productCategory = productCategoryDataStore.getByName(selectedProductValue);

        try {
            getProducts(productDataStore, supplier, productCategory);
        } catch (Exception ignored) {}


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        setVariables(resp, productCategoryDataStore, supplierDataStore, engine, context);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        selectedProductValue = req.getParameter("orderByProductCategory");
        selectedSupplierValue = req.getParameter("orderBySupplier");

        Supplier supplier = supplierDataStore.getByName(selectedSupplierValue);
        ProductCategory productCategory = productCategoryDataStore.getByName(selectedProductValue);


        getProducts(productDataStore, supplier, productCategory);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());


        if (productCategoryDataStore.getAll().contains(productCategory)) {
            context.setVariable("currentCategory", productCategory);
        }
        setVariables(resp, productCategoryDataStore, supplierDataStore, engine, context);

    }

    private void setVariables(HttpServletResponse resp, ProductCategoryDao productCategoryDataStore, SupplierDao supplierDataStore, TemplateEngine engine, WebContext context) throws IOException {
        context.setVariable("selectedProduct", selectedProductValue);
        context.setVariable("selectedSupplier", selectedSupplierValue);
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDaoJDBC.getAll());
        context.setVariable("supplier", supplierDaoJDBC.getAll());
        context.setVariable("products", products);
        engine.process("product/index.html", context, resp.getWriter());
    }

    private void getProducts(ProductDao productDataStore, Supplier supplier, ProductCategory productCategory) {
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