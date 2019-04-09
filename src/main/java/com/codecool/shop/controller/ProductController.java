package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
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

import static com.codecool.shop.controller.ShoppingCart.cart;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.find(1));
        context.setVariable("supplier", supplierDataStore.getAll());
        context.setVariable("products", productDataStore.getAll());
        engine.process("product/index.html", context, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products;

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        String selectedProductValue = req.getParameter("orderByProductCategory");
        String selectedSupplierValue = req.getParameter("orderBySupplier");

        Supplier supplier = supplierDataStore.getByName(selectedSupplierValue);
        ProductCategory productCategory = productCategoryDataStore.getByName(selectedProductValue);


        if(!selectedSupplierValue.equals("None")){
            products = productDataStore.getBy(supplier);
            if(!selectedProductValue.equals("None")){
                products = productDataStore.getBy(productCategory, supplier);
            }
        } else if(!selectedProductValue.equals("None")){
            products = productDataStore.getBy(productCategory);
        } else{
            products = productDataStore.getAll();
        }


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());


        if (productCategoryDataStore.getAll().contains(productCategory)) {
            context.setVariable("currentCategory", productCategory);
        }
        context.setVariable("selectedProduct", selectedProductValue);
        context.setVariable("selectedSupplier", selectedSupplierValue);
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("supplier", supplierDataStore.getAll());
        context.setVariable("products", products);
        engine.process("product/index.html", context, resp.getWriter());
    }

    if (req.getParameter("addToCart") != null) {
        cart.add(ProductDaoMem.getInstance().find(Integer.parseInt(req.getParameter("addToCart"))));
        resp.sendRedirect("/");
    }
}
