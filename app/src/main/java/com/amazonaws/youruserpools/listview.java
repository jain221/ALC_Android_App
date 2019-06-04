package com.amazonaws.youruserpools;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class listview extends AppCompatActivity  {



    LatLng center, latLng;
    String title;
    private RequestQueue mRequestQueue;
    public static final String TAG1 = listview.class.getSimpleName();
    public static final String ID = "id";
    public static final String TITLE = "ipaddress";
    public static final String LAT = "latitude";
    public static final String LNG = "longitude";
    private Marker myMarker;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17f;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String URL_PRODUCTS = "https://wwf5avjfai.execute-api.eu-west-1.amazonaws.com/ISDMAPDATA/idname";
    int id;
    Double latitude = null;
    Double longitude = null;
    String ipaddress;
    // GPS Location

    ArrayList<String> list = new ArrayList<>();

    ListView listView;
    Boolean isInternetPresent = false;

   double latt ,logg;








    // Button
    Button btnShowOnMap;

    // Progress dialog
    ProgressDialog pDialog;

    // Places Listview
    ListView lv;
    // ListItems data
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);


//        contactAdapter= new ContactAdapter(this,R.layout.row_layout);
        lv= (ListView)findViewById(R.id.mapdata);
        // button show on map
        btnShowOnMap = (Button) findViewById(R.id.btn_show_map);
        getDeviceLocation();
        getMarkers();
       // new LoadPlaces().execute();



    }

    private void getDeviceLocation() {
        Log.d(TAG1, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(listview.this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG1, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            latt= currentLocation.getLatitude();
                            logg=currentLocation.getLongitude();


                        } else {
                            Log.d(TAG1, "onComplete: current location is null");
                            Toast.makeText(listview.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG1, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }





    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG1 : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }




    private void getMarkers() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            double radius = 1000; // 1000 meters


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                id= Integer.parseInt(product.getString(ID));
                                ipaddress = product.getString(TITLE);
                                latitude = Double.parseDouble(product.getString(LAT));
                                longitude = Double.parseDouble(product.getString(LNG));
                                //adding the product to product list

                                // onMarkerClick(latLng);
//                                Contacts contacts= new Contacts(id, ipaddress ,latitude,longitude);
//                                contactAdapter.add(contacts);

                                list.add(ipaddress);

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(listview.this, android.R.layout.simple_list_item_1,list);
                                lv.setAdapter(arrayAdapter);

                            }

                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(listview.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }



}
