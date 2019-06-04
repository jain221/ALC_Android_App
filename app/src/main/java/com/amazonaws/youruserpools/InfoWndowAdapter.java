package com.amazonaws.youruserpools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;


public class InfoWndowAdapter implements InfoWindowAdapter {

    private Context context;
    private Button infoButton;
    CheckBox AllData;


    public InfoWndowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.gir_forest, null);

        TextView tvGir = (TextView) v.findViewById(R.id.tvgir);
        TextView tvDetails = (TextView) v.findViewById(R.id.tvd);
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

