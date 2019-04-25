package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;

public class SupplierDaoMem implements SupplierDao {

    private List<Supplier> data = new ArrayList<>();
    private static SupplierDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoMem() {
    }

    public static SupplierDaoMem getInstance() {
        if (instance == null) {
            instance = new SupplierDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        supplier.setId(data.size() + 1);
        data.add(supplier);
    }

    @Override
    public Supplier find(String supp) {
        return data.stream().filter(t -> t.getName().equals(supp)).findFirst().orElse(null);
    }

    @Override
    public void remove(String supp) {
        data.remove(find(supp));
    }

    @Override
    public List<Supplier> getAll() {
        return data;
    }

    @Override
    public Supplier getByName(String supplierName){
        for(Supplier supp : data){
            if(supp.getName().equals(supplierName)){
                return supp;
            }
        }
        return null;
    };
}
