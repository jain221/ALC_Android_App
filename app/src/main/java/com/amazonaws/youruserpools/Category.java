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

//
//    private int id;
//    private String Column_material;
//
//    public Category(){}
//
//    public Category(int id, String Column_material){
//        this.id = id;
//        this.Column_material=Column_material;
//
//    }
//
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setName(String Column_material) {
//        this.Column_material = Column_material;
//    }
//
//    public int getId() {
//        return this.id;
//    }
//    public String getName() {
//        return this.Column_material;
//    }


}
