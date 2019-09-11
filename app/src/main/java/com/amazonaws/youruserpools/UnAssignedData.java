package com.amazonaws.youruserpools;
import android.Manifest;
import android.app.ProgressDialog;
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
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
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
import com.google.android.gms.maps.StreetViewPanorama;
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

public class UnAssignedData extends AppCompatActivity  implements OnMapReadyCallback,ClusterManager.OnClusterClickListener<Items>, ClusterManager.OnClusterInfoWindowClickListener<Items>, ClusterManager.OnClusterItemClickListener<Items>, GoogleMap.OnMarkerClickListener, ClusterManager.OnClusterItemInfoWindowClickListener<Items> {

    private int ii;
    public static String[] arrPath;
    private Context context;
    MapFragment mapFragment;
    ArrayList<ArrayList<String>> myList = new ArrayList<>();
    GoogleMap gMap;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
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
    double latit,longgg;
    String ipaddr;

    //vars
    private ArrayList<SuggestGetSet> List;
    //    private SuggestionAdapter mSuggestionAdapter;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String URL_PRODUCTS = "https://wwf5avjfai.execute-api.eu-west-1.amazonaws.com/ISDMAPDATA/idname";
    private static final String URL_Data = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/StationNameGetFunction";
    private ClusterManager<Items> mClusterManager;
    private java.util.List<Items> items =new ArrayList<>();
    private ImageView mGps;
    private ImageView menu;
    private ImageView addAllData, streetView;
    String data;

    AutoCompleteTextView et;

    private LatLngBounds bounds;
    private LatLngBounds.Builder builder;

    double latt ,logg;

    String TempItem;
    int count =0;

    StreetViewPanorama mStreetViewPanorama;

    CheckBox AllData;
    private NavigationView nDrawer;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private AlertDialog userDialog;
    private ProgressDialog waitDialog;
    private ListView attributesList;

//    List<SuggestGetSet> ListData = new ArrayList<SuggestGetSet>();
    private TableLayout mLinearLayout;
    private Handler mHandler;
    Handler handler = new Handler();
    Runnable refresh;
    ArrayList<String> latitude2 = new ArrayList<String>();
    ArrayList<String> longitude2 = new ArrayList<String>();
    ArrayList<String> ipaddress2 = new ArrayList<String>();
    LatLngBounds.Builder builder1;
    ImageView  resetButton ;


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
                    List<Address> list;
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
        resetButton =(ImageView) findViewById(R.id.ic_addData);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                Toast.makeText(UnAssignedData.this,"Multiple data is been selected", Toast.LENGTH_LONG).show();
                multipledata();
                showStartDialog();
            }
        });
    }


//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        gMap = googleMap;
//        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
//        Log.d(TAG1, "onMapReady: map is ready");
//        // Mengarahkan ke alun-alun Demak
////        center = new LatLng(51.52042, -3.23113);
//        mClusterManager = new ClusterManager<>(this, gMap);
//        gMap.setOnCameraIdleListener(mClusterManager);
//        gMap.setOnMarkerClickListener(mClusterManager);
//        gMap.setOnInfoWindowClickListener(mClusterManager);
//        mClusterManager.setOnClusterClickListener(this);
//        mClusterManager.setOnClusterInfoWindowClickListener(this);
//        mClusterManager.setOnClusterItemClickListener(this);
//        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
//        mClusterManager.cluster();
//
//
//        gMap.setOnMarkerClickListener(this);
//
////        builder = new LatLngBounds.Builder();
////        bounds = builder.build();
////        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
////        gMap.animateCamera(cu);
//
////        if (mLocationPermissionsGranted) {
////            getDeviceLocation();
////
////            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
////                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
////                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////                return;
////            }
////            gMap.setMyLocationEnabled(true);
////            gMap.getUiSettings().setMyLocationButtonEnabled(false);
////
////        }
//
////        LatLngBounds bounds = builder.build();
////        int padding = 0; // offset from edges of the map in pixels
////        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
////
////        gMap.moveCamera(cu);
////        // Setting a custom info window adapter for the google map
//        InfoWndowAdapter markerInfoWindowAdapter = new InfoWndowAdapter(getApplicationContext());
//        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
//
////        googleMap.setOnInfoWindowClickListener(this);
//
//        getMarkers();
////        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);
//        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);
////           gMap.setOnCameraChangeListener((GoogleMap.OnCameraChangeListener) mClusterManager);
//
//        mGps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG1, "onClick: clicked gps icon");
//                getDeviceLocation();
//            }
//        });
//
////        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
//
////        addAllData.setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View view) {
////          //      showDialog(NodeMapSingleData.this);
////
////                multipledata();
////                showStartDialog();
////            }
////
////        });
//
////        mGps.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                marker.hideInfoWindow();
////            }
////        });
//
//
////        streetView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Log.d(TAG1, "onClick: StreetView");
////                Streetview1();
////
////            }
////        });
//
////        menu.setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View view) {
////                Log.d(TAG1, "onClick: clicked gps icon");
////
////                run1();
////
////            }
////
////        });
////        menu.setOnLongClickListener(new View.OnLongClickListener() {
////
////            @Override
////            public boolean onLongClick(View v) {
////
////                gMap.setMapType(gMap.MAP_TYPE_NORMAL);
////                return true;
////
////            }
////
////        });
//
////        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
////            @Override
////            public void onMapLongClick(LatLng latLng) {
////                Intent intent = new Intent(NodeMapSingleData.this, AvaliableData.class);
////                startActivity(intent);
////            }
////        });
//
////        onBackPressed();
//        final CustomClusterRenderer renderer = new CustomClusterRenderer(this, gMap, mClusterManager);
//
//        mClusterManager.setRenderer(renderer);
//
////        onBackPressed();
//
//
//    }


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
//        builder = new LatLngBounds.Builder();
        InfoWndowAdapter markerInfoWindowAdapter = new InfoWndowAdapter(getApplicationContext());
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
//
        // Set a listener for marker click.
        gMap.setOnMarkerClickListener(this);
        getMarkers();



//        getLocation();
//        if (mLocationPermissionsGranted) {
//            getDeviceLocation();
//
////                getLocation();
//
//
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            gMap.setMyLocationEnabled(true);
//            gMap.getUiSettings().setMyLocationButtonEnabled(false);
//
//        }




//        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);
        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);
        //   gMap.setOnCameraChangeListener((GoogleMap.OnCameraChangeListener) mClusterManager);

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG1, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

                final CustomClusterRenderer renderer = new CustomClusterRenderer(this, gMap, mClusterManager);

        mClusterManager.setRenderer(renderer);




    }

    private void getLocation() {

//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        builder.include(marker.getPosition());
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(latLng)      // Sets the center of the map to Mountain View
//                .zoom(10)                   // Sets the zoom
////                .bearing(180)                // Sets the orientation of the camera to east
////                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
//                .build();                   // Creates a CameraPosition from the builder
//        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        gMap.animateCamera(cu);

    }


    private void onMarkerClick(GoogleMap.OnMarkerClickListener onMarkerClickListener) {
    }


    private void run1(){
//Your first functionality

        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);

    }

    public void onBackPressed() {

        gMap.setMapType(gMap.MAP_TYPE_NORMAL);
    }


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
//                .bearing(180)                // Sets the orientation of the camera to east
//                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));




                hideSoftKeyboard();
    }

    private void initMap() {
        Log.d(TAG1, "initMap: initializing map");
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(UnAssignedData.this);

//        StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama);
//        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);


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

//            if(myMarker != null)
//                myMarker.remove();
//            myMarker = gMap.addMarker(new MarkerOptions()
//                    .position(latLng)
//                    .title(locality)
//                    .snippet("Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude)
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
//            ;

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

//        gMap.clear();
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        // marker.showInfoWindow();
        builder.include(latLng);
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        gMap.animateCamera(cu);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(latLng)      // Sets the center of the map to Mountain View
//                .zoom(10)                   // Sets the zoom
////                .bearing(180)                // Sets the orientation of the camera to east
////                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
//                .build();                   // Creates a CameraPosition from the builder
//        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



        double lat =latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet = "Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude;

// Create a cluster item for the marker and set the title and snippet using the constructor.
        Items infoWindowItem = new Items(lat,lng, title, snippet);
//        builder.include(latLng);
//
// Add the cluster item (marker) to the cluster manager.
        mClusterManager.addItem(infoWindowItem);

        mClusterManager.setRenderer(new CustomClusterRenderer(UnAssignedData.this, gMap, mClusterManager));

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Items>() {
            @Override
            public boolean onClusterClick(Cluster<Items> cluster) {

                String firstName = cluster.getItems().iterator().next().getTitle();
                //     Create the builder to collect all essential cluster items for the bounds.
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

//                for (ClusterItem item : cluster.getItems()) {
//                  data = (item.getTitle());
//                }

                ArrayList<String> addData = new ArrayList<String>();

                addData.add(title);


                return true;
            }
        });





    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
//        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

//        Toast.makeText(UnAssignedData.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
//        Integer clickCount = (Integer) marker.getTag();
//
//        if (clickCount == null) {
//            clickCount = 0;
//        }
//
//        clickCount = clickCount + 1;
//
//        if(clickCount ==2){
//
//            Toast.makeText(this, "Refersh the page you cliced many times " + clickCount + " times.", Toast.LENGTH_SHORT).show();
//            showStartDialog();
//        }
////        marker.setTag(clickCount
//        else {

            ipaddr = marker.getTitle();
            latit = marker.getPosition().latitude;
            longgg = marker.getPosition().longitude;
            ipaddress2.add(ipaddr);
            latitude2.add(String.valueOf(latit));
            longitude2.add(String.valueOf(longgg));



//        return true;

        return false;





    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(1, 1, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    private void Streetview1() {

        double l = latit;
        double lg = longgg;
        Intent intent = new Intent(UnAssignedData.this, StreetViewMap.class);
        intent.putExtra("doubleValue_e1", l);
        intent.putExtra("doubleValue_e2", lg);

        startActivity(intent);
    }



    private void multipledata() {



        myList.add(ipaddress2);
        myList.add(latitude2);
        myList.add(longitude2);



        Intent intent = new Intent(UnAssignedData.this, AddUnssignedData.class);
        for(int i = 0; i < myList.size(); i++){
            intent.putExtra("doubleValue_e1", ipaddress2.get(i));
            intent.putExtra("doubleValue_e2", latitude2.get(i));
            intent.putExtra("doubleValue_e3", longitude2.get(i));
            startActivity(intent);

        }

//        ipaddress2.add(ipaddr);
//        latitude2.add(String.valueOf(latit));
//        longitude2.add(String.valueOf(longgg));
//        multiple = new Multiple();
//        array = new ArrayList<Multiple>();
//        array.add(new Multiple( ipaddress2,latitude2,longitude2));
//
//        for (int i =0 ; i< array.size();i++){
//
//            multiple.setIpaddress1(array.get(i).getIpaddress1());
//            multiple.setLattitude1(array.get(i).getLattitude1());
//            multiple.setLongitude1(array.get(i).getLongitude1());
//        }
//
//
//        ArrayList<Multiple> object = new ArrayList<Multiple>();
//        Intent intent = new Intent(NodeMapSingleData.this, SingleDataBASEADDING.class);
//        Bundle args = new Bundle();
//        args.putSerializable("ARRAYLIST",(Serializable)array);
//        intent.putExtra("BUNDLE",args);
//        startActivity(intent);
    }


    public class CustomClusterRenderer extends DefaultClusterRenderer<Items> {

        public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<Items> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected int getColor(int clusterSize) {
//            return Color.parseColor("#567238");
            return Color.CYAN ;// Return any color you want here. You can base it on clusterSize.
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
                multipledata();
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
//    public boolean onMarkerClick(final Marker marker) {
//
//        // Return false to indicate that we have not consumed the event and that we wish
//        // for the default behavior to occur (which is for the camera to move such that the
//        // marker is centered and for the marker's info window to open, if it has one).
//        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//
//
//
//        return false;
//    }
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
                                // onMarkerClick(latLng);


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


//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        Toast.makeText(UnAssignedData.this, "Cluster item click", Toast.LENGTH_SHORT).show();

        final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
        markerOptions.icon(markerDescriptor);
//        final Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
//        gMap.animateCamera(CameraUpdateFactory.zoomTo(previousZoomLevel),
//                2000, null);
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(Items items) {

//        Toast.makeText(this, items.getTitle() , Toast.LENGTH_SHORT).show();
        double lat = items.getPosition().latitude;
        double lng = items.getPosition().longitude;
        String ipaddress =items.getTitle();
        String lng1 = String.valueOf(lat);
        String logg = String.valueOf(lng);
        Intent intent = new Intent(UnAssignedData.this, AddUnssignedData.class);
        intent.putExtra("doubleValue_e1", ipaddress);
        intent.putExtra("doubleValue_e2", lng1);
        intent.putExtra("doubleValue_e3", logg);
        startActivity(intent);
        showStartDialog();


    }

    private void showStartDialog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setIcon(R.drawable.refereshbutton);
        builder1.setCancelable(false);
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

}

