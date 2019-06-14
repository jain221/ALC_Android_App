package com.amazonaws.youruserpools;
import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import com.google.android.gms.maps.StreetViewPanorama;
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
import java.util.HashMap;
import java.util.List;

public class AvaliableData extends AppCompatActivity  implements OnMapReadyCallback,ClusterManager.OnClusterClickListener<Items>, ClusterManager.OnClusterInfoWindowClickListener<Items>, ClusterManager.OnClusterItemClickListener<Items>, ClusterManager.OnClusterItemInfoWindowClickListener<Items>, GoogleMap.OnMarkerClickListener {

    HttpParse httpParse = new HttpParse();
    String HttpURL = "https://o5yklvu3td.execute-api.eu-west-1.amazonaws.com/default/fetchData";
    String ipaddress2;
    MapFragment mapFragment;
    GoogleMap gMap;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng center, latLng;
    String title;
    private RequestQueue mRequestQueue;
    public static final String TAG1 = CurrentNode.class.getSimpleName();
    public static final String ID = "id";
    public static final String ID1 = "Id";
    public static final String TITLE = "ipaddress";
    public static final String LAT = "latitude";
    public static final String LNG = "longitude";
    public static final String Station11 = "Station";
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
    public static final String LatenManfu = "lantern_manufacturer";
    String ip, latt, logg, cl, rs, cm, ct, chg, nd, dd, ft, bt, bl, eage, lm;
    private Marker myMarker;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17f;
    //vars
    String latit,longgg,ipaddr;
    TextView ipaddress, lat2, log2, cl12, rs12, cm12, ct12, chg12, nd12, dd12, ft12, bt12, bl12, eage12, lm12;
    private ArrayList<SuggestGetSet> List;
    //    private SuggestionAdapter mSuggestionAdapter;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String URL_PRODUCTS = "https://wwf5avjfai.execute-api.eu-west-1.amazonaws.com/ISDMAPDATA/ISDgetAllData";
    private static final String URL_Data = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/StationNameGetFunction";
    private ClusterManager<Items> mClusterManager;
    private List<Items> items = new ArrayList<>();
    private ImageView mGps;
    private ImageView menu;
    String data;
    AutoCompleteTextView et;
    String tag_json_obj = "json_obj_req";
    private AutoCompleteTextView mSearchText;
    ArrayList<String> displayData = new ArrayList<String>();
    private static final LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);
    private Marker locationMarker;
    private Context context;
    private Button infoButton;
    CheckBox AllData;


    String lat3 ;
    String lng3;
    ProgressDialog pDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView NAME,PHONE_NUMBER,CLASS;
    String NameHolder, NumberHolder, ClassHolder;
    Button UpdateButton, DeleteButton;
    String TempItem;
    ProgressDialog progressDialog2;


    TextView lat, log, cl1, rs1, cm1, ct1, chg1, nd1, dd1, ft1, bt1, bl1, eage1, lm1;
    //    String ip,latt,logg,cl, rs, cm, ct, chg, nd, dd, ft, bt, bl, eage, lm;
    String e1, e2, e3;
    StreetViewPanorama mStreetViewPanorama;

    //    List<SuggestGetSet> ListData = new ArrayList<SuggestGetSet>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_node);

        mGps = (ImageView) findViewById(R.id.ic_gps);
        menu= (ImageView) findViewById(R.id.sattel);
        //  mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);
        et = (AutoCompleteTextView) findViewById(R.id.editText);
        getLocationPermission();
        if (gMap != null) {

            gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Geocoder geocoder = new Geocoder(AvaliableData.this);
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


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
//        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
//        Log.d(TAG1, "onMapReady: map is ready");
//        // Mengarahkan ke alun-alun Demak
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
        InfoWndowAdapter markerInfoWindowAdapter = new InfoWndowAdapter(getApplicationContext());
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
        // Set a listener for marker click.
        gMap.setOnMarkerClickListener(this);

        cameraPosition = new CameraPosition.Builder().target(center).zoom(5).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setMyLocationButtonEnabled(false);

        }

        getMarkers();
//        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);
        gMap.setMapType(gMap.MAP_TYPE_NORMAL);
        //   gMap.setOnCameraChangeListener((GoogleMap.OnCameraChangeListener) mClusterManager);

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG1, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG1, "onClick: clicked gps icon");
                run1();



            }

        });

        menu.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                gMap.setMapType(gMap.MAP_TYPE_NORMAL);
                return true;

            }

        });

        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Intent intent = new Intent(AvaliableData .this, NodeMapSingleData.class);
                startActivity(intent);
            }
        });

        onBackPressed();
        final CustomClusterRenderer renderer = new CustomClusterRenderer(this, gMap, mClusterManager);

        mClusterManager.setRenderer(renderer);

        onBackPressed();


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

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AvaliableData.this);

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
                            Toast.makeText(AvaliableData.this, "unable to get current location", Toast.LENGTH_SHORT).show();
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
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        hideSoftKeyboard();
    }

    private void initMap() {
        Log.d(TAG1, "initMap: initializing map");
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(AvaliableData.this);

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

//    public void findLocation(View v) throws IOException {
//
////        EditText et = (EditText) findViewById(R.id.editText);
//
//
//        String location = et.getText().toString();
//        Geocoder geocoder = new Geocoder(this);
//        List<Address> list = geocoder.getFromLocationName(location, 1);
//        Address add = list.get(0);
//        String locality = add.getLocality();
//        LatLng ll = new LatLng(add.getLatitude(), add.getLongitude());
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 17);
//        hideSoftKeyboard();
//   //    et.setError("Wrong Search! ");
//        gMap.moveCamera(update);
//        if(myMarker != null)
//            myMarker.remove();
//        myMarker = gMap.addMarker(new MarkerOptions()
//                .position(ll)
//                .title(locality)
//                .snippet("Latitude:" + ll.latitude + ",Longitude" + ll.longitude)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
//        ;
//
//
//    }


    public void findLocation(View v) throws IOException {

//        EditText et = (EditText) findViewById(R.id.editText);


        String location = et.getText().toString();
        et.getText().clear();
        hideSoftKeyboard();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        if (list.size() > 0) {
            Address address = list.get(0);
            String locality = address.getLocality();
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            gMap.addMarker(new MarkerOptions().position(latLng).title("Find Pro"));
            gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            if(myMarker != null)
                myMarker.remove();
            myMarker = gMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(locality)
                    .snippet("Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
            ;

        }else {
            Toast.makeText(AvaliableData.this, "Check Spelling Or Try Again !", Toast.LENGTH_SHORT).show();
        }

//        Address add = list.get(0);
//        String locality = add.getLocality();
//        LatLng ll = new LatLng(add.getLatitude(), add.getLongitude());
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 17);
//        hideSoftKeyboard();
//        //    et.setError("Wrong Search! ");
//        gMap.moveCamera(update);
//        if(myMarker != null)
//            myMarker.remove();
//        myMarker = gMap.addMarker(new MarkerOptions()
//                .position(ll)
//                .title(locality)
//                .snippet("Latitude:" + ll.latitude + ",Longitude" + ll.longitude)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
//        ;


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



//        markerOptions.position(latLng);
//        markerOptions.title(title);
//        gMap.addMarker(markerOptions);
//
//
//        locationMarker = gMap.addMarker(markerOptions);
//        myMarker = gMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .title(title)
//                .snippet("Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
//        ;
//        Items offsetItem = new Items(latLng);
//        mClusterManager.addItem(offsetItem);



        // Supported formats are: #RRGGBB #AARRGGBB
        //   #AA is the alpha, or amount of transparency



        double lat =latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet = "Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude;

// Create a cluster item for the marker and set the title and snippet using the constructor.
        Items infoWindowItem = new Items(lat,lng, title, snippet);

//
// Add the cluster item (marker) to the cluster manager.
        mClusterManager.addItem(infoWindowItem);

        mClusterManager.setRenderer(new CustomClusterRenderer(AvaliableData.this, gMap, mClusterManager));
//

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

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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


            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * Called when the user clicks a marker.
     */
    public boolean onMarkerClick(final Marker marker) {

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        latit = String.valueOf(marker.getPosition().latitude);
        longgg = String.valueOf(marker.getPosition().longitude);
        ipaddr = marker.getTitle();
        showDialog(AvaliableData.this);
        return false;
    }


    private void showDialog(AvaliableData avaliableData) {

        final Dialog dialog = new Dialog(avaliableData);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_listview);

        Button btndialog = (Button) dialog.findViewById(R.id.btndialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


//        Button Rnode = (Button) dialog.findViewById(R.id.Rnode);
//        Rnode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(AvaliableData.this, OldScmsInstallation.class);
////                startActivity(intent);
//
//
////                Toast.makeText(this, myMarker.getTitle() , Toast.LENGTH_SHORT).show();
//                String lat = latit;
//                String lng = longgg;
//                String ipaddress =ipaddr;
//                String c11 =String.valueOf(cl);
//                String rs1 =String.valueOf(rs);
//                String cm1 =String.valueOf(cm);
//                String ct1 =String.valueOf(ct);
//                String chg1 =String.valueOf(chg);
//                String nd1 =String.valueOf(nd);
//                String dd1 =String.valueOf(dd);
//                String ft1 =String.valueOf(ft);
//                String bt1 =String.valueOf(bt);
//                String b11 =String.valueOf(bl);
//                String eage1 =String.valueOf(eage);
//                String lm1 =String.valueOf(lm);
//                Intent intent = new Intent(AvaliableData.this,showsingleData.class);
//                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
//                intent.putExtra("doubleValue_e1", ipaddress);
//                intent.putExtra("doubleValue_e2", lat);
//                intent.putExtra("doubleValue_e3", lng);
//                intent.putExtra("doubleValue_e14", c11);
//                intent.putExtra("doubleValue_e15", rs1);
//                intent.putExtra("doubleValue_e4", cm1);
//                intent.putExtra("doubleValue_e5", ct1);
//                intent.putExtra("doubleValue_e6", chg1);
//                intent.putExtra("doubleValue_e7", nd1);
//                intent.putExtra("doubleValue_e8", dd1);
//                intent.putExtra("doubleValue_e9", ft1);
//                intent.putExtra("doubleValue_e10", bt1);
//                intent.putExtra("doubleValue_e11", b11);
//                intent.putExtra("doubleValue_e12", eage1);
//                intent.putExtra("doubleValue_e13", lm1);
//                startActivity(intent);
//
//                dialog.dismiss();
//            }
//        });
//





        Button Rnode = (Button) dialog.findViewById(R.id.Rnode);
        Rnode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AvaliableData.this, OldScmsInstallation.class);
//                startActivity(intent);


//                Toast.makeText(this, myMarker.getTitle() , Toast.LENGTH_SHORT).show();
                String lat = latit;
                String lng = longgg;
                String ipaddress =ipaddr;

                Intent intent = new Intent(AvaliableData.this,ReplaceNode.class);
                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
                intent.putExtra("doubleValue_e1", ipaddress);
                intent.putExtra("doubleValue_e2", lat);
                intent.putExtra("doubleValue_e3", lng);

                startActivity(intent);

                dialog.dismiss();
            }
        });


        Button Rlatern = (Button) dialog.findViewById(R.id.Rlatern);
        Rlatern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AvaliableData.this, OldScmsInstallation.class);
//                startActivity(intent);


//                Toast.makeText(this, myMarker.getTitle() , Toast.LENGTH_SHORT).show();
                String lat = latit;
                String lng = longgg;
                String ipaddress =ipaddr;

                Intent intent = new Intent(AvaliableData.this,ReplaceLatern.class);
                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
                intent.putExtra("doubleValue_e1", ipaddress);
                intent.putExtra("doubleValue_e2", lat);
                intent.putExtra("doubleValue_e3", lng);

                startActivity(intent);

                dialog.dismiss();
            }
        });

        Button RColume = (Button) dialog.findViewById(R.id.RColume);
        RColume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lat = latit;
                String lng = longgg;
                String ipaddress =ipaddr;

                Intent intent = new Intent(AvaliableData.this,ReplaceColumn.class);
                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
                intent.putExtra("doubleValue_e1", ipaddress);
                intent.putExtra("doubleValue_e2", lat);
                intent.putExtra("doubleValue_e3", lng);

                startActivity(intent);

                dialog.dismiss();
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
                String ipaddress =ipaddr;

                Intent intent = new Intent(AvaliableData.this,ReplaceLatern.class);
                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
                intent.putExtra("doubleValue_e1", ipaddress);
                intent.putExtra("doubleValue_e2", lat);
                intent.putExtra("doubleValue_e3", lng);

                startActivity(intent);

                dialog.dismiss();
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
                String ipaddress =ipaddr;

                Intent intent = new Intent(AvaliableData.this,ReplaceLatern.class);
                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
                intent.putExtra("doubleValue_e1", ipaddress);
                intent.putExtra("doubleValue_e2", lat);
                intent.putExtra("doubleValue_e3", lng);

                startActivity(intent);

                dialog.dismiss();
            }
        });


        Button checkcurrent = (Button) dialog.findViewById(R.id.checkcurrent);
        checkcurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                String lat = latit;
//                String lng = longgg;
//                String ipaddress2 =ipaddr;
//                Intent intent = new Intent(AvaliableData.this,showsingleData.class);
//                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
////                intent.putExtra("doubleValue_e1", ipaddress2);
////                intent.putExtra("doubleValue_e2", lat);
////                intent.putExtra("doubleValue_e3", lng);
//                dialog.dismiss();


//                Toast.makeText(this, myMarker.getTitle() , Toast.LENGTH_SHORT).show();
//                String lat = latit;
//                String lng = longgg;
                String ipaddress =ipaddr;

                Intent intent = new Intent(AvaliableData.this,showsingleData.class);
                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
                intent.putExtra("doubleValue_e1", ipaddress);
//                intent.putExtra("doubleValue_e2", lat);
//                intent.putExtra("doubleValue_e3", lng);

                startActivity(intent);

                dialog.dismiss();
            }
        });

        dialog.show();

    }


    //Method to show current record Current Selected Record
    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(AvaliableData.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                //Storing Complete JSon Object into String Variable.
                FinalJSonObject = httpResponseMsg ;

                //Parsing the Stored JSOn String to GetHttpResponse Method.
                new AvaliableData.GetHttpResponse(AvaliableData.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("ipaddress2",params[0]);

                ParseResult = httpParse.postRequest(ResultHash, HttpURL);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }


    // Parsing Complete JSON Object.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject product ;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            product  = jsonArray.getJSONObject(i);

//                            // Storing Student Name, Phone Number, Class into Variables.
//                            NameHolder = jsonObject.getString("name").toString() ;
//                            NumberHolder = jsonObject.getString("phone_number").toString() ;
//                            ClassHolder = jsonObject.getString("class").toString() ;
//
//      /
                            lat3 = String.valueOf(Double.parseDouble(product.getString(LAT)));
                            lng3= String.valueOf(Double.parseDouble(product.getString(LNG)));
                            cl = product.getString(columeManf).toString() ;
                            rs = product.getString(RaiseandLow).toString() ;
                            cm = product.getString(columeMaterial).toString() ;
                            ct = product.getString(ColumeType).toString() ;
                            chg = product.getString(ColumeHight).toString() ;
                            nd = product.getString(NumDoors).toString() ;
                            dd = product.getString(DoorDimen).toString() ;
                            ft = product.getString(Foundation).toString() ;
                            bt = product.getString(ColumeBracket).toString() ;
                            bl = product.getString(BracketLenth).toString() ;
                            eage = product.getString(EstimatedAge).toString() ;
                            lm = product.getString(LatenManfu).toString() ;

                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {


            String lat = lat3;
            String lng = lng3;
            String c11 =String.valueOf(cl);
            String rs1 =String.valueOf(rs);
            String cm1 =String.valueOf(cm);
            String ct1 =String.valueOf(ct);
            String chg1 =String.valueOf(chg);
            String nd1 =String.valueOf(nd);
            String dd1 =String.valueOf(dd);
            String ft1 =String.valueOf(ft);
            String bt1 =String.valueOf(bt);
            String b11 =String.valueOf(bl);
            String eage1 =String.valueOf(eage);
            String lm1 =String.valueOf(lm);
            Intent intent = new Intent(AvaliableData.this,showsingleData.class);
            intent.putExtra("doubleValue_e1", ipaddress2);
            intent.putExtra("doubleValue_e2", lat);
            intent.putExtra("doubleValue_e3", lng);
            intent.putExtra("doubleValue_e14", c11);
            intent.putExtra("doubleValue_e15", rs1);
            intent.putExtra("doubleValue_e4", cm1);
            intent.putExtra("doubleValue_e5", ct1);
            intent.putExtra("doubleValue_e6", chg1);
            intent.putExtra("doubleValue_e7", nd1);
            intent.putExtra("doubleValue_e8", dd1);
            intent.putExtra("doubleValue_e9", ft1);
            intent.putExtra("doubleValue_e10", bt1);
            intent.putExtra("doubleValue_e11", b11);
            intent.putExtra("doubleValue_e12", eage1);
            intent.putExtra("doubleValue_e13", lm1);
            startActivity(intent);


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

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                title = product.getString(TITLE);
                                latLng = new LatLng(Double.parseDouble(product.getString(LAT)), Double.parseDouble(product.getString(LNG)));
                                //adding the product to product list
//                                cl = product.getString(columeManf);
//                                rs = product.getString(RaiseandLow);
//                                cm = product.getString(columeMaterial);
//                                ct = product.getString(ColumeType);
//                                chg = product.getString(ColumeHight);
//                                nd = product.getString(NumDoors);
//                                dd = product.getString(DoorDimen);
//                                ft = product.getString(Foundation);
//                                bt = product.getString(ColumeBracket);
//                                bl = product.getString(BracketLenth);
//                                eage = product.getString(EstimatedAge);
//                                lm = product.getString(LatenManfu);
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
                        Toast.makeText(AvaliableData.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(AvaliableData.this, error.getMessage(), Toast.LENGTH_LONG).show();

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

        Intent intent = new Intent(AvaliableData.this, listview.class);
        startActivity(intent);


        return true ;
    }



    @Override
    public void onClusterInfoWindowClick(Cluster<Items> cluster) {
    }

    @Override
    public boolean onClusterItemClick(Items items) {


        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(Items items) {

        Toast.makeText(this, items.getTitle() , Toast.LENGTH_SHORT).show();
//        double lat = items.getPosition().latitude;
//        double lng = items.getPosition().longitude;
//        String ipaddress =items.getTitle();
//        String lng1 = String.valueOf(lat);
//        String logg = String.valueOf(lng);
//        String c11 =String.valueOf(cl);
//        String rs1 =String.valueOf(rs);
//        String cm1 =String.valueOf(cm);
//        String ct1 =String.valueOf(ct);
//        String chg1 =String.valueOf(chg);
//        String nd1 =String.valueOf(nd);
//        String dd1 =String.valueOf(dd);
//        String ft1 =String.valueOf(ft);
//        String bt1 =String.valueOf(bt);
//        String b11 =String.valueOf(bl);
//        String eage1 =String.valueOf(eage);
//        String lm1 =String.valueOf(lm);
//        Intent intent = new Intent(AvaliableData.this,showsingleData.class);
//        intent.putExtra("doubleValue_e1", ipaddress);
//        intent.putExtra("doubleValue_e2", lng1);
//        intent.putExtra("doubleValue_e3", logg);
//        intent.putExtra("doubleValue_e14", c11);
//        intent.putExtra("doubleValue_e15", rs1);
//        intent.putExtra("doubleValue_e4", cm1);
//        intent.putExtra("doubleValue_e5", ct1);
//        intent.putExtra("doubleValue_e6", chg1);
//        intent.putExtra("doubleValue_e7", nd1);
//        intent.putExtra("doubleValue_e8", dd1);
//        intent.putExtra("doubleValue_e9", ft1);
//        intent.putExtra("doubleValue_e10", bt1);
//        intent.putExtra("doubleValue_e11", b11);
//        intent.putExtra("doubleValue_e12", eage1);
//        intent.putExtra("doubleValue_e13", lm1);
//        startActivity(intent);

    }
}

