package com.amazonaws.youruserpools;

public class Contacts {

        private int id;
        private double latitude,longitude;
        private String ipaddress;

    public Contacts(int id, String ipaddress, double latitude, double longitude){

        this.id = id;
        this.ipaddress=ipaddress;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setipaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
    public void setlatitude(double latitude) {

        this.latitude = latitude;
    }
    public void setlongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getid() {
        return this.id;
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

}