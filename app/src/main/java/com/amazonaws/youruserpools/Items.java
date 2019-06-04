package com.amazonaws.youruserpools;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

class Items implements ClusterItem {
    private final LatLng mPosition;
    private  String mTitle ;
    private  String mSnippet;

    public Items(double lat, double lng, String title, String snippet, String cl, String rs, String cm, String ct, String chg, String nd, String dd, String ft, String bt, String bl, String eage, String lm) {
        mPosition = new LatLng(lat, lng);
    }

    public Items(double lat, double lng, String title, String lng1) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = lng1;
    }

    public Items(LatLng mPosition) {
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


//
//    public Items(LatLng mPosition) {
//        this.mPosition =mPosition;
//    }
//
//        public Items(LatLng mPosition, String title, String snippet) {
//        this.title = title;
//        this.snippet = snippet;
//        this.mPosition =mPosition;
//    }
//
////    public String getName() {
////        return title;
////    }
////
////    public void setName(String name) {
////        this.title = title;
////    }
//
//    @Override
//    public LatLng getPosition() {
//        return mPosition;
//    }
//
//    @Override
//    public String getTitle() {
//        return title;
//    }
//
//    @Override
//    public String getSnippet() {
//        return snippet;
//    }
//}