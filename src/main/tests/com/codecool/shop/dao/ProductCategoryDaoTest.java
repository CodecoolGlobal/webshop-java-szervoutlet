package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {

    private ProductCategoryDao productDataStore;
    private ProductCategory test1 = new ProductCategory(1, "test1", "department1","description1");
    private ProductCategory test2 = new ProductCategory(2, "test2", "department2","description2");
    private int lastIndex;

    @BeforeEach
    void init() {
        productDataStore = ProductCategoryDaoMem.getInstance();
        productDataStore.add(test1);
        productDataStore.add(test2);
        List<ProductCategory> data;
        data = productDataStore.getAll();
        lastIndex = data.size() - 1;
    }

    @AfterEach
    void after() {
        productDataStore.remove(1);
        productDataStore.remove(2);
    }


    @Test
    void testIsSupplierAdded() {
        String result = "test2";
        assertEquals(result, productDataStore.getAll().get(lastIndex).getName());
    }

    @Test
    void testIsRightIdFound() {
        String result = "test1";
        assertEquals(result, productDataStore.find(1).getName());
    }

    @Test
    void testIsElementRemoved() {
        productDataStore.remove(1);
        assertNull(productDataStore.find(1));
    }

    @Test
    void testIsGotAll() {
        int result = 2;
        assertEquals(result, productDataStore.getAll().size());
    }

}