package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoMemTest {

    private Supplier testSupplier = new Supplier("Test", "TestSupplier");

    @BeforeEach
    void setTestSupplier(){
        SupplierDaoMem.getInstance().add(testSupplier);
    }

    @AfterEach
    void removeTestSupplier(){
        SupplierDaoMem.getInstance().remove(testSupplier.getId());
    }

    @Test
    void testInstanceIsNotNull() {
        assertNotNull(SupplierDaoMem.getInstance());
    }

    @Test
    void testSupplierIsAdded() {
        assertNotNull(SupplierDaoMem.getInstance().getByName("Test"));
    }

    @Test
    void testSupplierIsFind() {
        assertSame(testSupplier, SupplierDaoMem.getInstance().find(testSupplier.getId()));

    }

    @Test
    void remove() {
        SupplierDaoMem.getInstance().remove(testSupplier.getId());
        assertNull(SupplierDaoMem.getInstance().find(testSupplier.getId()));
    }

    @Test
    void testGetAllSupplier() {
        assertSame(SupplierDaoMem.getInstance().getAll().size(), 1);
    }

    @Test
    void testGetSupplierByName() {
        assertEquals(testSupplier, SupplierDaoMem.getInstance().getByName("Test"));
    }
}