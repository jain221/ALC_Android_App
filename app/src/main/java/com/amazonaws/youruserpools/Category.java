package com.amazonaws.youruserpools;

public class Category {
    private int id;
    private String manufacturer_name;


    public Category(int id, String manufacturer_name) {
        this.id = id;
        this.manufacturer_name=manufacturer_name;

    }

    public int getid() {
        return this.id;
    }
    public String getName() {
        return this.manufacturer_name;
    }


}
