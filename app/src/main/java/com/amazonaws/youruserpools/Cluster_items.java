package com.amazonaws.youruserpools;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

class Cluster_items implements ClusterItem {
    private final LatLng mPosition;
    private  String mTitle ;
    private  String mSnippet;

    public Cluster_items(double lat, double lng, String title, String snippet, String cl, String rs, String cm, String ct, String chg, String nd, String dd, String ft, String bt, String bl, String eage, String lm) {
        mPosition = new LatLng(lat, lng);
    }

    public Cluster_items(double lat, double lng, String title, String lng1) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = lng1;
    }

    public Cluster_items(LatLng mPosition) {
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

