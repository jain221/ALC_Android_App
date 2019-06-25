package com.amazonaws.youruserpools;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoWndow2 implements GoogleMap.InfoWindowAdapter {
    private Context context;



    public InfoWndow2(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_info_wndow2, null);

        TextView tvGir = (TextView) v.findViewById(R.id.tvgir1);
        TextView tvDetails = (TextView) v.findViewById(R.id.tvd1);
//        infoButton = (Button) v.findViewById(R.id.button);
//        AllData = (CheckBox) v.findViewById(R.id.check);
//        AllData.setOnClickListener(this);



        // TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
//        tvGir.setText("Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude);
//        tvDetails.setText(Items.);
        //tvLng.setText("Longitude:"+ latLng.longitude);

        tvGir.setText(marker.getTitle());
        tvDetails.setText(marker.getSnippet());

        return v;
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.checkbox_meat:
//                if (AllData.isChecked())
//                    Toast.makeText(context.getApplicationContext(), "Your clicked abble", Toast.LENGTH_LONG).show();
//                break;
//
//        }


}