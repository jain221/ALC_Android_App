package com.amazonaws.youruserpools;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;

public class StreetViewMap extends AppCompatActivity  implements OnStreetViewPanoramaReadyCallback {


    double e1,e2;
    private StreetViewPanorama mStreetViewPanorama;
    private boolean secondLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view_map);


        SupportStreetViewPanoramaFragment streetViewFragment =
                (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.googleMapStreetView);
        streetViewFragment.getStreetViewPanoramaAsync(this);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                secondLocation = !secondLocation;
//                onStreetViewPanoramaReady(mStreetViewPanorama);
//            }
//        });

    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {

        mStreetViewPanorama = streetViewPanorama;
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            e1 = receiveIntent.getDoubleExtra("doubleValue_e1", 0.00);
//            e2 = extras.getDouble("doubleValue_e2");
//        }


//        String l ="51.64728";
//        String d ="-3.6351 ";
//
//        double ll = Double.parseDouble(String.format("%.5f", l));
//        double dd= Double.parseDouble(String.format("%.5f", d));

//        Intent tmp = getIntent();
//        e1 = tmp.getDoubleExtra("doubleValue_e1", 0.0);
//        e2 = tmp.getDoubleExtra("doubleValue_e2", 0.0);
//        Double lat= Double.valueOf((getIntent().getStringExtra("doubleValue_e1"+0.5f)));
//        Double lon= Double.valueOf((getIntent().getStringExtra("doubleValue_e2"+0.5f)));

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("doubleValue_e1", 0);
        double lng = intent.getDoubleExtra("doubleValue_e2", 0);
        LatLng latLng = new LatLng(lat, lng);
//        streetViewPanorama.setPosition(new LatLng( lat, lon));

        streetViewPanorama.setPosition(latLng);

//
//
//        streetViewPanorama.setPosition(new LatLng( e1, e2), StreetViewSource.DEFAULT.OUTDOOR);

//        streetViewPanorama.setPosition(new LatLng( lat, lon), StreetViewSource.DEFAULT.OUTDOOR);

//        if (secondLocation) {
//
////               streetViewPanorama.setPosition(new LatLng( e1, e2), StreetViewSource.DEFAULT.OUTDOOR);
////        } else {
////            streetViewPanorama.setPosition(new LatLng(51.52887, -0.1726073));
//       }
        streetViewPanorama.setStreetNamesEnabled(true);
        streetViewPanorama.setPanningGesturesEnabled(true);
        streetViewPanorama.setZoomGesturesEnabled(true);
        streetViewPanorama.setUserNavigationEnabled(true);
        streetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().
                        orientation(new StreetViewPanoramaOrientation(20, 20))
                        .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                        .build(), 2000);

        streetViewPanorama.setOnStreetViewPanoramaChangeListener(panoramaChangeListener);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private StreetViewPanorama.OnStreetViewPanoramaChangeListener panoramaChangeListener =
            new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
                @Override
                public void onStreetViewPanoramaChange(
                        StreetViewPanoramaLocation streetViewPanoramaLocation) {


                    Toast.makeText(getApplicationContext(), "Lat: " + streetViewPanoramaLocation.position.latitude + " Lng: " + streetViewPanoramaLocation.position.longitude, Toast.LENGTH_SHORT).show();

                }
            };
}
