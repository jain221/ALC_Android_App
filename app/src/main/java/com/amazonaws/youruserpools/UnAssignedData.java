package com.amazonaws.youruserpools;
import android.Manifest;
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
import android.support.annotation.Nullable;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnAssignedData extends AppCompatActivity  implements OnMapReadyCallback,ClusterManager.OnClusterClickListener<Items>, ClusterManager.OnClusterInfoWindowClickListener<Items>, ClusterManager.OnClusterItemClickListener<Items>,  ClusterManager.OnClusterItemInfoWindowClickListener<Items> {

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
    private static final String URL_PRODUCTS = "https://wwf5avjfai.execute-api.eu-west-1.amazonaws.com/ISDMAPDATA/idname";
    private static final String URL_Data = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/StationNameGetFunction";
    private ClusterManager<Items> mClusterManager;

    private ImageView mGps;

    String data;

    AutoCompleteTextView et;
    private LatLngBounds.Builder builder;

    double latt ,logg;
    String TempItem;
    Handler handler = new Handler();
    Runnable refresh;
    Marker prevMarker1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unassigneddata);

        mGps = (ImageView) findViewById(R.id.ic_gps);
        et = (AutoCompleteTextView) findViewById(R.id.editText);


        getLocationPermission();
        if (gMap != null) {

            gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Geocoder geocoder = new Geocoder(UnAssignedData.this);
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

// Creating Marker Using MySQL database and calling that function.
        gMap.clear();
        getMarkers();

// Creating Info window
        InfoWndowAdapter markerInfoWindowAdapter = new InfoWndowAdapter(getApplicationContext());
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);

      gMap.setMapType(gMap.MAP_TYPE_SATELLITE);


      mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG1, "onClick: clicked gps icon");
                getDeviceLocation();

            }
        });

        final UnAssignedData.CustomClusterRenderer renderer = new UnAssignedData.CustomClusterRenderer(this, gMap, mClusterManager);


        mClusterManager.setRenderer(renderer);




    }


// Get device location


    private void getDeviceLocation() {
        Log.d(TAG1, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(UnAssignedData.this);

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
                            latt= currentLocation.getLatitude();
                            logg=currentLocation.getLongitude();


                        } else {
                            Log.d(TAG1, "onComplete: current location is null");
                            Toast.makeText(UnAssignedData.this, "unable to get current location", Toast.LENGTH_SHORT).show();
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
        mapFragment.getMapAsync(UnAssignedData.this);
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
        List<Address> list = geocoder.getFromLocationName(location, 1);
        if (list.size() > 0) {
            Address address = list.get(0);
            String locality = address.getLocality();
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//            gMap.addMarker(new MarkerOptions().position(latLng).title("Find Pro"));
            gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));



        }else {
            Toast.makeText(UnAssignedData.this, "Check Spelling Or Try Again !", Toast.LENGTH_SHORT).show();
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


        MarkerOptions marker = new MarkerOptions();
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));


        builder.include(latLng);
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        gMap.animateCamera(cu);
        double lat =latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet = "Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude;

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
            return Color.BLUE ;// Return any color you want here. You can base it on clusterSize.
        }
        @Override
        protected void onBeforeClusterItemRendered(Items item, MarkerOptions markerOptions) {

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
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
                Intent intent = new Intent(UnAssignedData.this, StationList.class);
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
                                ii = product.getInt(ID);
                                title = product.getString(TITLE);
                                latLng = new LatLng(Double.parseDouble(product.getString(LAT)), Double.parseDouble(product.getString(LNG)));
                                //adding the product to product list
                                addMarker(latLng, title);

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
                        Toast.makeText(UnAssignedData.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(UnAssignedData.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
//        builder1.setCancelable(false);
        builder1.setTitle("Refresh Page");
        builder1.setMessage("Please type Yes button to Refresh page");
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
    private void mulitpleMarker() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setIcon(R.drawable.refereshbutton);
//        builder1.setCancelable(false);
        builder1.setTitle("Multiple Data");
        builder1.setMessage("->This is View Mode"+ "\n->Please Zoom IN,If you want to Add Multiple data Please click on Yes button");
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showStartDialogallData();
                        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                            @Override
                            public boolean onMarkerClick(final Marker marker1) {



                                if (prevMarker1 != null) {
                                    marker1.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    latit = String.valueOf(marker1.getPosition().latitude);
                                    longgg = String.valueOf(marker1.getPosition().longitude);
                                    ipaddr = marker1.getTitle();
                                    Set<String> set = new HashSet<>(ipaddress3);
                                    ipaddress3.clear();
                                    ipaddress3.addAll(set);
                                    ipaddress3.remove(ipaddr);

                                }



                                if(!marker1.equals(prevMarker1)) {


                                    marker1.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
                                    prevMarker1 = marker1;
                                    latit = String.valueOf(marker1.getPosition().latitude);
                                    longgg = String.valueOf(marker1.getPosition().longitude);
                                    ipaddr = marker1.getTitle();
                                    ipaddress3.add(ipaddr);
                                    Set<String> set = new HashSet<>(ipaddress3);
                                    ipaddress3.clear();
                                    ipaddress3.addAll(set);
                                    latitude2.add(String.valueOf(latit));
                                    longitude2.add(String.valueOf(longgg));

                                }

                                size= ipaddress3.size();
                                showStartDialogallData1();

                                return true;
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

    private void showStartDialogallData() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Click on each Asset for Adding Multiple data");
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
                        AssignMode();


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
        builder1.setCancelable(false);
        builder1.setTitle("Enter Asset ID");
        builder1.setMessage("Do you want to enter an individual Asset ID.");
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//
//                        Toast.makeText(UnassignedMode.this, "Edit Mode is Selected", Toast.LENGTH_LONG).show();
                        for (int i = 0; i < ipaddress3.size(); i++) {
                            Intent intent = new Intent(UnAssignedData.this, AddingAssertNumber.class);
                            intent.putExtra("doubleValue_e1", ipaddress3.get(i));
                            intent.putExtra("doubleValue_e2", latitude2.get(i));
                            intent.putExtra("doubleValue_e3", longitude2.get(i));
                            startActivity(intent);


                        }

                        finishD();

                    }


                });

        builder1.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Referesh();
            }
        });
        AlertDialog dialog = builder1.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        wmlp.gravity = Gravity.BOTTOM | Gravity.LEFT;
        wmlp.x = 100;   //x position
        wmlp.y = 10;   //y position

        dialog.show();

        Button buttonbackground1 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setTextColor(Color.BLACK);

        Button buttonbackground2 = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        buttonbackground2.setTextColor(Color.BLACK);


    }

    private void finishD() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        builder1.setMessage(size+" asset ID's entered.");
        builder1.setPositiveButton("Finish",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Referesh();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setTextColor(Color.BLACK);

    }

    private void multipledata() {

        Intent intent = new Intent(UnAssignedData.this, AddingUnassignedAttribute.class);
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


