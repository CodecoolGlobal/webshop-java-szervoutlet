package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {

    void add(ProductCategory category);
    ProductCategory find(String type);
    void remove(String type);

    List<ProductCategory> getAll();

    ProductCategory getByName(String productCategoryName);
}
