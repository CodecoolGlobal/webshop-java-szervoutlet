package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    private ProductDao productDao = ProductDaoMem.getInstance();
    private Supplier testSupplier = new Supplier(1, "test", "description");
    private ProductCategory testProductCat = new ProductCategory(1, "name", "department", "description");
    private Product testProduct = new Product(1, "test", 1, "USD", "description", testProductCat, testSupplier, 1);

    @BeforeEach
    void init() {
        productDao.add(testProduct);
    }

    @AfterEach
    void after() {
        productDao.remove(1);
    }

    @Test
    void testIsOneEqualsToOne() {assertEquals(1,1);}

    @Test
    void testIsProductDaoNotNull() {
        assertNotNull(productDao);
    }

    @Test
    void testIsProductNotNull() {
        List<Product> products = productDao.getAll();
        assertNotNull(products.get(0));
    }

    @Test
    void testIsProductAdded() {
        productDao.add(new Product(2, "test", 2, "USD", "description", new ProductCategory(2, "name", "department", "description"), new Supplier(2, "test", "description"),1));
        List<Product> products = productDao.getAll();
        assertEquals(2, products.get(products.size() - 1).getId());
        productDao.remove(products.get(products.size() - 1).getId());
    }

    @Test
    void testIsProductFound() {
        List<Product> products = productDao.getAll();
        assertEquals(products.get(0), productDao.find(products.get(0).getId()));
    }

    @Test
    void testIsProductRemoved() {
        Product p = new Product(3, "test", 4, "USD", "description", new ProductCategory(3, "name", "department", "description"), new Supplier(3, "test", "description"),1);
        productDao.add(p);
        int id = p.getId();
        productDao.remove(id);
        assertNull(productDao.find(id));
    }

    @Test
    void testAreAllProductsGot() {
        assertEquals(1, productDao.getAll().size());
    }

    @Test
    void testIsProductGotBySupplier() {
        assertEquals(testProduct, productDao.getBy(testSupplier).get(0));
    }

    @Test
    void testIsProductGotByCategory() {
        assertEquals(testProduct, productDao.getBy(testProductCat).get(0));
    }
}