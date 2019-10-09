package com.amazonaws.youruserpools;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class infoWindoEdit implements GoogleMap.InfoWindowAdapter  {

    private Context context;


    public infoWindoEdit (Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_info_windo_edit, null);
        TextView tvDetails = (TextView) v.findViewById(R.id.tvd1);
        tvDetails.setText(marker.getSnippet());

        return v;
    }

}


