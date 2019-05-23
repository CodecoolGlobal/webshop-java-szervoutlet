package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoTest {

    private SupplierDao supplierDataStore;
    private Supplier test1 = new Supplier(1, "test1", "This is a test supplier1");
    private Supplier test2 = new Supplier(2, "test2", "This is a test supplier2");
    private int lastIndex;

    @BeforeEach
    void init() {
        supplierDataStore = SupplierDaoMem.getInstance();
        supplierDataStore.add(test1);
        supplierDataStore.add(test2);
        List<Supplier> data;
        data = supplierDataStore.getAll();
        lastIndex = data.size() - 1;
    }

    @AfterEach
    void after() {
        supplierDataStore.remove(1);
        supplierDataStore.remove(2);
    }


    @Test
    void testIsSupplierAdded() {
        String result = "test2";
        assertEquals(result, supplierDataStore.getAll().get(lastIndex).getName());
    }

    @Test
    void testIsRightIdFound() {
        String result = "test1";
        assertEquals(result, supplierDataStore.find(1).getName());
    }

    @Test
    void testIsElementRemoved() {
        supplierDataStore.remove(1);
        assertNull(supplierDataStore.find(1));
    }

    @Test
    void testIsGotAll() {
        int result = 2;
        assertEquals(result, supplierDataStore.getAll().size());
    }

}