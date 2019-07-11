package com.amazonaws.youruserpools;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Multiple implements Serializable {

    private ArrayList<String> ipaddress1;
    private ArrayList<String> lattitude1;
    private ArrayList<String> longitude1;

    public Multiple() {
    }

    public Multiple(ArrayList<String> ipaddress1, ArrayList<String> lattitude1, ArrayList<String> longitude1) {
        this.ipaddress1 = ipaddress1;
        this.lattitude1 = lattitude1;
        this.longitude1 = longitude1;
    }

    public ArrayList<String> getIpaddress1() {
        return ipaddress1;
    }

    public void setIpaddress1(ArrayList<String> ipaddress1) {
        this.ipaddress1 = ipaddress1;
    }

    public ArrayList<String> getLattitude1() {
        return lattitude1;
    }

    public void setLattitude1(ArrayList<String> lattitude1) {
        this.lattitude1 = lattitude1;
    }

    public ArrayList<String> getLongitude1() {
        return longitude1;
    }

    public void setLongitude1(ArrayList<String> longitude1) {
        this.longitude1 = longitude1;
    }
}