package com.amazonaws.youruserpools;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import java.util.List;

public class UnassignedEditData extends AppCompatActivity implements OnMapReadyCallback,ClusterManager.OnClusterClickListener<Items>, ClusterManager.OnClusterInfoWindowClickListener<Items>, ClusterManager.OnClusterItemClickListener<Items>,  ClusterManager.OnClusterItemInfoWindowClickListener<Items> {


    int size =0;
    private int ii;
    String latit, longgg, ipaddr;
    MapFragment mapFragment;
    GoogleMap gMap;
    LatLng center, latLng;
    String title;

    private RequestQueue mRequestQueue;
    public static final String TAG1 = CurrentNode.class.getSimpleName();
    public static final String ID = "id";
    public static final String ID1 = "Id";
    public static final String TITLE = "iccid";
    public static final String LAT = "latitude";
    public static final String LNG = "longitude";
    public static final String Station11 = "Station";

    public static final String columeNumber = "colume_number";
    public static final String columeManf = "Colume_Manfucture";
    public static final String RaiseandLow = "Raise_and_Lower";
    public static final String columeMaterial = "Colume_Material";
    public static final String ColumeType = "Colume_Type";
    public static final String ColumeHight = "column_height_from_ground";
    public static final String NumDoors = "number_of_door";
    public static final String DoorDimen = "door_dimensions";
    public static final String Foundation = "foundation_type";
    public static final String ColumeBracket = "bracket_type";
    public static final String BracketLenth = "bracket_length";
    public static final String EstimatedAge = "estimated_column_age";
    public static final String CoastKM = "cost_km";
    public static final String LatenManfu = "lantern_manufacturer";
    public static final String TEXT19 = "text";
    ArrayList<String> mark1 = new ArrayList<>();
    public static final String MY_PREF_KEY = "Selection";
    ArrayList<JSONObject> displayData = new ArrayList<JSONObject>();
    String ip, latt, logg, cnum, cl, rs, cm, ct, chg, nd, dd, ft, bt, bl, eage, cstkm, lm;
    private Marker myMarker;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17f;

    ArrayList<String> latitude2 = new ArrayList<String>();
    ArrayList<String> longitude2 = new ArrayList<String>();
    ArrayList<String> ipaddress3 = new ArrayList<String>();
    private ArrayList<SuggestGetSet> List;


    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String URL_PRODUCTS = "https://wwf5avjfai.execute-api.eu-west-1.amazonaws.com/ISDMAPDATA/ISDgetAllData";
    private static final String URL_Data = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/StationNameGetFunction";
    private ClusterManager<Items> mClusterManager;

    private ImageView mGps;

    String data;

    AutoCompleteTextView et;
    private LatLngBounds.Builder builder;


    String TempItem;
    Handler handler = new Handler();
    Runnable refresh;
    Marker prevMarker1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unassigneddata);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        et = (AutoCompleteTextView) findViewById(R.id.editText);


        getLocationPermission();
        if (gMap != null) {

            gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Geocoder geocoder = new Geocoder(UnassignedEditData.this);
                    java.util.List<Address> list;
                    try {
                        list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    } catch (IOException e) {
                        return;
                    }

                    Address address = list.get(0);
                    if (myMarker != null) {
                        myMarker.remove();
                    }

                    MarkerOptions options = new MarkerOptions()
                            .title(address.getLocality())
                            .position(new LatLng(latLng.latitude, latLng.longitude));

                    myMarker = gMap.addMarker(options);
                }
            });
        }

        List = new ArrayList<SuggestGetSet>();
        getAutoComlete();

    }


// Creating Map Parameters with using cluster Map

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        center = new LatLng(51.52042, -3.23113);
        mClusterManager = new ClusterManager<>(this, gMap);
        gMap.setOnCameraIdleListener(mClusterManager);
        gMap.setOnMarkerClickListener(mClusterManager);
        gMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
        mClusterManager.cluster();
        builder = new LatLngBounds.Builder();
        infoWindoEdit markerInfoWindowAdapter = new infoWindoEdit (getApplicationContext());
        gMap.setInfoWindowAdapter(markerInfoWindowAdapter);
// Creating Marker Using MySQL database and calling that function.
        gMap.clear();
        getMarkers();

// Creating Info window
//        InfoWndowAdapter markerInfoWindowAdapter = new InfoWndowAdapter(getApplicationContext());
//        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);

        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);


        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG1, "onClick: clicked gps icon");
                getDeviceLocation();

            }
        });

        final UnassignedEditData.CustomClusterRenderer renderer = new UnassignedEditData.CustomClusterRenderer(this, gMap, mClusterManager);


        mClusterManager.setRenderer(renderer);




    }


// Get device location


    private void getDeviceLocation() {
        Log.d(TAG1, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(UnassignedEditData.this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG1, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);
                          


                        } else {
                            Log.d(TAG1, "onComplete: current location is null");
                            Toast.makeText(UnassignedEditData.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG1, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }



    // Set Parameter for moving camera.

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG1, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));



        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));

// Zoom in, animating the camera.
        gMap.animateCamera(CameraUpdateFactory.zoomIn());

// Zoom out to zoom level 10, animating with a duration of 2 seconds.
        gMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        //Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        hideSoftKeyboard();
    }

    private void initMap() {
        Log.d(TAG1, "initMap: initializing map");
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(UnassignedEditData.this);
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


    public void findLocation(View v) throws IOException {



        String location = et.getText().toString();
        et.getText().clear();
        hideSoftKeyboard();
        Geocoder geocoder = new Geocoder(this);
        java.util.List<Address> list = geocoder.getFromLocationName(location, 1);
        if (list.size() > 0) {
            Address address = list.get(0);
            String locality = address.getLocality();
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//            gMap.addMarker(new MarkerOptions().position(latLng).title("Find Pro"));
            gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));



        }else {
            Toast.makeText(UnassignedEditData.this, "Check Spelling Or Try Again !", Toast.LENGTH_SHORT).show();
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


    private void addMarker(final LatLng latLng, final String title) {

        double lat = latLng.latitude;
        double lng = latLng.longitude;
        MarkerOptions marker = new MarkerOptions();
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

       
// Create a cluster item for the marker and set the title and snippet using the constructor.
        //  Items infoWindowItem = new Items(lat,lng, title, snippet);
//        MarkerOptions markerOptions2 = new MarkerOptions()
//                .position(latLng)
//                .title(title)
//                .snippet(snippet2)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//        gMap.addMarker(markerOptions2);

//        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker2) {
////                Toast.makeText(CurrentLocation.this, marker2.getTitle(), Toast.LENGTH_SHORT).show();
//
//                latit = String.valueOf(marker2.getPosition().latitude);
//                longgg = String.valueOf(marker2.getPosition().longitude);
//                ipaddr = marker2.getTitle();
//                showDialog( UnassignedEditData.this);
//
//            }
//        });
        builder.include(latLng);
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        gMap.animateCamera(cu);
  
        // Set he title and snippet strings.

        final String snippet= (" Status : Assigned Assets " + " \n Column : " + cnum + " \n Lat: " + lat + ",Longitude: " + lng + " \n Colume Manf: " + cl + "\n Raise & Lower: " + rs +
                "\n Colume Material: " + cm + " \n Colume Type: " + ct + " \n Colume Height: " + chg + " \n Number of Door: " + nd + " \n Door Dimen: " + dd + "\n Foundation type: " + ft +
                "\n Column Bracket:" + bt + " \n Bracket Length:" + bl + "\n Estimated Age of Lat:" + eage + "\n Installation coast 5Km ?:" + cstkm + " \n Lat. Manf: " + lm);

       

// Create a cluster item for the marker and set the title and snippet using the constructor.
        Items infoWindowItem = new Items(lat,lng, title, snippet);

// Add the cluster item (marker) to the cluster manager.
        mClusterManager.addItem(infoWindowItem);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Items>() {
            @Override
            public boolean onClusterClick(Cluster<Items> cluster) {


                LatLngBounds.Builder builder = LatLngBounds.builder();
                String array = null;
                for (ClusterItem item : cluster.getItems()) {
                    builder.include(item.getPosition());
                    array = item.getTitle();
                }
                // Get the LatLngBounds
                final LatLngBounds bounds = builder.build();

                // Animate camera to the bounds
                try {
                    gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayList<String> addData = new ArrayList<String>();

                addData.add(title);


                return true;
            }
        });
    }




    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(1, 1, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    public class CustomClusterRenderer extends DefaultClusterRenderer<Items> {

        public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<Items> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected int getColor(int clusterSize) {
//            return Color.parseColor("#567238");
            return Color.GREEN ;// Return any color you want here. You can base it on clusterSize.
        }
        @Override
        protected void onBeforeClusterItemRendered(Items item, MarkerOptions markerOptions) {

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add:
                Toast.makeText(this, "Multiple data is selected", Toast.LENGTH_SHORT)
                        .show();

                showStartDialog();
                break;
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                Referesh();
                break;
            case R.id.mapTypeNone:
                gMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            case R.id.stationList:
                Intent intent = new Intent(UnassignedEditData.this, StationList.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }




    private void Referesh(){
        finish();
        startActivity(getIntent());
        handler.post(refresh);
    }

    /**
     * Called when the user clicks a marker.
     */

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

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {


                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                title = product.getString(TITLE);
                                latLng = new LatLng(Double.parseDouble(product.getString(LAT)), Double.parseDouble(product.getString(LNG)));
                                double lat3 = Double.parseDouble(String.valueOf(Double.parseDouble(product.getString(LAT))));
                                double lng3 = Double.parseDouble(String.valueOf(Double.parseDouble(product.getString(LNG))));
                                //adding the product to product list
                                cnum = product.getString(columeNumber);
                                cl = product.getString(columeManf);
                                rs = product.getString(RaiseandLow);
                                cm = product.getString(columeMaterial);
                                ct = product.getString(ColumeType);
                                chg = product.getString(ColumeHight);
                                nd = product.getString(NumDoors);
                                dd = product.getString(DoorDimen);
                                ft = product.getString(Foundation);
                                bt = product.getString(ColumeBracket);
                                bl = product.getString(BracketLenth);
                                eage = product.getString(EstimatedAge);
                                cstkm = product.getString(CoastKM);
                                lm = product.getString(LatenManfu);
                                addMarker(latLng, title);
                                displayData.add(product);
                                // onMarkerClick(latLng);
//                                product1 = new Product(title,lat3,lng3,cl,rs,cm,ct,chg,nd,dd,ft,bt,bl,eage,lm);

                            }
                            for (int i = 0; i < displayData.size(); i++) {
                                //    String ip, latt, logg, cl, rs, cm, ct, chg, nd, dd, ft, bt, bl, eage, lm;
                                double latt = Double.parseDouble(String.valueOf(Double.parseDouble(displayData.get(i).get(LAT).toString())));
                                double logg = Double.parseDouble(String.valueOf(Double.parseDouble(displayData.get(i).get(LNG).toString())));
                                ip = displayData.get(i).get(TITLE).toString();
                                cnum = displayData.get(i).get(columeNumber).toString();
                                cl = displayData.get(i).get(columeManf).toString();
                                rs = displayData.get(i).get(RaiseandLow).toString();
                                cm = displayData.get(i).get(columeMaterial).toString();
                                ct = displayData.get(i).get(ColumeType).toString();
                                chg = displayData.get(i).get(ColumeHight).toString();
                                nd = displayData.get(i).get(NumDoors).toString();
                                dd = displayData.get(i).get(DoorDimen).toString();
                                ft = displayData.get(i).get(Foundation).toString();
                                bt = displayData.get(i).get(ColumeBracket).toString();
                                bl = displayData.get(i).get(BracketLenth).toString();
                                eage = displayData.get(i).get(EstimatedAge).toString();
                                cstkm = displayData.get(i).get(EstimatedAge).toString();
                                lm = displayData.get(i).get(LatenManfu).toString();


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
                        Toast.makeText(UnassignedEditData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    private void getAutoComlete() {



        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject catobj= array.getJSONObject(i);
                                SuggestGetSet colman = new  SuggestGetSet(catobj.getString(ID1), catobj.getString(Station11));
//                                ListData.add(new SuggestGetSet(r.getString("Id"),r.getString("Station")));
                                List.add(colman);
//                                populateSpinner();
                                getData();

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
                        Toast.makeText(UnassignedEditData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }

    private void getData() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < List.size(); i++) {
            lables.add(List.get(i).getStation());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lables );
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        et.setAdapter(adapter);


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






    @Override
    public boolean onClusterClick(Cluster<Items> cluster) {


        return true ;
    }


    @Override
    public void onClusterInfoWindowClick(Cluster<Items> cluster) {




    }

    @Override
    public boolean onClusterItemClick(Items items) {


        mulitpleMarker();


        return true;
    }

    @Override
    public void onClusterItemInfoWindowClick(Items items) {




    }

    private void showStartDialog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setIcon(R.drawable.refereshbutton);
        builder1.setCancelable(false);
        builder1.setTitle("Refresh Page");
        builder1.setMessage("Are You finish Editing the Asset");
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Referesh();
                    }
                });

        builder1.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();


        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setTextColor(Color.BLACK);
        Button buttonbackground2 = alert11.getButton(DialogInterface.BUTTON_NEUTRAL);
        buttonbackground2.setTextColor(Color.BLACK);


    }
    private void mulitpleMarker() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setIcon(R.drawable.refereshbutton);
//        builder1.setCancelable(false);
        builder1.setTitle("Edit Asset");
        builder1.setMessage("->This is View Mode"+ "\n->Please Zoom IN,If you want to Edit data Please click on Yes button for Edit");
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showStartDialogallData();
                        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                            @Override
                            public boolean onMarkerClick(final Marker marker1) {
                        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker2) {
//                Toast.makeText(CurrentLocation.this, marker2.getTitle(), Toast.LENGTH_SHORT).show();

                                latit = String.valueOf(marker2.getPosition().latitude);
                                longgg = String.valueOf(marker2.getPosition().longitude);
                                ipaddr = marker2.getTitle();
                                showDialog( UnassignedEditData.this);

                            }
                        });

                                return false;
                            }
                        });

                    }
                });

        builder1.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();


        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setTextColor(Color.BLACK);

        Button buttonbackground2 = alert11.getButton(DialogInterface.BUTTON_NEUTRAL);
        buttonbackground2.setTextColor(Color.BLACK);


    }


    private void showDialog(UnassignedEditData currentLocation) {

        final Dialog dialog = new Dialog(currentLocation);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_listview);
        dialog.setTitle("        \b EDIT ASSET");
        Button btndialog = (Button) dialog.findViewById(R.id.btndialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        Button columeNumber = (Button) dialog.findViewById(R.id.Rlatern);
        columeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AvaliableData.this, OldScmsInstallation.class);
//                startActivity(intent);


//                Toast.makeText(this, myMarker.getTitle() , Toast.LENGTH_SHORT).show();
                String lat = latit;
                String lng = longgg;
                String ipaddress = ipaddr;

                Intent intent = new Intent(UnassignedEditData.this, AddingAssertNumber.class);
                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
                intent.putExtra("doubleValue_e1", ipaddress);
                intent.putExtra("doubleValue_e2", lat);
                intent.putExtra("doubleValue_e3", lng);

                startActivity(intent);

                Referesh();
            }
        });

        Button RColume = (Button) dialog.findViewById(R.id.RColume);
        RColume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lat = latit;
                String lng = longgg;
                String ipaddress = ipaddr;

                Intent intent = new Intent(UnassignedEditData.this, ReplaceColumn.class);
                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
                intent.putExtra("doubleValue_e1", ipaddress);
                intent.putExtra("doubleValue_e2", lat);
                intent.putExtra("doubleValue_e3", lng);

                startActivity(intent);

                dialog.dismiss();
                showStartDialog();
            }
        });

        Button RFoundationRep = (Button) dialog.findViewById(R.id.RFoundationRep);
        RFoundationRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AvaliableData.this, OldScmsInstallation.class);
//                startActivity(intent);


//                Toast.makeText(this, myMarker.getTitle() , Toast.LENGTH_SHORT).show();
                String lat = latit;
                String lng = longgg;
                String ipaddress = ipaddr;

                Intent intent = new Intent(UnassignedEditData.this, ReplaceLatern.class);
                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
                intent.putExtra("doubleValue_e1", ipaddress);
                intent.putExtra("doubleValue_e2", lat);
                intent.putExtra("doubleValue_e3", lng);

                startActivity(intent);

                dialog.dismiss();
                showStartDialog();
            }
        });

        Button EditChar = (Button) dialog.findViewById(R.id.EditChar);
        EditChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AvaliableData.this, OldScmsInstallation.class);
//                startActivity(intent);


//                Toast.makeText(this, myMarker.getTitle() , Toast.LENGTH_SHORT).show();
                String lat = latit;
                String lng = longgg;
                String ipaddress = ipaddr;

                Intent intent = new Intent(UnassignedEditData.this, ReplaceLatern.class);
                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
                intent.putExtra("doubleValue_e1", ipaddress);
                intent.putExtra("doubleValue_e2", lat);
                intent.putExtra("doubleValue_e3", lng);

                startActivity(intent);

                dialog.dismiss();
                showStartDialog();
            }
        });


        dialog.show();

    }
    private void showStartDialogallData() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("->Click on each Asset for Editing data\"");
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
    private void showStartDialogallData1() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Unassigned Assets");
        builder1.setMessage("Number of Assets selected "+ size +". " + "\n If you want to add or deselect further assets continue selecting from the map");
        builder1.setPositiveButton("Finish",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        multipledata();
                        showStartAll();

                    }
                });



        AlertDialog dialog = builder1.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();


        wmlp.gravity = Gravity.BOTTOM | Gravity.LEFT;
        wmlp.x = 100;   //x position
        wmlp.y = 10;   //y position

        dialog.show();


        Button buttonbackground1 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setTextColor(Color.BLACK);



    }



    private void AssignMode() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setCancelable(false);
        builder1.setTitle("Asset Number");
        builder1.setMessage("Do you want to enter an individual Asset Number");
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

//
//                        if (assignn.isChecked()) {
//                            assignn.setChecked(true);
//                            Referesh();
//                            assignn.setChecked(true);}

                        Referesh();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setBackgroundColor(R.drawable.button);
        buttonbackground1.setTextColor(Color.BLACK);

    }

    private void multipledata() {

        Intent intent = new Intent(UnassignedEditData.this, AddingUnassignedAttribute.class);
        for (int i = 0; i < ipaddress3.size(); i++) {
            intent.putExtra("doubleValue_e1", ipaddress3.get(i));
            intent.putExtra("doubleValue_e2", latitude2.get(i));
            intent.putExtra("doubleValue_e3", longitude2.get(i));
            startActivity(intent);

        }


    }

    private void showStartAll() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setIcon(R.drawable.refereshbutton);
        builder1.setCancelable(false);
        builder1.setTitle("Refresh Page");
        builder1.setMessage("Are you Sure you want to see all Column Node");
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Referesh();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setBackgroundColor(R.drawable.button);
        buttonbackground1.setTextColor(Color.BLACK);

    }

}


