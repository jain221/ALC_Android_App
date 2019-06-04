package com.amazonaws.youruserpools;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageView;
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

public class NodeMapSingleData extends AppCompatActivity  implements OnMapReadyCallback,ClusterManager.OnClusterClickListener<Items>, ClusterManager.OnClusterInfoWindowClickListener<Items>, ClusterManager.OnClusterItemClickListener<Items>, GoogleMap.OnMarkerClickListener, ClusterManager.OnClusterItemInfoWindowClickListener<Items> {

    private int ii;
    public static String[] arrPath;
    private Context context;
    MapFragment mapFragment;
    GoogleMap gMap;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng center, latLng;
    String title;
    private CheckBox infoCheckBox;
    private Bitmap[] thumbnails;
    boolean[] thumbnailsselection;
    private RequestQueue mRequestQueue;
    public static final String TAG1 = CurrentNode.class.getSimpleName();
    public static final String ID = "id";
    public static final String ID1 = "Id";
    public static final String TITLE = "ipaddress";
    public static final String LAT = "latitude";
    public static final String LNG = "longitude";
    public static final String Station11 = "Station";
    private Marker myMarker;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17f;
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
    String data;

    AutoCompleteTextView et;
    String tag_json_obj = "json_obj_req";
    private AutoCompleteTextView mSearchText;
    ArrayList<String> displayData = new ArrayList<String>();
    private static final LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);
    private Marker locationMarker;
    double latt ,logg;
    private Marker marker;

    StreetViewPanorama mStreetViewPanorama;

    CheckBox AllData;


        List<SuggestGetSet> ListData = new ArrayList<SuggestGetSet>();
    private TableLayout mLinearLayout;

//
//    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
//
//        private final View myContentsView;
//
//        MyInfoWindowAdapter(){
//            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
//        }
//
//        @Override
//        public View getInfoContents(Marker marker) {
//
//            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
//            tvTitle.setText(marker.getTitle());
//            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
//            tvSnippet.setText(marker.getSnippet());
//
//            return myContentsView;
//        }
//
//        @Override
//        public View getInfoWindow(Marker marker) {
//            // TODO Auto-generated method stub
//            return null;
//        }
//
//    }
//
//    final int RQS_GooglePlayServices = 1;
//    TextView tvLocInfo;
//
//






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_map_single_data);

        mGps = (ImageView) findViewById(R.id.ic_gps);
        menu= (ImageView) findViewById(R.id.sattel);
        //  mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);
        et = (AutoCompleteTextView) findViewById(R.id.editText);

      //  tvLocInfo = (TextView)findViewById(R.id.locinfo);

        getLocationPermission();
        if (gMap != null) {

            gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Geocoder geocoder = new Geocoder(NodeMapSingleData.this);
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

//        gMap.getUiSettings().setZoomControlsEnabled(true);
//        gMap.getUiSettings().setCompassEnabled(true);
//        gMap.getUiSettings().setMyLocationButtonEnabled(true);
//
//        gMap.getUiSettings().setRotateGesturesEnabled(true);
//        gMap.getUiSettings().setScrollGesturesEnabled(true);
//        gMap.getUiSettings().setTiltGesturesEnabled(true);
//        gMap.getUiSettings().setZoomGesturesEnabled(true);
        //or myMap.getUiSettings().setAllGesturesEnabled(true);

//        gMap.setTrafficEnabled(true);


//
//        gMap.setInfoWindowAdapter(new MyInfoWindowAdapter());


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG1, "onMapReady: map is ready");
        // Mengarahkan ke alun-alun Demak
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


//        // Set a listener for marker click.
        gMap.setOnMarkerClickListener(this);


        onMarkerClick(new GoogleMap.OnMarkerClickListener() {

                          @Override
                          public boolean onMarkerClick(Marker marker) {

                              // TODO Auto-generated method stub
                         
//                              final int len = thumbnailsselection.length;
//                              int cnt = 0;
//                              String selectImages = "";
//                              ArrayList<Integer> pics = new ArrayList(thumbnailsselection.length);
//                              for (int i = 0; i < len; i++)
//                                  if (thumbnailsselection[i]) {
//                                  pics.add(i);
//                                      cnt++;
//                                      selectImages = selectImages + arrPath[i] + "|";
//
//                                  }
//                              if (cnt == 0) {
//                                  Toast.makeText(getApplicationContext(),
//                                          "Please select at least one image",
//                                          Toast.LENGTH_LONG).show();
//                              } else {
//                                  Toast.makeText(getApplicationContext(),
//                                          "You've selected Total " + cnt + " image(s).",
//                                          Toast.LENGTH_LONG).show();
//                                  Log.d("SelectedImages", selectImages);
//                                  Intent myIntent = new Intent(NodeMapSingleData.this, Test.class);
//                                  myIntent.putIntegerArrayListExtra("arr", pics);//pics is your array with id-s of bitmaps
//                                  startActivity(myIntent);
//                              }
                              return false;
                          }

                      });




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
//        // Setting a custom info window adapter for the google map
        InfoWndowAdapter markerInfoWindowAdapter = new InfoWndowAdapter(getApplicationContext());
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);

//        googleMap.setOnInfoWindowClickListener(this);

        getMarkers();
//        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);
        gMap.setMapType(gMap.MAP_TYPE_NORMAL);
//           gMap.setOnCameraChangeListener((GoogleMap.OnCameraChangeListener) mClusterManager);

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG1, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

//        mGps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                marker.hideInfoWindow();
//            }
//        });

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
                Intent intent = new Intent(NodeMapSingleData.this, AvaliableData.class);
                startActivity(intent);
            }
        });

        onBackPressed();
        final CustomClusterRenderer renderer = new CustomClusterRenderer(this, gMap, mClusterManager);

        mClusterManager.setRenderer(renderer);

        onBackPressed();


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

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(NodeMapSingleData.this);

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
                            Toast.makeText(NodeMapSingleData.this, "unable to get current location", Toast.LENGTH_SHORT).show();
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


        //        hideSoftKeyboard();
    }

    private void initMap() {
        Log.d(TAG1, "initMap: initializing map");
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(NodeMapSingleData.this);

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
//
//
//    }


    public void findLocation(View v) throws IOException {

//        EditText et = (EditText) findViewById(R.id.editText);


//        String location = et.getText().toString();

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
            Toast.makeText(NodeMapSingleData.this, "Check Spelling Or Try Again !", Toast.LENGTH_SHORT).show();
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

        double lat =latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet = "Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude;

// Create a cluster item for the marker and set the title and snippet using the constructor.
        Items infoWindowItem = new Items(lat,lng, title, snippet);

//
// Add the cluster item (marker) to the cluster manager.
        mClusterManager.addItem(infoWindowItem);

        mClusterManager.setRenderer(new CustomClusterRenderer(NodeMapSingleData.this, gMap, mClusterManager));

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

        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        return false;
    }

//    @Override
//    public void onInfoWindowClick(Marker marker) {
//        Toast.makeText(this, "Gir Forest Clicked!!!!", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, getTitle() , Toast.LENGTH_SHORT).show();
//        double lat = marker.getPosition().latitude;
//        double lng = marker.getPosition().longitude;
//        String ipaddress =marker.getTitle();
//        String lng1 = String.valueOf(lat);
//        String logg = String.valueOf(lng);
//        Intent intent = new Intent(NodeMapSingleData.this, SingleDataBASEADDING.class);
//        intent.putExtra("doubleValue_e1", ipaddress);
//        intent.putExtra("doubleValue_e2", lng1);
//        intent.putExtra("doubleValue_e3", logg);
//        startActivity(intent);
//    }


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

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }

//
//
//        @Override
//        protected boolean shouldRenderAsCluster(Cluster<Items> cluster){
//            return cluster.getSize() > 1;
//        }

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

            case R.id.mapTypestree:
                StoredData();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void StoredData() {
        Intent intent = new Intent(NodeMapSingleData.this, AvaliableData.class);
        startActivity(intent);
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
                        Toast.makeText(NodeMapSingleData.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(NodeMapSingleData.this, error.getMessage(), Toast.LENGTH_LONG).show();

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

//        //Toast.makeText(this, cluster.getSize()+ " (including " + title + ")" , Toast.LENGTH_SHORT).show();
//
//        Log.e(TAG1, "onClusterClick");
//
//        // Show a toast with some info when the cluster is clicked.
//        String firstName = cluster.getItems().iterator().next().getTitle();
//       // Toast.makeText(this, cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();
//
//
//
//        // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
//        // inside of bounds, then animate to center of the bounds.
//
//        // Create the builder to collect all essential cluster items for the bounds.
//        LatLngBounds.Builder builder = LatLngBounds.builder();
//        for (ClusterItem item : cluster.getItems()) {
//            builder.include(item.getPosition());
//        }
//        // Get the LatLngBounds
//        final LatLngBounds bounds = builder.build();
//
//        // Animate camera to the bounds
//        try {
//            gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Intent intent = new Intent(CurrentNode.this, ListViewMappActivity.class);
//        startActivity(intent);
//        startActivity(new Intent(CurrentNode.this,ListViewMappActivity.class));

        Intent intent = new Intent(NodeMapSingleData.this, listview.class);
//                intent.putStringArrayListExtra("doubleValue_e1",addData);
        startActivity(intent);


        return true ;
    }
//
//    private void getDataDisplay(LatLng latLng, String title) {
//
//        String ipaddress = title;
//
//        Intent intent = new Intent(CurrentNode.this, ListViewMappActivity.class);
//        intent.putExtra("array_list", ipaddress);
//        startActivity(intent);
//    }

    @Override
    public void onClusterInfoWindowClick(Cluster<Items> cluster) {




    }

    @Override
    public boolean onClusterItemClick(Items items) {


//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        Toast.makeText(NodeMapSingleData.this, "Cluster item click", Toast.LENGTH_SHORT).show();

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

        Toast.makeText(this, items.getTitle() , Toast.LENGTH_SHORT).show();
        double lat = items.getPosition().latitude;
        double lng = items.getPosition().longitude;
        String ipaddress =items.getTitle();
        String lng1 = String.valueOf(lat);
        String logg = String.valueOf(lng);
        Intent intent = new Intent(NodeMapSingleData.this, SingleDataBASEADDING.class);
        intent.putExtra("doubleValue_e1", ipaddress);
        intent.putExtra("doubleValue_e2", lng1);
        intent.putExtra("doubleValue_e3", logg);
        startActivity(intent);

    }
}

