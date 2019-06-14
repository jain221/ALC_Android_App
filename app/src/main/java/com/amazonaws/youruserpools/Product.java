package com.amazonaws.youruserpools;

public class Product {
    private int id;
    private double latitude,longitude;
    private String ipaddress;
    private String Colume_Manfucture;
    private String Raise_and_Lower;
    private String Colume_Material;
    private String Colume_Type;
    private String column_height_from_ground;
    private String number_of_door;
    private String door_dimensions;
    private String foundation_type;
    private String bracket_type;
    private String bracket_length;
    private String estimated_column_age;
    private String lantern_manufacture;


    public Product( String ipaddress,double latitude,double longitude,String Colume_Manfucture,String Raise_and_Lower,String Colume_Material,String Colume_Type,String column_height_from_ground ,String number_of_door,String door_dimensions,String foundation_type ,String bracket_type ,String bracket_length,String estimated_column_age ,String lantern_manufacture ) {

        this.ipaddress=ipaddress;
        this.latitude=latitude;
        this.longitude=longitude;
        this.Colume_Manfucture = Colume_Manfucture;
        this.Raise_and_Lower=Raise_and_Lower;
        this.Colume_Material=Colume_Material;
        this.Colume_Type=Colume_Type;
        this.column_height_from_ground = column_height_from_ground;
        this.number_of_door =number_of_door;
        this.door_dimensions=door_dimensions;
        this.foundation_type=foundation_type;
        this.bracket_type = bracket_type;
        this.bracket_length=bracket_length;
        this.estimated_column_age = estimated_column_age;
        this.lantern_manufacture=lantern_manufacture;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public Product() {

    }


    public String getipaddress() {
        return this.ipaddress;
    }
    public double getlatitude() {
        return this.latitude;
    }
    public double getlongitude() {
        return this.longitude;
    }
    public String getColume_Manfucture() {
        return this.Colume_Manfucture;
    }
    public String getRaise_and_Lower() {
        return this.Raise_and_Lower;
    }
    public String getColume_Material() {
        return this.Colume_Material;
    }
    public String getColume_Type() {
        return this.Colume_Type;
    }
    public String getcolumn_height_from_ground() {
        return this.column_height_from_ground;
    }
    public String getnumber_of_door() {
        return this.number_of_door;
    }
    public String getdoor_dimensions() {
        return this.door_dimensions;
    }
    public String getfoundation_type() {
        return this.foundation_type;
    }
    public String getbracket_type() {
        return this.bracket_type;
    }
    public String getbracket_length() {
        return this.bracket_length;
    }
    public String getestimated_column_age() {
        return this.estimated_column_age;
    }
    public String getlantern_manufacture() {
        return this.lantern_manufacture;
    }


}
