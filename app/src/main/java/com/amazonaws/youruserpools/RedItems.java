package com.amazonaws.youruserpools;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class RedItems implements ClusterItem {
    private final LatLng mPosition;
    private  String mTitle ;
    private  String mSnippet;

    public RedItems(double lat, double lng, String title, String snippet, String cl, String rs, String cm, String ct, String chg, String nd, String dd, String ft, String bt, String bl, String eage, String lm) {
        mPosition = new LatLng(lat, lng);
    }

    public RedItems(double lat, double lng, String title, String lng1) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = lng1;
    }

    public RedItems(LatLng mPosition) {
        this.mPosition =mPosition;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}
