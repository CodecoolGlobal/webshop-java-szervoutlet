package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;

import java.util.List;

public interface SupplierDao {

    void add(Supplier supplier);
    Supplier find(String supp);
    void remove(String supp);

    List<Supplier> getAll();

    Supplier getByName(String supplierName);
}
