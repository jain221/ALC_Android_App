package com.amazonaws.youruserpools;

class Colume_Manf {
    private int id;
    private String manufacturer_name;
    private String flag;


    public Colume_Manf(int anInt, String manufacturer_name) {
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

class Raise_and_Low {
    private int id;
    private String flag;

    public Raise_and_Low(int id, String flag) {
        this.id = id;
        this.flag = flag;
    }



    public int getid() {
        return this.id;
    }

    public String getName1() {
        return this.flag;
    }
}


class Colume_Material {
    private int id;
    private String material_name;

    public Colume_Material(int id, String material_name) {
        this.id = id;
        this.material_name = material_name;
    }



    public int getid() {
        return this.id;
    }

    public String getName2() {
        return this.material_name;
    }
}

class Colume_Type {
    private int id;
    private String type_name;

    public Colume_Type(int id, String type_name) {
        this.id = id;
        this.type_name = type_name;
    }

    public int getid() {
        return this.id;
    }

    public String getName3() {
        return this.type_name;
    }
}

class Colume_Hight {
    private int id;
    private String column_height;


    public Colume_Hight(int id, String column_height) {
        this.id = id;
        this.column_height = column_height;

    }

    public int getid() {
        return this.id;
    }

    public String getName4() {
        return this.column_height;
    }
}

class Number_of_Doors {
    private int id;
    private String number_of_door;


    public Number_of_Doors(int id, String number_of_door) {
        this.id = id;
        this.number_of_door = number_of_door;

    }

    public int getid() {
        return this.id;
    }

    public String getName5() {
        return this.number_of_door;
    }
}

class Door_Dimension {
    private int id;
    private String number_of_door;


    public Door_Dimension(int id, String number_of_door) {
        this.id = id;
        this.number_of_door = number_of_door;

    }

    public int getid() {
        return this.id;
    }

    public String getName6() {
        return this.number_of_door;
    }
}


class Foundation_type {
    private int id;
    private String foundation_type;


    public Foundation_type(int id, String foundation_type) {
        this.id = id;
        this.foundation_type = foundation_type;

    }

    public int getid() {
        return this.id;
    }

    public String getName7() {
        return this.foundation_type;
    }
}


class Colume_Bracket {
    private int id;
    private String bracket_type;


    public Colume_Bracket(int id, String bracket_type) {
        this.id = id;
        this.bracket_type = bracket_type;

    }

    public int getid() {
        return this.id;
    }

    public String getName8() {
        return this.bracket_type;
    }
}


class Bracket_Length {
    private int id;
    private String bracket_length;


    public Bracket_Length(int id, String bracket_length) {
        this.id = id;
        this.bracket_length = bracket_length;

    }

    public int getid() {
        return this.id;
    }

    public String getName9() {
        return this.bracket_length;
    }
}

class Lantern_Estimated_Age {
    private int id;
    private String column_ages;


    public Lantern_Estimated_Age(int id, String column_ages) {
        this.id = id;
        this.column_ages = column_ages;

    }

    public int getid() {
        return this.id;
    }

    public String getName10() {
        return this.column_ages;
    }
}
class Asset_use{
    private int id;
    private String asset_use;

    public Asset_use(int id, String asset_use) {
        this.id = id;
        this.asset_use = asset_use;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsset_use() {
        return asset_use;
    }

    public void setAsset_use(String asset_use) {
        this.asset_use = asset_use;
    }
}

class Lanten_Manf {
    private int id;
    private String lantern_manufacturer;


    public Lanten_Manf(int id, String lantern_manufacturer) {
        this.id = id;
        this.lantern_manufacturer = lantern_manufacturer;

    }

    public int getid() {
        return this.id;
    }

    public String getName11() {
        return this.lantern_manufacturer;
    }
}

class lantern_model {
    private int id;
    private String lantern_model;

    public lantern_model(int id,String lantern_model) {
        this.id = id;
        this.lantern_model = lantern_model;
    }
    public int getid() {
        return this.id;
    }
    public String getLantern_model() {
        return lantern_model;
    }
}