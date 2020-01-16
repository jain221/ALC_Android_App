package com.amazonaws.youruserpools;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;

public class ReplaceNode extends AppCompatActivity implements OnMapReadyCallback {

    HttpParse httpParse = new HttpParse();
    String URL_Get = "http://ec2-35-178-150-70.eu-west-2.compute.amazonaws.com/filter.php";
    //private static final String URL_Get = "https://o5yklvu3td.execute-api.eu-west-1.amazonaws.com/default/fetchData";
    private static final Object JSONObject = null;

    public static final String ID = "id";

    public static final String ID1 = "Id";
    public static final String TITLE = "iccid";
    public static final String LAT =  "latitude";
    public static final String LNG = "longitude";

    ProgressDialog pDialog;

    double lattt,longgg;

    private RequestQueue mRequestQueue;
    public static final String TAG1 = CurrentNode.class.getSimpleName();
    double ll,lg;
    private Marker myMarker;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17f;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    MapFragment mapFragment;
    GoogleMap gMap;

    LatLng latLng;
    EditText assertNumber;
    String ip,ippp, latt, logg,title,data,e1, e2, e3,TempItem;
    TextView ipaddress,lat, log,Log1;
    Handler handler = new Handler();
    Runnable refresh;
    private static final String URL_UnAssinged = "https://wwf5avjfai.execute-api.eu-west-1.amazonaws.com/ISDMAPDATA/idname";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unassigneddata);

        TempItem = getIntent().getStringExtra("doubleValue_e1");
        latt = getIntent().getStringExtra("doubleValue_e2");
        logg = getIntent().getStringExtra("doubleValue_e3");
//

//        ipaddress = (TextView) findViewById(R.id.t1);
//        lat = (TextView) findViewById(R.id.t2);
//        log = (TextView) findViewById(R.id.t3);
//
//
//        ipaddress.setText(TempItem);
//        lat.setText(latt);
//        log.setText(logg);

        getLocationPermission();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        getMarker();
        getMarker(URL_UnAssinged,BitmapDescriptorFactory.HUE_BLUE,"Unassigned Assets");
        if (mLocationPermissionsGranted) {
//            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setMyLocationButtonEnabled(false);

        }
        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);



    }


    private void getMarker(String url, final float color, final String status) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                title = product.getString(TITLE);
                                latLng = new LatLng(Double.parseDouble(product.getString(LAT)), Double.parseDouble(product.getString(LNG)));

                                addMarkerGreen(latLng, title, color, status);



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
                        Toast.makeText(ReplaceNode.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void addMarkerGreen(final LatLng latLng, final String title,float color_node,String status) {


        double lat = latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet3 = (" Status : " + status );

        MarkerOptions markerOptions3 = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet3)
                .icon(BitmapDescriptorFactory.defaultMarker(color_node));
        gMap.addMarker(markerOptions3);


        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                //
                ippp = marker.getTitle();
                lattt = marker.getPosition().latitude;
                longgg = marker.getPosition().longitude;

                showDialog(ReplaceNode.this);


                return false;
            }


        });
    }




    private void initMap() {
        Log.d(TAG1, "initMap: initializing map");
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(ReplaceNode.this);
    }

    private void getLocationPermission() {
        Log.d(TAG1, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }





    private void hideSoftKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG1, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG1, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG1, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void getMarker() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            e1 = extras.getString("doubleValue_e1");
            ll = Double.parseDouble((extras.getString("doubleValue_e2")));
            lg= Double.parseDouble((extras.getString("doubleValue_e3")));

        }
        LatLng lattt= new LatLng(ll,lg);
//        MarkerOptions markerOptions1 = new MarkerOptions()
//                .position(lattt)
//                .title(e1)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//        gMap.addMarker(markerOptions1);

        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                .data(Collections.singleton(lattt))
                .radius(50)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        TileOverlay mOverlay = gMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(lattt)      // Sets the center of the map to Mountain View
                .zoom(30)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        showDialog( ReplaceNode.this);
    }



    private void showDialog(ReplaceNode currentLocation) {



        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

//        builder1.setCancelable(false);
        builder1.setTitle("Do you want to Replace this Asset.");

        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Toast.makeText(ReplaceNode.this, "Asset Replace SuccessFully!!!", Toast.LENGTH_SHORT).show();

                        if (checkNetworkConnection()) {
                            new HTTPAsyncTask().execute("https://svjuuau0x8.execute-api.eu-west-1.amazonaws.com/default/ISDColumeUpdate");
                            onBackPressed();
                        } else
                            Toast.makeText(ReplaceNode.this, "Not Connected!", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();

                    }
                });


        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    dialog.dismiss();

                    }
                });

        AlertDialog alert11 = builder1.create();
//        Dialog dialog = new Dialog(currentLocation);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        alert11.show();

//
        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);

        buttonbackground1.setTextColor(Color.BLACK);

        Button buttonbackground2 = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);

        buttonbackground2.setTextColor(Color.BLACK);
//
//
//
//
//
//        dialog.setContentView(R.layout.activity_edite_colume_number);
//
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            e1 = extras.getString("doubleValue_e1");
//            e2 = extras.getString("doubleValue_e2");
//            e3 = extras.getString("doubleValue_e3");
//
//        }
//        assertNumber = (EditText) dialog.findViewById(R.id.editName);
//        Button saveNumber = (Button) dialog.findViewById(R.id.btnSave);
//
//        assertNumber.setShowSoftInputOnFocus(true);
//        saveNumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//
//
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
//        wmlp.gravity = Gravity.BOTTOM | Gravity.LEFT;
//        wmlp.x = 120;   //x position
//        wmlp.y = 5;   //y position
//
//        dialog.show();
////        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
////            @Override
////            public void onDismiss(DialogInterface dialog) {
////                Intent intent = getIntent();
////                overridePendingTransition(0, 0);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
////                finish();
////                overridePendingTransition(0, 0);
////                startActivity(intent);
////
////            }
////
//        });
    }



    // check network connection
    public boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            // show "Connected" & type of network "WIFI or MOBILE"


        } else {
        }

        return isConnected;
    }


    private String httpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. build JSON object
        org.json.JSONObject jsonObject = buidJsonObject();

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject);

        // 4. make POST request to the given URL
        conn.connect();

        // 5. return response message
        return conn.getResponseMessage() + "";

    }


    class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return httpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

        }
    }


    private JSONObject buidJsonObject() throws JSONException {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            e1 = extras.getString("doubleValue_e1");
            e2 = extras.getString("doubleValue_e2");
            e3 = extras.getString("doubleValue_e3");

        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("columeupdate", "6");
        jsonObject.put("iccid", e1);
        jsonObject.put("iccidNew", ippp);
        jsonObject.put("newlatitude", lattt);
        jsonObject.put("newloggitude", longgg);


        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(MainActivity.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
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




}
