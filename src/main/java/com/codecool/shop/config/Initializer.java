package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier burkina = new Supplier("Burkina Faso", "From the heart of the West Africa");
        supplierDataStore.add(burkina);
        Supplier senegal = new Supplier("South Senegal", "From the heart of the South Africa");
        supplierDataStore.add(senegal);
        Supplier mozambic = new Supplier("Mozambic", "From the heart of Mozambic");
        supplierDataStore.add(mozambic);
        Supplier tibet = new Supplier("Tibet", "Asian high quality from the goats");
        supplierDataStore.add(tibet);
        Supplier marocco = new Supplier("Marocco", "Asian high quality from the goats");
        supplierDataStore.add(marocco);
        Supplier sudan = new Supplier("West Sudan", "Asian high quality from the goats");
        supplierDataStore.add(sudan);


        //setting up a new product category
        ProductCategory eye = new ProductCategory("Eye", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(eye);
        ProductCategory liver = new ProductCategory("Liver", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(liver);
        ProductCategory heart = new ProductCategory("Heart", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(heart);
        ProductCategory brain = new ProductCategory("Brain", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(brain);
        ProductCategory kindey = new ProductCategory("Kidney", "Bionic", "Parts of the Human body.");
        productCategoryDataStore.add(kindey);
        ProductCategory ear = new ProductCategory("Ear", "Bionic", "Parts of the Human body.");
        productCategoryDataStore.add(ear);
        ProductCategory lung = new ProductCategory("Lung", "Bionic", "Parts of the Human body.");
        productCategoryDataStore.add(lung);

        //setting up products and printing it
        productDataStore.add(new Product("Stock Brain", 1000, "USD", "Traditional central processing unit of a Human body", brain, marocco));
        productDataStore.add(new Product("Almost new Liver", 750, "USD", "Freshly imported liver, package damaged. Self-healing item!", liver, burkina));
        productDataStore.add(new Product("Kidney", 400, "USD", "50% OFF", kindey, burkina));
        productDataStore.add(new Product("Bionic heart", 1900, "USD", "Send a heart for your friend now in real life, not just on the Messenger!", heart, mozambic));
        productDataStore.add(new Product("Left pitvar", 190, "USD", "Left pitvar for everyone! Fresh, new from Burkina Faso. Just with gifted tounqe!!!", heart, senegal));
        productDataStore.add(new Product("Eyes", 470, "USD", "Behind blue eyes! - The Who!!!", eye, tibet));
        productDataStore.add(new Product("Ear", 470, "USD", "Your grandma didn't hear that you make embarrassing comments for his shape? Just buy an ear and wait for the big lovely slap ", ear, mozambic));
        productDataStore.add(new Product("Remanufactured Ear", 170, "USD", " Buy this ear for your grandma!! Laugh on him with your family", ear, senegal));
        productDataStore.add(new Product("Bloody Eye", 260, "USD", "Eyes for screaming all man on the metro?? There's your product!! No 10% sale", eye, sudan));
        productDataStore.add(new Product("Stressed heart", 920, "USD", "Used heart for sale, while the stocks last", heart, sudan));
        productDataStore.add(new Product("Half ready Lungs", 920, "USD", "Half fresh, half dead lung for the brave mens.", lung, tibet));
        productDataStore.add(new Product("calculus(veseko)", 70, "USD", "A real jewellery for your partner, especially at the dinner.", kindey, marocco));
    }
}
