package com.amazonaws.youruserpools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class Assign_info_Windo_Edit implements GoogleMap.InfoWindowAdapter  {

    private Context context;


    public Assign_info_Windo_Edit(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.assign_info_windo_edit, null);
        TextView tvDetails = (TextView) v.findViewById(R.id.tvd1);
        tvDetails.setText(marker.getSnippet());

        return v;
    }

}



