package com.codecool.shop.model;


import java.lang.reflect.Field;

public class BaseModel {

    protected int id;
    String name;
    String description;
    int quantity;

    BaseModel(String name) {
        this.name = name;
    }

    BaseModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    BaseModel(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(this);
                if (value != null) {
                    sb.append(field.getName()).append(":").append(value).append(",");
                }
            } catch (IllegalAccessException ignored) {

            }
        }
        return sb.toString();
    }
}
