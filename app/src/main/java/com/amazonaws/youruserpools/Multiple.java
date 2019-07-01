package com.amazonaws.youruserpools;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Multiple implements Parcelable {

//    private ArrayList<String>  ipaddress1;
//    private ArrayList<Double> lattitude1;
//    private ArrayList<Double> longitude1;

    private String  ipaddress1;
    private  Double lattitude1;
    private Double longitude1;

    public Multiple() {
    }

    public Multiple(String ipaddress1, Double lattitude1, Double longitude1) {
        this.ipaddress1 = ipaddress1;
        this.lattitude1 = lattitude1;
        this.longitude1 = longitude1;
    }

    public String getIpaddress1() {
        return ipaddress1;
    }

    public void setIpaddress1(String ipaddress1) {
        this.ipaddress1 = ipaddress1;
    }

    public Double getLattitude1() {
        return lattitude1;
    }

    public void setLattitude1(Double lattitude1) {
        this.lattitude1 = lattitude1;
    }

    public Double getLongitude1() {
        return longitude1;
    }

    public void setLongitude1(Double longitude1) {
        this.longitude1 = longitude1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ipaddress1);
        dest.writeDouble(lattitude1);
        dest.writeDouble(longitude1);
    }
    //    public Multiple(ArrayList<String> ipaddress1, ArrayList<Double> lattitude1, ArrayList<Double> longitude1) {
//        this.ipaddress1 = ipaddress1;
//        this.lattitude1 = lattitude1;
//        this.longitude1 = longitude1;
//    }
//
////    public Multiple(String ipaddr, double latit, double longgg) {
////    }
//
//    public ArrayList<String> getIpaddress1() {
//        return ipaddress1;
//    }
//
//    public void setIpaddress1(ArrayList<String> ipaddress1) {
//        this.ipaddress1 = ipaddress1;
//    }
//
//    public ArrayList<Double> getLattitude1() {
//        return lattitude1;
//    }
//
//    public void setLattitude1(ArrayList<Double> lattitude1) {
//        this.lattitude1 = lattitude1;
//    }
//
//    public ArrayList<Double> getLongitude1() {
//        return longitude1;
//    }
//
//    public void setLongitude1(ArrayList<Double> longitude1) {
//        this.longitude1 = longitude1;
//    }
}
