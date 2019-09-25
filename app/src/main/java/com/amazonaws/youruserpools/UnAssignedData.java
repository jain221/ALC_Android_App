package com.amazonaws.youruserpools;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnAssignedData extends AppCompatActivity  implements OnMapReadyCallback,ClusterManager.OnClusterClickListener<Items>, ClusterManager.OnClusterInfoWindowClickListener<Items>, ClusterManager.OnClusterItemClickListener<Items>,  ClusterManager.OnClusterItemInfoWindowClickListener<Items> {
    int size =0;
    private int ii;
    String latit, longgg, ipaddr;
    public static String[] arrPath;
    private Context context;
    MapFragment mapFragment;
    ArrayList<ArrayList<String>> myList = new ArrayList<>();
    GoogleMap gMap;
    GoogleMap gmap;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng center, latLng;
    String title;
    AbstractList<Marker> myListMarker;
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
    //    double latit,longgg;
//    String ipaddr;
//    ArrayList<ArrayList<String>> myList = new ArrayList<>();
    ArrayList<String> latitude2 = new ArrayList<String>();
    ArrayList<String> longitude2 = new ArrayList<String>();
    ArrayList<String> ipaddress3 = new ArrayList<String>();

    double latitU ;
    double longggU ;
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

    Marker prevMarker1;
    ArrayList<String> ipaddress2 = new ArrayList<String>();
    LatLngBounds.Builder builder1;
    ImageView  resetButton ;
    private RadioButton editMode, allUnassingedNode,mulitiedit;
    private RadioGroup radioGroup;
    SharedPreferences sharedpreferences;
    public static final String BUTTON_STATE = "Button_State";
    // this is name of shared preferences file, must be same whenever accessing
    // the key value pair.
    public static final String MyPREFERENCES = "MyPrefs" ;
    Marker marker1;
    String ipaddr1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unassigneddata);
//        startDialog();

//        mGps = (ImageView) findViewById(R.id.ic_gps);
//        et = (AutoCompleteTextView) findViewById(R.id.editText);
//        mulitiedit = (RadioButton) findViewById(R.id.multiedit);
//        editMode = (RadioButton) findViewById(R.id.radioButton);
//        allUnassingedNode = (RadioButton) findViewById(R.id.allNode);
//        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
//        radioGroup.check(  allUnassingedNode.getId());
//        getLocationPermission();
//        if (gMap != null) {
//
//            gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//                @Override
//                public void onMapLongClick(LatLng latLng) {
//                    Geocoder geocoder = new Geocoder(UnAssignedData.this);
//                    List<Address> list;
//                    try {
//                        list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                    } catch (IOException e) {
//                        return;
//                    }
//
//                    Address address = list.get(0);
//                    if (myMarker != null) {
//                        myMarker.remove();
//                    }
//
//                    MarkerOptions options = new MarkerOptions()
//                            .title(address.getLocality())
//                            .position(new LatLng(latLng.latitude, latLng.longitude));
//
//                    myMarker = gMap.addMarker(options);
//                }
//            });
//        }
//
//        List = new ArrayList<SuggestGetSet>();
//        getAutoComlete();
//        resetButton =(ImageView) findViewById(R.id.ic_addData);
//        resetButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Log.d("onClick", "Button is Clicked");
//                Toast.makeText(UnAssignedData.this,"Multiple data is been selected", Toast.LENGTH_LONG).show();
//                multipledata();
//                showStartDialog();
//            }
//        });
//
////        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
////        // grab the last saved state here on each activity start
////        Boolean lastButtonState = sharedpreferences.getBoolean(BUTTON_STATE, false);
////
////        allUnassingedNode.setChecked(lastButtonState);
//
////
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
////                RadioButton radioButton=(RadioButton)findViewById(checkedId);
////                Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
//
//                switch( checkedId) {
//                    case R.id.radioButton:
////                        RefereshDialog();
////                        showStartDialog1();
//                        gMap.clear();
//                        getMarkers1();
//                        Toast.makeText(UnAssignedData.this,"Single Edit Mode is Selected", Toast.LENGTH_LONG).show();
//                        break;
//
//                    case R.id.multiedit:
////                        showStartDialog1();
//                        gMap.clear();
//                        Intent intent = new Intent(UnAssignedData.this, UnassignedMode.class);
//                        startActivity(intent);
//                        Toast.makeText(UnAssignedData.this,"Multiple Edit Mode is Selected", Toast.LENGTH_LONG).show();
////
//
//                        break;
//
//                    case R.id.allNode:
////                        SharedPreferences.Editor editor = sharedpreferences.edit();
////                        Boolean isChecked = allUnassingedNode.isChecked();
////                        editor.putBoolean(BUTTON_STATE, isChecked);
////                        editor.apply();
////                        RefereshDialog();
////                        Toast.makeText(UnAssignedData.this,"All Data Selected", Toast.LENGTH_LONG).show();
////                        gMap.clear();
//                        getMarkers();
//
//
//
//                        break;
//                }
//            }
//        });
//
//
////        allUnassingedNode.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                SharedPreferences.Editor editor = sharedpreferences.edit();
////                Boolean isChecked = allUnassingedNode.isChecked();
////                editor.putBoolean(BUTTON_STATE, isChecked);
////                editor.apply();
////                Toast.makeText(UnAssignedData.this,"All Data Selected", Toast.LENGTH_LONG).show();
////                gMap.clear();
////                getMarkers();
////
////
////
//////
////            }
////        });
//
////        editMode.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                // call this to enable editing of the shared preferences file
////                // in the event of a change
//////                SharedPreferences.Editor editor = sharedpreferences.edit();
//////                Boolean isChecked = allUnassingedNode.isChecked();
//////                // use this to add the new state
//////                editor.putBoolean(BUTTON_STATE, isChecked);
//////                // save
//////                editor.apply();
////                showStartDialog1();
////                gMap.clear();
////                getMarkers1();
////                Toast.makeText(UnAssignedData.this,"Single Edit Mode is Selected", Toast.LENGTH_LONG).show();
////
////            }
////        });
//
////        mulitiedit.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                // call this to enable editing of the shared preferences file
////                // in the event of a change
//////                SharedPreferences.Editor editor = sharedpreferences.edit();
//////                Boolean isChecked = allUnassingedNode.isChecked();
//////                // use this to add the new state
//////                editor.putBoolean(BUTTON_STATE, isChecked);
//////                // save
//////                editor.apply();
////                showStartDialog1();
////                gMap.clear();
////                getMarkersMultiple();
////                Toast.makeText(UnAssignedData.this,"Multiple Edit Mode is Selected", Toast.LENGTH_LONG).show();
////
////            }
////        });
//    }
//
//
//private void showStartAll() {
//
//
//    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//    builder1.setIcon(R.drawable.refereshbutton);
//    builder1.setCancelable(false);
//    builder1.setTitle("Refresh Page");
//    builder1.setMessage("Are you Sure you want to see all Column Node");
//    builder1.setPositiveButton("Yes",
//            new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    Referesh();
//                }
//            });
//
//    AlertDialog alert11 = builder1.create();
//    alert11.show();
//
//
//    Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
//    buttonbackground1.setBackgroundColor(R.drawable.button);
//    buttonbackground1.setTextColor(Color.BLACK);
//
//}
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        gMap = googleMap;
//        center = new LatLng(51.52042, -3.23113);
//        mClusterManager = new ClusterManager<>(this, gMap);
//        gMap.setOnCameraIdleListener(mClusterManager);
//        gMap.setOnMarkerClickListener(mClusterManager);
//        gMap.setOnInfoWindowClickListener(mClusterManager);
//        mClusterManager.setOnClusterClickListener(this);
//        mClusterManager.setOnClusterInfoWindowClickListener(this);
//        mClusterManager.setOnClusterItemClickListener(this);
//        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
//        mClusterManager.cluster();
//        builder = new LatLngBounds.Builder();
//        getMarkers();
////        builder = new LatLngBounds.Builder();
//        InfoWndowAdapter markerInfoWindowAdapter = new InfoWndowAdapter(getApplicationContext());
//        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
////
//        // Set a listener for marker click.
////        gMap.setOnMarkerClickListener(this);
////        getMarkers();
//
//        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                float zoomLevel = 16.0f; //This goes up to 21
//                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
//            }
//        });
//
//        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);
//
//        mGps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Log.d(TAG1, "onClick: clicked gps icon");
////                getDeviceLocation();
//                latitude2.add(String.valueOf(latit));
//                longitude2.add(String.valueOf(longgg));
//                ipaddress2.remove(0);
//                latitude2.remove(0);
//                longitude2.remove(0);
//                double lat = Double.parseDouble(latitude2.remove(0));
//                double loggg = Double.parseDouble(longitude2.remove(0));
//                LatLng latLng1= new LatLng ( lat, loggg );
//                size = ipaddress2.size();
//                showStartDialogallData1();
////                MarkerOptions markerOptions1 = new MarkerOptions()
////                        .position(latLng1)
////                        .title(title)
////                        .snippet("Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude)
////                      preMa  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
////                gMap.addMarker(markerOptions1);
//               prevMarker1.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
////               myList.remove(myList.getItem(count - 1));
//            }
//        });
//
//        final CustomClusterRenderer renderer = new CustomClusterRenderer(this, gMap, mClusterManager);
//
//        mClusterManager.setRenderer(renderer);
//
//
//
//
//    }
//
//    private void getLocation() {
//
////        LatLngBounds.Builder builder = new LatLngBounds.Builder();
////        builder.include(marker.getPosition());
////        CameraPosition cameraPosition = new CameraPosition.Builder()
////                .target(latLng)      // Sets the center of the map to Mountain View
////                .zoom(10)                   // Sets the zoom
//////                .bearing(180)                // Sets the orientation of the camera to east
//////                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
////                .build();                   // Creates a CameraPosition from the builder
////        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//        LatLngBounds bounds = builder.build();
//
//        int width = getResources().getDisplayMetrics().widthPixels;
//        int height = getResources().getDisplayMetrics().heightPixels;
//        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
//
//        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
//
//        gMap.animateCamera(cu);
//
//    }
//
//
//    private void onMarkerClick(GoogleMap.OnMarkerClickListener onMarkerClickListener) {
//    }
//
//
//    private void run1(){
////Your first functionality
//
//        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);
//
//    }
//
//    public void onBackPressed() {
//
//        gMap.setMapType(gMap.MAP_TYPE_NORMAL);
//    }
//
//
//    private void getDeviceLocation() {
//        Log.d(TAG1, "getDeviceLocation: getting the devices current location");
//
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(UnAssignedData.this);
//
//        try {
//            if (mLocationPermissionsGranted) {
//
//                final Task location = mFusedLocationProviderClient.getLastLocation();
//                location.addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG1, "onComplete: found location!");
//                            Location currentLocation = (Location) task.getResult();
//
//                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
//                                    DEFAULT_ZOOM);
//                            latt= currentLocation.getLatitude();
//                            logg=currentLocation.getLongitude();
//
//
//                        } else {
//                            Log.d(TAG1, "onComplete: current location is null");
//                            Toast.makeText(UnAssignedData.this, "unable to get current location", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e) {
//            Log.e(TAG1, "getDeviceLocation: SecurityException: " + e.getMessage());
//        }
//    }
//
//
//
//    private void moveCamera(LatLng latLng, float zoom) {
//        Log.d(TAG1, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
//        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
//
//
//
//        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
//
//// Zoom in, animating the camera.
//        gMap.animateCamera(CameraUpdateFactory.zoomIn());
//
//// Zoom out to zoom level 10, animating with a duration of 2 seconds.
//        gMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
//
//        //Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(latLng)      // Sets the center of the map to Mountain View
//                .zoom(17)                   // Sets the zoom
////                .bearing(180)                // Sets the orientation of the camera to east
////                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
//                .build();                   // Creates a CameraPosition from the builder
//        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//
//
//
//                hideSoftKeyboard();
//    }
//
//    private void initMap() {
//        Log.d(TAG1, "initMap: initializing map");
//        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(UnAssignedData.this);
//
////        StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama);
////        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
//
//
//    }
//
//    private void getLocationPermission() {
//        Log.d(TAG1, "getLocationPermission: getting location permissions");
//        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION};
//
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                mLocationPermissionsGranted = true;
//                initMap();
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        permissions,
//                        LOCATION_PERMISSION_REQUEST_CODE);
//            }
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    permissions,
//                    LOCATION_PERMISSION_REQUEST_CODE);
//        }
//    }
//
//
//    public void findLocation(View v) throws IOException {
//
//
//
//        String location = et.getText().toString();
//        et.getText().clear();
//        hideSoftKeyboard();
//        Geocoder geocoder = new Geocoder(this);
//        List<Address> list = geocoder.getFromLocationName(location, 1);
//        if (list.size() > 0) {
//            Address address = list.get(0);
//            String locality = address.getLocality();
//            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
////            gMap.addMarker(new MarkerOptions().position(latLng).title("Find Pro"));
//            gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
////            if(myMarker != null)
////                myMarker.remove();
////            myMarker = gMap.addMarker(new MarkerOptions()
////                    .position(latLng)
////                    .title(locality)
////                    .snippet("Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude)
////                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
////            ;
//
//        }else {
//            Toast.makeText(UnAssignedData.this, "Check Spelling Or Try Again !", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
//    private void hideSoftKeyboard() {
//        // Check if no view has focus:
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }
//
//
//
//
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
//
////    @Override
////    public boolean onMarkerClick(Marker marker) {
////        marker.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
//////        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
////
//////        Toast.makeText(UnAssignedData.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
//////        Integer clickCount = (Integer) marker.getTag();
//////
//////        if (clickCount == null) {
//////            clickCount = 0;
//////        }
//////
//////        clickCount = clickCount + 1;
//////
//////        if(clickCount ==2){
//////
//////            Toast.makeText(this, "Refersh the page you cliced many times " + clickCount + " times.", Toast.LENGTH_SHORT).show();
//////            showStartDialog();
//////        }
////////        marker.setTag(clickCount
//////        else {
////
////            ipaddr = marker.getTitle();
////            latit = marker.getPosition().latitude;
////            longgg = marker.getPosition().longitude;
////            ipaddress2.add(ipaddr);
////            latitude2.add(String.valueOf(latit));
////            longitude2.add(String.valueOf(longgg));
////
////
////
//////        return true;
////
////        return false;
////
////
////
////
////
////    }
//    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
//        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
//        vectorDrawable.setBounds(1, 1, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
//        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        vectorDrawable.draw(canvas);
//        return BitmapDescriptorFactory.fromBitmap(bitmap);
//    }
//
//
////    private void Streetview1() {
////
////        double l = latit;
////        double lg = longgg;
////        Intent intent = new Intent(UnAssignedData.this, StreetViewMap.class);
////        intent.putExtra("doubleValue_e1", l);
////        intent.putExtra("doubleValue_e2", lg);
////
////        startActivity(intent);
////    }
//
//
//
//    private void multipledata() {
//
//
//
//
//              myList.add(ipaddress2);
//              myList.add(latitude2);
//              myList.add(longitude2);
//
//
//        Intent intent = new Intent(UnAssignedData.this, AddUnssignedData.class);
//        for(int i = 0; i <= myList.size(); i++){
//            intent.putExtra("doubleValue_e1", ipaddress2.get(i));
//            intent.putExtra("doubleValue_e2", latitude2.get(i));
//            intent.putExtra("doubleValue_e3", longitude2.get(i));
//            startActivity(intent);
//
//        }
//
////        ipaddress2.add(ipaddr);
////        latitude2.add(String.valueOf(latit));
////        longitude2.add(String.valueOf(longgg));
////        multiple = new Multiple();
////        array = new ArrayList<Multiple>();
////        array.add(new Multiple( ipaddress2,latitude2,longitude2));
////
////        for (int i =0 ; i< array.size();i++){
////
////            multiple.setIpaddress1(array.get(i).getIpaddress1());
////            multiple.setLattitude1(array.get(i).getLattitude1());
////            multiple.setLongitude1(array.get(i).getLongitude1());
////        }
////
////
////        ArrayList<Multiple> object = new ArrayList<Multiple>();
////        Intent intent = new Intent(NodeMapSingleData.this, SingleDataBASEADDING.class);
////        Bundle args = new Bundle();
////        args.putSerializable("ARRAYLIST",(Serializable)array);
////        intent.putExtra("BUNDLE",args);
////        startActivity(intent);
//    }
//
//
//    public class CustomClusterRenderer extends DefaultClusterRenderer<Items> {
//
//        public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<Items> clusterManager) {
//            super(context, map, clusterManager);
//        }
//
//        @Override
//        protected int getColor(int clusterSize) {
////            return Color.parseColor("#567238");
//            return Color.BLUE ;// Return any color you want here. You can base it on clusterSize.
//        }
//        @Override
//        protected void onBeforeClusterItemRendered(Items item, MarkerOptions markerOptions) {
//
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//        }
//
//
//
//
//    }
//
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.add:
//                Toast.makeText(this, "Multiple data is selected", Toast.LENGTH_SHORT)
//                        .show();
//                multipledata();
//                showStartDialog();
//                break;
//            case R.id.action_refresh:
//                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
//                        .show();
//                Referesh();
//                break;
//            case R.id.mapTypeNone:
//                gMap.setMapType(GoogleMap.MAP_TYPE_NONE);
//                break;
//            case R.id.mapTypeNormal:
//                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                break;
//            case R.id.mapTypeSatellite:
//                gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                break;
//            case R.id.mapTypeTerrain:
//                gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//                break;
//            case R.id.mapTypeHybrid:
//                gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                break;
//
//            case R.id.stationList:
//                Intent intent = new Intent(UnAssignedData.this, StationList.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//
//
//    private void Referesh(){
//        finish();
//        startActivity(getIntent());
//        handler.post(refresh);
//    }
//
//    /**
//     * Called when the user clicks a marker.
//     */
////    public boolean onMarkerClick(final Marker marker) {
////
////        // Return false to indicate that we have not consumed the event and that we wish
////        // for the default behavior to occur (which is for the camera to move such that the
////        // marker is centered and for the marker's info window to open, if it has one).
////        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
////
////
////
////        return false;
////    }
//    public RequestQueue getRequestQueue() {
//        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        }
//
//        return mRequestQueue;
//    }
//
//    public <T> void addToRequestQueue(Request<T> req, String tag) {
//        req.setTag(TextUtils.isEmpty(tag) ? TAG1 : tag);
//        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        getRequestQueue().add(req);
//    }
//
//    public OnMapReadyCallback onMapReadyCallback1(){
//        return new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                gmap = googleMap;
//                LatLng vannes = new LatLng(47.66, -2.75);
//                gmap.addMarker(new MarkerOptions().position(vannes).title("Vannes"));
//                gmap.moveCamera(CameraUpdateFactory.newLatLng(vannes));
//            }
//        };
//    }
//
//
//
//    private void getMarkers() {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            //converting the string to json array object
//                            JSONArray array = new JSONArray(response);
//
//                            //traversing through all the object
//                            for (int i = 0; i < array.length(); i++) {
//
//                                //getting product object from json array
//                                JSONObject product = array.getJSONObject(i);
//                                ii = product.getInt(ID);
//                                title = product.getString(TITLE);
//                                latLng = new LatLng(Double.parseDouble(product.getString(LAT)), Double.parseDouble(product.getString(LNG)));
//                                //adding the product to product list
//                                addMarker(latLng, title);
//                                // onMarkerClick(latLng);
//
//
//                            }
//
//                            //creating adapter object and setting it to recyclerview
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(UnAssignedData.this, error.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }
//                });
//
//        //adding our stringrequest to queue
//        Volley.newRequestQueue(this).add(stringRequest);
//
//    }
//    private void addMarker(final LatLng latLng, final String title) {
//
//
//        MarkerOptions marker = new MarkerOptions();
//        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//
////        gMap.clear();
////        MarkerOptions markerOptions = new MarkerOptions();
////        markerOptions.position(latLng);
////        // marker.showInfoWindow();
////        MarkerOptions markerOptions1 = new MarkerOptions()
////                .position(latLng)
////                .title(title)
////                .snippet("Lat" + latLng.latitude + ",Logt." + latLng.longitude)
////                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
////        gMap.addMarker(markerOptions1);
//
//        builder.include(latLng);
//        LatLngBounds bounds = builder.build();
//        int width = getResources().getDisplayMetrics().widthPixels;
//        int height = getResources().getDisplayMetrics().heightPixels;
//        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
//
//        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
//
//        gMap.animateCamera(cu);
//
//
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(latLng)      // Sets the center of the map to Mountain View
//                .zoom(10)                   // Sets the zoom
////                .bearing(180)                // Sets the orientation of the camera to east
////                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
//                .build();                   // Creates a CameraPosition from the builder
//        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//
//
//        double lat =latLng.latitude;
//        double lng = latLng.longitude;
//        // Set he title and snippet strings.
//
//
//        final String snippet = "Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude;
//
//// Create a cluster item for the marker and set the title and snippet using the constructor.
//        Items infoWindowItem = new Items(lat,lng, title, snippet);
////        builder.include(latLng);
////
//// Add the cluster item (marker) to the cluster manager.
//        mClusterManager.addItem(infoWindowItem);
//
////        mClusterManager.setRenderer(new CustomClusterRenderer(UnAssignedData.this, gMap, mClusterManager));
//
//        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Items>() {
//            @Override
//            public boolean onClusterClick(Cluster<Items> cluster) {
//
//                String firstName = cluster.getItems().iterator().next().getTitle();
//                //     Create the builder to collect all essential cluster items for the bounds.
//                LatLngBounds.Builder builder = LatLngBounds.builder();
//                String array = null;
//                for (ClusterItem item : cluster.getItems()) {
//                    builder.include(item.getPosition());
//                    array = item.getTitle();
//                }
//                // Get the LatLngBounds
//                final LatLngBounds bounds = builder.build();
//
//                // Animate camera to the bounds
//                try {
//                    if(bounds.equals(cu)){
//                        editMode.setVisibility(View.INVISIBLE);
//                    }
//                    else {
//                        gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
//                        editMode.setVisibility(View.VISIBLE);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
////                for (ClusterItem item : cluster.getItems()) {
////                  data = (item.getTitle());
////                }
//
//                ArrayList<String> addData = new ArrayList<String>();
//
//                addData.add(title);
//
//
//                return true;
//            }
//        });
//
//
//
//
//
//    }
//
//    private void getMarkers1() {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET ,URL_PRODUCTS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            //converting the string to json array object
//                            JSONArray array = new JSONArray(response);
//
//                            //traversing through all the object
//                            for (int i = 0; i < array.length(); i++) {
//
//                                //getting product object from json array
//                                JSONObject product = array.getJSONObject(i);
//                                ii = product.getInt(ID);
//                                title = product.getString(TITLE);
//                                latLng = new LatLng(Double.parseDouble(product.getString(LAT)), Double.parseDouble(product.getString(LNG)));
//                                addMarkerAll1(latLng, title);
//
//                            }
//
//                            //creating adapter object and setting it to recyclerview
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(UnAssignedData.this, error.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }
//                });
//
//        //adding our stringrequest to queue
//        Volley.newRequestQueue(this).add(stringRequest);
//
//    }
//
//    private void addMarkerAll1(final LatLng latLng, final String title) {
//
//
//
////        final String snippet1 = (" Status : Unassigned Structure" + " \n Lat:  " + lat + ",Longitude:  " + lng + " \n Column : Null " + " \n Colume Manf: Null " + "\n Raise & Lower: Null " +
////                "\n Colume Material: Null " + " \n Colume Type: Null" + " \n Colume Height: Null " + " \n Number of Door: Null " + " \n Door Dimen: Null " + "\n Foundation type: Null" +
////                "\n Column Bracket: Null" + " \n Bracket Length: Null" + "\n Estimated Age of Lat: Null" + " \n Lat. Manf: Null ");
//
//        MarkerOptions markerOptions1 = new MarkerOptions()
//                .position(latLng)
//                .title(title)
//                .snippet("Lat" + latLng.latitude + ",Logt." + latLng.longitude)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//        gMap.addMarker(markerOptions1);
////
//        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker mark1) {
////                        Toast.makeText(CurrentLocation.this, mark1.getTitle(), Toast.LENGTH_SHORT).show();
//
//                double lat = mark1.getPosition().latitude;
//                double lng = mark1.getPosition().longitude;
//                String ipaddress =mark1.getTitle();
//                String lng1 = String.valueOf(lat);
//                String logg = String.valueOf(lng);
//                Intent intent = new Intent(UnAssignedData.this, AddUnssignedData.class);
//                intent.putExtra("doubleValue_e1", ipaddress);
//                intent.putExtra("doubleValue_e2", lng1);
//                intent.putExtra("doubleValue_e3", logg);
//                startActivity(intent);
////                        showStartDialog();
//                showStartDialog();
//
//            }
//        });
//
//    }
////    private void getMarkersMultiple() {
////        StringRequest stringRequest = new StringRequest(Request.Method.GET ,URL_PRODUCTS,
////                new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
////                        try {
////                            //converting the string to json array object
////                            JSONArray array = new JSONArray(response);
////
////                            //traversing through all the object
////                            for (int i = 0; i < array.length(); i++) {
////
////                                //getting product object from json array
////                                JSONObject product = array.getJSONObject(i);
////                                ii = product.getInt(ID);
////                                title = product.getString(TITLE);
////                                latLng = new LatLng(Double.parseDouble(product.getString(LAT)), Double.parseDouble(product.getString(LNG)));
////                                addMarkerMultile(latLng, title);
////
////
////                            }
////
////                            //creating adapter object and setting it to recyclerview
////
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(UnAssignedData.this, error.getMessage(), Toast.LENGTH_LONG).show();
////
////                    }
////                });
////
////        //adding our stringrequest to queue
////        Volley.newRequestQueue(this).add(stringRequest);
////
////    }
////
////    private void addMarkerMultile(final LatLng latLng, final String title) {
////
////        MarkerOptions markerOptions1 = new MarkerOptions()
////                .position(latLng)
////                .title(title)
////                .snippet("Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude)
////                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
////        gMap.addMarker(markerOptions1);
////
////
////        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
////
////            @Override
////            public boolean onMarkerClick(Marker marker1) {
////                marker1.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
////////                marker1.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
//////
//////
//////                if (prevMarker1 != null) {
//////                    //Set prevMarker back to default color
//////                    marker1.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//////
////////                    latit = String.valueOf(marker1.getPosition().latitude);
//////////                    longgg = String.valueOf(marker1.getPosition().longitude);
//////////                    ipaddr = marker1.getTitle();
//////////
//////////                    ipaddress2.add(ipaddr);
//////////                    latitude2.add(String.valueOf(latit));
//////////                    longitude2.add(String.valueOf(longgg));
//////
////////                    latitude2.clear();
////////                    longitude2.clear();
////////                    myList.clear();
//////                }
//////
//////                //leave Marker default color if re-click current Marker
//////               if(!marker1.equals(prevMarker1)) {
////////                   double lat = (double) Double.parseDouble(marker1.);
////////                   double loggg = Double.parseDouble(longitude2.remove(0));
////////                   LatLng latLng1= new LatLng ( marker1);
////////
////////                   showStartDialogallData1();
////////                   MarkerOptions markerOptions1 = new MarkerOptions()
////////                           .position(latLng1)
////////                           .title(title)
////////                           .snippet("Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude)
////////                           .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
////////                   gMap.addMarker(markerOptions1);
//////
//////                    marker1.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
//////                    prevMarker1 = marker1;
//////               }
//////                prevMarker1 = marker1;
//////                latit = String.valueOf(marker1.getPosition().latitude);
//////                longgg = String.valueOf(marker1.getPosition().longitude);
//////                ipaddr = marker1.getTitle();
//////                ipaddress2.add(ipaddr);
//////                latitude2.add(String.valueOf(latit));
//////                longitude2.add(String.valueOf(longgg));
////////               ipaddress2.si;
//////
//////
//////
//////                size =    ipaddress2.size();
////////                System.out.println("length of ArrayList after adding elements: " + size);
//////                showStartDialogallData1();
////
////                return true;
////
////            }
////        });
////
////    }
//
//
//
//    private void getAutoComlete() {
//
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Data,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            //converting the string to json array object
//                            JSONArray array = new JSONArray(response);
//
//                            //traversing through all the object
//                            for (int i = 0; i < array.length(); i++) {
//
//                                //getting product object from json array
//                                JSONObject catobj= array.getJSONObject(i);
//                                SuggestGetSet colman = new  SuggestGetSet(catobj.getString(ID1), catobj.getString(Station11));
////                                ListData.add(new SuggestGetSet(r.getString("Id"),r.getString("Station")));
//                                List.add(colman);
////                                populateSpinner();
//                                getData();
//
//                            }
//                            //creating adapter object and setting it to recyclerview
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(UnAssignedData.this, error.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }
//                });
//
//        //adding our stringrequest to queue
//        Volley.newRequestQueue(this).add(stringRequest);
//
//
//    }
//
//    private void getData() {
//        List<String> lables = new ArrayList<String>();
//
//        //txtCategory.setText("");
//
//        for (int i = 0; i < List.size(); i++) {
//            lables.add(List.get(i).getStation());
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lables );
//        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
//        et.setAdapter(adapter);
//
//
//    }
//
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.d(TAG1, "onRequestPermissionsResult: called.");
//        mLocationPermissionsGranted = false;
//
//        switch (requestCode) {
//            case LOCATION_PERMISSION_REQUEST_CODE: {
//                if (grantResults.length > 0) {
//                    for (int i = 0; i < grantResults.length; i++) {
//                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                            mLocationPermissionsGranted = false;
//                            Log.d(TAG1, "onRequestPermissionsResult: permission failed");
//                            return;
//                        }
//                    }
//                    Log.d(TAG1, "onRequestPermissionsResult: permission granted");
//                    mLocationPermissionsGranted = true;
//                    //initialize our map
//                    initMap();
//                }
//            }
//        }
//    }
//
//
//
//
//
//
//    @Override
//    public boolean onClusterClick(Cluster<Items> cluster) {
//
//
//        return true ;
//    }
//
//
//    @Override
//    public void onClusterInfoWindowClick(Cluster<Items> cluster) {
//
//
//
//
//    }
//
//    @Override
//    public boolean onClusterItemClick(Items items) {
//
//
////        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//
////        Toast.makeText(UnAssignedData.this, "Cluster item click", Toast.LENGTH_SHORT).show();
//        showStartDialogallData();
//
//
//
//
//        return true;
//    }
//
//    @Override
//    public void onClusterItemInfoWindowClick(Items items) {
//
//
//
//
//    }
//
//    private void showStartDialog() {
//
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setIcon(R.drawable.refereshbutton);
////        builder1.setCancelable(false);
//        builder1.setTitle("Refresh Page");
//        builder1.setMessage("Please type Yes button to Refresh page");
//        builder1.setPositiveButton("Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Referesh();
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//
//
//        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
//        buttonbackground1.setBackgroundColor(R.drawable.button);
//        buttonbackground1.setTextColor(Color.BLACK);
//
//
//    }
//
//
//
//
//    private void showStartDialogallData() {
//
//
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setMessage("->This is View Mode"+ "\n->Please click on Edit Mode Button on Top for filling data");
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//
//    }
//    private void showStartDialogallData1() {
//
//
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setMessage("->Marker is Clicked"+ size);
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//
//    }
//
//    private void startDialog() {
//
//
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setMessage("Please click on Non Edit Mode");
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//
//    }
//    private void showStartDialog1() {
//
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
////        builder1.setIcon(R.drawable.refereshbutton);
////        builder1.setCancelable(false);
//        builder1.setTitle("Edit Mode");
//        builder1.setMessage("Have you Zoom IN ?" + "\nIf Yes Please select which edit mode you want");
////        builder1.setPositiveButton("   Single",
////                new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        gMap.clear();
////                        getMarkers1();
////
////                    }
////                });
////        builder1.setNegativeButton("Multiple  ", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                gMap.clear();
////                getMarkersMultiple();
////
////            }
////        });
//
////
////        builder1.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                dialog.cancel();
////            }
////        });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
////
////
////        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
////        buttonbackground1.setTextColor(Color.BLACK);
////        Button buttonbackground2 = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
////        buttonbackground2.setTextColor(Color.BLACK);
////        Button buttonbackground3 = alert11.getButton(DialogInterface.BUTTON_NEUTRAL);
////        buttonbackground3.setTextColor(Color.BLACK);
//
//
//    }
//    private void RefereshDialog() {
//
//
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setIcon(R.drawable.refereshbutton);
////        builder1.setCancelable(false);
//        builder1.setTitle("Refresh Page");
//        builder1.setMessage("Are you Sure you want to see all Column Node");
//        builder1.setPositiveButton("Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Referesh();
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//
//
//        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
//        buttonbackground1.setBackgroundColor(R.drawable.button);
//        buttonbackground1.setTextColor(Color.BLACK);
//
//    }
//}
//



        mGps = (ImageView) findViewById(R.id.ic_gps);
        et = (AutoCompleteTextView) findViewById(R.id.editText);

//        mulitiedit = (RadioButton) findViewById(R.id.multiedit);
//        editMode = (RadioButton) findViewById(R.id.radioButton);
//        allUnassingedNode = (RadioButton) findViewById(R.id.allNode);
//        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
//        radioGroup.check(  mulitiedit.getId());
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // grab the last saved state here on each activity start
//        Boolean lastButtonState = sharedpreferences.getBoolean(BUTTON_STATE, false);



//        radioGroup.check( alldata.getId());
//        allUnassingedNode.setChecked(lastButtonState);
//        allUnassingedNode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // call this to enable editing of the shared preferences file
//                // in the event of a change
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                Boolean isChecked = allUnassingedNode.isChecked();
//                // use this to add the new state
//                editor.putBoolean(BUTTON_STATE, isChecked);
//                // save
//                editor.apply();
//                Toast.makeText(UnassignedMode.this,"All Data Selected", Toast.LENGTH_LONG).show();
//                gMap.clear();
////                showStartAll();
//                getMarkers();
//
////
//            }
//        });

//        editMode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                gMap.clear();
//                Toast.makeText(UnassignedMode.this,"Edit Mode is Selected", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(UnassignedMode.this, UnAssignedData.class);
//                startActivity(intent);
//            }
//        });
//
//
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
////                RadioButton radioButton=(RadioButton)findViewById(checkedId);
////                Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
//
//                switch( checkedId) {
//                    case R.id.radioButton:
////                        RefereshDialog();
////                        showStartDialog1();
//                        gMap.clear();
//                        Toast.makeText(UnassignedMode.this,"Edit Mode is Selected", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(UnassignedMode.this, UnAssignedData.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.multiedit:
////                        showStartDialog1();
//                        gMap.clear();
//
////
//
//                        break;
//
//                    case R.id.allNode:
////                        SharedPreferences.Editor editor = sharedpreferences.edit();
////                        Boolean isChecked = allUnassingedNode.isChecked();
////                        editor.putBoolean(BUTTON_STATE, isChecked);
////                        editor.apply();
////                        RefereshDialog();
////                        Toast.makeText(UnAssignedData.this,"All Data Selected", Toast.LENGTH_LONG).show();
////                        gMap.clear();
//                        getMarkers();
//
//
//
//                        break;
//                }
//            }
//        });
//




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
        gMap.clear();
        getMarkers();
//        builder = new LatLngBounds.Builder();
        InfoWndowAdapter markerInfoWindowAdapter = new InfoWndowAdapter(getApplicationContext());
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
//


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

        final UnAssignedData.CustomClusterRenderer renderer = new UnAssignedData.CustomClusterRenderer(this, gMap, mClusterManager);


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

        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

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

//        mClusterManager.setRenderer(new CustomClusterRenderer(UnAssignedData.this, gMap, mClusterManager));

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
                    if(bounds.equals(cu)){
                        editMode.setVisibility(View.INVISIBLE);
                    }
                    else {
                        gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                        editMode.setVisibility(View.VISIBLE);
                    }
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

    public OnMapReadyCallback onMapReadyCallback1(){
        return new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                LatLng vannes = new LatLng(47.66, -2.75);
                gmap.addMarker(new MarkerOptions().position(vannes).title("Vannes"));
                gmap.moveCamera(CameraUpdateFactory.newLatLng(vannes));
            }
        };
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
        builder1.setMessage("->This is View Mode"+ "\n->Please Zoom IN "+"\n->You want to Add Multiple data");
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
        builder1.setMessage("->Select each Marker which Attribute data you want to add");
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
    private void showStartDialogallData1() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Add Unassigned Data");
        builder1.setIcon(R.drawable.multipledata);
        builder1.setMessage("You Click "+ size + " Marker." + " You want to add data");
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        multipledata();
                        showStartAll();

                    }
                });

        builder1.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
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

        Button buttonbackground2 = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        buttonbackground2.setTextColor(Color.BLACK);


    }

    private void multipledata() {

        Intent intent = new Intent(UnAssignedData.this, AddUnssignedData.class);
        for (int i = 0; i < ipaddress3.size(); i++) {
            intent.putExtra("doubleValue_e1", ipaddress3.get(i));
            intent.putExtra("doubleValue_e2", latitude2.get(i));
            intent.putExtra("doubleValue_e3", longitude2.get(i));
            startActivity(intent);

        }


    }

}


