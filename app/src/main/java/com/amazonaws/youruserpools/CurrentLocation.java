package com.amazonaws.youruserpools;
import android.Manifest;
import android.app.Dialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CurrentLocation extends AppCompatActivity  implements OnMapReadyCallback,ClusterManager.OnClusterClickListener<Items>, ClusterManager.OnClusterInfoWindowClickListener<Items>, ClusterManager.OnClusterItemClickListener<Items>, ClusterManager.OnClusterItemInfoWindowClickListener<Items> {

    HttpParse httpParse = new HttpParse();
    private int ii;
    String HttpURL = "https://o5yklvu3td.execute-api.eu-west-1.amazonaws.com/default/fetchData";
    String ipaddress2;
    MapFragment mapFragment;
    GoogleMap gMap;
    GoogleMap gMap1;
    GoogleMap gMap2;
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
    public static final String columeNumber = "colume_number";
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
    public static final String TEXT19 = "text";
    ArrayList<String> mark1 = new ArrayList<>();
    public static final String MY_PREF_KEY = "Selection";

    String ip, latt, logg, cnum, cl, rs, cm, ct, chg, nd, dd, ft, bt, bl, eage, lm;
    private Marker myMarker;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17f;
    //vars
    String latit, longgg, ipaddr;
    TextView ipaddress, lat2, log2, cl12, rs12, cm12, ct12, chg12, nd12, dd12, ft12, bt12, bl12, eage12, lm12;
    private ArrayList<SuggestGetSet> List;
    //    private SuggestionAdapter mSuggestionAdapter;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    //    private static final String URL_PRODUCTS = "https://wwf5avjfai.execute-api.eu-west-1.amazonaws.com/ISDMAPDATA/ISDgetAllData";
    private static final String URL_AMBER = "https://48b6kzowq1.execute-api.eu-west-1.amazonaws.com/default/SelectedamberColor";
    private static final String URL_GREEN = "https://48b6kzowq1.execute-api.eu-west-1.amazonaws.com/default/SelectGreenColor";
    private static final String URL_RED = "https://48b6kzowq1.execute-api.eu-west-1.amazonaws.com/default/SelectRedColor";
    private static final String URL_Data = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/StationNameGetFunction";
    private static final String URL_UnAssinged = "https://wwf5avjfai.execute-api.eu-west-1.amazonaws.com/ISDMAPDATA/idname";
    private ClusterManager<Items> mClusterManager;
    private ClusterManager<Items> mClusterManagerR;
    private List<Items> items = new ArrayList<>();
    private ImageView mGps;
    private ImageView menu;
    String data;
    AutoCompleteTextView et;
    String tag_json_obj = "json_obj_req";
    private AutoCompleteTextView mSearchText;
    ArrayList<JSONObject> displayData = new ArrayList<JSONObject>();
    private static final LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);
    private Marker locationMarker;
    private Context context;
    private Button infoButton;
    CheckBox AllData;

    int MarkerClick;
    Handler handler = new Handler();
    Runnable refresh;
    String lat3;
    String lng3;
    ProgressDialog pDialog;
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    String ParseResult;
    HashMap<String, String> ResultHash = new HashMap<>();
    String FinalJSonObject;
    TextView NAME, PHONE_NUMBER, CLASS;
    String NameHolder, NumberHolder, ClassHolder;
    Button UpdateButton, DeleteButton;
    String TempItem;
    ProgressDialog progressDialog2;

    Button assigned,unassigned,alld;
    TextView lat, log, cl1, rs1, cm1, ct1, chg1, nd1, dd1, ft1, bt1, bl1, eage1, lm1;
    //    String ip,latt,logg,cl, rs, cm, ct, chg, nd, dd, ft, bt, bl, eage, lm;
    String e1, e2, e3;
    StreetViewPanorama mStreetViewPanorama;
    Product product1;
    //    List<SuggestGetSet> ListData = new ArrayList<SuggestGetSet>();
    RadioGroup radioGroup;

    private RadioButton assignn;
    private RadioButton unassign;
    private RadioButton alldata;


    ArrayList<ArrayList<String>> myList = new ArrayList<>();
    ArrayList<String> latitude2 = new ArrayList<String>();
    ArrayList<String> longitude2 = new ArrayList<String>();
    ArrayList<String> ipaddress3 = new ArrayList<String>();
    View b;
//    Button resetButton;
    ImageView  resetButton ;

    // this will be key for the key value pair
    public static final String BUTTON_STATE = "Button_State";
    // this is name of shared preferences file, must be same whenever accessing
    // the key value pair.
    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_location);

        mGps = (ImageView) findViewById(R.id.ic_gps);
        et = (AutoCompleteTextView) findViewById(R.id.editText);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        assignn = (RadioButton) findViewById(R.id.radioButton1);
        unassign = (RadioButton) findViewById(R.id.radioButton2);
//        alldata= (RadioButton) findViewById(R.id.radioButton3);

        resetButton =(ImageView) findViewById(R.id.ic_addData);
        resetButton.setVisibility(View.INVISIBLE);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                Toast.makeText(CurrentLocation.this,"Multiple data is been adding", Toast.LENGTH_LONG).show();
                multipledata();
                showStartDialog();
            }
        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // grab the last saved state here on each activity start
        Boolean lastButtonState = sharedpreferences.getBoolean(BUTTON_STATE, false);
        alldata = (RadioButton) findViewById(R.id.radioButton3);
        radioGroup.check( alldata.getId());
        // restore previous state
//        alldata.setChecked(lastButtonState);
        // set a listener
//        alldata.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // call this to enable editing of the shared preferences file
//                // in the event of a change
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                Boolean isChecked = alldata.isChecked();
//                // use this to add the new state
//                editor.putBoolean(BUTTON_STATE, isChecked);
//                // save
//                editor.apply();
//
//                gMap.clear();
//                getAlldata();
//            }
//        });


        getLocationPermission();
        if (gMap != null) {

            gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Geocoder geocoder = new Geocoder(CurrentLocation.this);
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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton radioButton=(RadioButton)findViewById(checkedId);
//                Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();

                switch( checkedId) {
                    case R.id.radioButton1:
                        resetButton.setVisibility(View.INVISIBLE);
                        gMap.clear();
                        getMarkersAmber();
                        getMarkersGreen();
                        getMarkersRed();
                        Toast.makeText(CurrentLocation.this,"Assigned Mode is Selected", Toast.LENGTH_LONG).show();
                        saveRadioChoice();
                        retrieveChoices();
                        break;

                    case R.id.radioButton2:
                        if(unassign.isClickable())
                        {
                            gMap.clear();
                            getMarkers();
//                        sharedPreferences.edit().putBoolean("Unassigned Mode",  unassign.isChecked()).apply();
                            saveRadioChoice();
                            retrieveChoices();

                            Toast.makeText(CurrentLocation.this,"Unassigned Node Selected", Toast.LENGTH_LONG).show();
                            resetButton.setVisibility(View.VISIBLE); //To set visible
                        }
                        else {
                            resetButton.setVisibility(View.INVISIBLE); //To set visible
                        }

//

                        break;

                    case R.id.radioButton3:
                        resetButton.setVisibility(View.INVISIBLE);
                        gMap.clear();
                        getAlldata();


                        Toast.makeText(CurrentLocation.this,"All Data Selected", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        gMap1 = googleMap;
        gMap2 = googleMap;
//        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
//        Log.d(TAG1, "onMapReady: map is ready");
//        // Mengarahkan ke alun-alun Demak

        center = new LatLng(51.52042, -3.23113);
        getMarkers1();
        getMarkersAmber1();
        getMarkersGreen1();
        getMarkersRed1();
//        getAlldata();
        mClusterManager = new ClusterManager<>(this, gMap);
        gMap.setOnCameraIdleListener(mClusterManager);
        gMap.setOnMarkerClickListener(mClusterManager);
        gMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
        mClusterManager.cluster();

        mClusterManagerR = new ClusterManager<Items>(this, gMap);
        gMap.setOnCameraIdleListener(mClusterManagerR);
        gMap.setOnMarkerClickListener(mClusterManagerR);
        gMap.setOnInfoWindowClickListener(mClusterManagerR);
        mClusterManagerR.setOnClusterClickListener(this);
        mClusterManagerR.setOnClusterInfoWindowClickListener(this);
        mClusterManagerR.setOnClusterItemClickListener(this);
        mClusterManagerR.setOnClusterItemInfoWindowClickListener(this);
        mClusterManagerR.cluster();
        InfoWndow2 markerInfoWindowAdapter = new InfoWndow2(getApplicationContext());
        gMap.setInfoWindowAdapter(markerInfoWindowAdapter);

//        InfoWndowAdapter markerInfoWindowAdapter1 = new InfoWndowAdapter(getApplicationContext());
//        gMap1.setInfoWindowAdapter(markerInfoWindowAdapter1);
        // Set a listener for marker click.
//        gMap.setOnMarkerClickListener(this);

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

//        gMap.setOnInfoWindowClickListener(this);
//        getMarkers();

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




//       assigned.setOnClickListener(new View.OnClickListener() {
//            String AssignedNode = "Assigned Node";
//            @Override
//            public void onClick(View v) {
//                Log.d("onClick", "Button is Clicked");
//                gMap.clear();
////                String url = getUrl(latitude, longitude, Restaurant);
////                Object[] DataTransfer = new Object[2];
////                DataTransfer[0] = gMap;
////                DataTransfer[1] = url;
////                Log.d("onClick", url);
////                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
////                getNearbyPlacesData.execute(DataTransfer);
//                getMarkersAmber();
//                getMarkersGreen();
//                getMarkersRed();
//                Toast.makeText(CurrentLocation.this,"Nearby Restaurants", Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//        unassigned.setOnClickListener(new View.OnClickListener() {
//            String AssignedNode = "Assigned Node";
//            @Override
//            public void onClick(View v) {
//                Log.d("onClick", "Button is Clicked");
//                gMap.clear();
////                String url = getUrl(latitude, longitude, Restaurant);
////                Object[] DataTransfer = new Object[2];
////                DataTransfer[0] = gMap;
////                DataTransfer[1] = url;
////                Log.d("onClick", url);
////                unassignedclass getNearbyPlacesData = new unassignedclass();
////                getNearbyPlacesData.ecut;
//                getMarkers();
////                getMarkersAmber();
////                getMarkersGreen();
////                getMarkersRed();
//                Toast.makeText(CurrentLocation.this,"Nearby Restaurants", Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//
//        alld.setOnClickListener(new View.OnClickListener() {
//            String AssignedNode = "Assigned Node";
//            @Override
//            public void onClick(View v) {
//                Log.d("onClick", "Button is Clicked");
//                gMap.clear();
////                String url = getUrl(latitude, longitude, Restaurant);
////                Object[] DataTransfer = new Object[2];
////                DataTransfer[0] = gMap;
////                DataTransfer[1] = url;
////                Log.d("onClick", url);
////                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
////                getNearbyPlacesData.execute(DataTransfer);
//
//
//                getAlldata();
//                Toast.makeText(CurrentLocation.this,"Nearby Restaurants", Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//


    }

    private void saveRadioChoice(){
        SharedPreferences mSharedPref = getSharedPreferences(MY_PREF_KEY,MODE_PRIVATE);

        SharedPreferences.Editor editor = mSharedPref.edit();

// Initialize Radiogroup while saving choices
        RadioGroup localRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        editor.putInt( TEXT19, localRadioGroup.indexOfChild(findViewById(localRadioGroup.getCheckedRadioButtonId())));
        editor.apply();
    }

    private void retrieveChoices(){

        SharedPreferences sharedPref = getSharedPreferences(MY_PREF_KEY,MODE_PRIVATE);
        int i = sharedPref.getInt(TEXT19,-1);
        if( i >= 0){
            ((RadioButton) ((RadioGroup)findViewById(R.id.radioGroup)).getChildAt(i)).setChecked(true);
        }


    }

    private void getAlldata() {
        showStartAll();
        getMarkers1();
        getMarkersAmber1();
        getMarkersGreen1();
        getMarkersRed1();
    }




    private void getMarkers1() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_UnAssinged,
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
                                addMarkerAll1(latLng, title);


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
                        Toast.makeText(CurrentLocation.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void addMarkerAll1(final LatLng latLng1, final String title) {


        double lat = latLng1.latitude;
        double lng = latLng1.longitude;
        // Set he title and snippet strings.


        final String snippet1 = (" Status : Unassigned Structure" + " \n Lat:  " + lat + ",Longitude:  " + lng + " \n Column : Null " + " \n Colume Manf: Null " + "\n Raise & Lower: Null " +
                "\n Colume Material: Null " + " \n Colume Type: Null" + " \n Colume Height: Null " + " \n Number of Door: Null " + " \n Door Dimen: Null " + "\n Foundation type: Null" +
                "\n Column Bracket: Null" + " \n Bracket Length: Null" + "\n Estimated Age of Lat: Null" + " \n Lat. Manf: Null ");

        MarkerOptions markerOptions1 = new MarkerOptions()
                .position(latLng1)
                .title(title)
                .snippet(snippet1)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        gMap.addMarker(markerOptions1);
//

        gMap1.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker mark1) {
//                        Toast.makeText(CurrentLocation.this, "You Click on View Mode,please Click on different mode button for editing", Toast.LENGTH_SHORT).show();
                showStartDialog2();

            }
        });

    }

    private void getMarkersGreen1() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GREEN,
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
                                lm = product.getString(LatenManfu);
                                addMarkerGreen(latLng, title);
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
                                lm = displayData.get(i).get(LatenManfu).toString();

//                                MarkerOptions marker = new MarkerOptions().position(new LatLng(latt, logg)).title(ip).snippet("Lat: "+latt+",Longitude: "+logg+" \n Colume Manf: "+ cl+"\n Raise & Lower "+rs+
//                                        "\n Colume Material: "+cm+" \n Colume Type: "+" \n Colume Height: "+chg +" \n Number of Door's: "+nd+" \n Door Dim: "+dd+"\n Foundation type: "+ft+
//                                        "\n Column Bracket:"+bt+" \n Bracket Length:"+bl+"\n Estimated Age of Lat:"+ eage +" \n Lat. Manf: "+ lm );
//                                gMap.addMarker(marker);

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
                        Toast.makeText(CurrentLocation.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void addMarkerGreen(final LatLng latLng, final String title) {


        double lat = latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet3 = (" Status : Green " + " \n Column : " + cnum + " \n Lat: " + lat + ",Longitude: " + lng + " \n Colume Manf: " + cl + "\n Raise & Lower: " + rs +
                "\n Colume Material: " + cm + " \n Colume Type: " + ct + " \n Colume Height: " + chg + " \n Number of Door: " + nd + " \n Door Dimen: " + dd + "\n Foundation type: " + ft +
                "\n Column Bracket:" + bt + " \n Bracket Length:" + bl + "\n Estimated Age of Lat:" + eage + " \n Lat. Manf: " + lm);

        MarkerOptions markerOptions3 = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet3)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        gMap.addMarker(markerOptions3);

    }

    private void getMarkersAmber1() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_AMBER,
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
                                lm = product.getString(LatenManfu);
                                addMarkerAmber(latLng, title);
                                displayData.add(product);


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
                                lm = displayData.get(i).get(LatenManfu).toString();

//                                MarkerOptions marker = new MarkerOptions().position(new LatLng(latt, logg)).title(ip).snippet("Lat: "+latt+",Longitude: "+logg+" \n Colume Manf: "+ cl+"\n Raise & Lower "+rs+
//                                        "\n Colume Material: "+cm+" \n Colume Type: "+" \n Colume Height: "+chg +" \n Number of Door's: "+nd+" \n Door Dim: "+dd+"\n Foundation type: "+ft+
//                                        "\n Column Bracket:"+bt+" \n Bracket Length:"+bl+"\n Estimated Age of Lat:"+ eage +" \n Lat. Manf: "+ lm );
//                                gMap.addMarker(marker);

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
                        Toast.makeText(CurrentLocation.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void addMarkerAmber(final LatLng latLng, final String title) {
        double lat = latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet2 = (" Status : Amber " + " \n Column : " + cnum + " \n Lat: " + lat + ",Longitude: " + lng + " \n Colume Manf: " + cl + "\n Raise & Lower: " + rs +
                "\n Colume Material: " + cm + " \n Colume Type: " + ct + " \n Colume Height: " + chg + " \n Number of Door: " + nd + " \n Door Dimen: " + dd + "\n Foundation type: " + ft +
                "\n Column Bracket:" + bt + " \n Bracket Length:" + bl + "\n Estimated Age of Lat:" + eage + " \n Lat. Manf: " + lm);

// Create a cluster item for the marker and set the title and snippet using the constructor.
        //  Items infoWindowItem = new Items(lat,lng, title, snippet);
        MarkerOptions markerOptions2 = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet2)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        gMap.addMarker(markerOptions2);
    }


    private void getMarkersRed1() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_RED,
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
                                lm = product.getString(LatenManfu);
                                addMarkerRed(latLng, title);
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
                                lm = displayData.get(i).get(LatenManfu).toString();

//                                MarkerOptions marker = new MarkerOptions().position(new LatLng(latt, logg)).title(ip).snippet("Lat: "+latt+",Longitude: "+logg+" \n Colume Manf: "+ cl+"\n Raise & Lower "+rs+
//                                        "\n Colume Material: "+cm+" \n Colume Type: "+" \n Colume Height: "+chg +" \n Number of Door's: "+nd+" \n Door Dim: "+dd+"\n Foundation type: "+ft+
//                                        "\n Column Bracket:"+bt+" \n Bracket Length:"+bl+"\n Estimated Age of Lat:"+ eage +" \n Lat. Manf: "+ lm );
//                                gMap.addMarker(marker);

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
                        Toast.makeText(CurrentLocation.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void addMarkerRed(final LatLng latLng, final String title) {
        double lat = latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet4 = (" Status : Red " + " \n Column : " + cnum + " \n Lat: " + lat + ",Longitude: " + lng + " \n Colume Manf: " + cl + "\n Raise & Lower: " + rs +
                "\n Colume Material: " + cm + " \n Colume Type: " + ct + " \n Colume Height: " + chg + " \n Number of Door: " + nd + " \n Door Dimen: " + dd + "\n Foundation type: " + ft +
                "\n Column Bracket:" + bt + " \n Bracket Length:" + bl + "\n Estimated Age of Lat:" + eage + " \n Lat. Manf: " + lm);

// Create a cluster item for the marker and set the title and snippet using the constructor.
        //  Items infoWindowItem = new Items(lat,lng, title, snippet);
        MarkerOptions markerOptions4 = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet4)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        gMap.addMarker(markerOptions4);
    }


    private void getDeviceLocation() {
        Log.d(TAG1, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CurrentLocation.this);

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
                            Toast.makeText(CurrentLocation.this, "unable to get current location", Toast.LENGTH_SHORT).show();
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


        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));

// Zoom in, animating the camera.
        gMap.animateCamera(CameraUpdateFactory.zoomIn());

// Zoom out to zoom level 10, animating with a duration of 2 seconds.
        gMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        //Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to Mountain View
                .zoom(18)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        hideSoftKeyboard();
    }



    private void initMap() {
        Log.d(TAG1, "initMap: initializing map");
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(CurrentLocation.this);


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
            if (myMarker != null)
                myMarker.remove();
            myMarker = gMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(locality)
                    .snippet("Latitude:" + latLng.latitude + ",Longitude" + latLng.longitude)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
            ;

        } else {
            Toast.makeText(CurrentLocation.this, "Check Spelling Or Try Again !", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(CurrentLocation.this, StationList.class);
                startActivity(intent);
                break;


            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void multipledata() {


        myList.add(ipaddress3);
        myList.add(latitude2);
        myList.add(longitude2);


        Intent intent = new Intent(CurrentLocation.this, AddUnssignedData.class);
        for (int i = 0; i < myList.size(); i++) {
            intent.putExtra("doubleValue_e1", ipaddress3.get(i));
            intent.putExtra("doubleValue_e2", latitude2.get(i));
            intent.putExtra("doubleValue_e3", longitude2.get(i));
            startActivity(intent);

        }
    }

    private void Referesh() {
        finish();
        startActivity(getIntent());
        handler.post(refresh);
    }

    /**
     * Called when the user clicks a marker.
     */
//    public boolean onMarkerClick(Marker marker) {
//
//
////        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//        marker.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
//        latit = String.valueOf(marker.getPosition().latitude);
//        longgg = String.valueOf(marker.getPosition().longitude);
//        ipaddr = marker.getTitle();
//
//
//        return false;
//    }


    private void showDialog(CurrentLocation currentLocation) {

        final Dialog dialog = new Dialog(currentLocation);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_listview);

        Button btndialog = (Button) dialog.findViewById(R.id.btndialog);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                showStartDialog();
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
//                String ipaddress = ipaddr;
//
//                Intent intent = new Intent(CurrentLocation.this, AddUnssignedData.class);
//                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
////                intent.putExtra("ListViewValue", MarkerClick);
//                intent.putExtra("doubleValue_e1", ipaddress);
//                intent.putExtra("doubleValue_e2", lat);
//                intent.putExtra("doubleValue_e3", lng);
//
//                startActivity(intent);
//
//                dialog.dismiss();
//                showStartDialog();
//            }
//        });


        Button Rlatern = (Button) dialog.findViewById(R.id.Rlatern);
        Rlatern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AvaliableData.this, OldScmsInstallation.class);
//                startActivity(intent);


//                Toast.makeText(this, myMarker.getTitle() , Toast.LENGTH_SHORT).show();
                String lat = latit;
                String lng = longgg;
                String ipaddress = ipaddr;

                Intent intent = new Intent(CurrentLocation.this, editeColumeNumber.class);
                //intent.putExtra("ListViewValue", IdList.get(Integer.parseInt(ID)).toString());
                intent.putExtra("doubleValue_e1", ipaddress);
                intent.putExtra("doubleValue_e2", lat);
                intent.putExtra("doubleValue_e3", lng);

                startActivity(intent);

                dialog.dismiss();
                showStartDialog();
            }
        });

        Button RColume = (Button) dialog.findViewById(R.id.RColume);
        RColume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lat = latit;
                String lng = longgg;
                String ipaddress = ipaddr;

                Intent intent = new Intent(CurrentLocation.this, ReplaceColumn.class);
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

                Intent intent = new Intent(CurrentLocation.this, ReplaceLatern.class);
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

                Intent intent = new Intent(CurrentLocation.this, ReplaceLatern.class);
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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_UnAssinged,
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
                                addMarkerAll(latLng, title);


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
                        Toast.makeText(CurrentLocation.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void addMarkerAll(final LatLng latLng, final String title) {

        java.util.List<LatLng> lstMarcadores =  new ArrayList<>();
        double lat = latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet1 = (" Status : Unassigned Structure" + " \n Lat:  " + lat + ",Longitude:  " + lng + " \n Column : Null " + " \n Colume Manf: Null " + "\n Raise & Lower: Null " +
                "\n Colume Material: Null " + " \n Colume Type: Null" + " \n Colume Height: Null " + " \n Number of Door: Null " + " \n Door Dimen: Null " + "\n Foundation type: Null" +
                "\n Column Bracket: Null" + " \n Bracket Length: Null" + "\n Estimated Age of Lat: Null" + " \n Lat. Manf: Null ");



        final Marker marker = gMap1.addMarker(new MarkerOptions().position(latLng)
                .title(title)
                .snippet(snippet1)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mark1.add(marker.getId());
//        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
//        {
//            @Override
//            public boolean onMarkerClick(Marker marker1)
//            {
//
//                // Retrieve the data from the marker.
//                Integer clickCount = (Integer) marker.getTag();
//
//                // Check if a click count was set, then display the click count.
//                if (clickCount != null) {
//                    clickCount = clickCount + 1;
//                    marker.setTag(clickCount);
//                    Toast.makeText(CurrentLocation.this," has been clicked " + clickCount + " times.", Toast.LENGTH_SHORT).show();
//                }
//
//                // Return false to indicate that we have not consumed the event and that we wish
//                // for the default behavior to occur (which is for the camera to move such that the
//                // marker is centered and for the marker's info window to open, if it has one).
//                return false;
//            }
//        });




        gMap1.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker mark1) {
//                        Toast.makeText(CurrentLocation.this, mark1.getTitle(), Toast.LENGTH_SHORT).show();

                        double lat = mark1.getPosition().latitude;
                        double lng = mark1.getPosition().longitude;
                        String ipaddress =mark1.getTitle();
                        String lng1 = String.valueOf(lat);
                        String logg = String.valueOf(lng);
                        Intent intent = new Intent(CurrentLocation.this, AddUnssignedData.class);
                        intent.putExtra("doubleValue_e1", ipaddress);
                        intent.putExtra("doubleValue_e2", lng1);
                        intent.putExtra("doubleValue_e3", logg);
                        startActivity(intent);
//                        showStartDialog();
                        showStartDialog();

                    }
                });



        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker1) {
                marker1.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
                latit = String.valueOf(marker1.getPosition().latitude);
                longgg = String.valueOf(marker1.getPosition().longitude);
                ipaddr = marker1.getTitle();
                ipaddress3.add(ipaddr);
                latitude2.add(String.valueOf(latit));
                longitude2.add(String.valueOf(longgg));

                return false;
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
    //                    marker1.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
    private void getMarkersAmber() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_AMBER,
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
                                lm = product.getString(LatenManfu);
                                addMarker2(latLng, title);
                                displayData.add(product);


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
                                lm = displayData.get(i).get(LatenManfu).toString();

//                                MarkerOptions marker = new MarkerOptions().position(new LatLng(latt, logg)).title(ip).snippet("Lat: "+latt+",Longitude: "+logg+" \n Colume Manf: "+ cl+"\n Raise & Lower "+rs+
//                                        "\n Colume Material: "+cm+" \n Colume Type: "+" \n Colume Height: "+chg +" \n Number of Door's: "+nd+" \n Door Dim: "+dd+"\n Foundation type: "+ft+
//                                        "\n Column Bracket:"+bt+" \n Bracket Length:"+bl+"\n Estimated Age of Lat:"+ eage +" \n Lat. Manf: "+ lm );
//                                gMap.addMarker(marker);

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
                        Toast.makeText(CurrentLocation.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void addMarker2(final LatLng latLng, final String title) {
        double lat = latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet2 = (" Status : Amber " + " \n Column : " + cnum + " \n Lat: " + lat + ",Longitude: " + lng + " \n Colume Manf: " + cl + "\n Raise & Lower: " + rs +
                "\n Colume Material: " + cm + " \n Colume Type: " + ct + " \n Colume Height: " + chg + " \n Number of Door: " + nd + " \n Door Dimen: " + dd + "\n Foundation type: " + ft +
                "\n Column Bracket:" + bt + " \n Bracket Length:" + bl + "\n Estimated Age of Lat:" + eage + " \n Lat. Manf: " + lm);

// Create a cluster item for the marker and set the title and snippet using the constructor.
        //  Items infoWindowItem = new Items(lat,lng, title, snippet);
        MarkerOptions markerOptions2 = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet2)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        gMap.addMarker(markerOptions2);
//
//        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//
//            @Override
//            public boolean onMarkerClick(Marker marker2) {
//
//
//
//                if (prevMarker1 != null) {
//                    //Set prevMarker back to default color
////                    prevMarker1.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
////                    MarkerOptions markerOptions2 = new MarkerOptions()
////                            .position(latLng)
////                            .title(title)
////                            .snippet(snippet2)
////                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
////                    gMap.addMarker(markerOptions2);
//
//                    prevMarker1.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//
//                }
//
//                //leave Marker default color if re-click current Marker
//                if (!marker2.equals(prevMarker1)) {
//
//                    marker2.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
////                    marker1.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
//                    prevMarker1 = marker1;
//
//                }
//                prevMarker1 = marker2;
//
//                return false;
//            }
//
//        });

//        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
//        {
//            @Override
//            public boolean onMarkerClick(Marker marker)
//            {
//                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
//                {
//                    @Override
//                    public boolean onMarkerClick(Marker marker)
//                    {
//                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//                        return false;
//                    }
//                });
//                //Code for marker deselect goes here
//
//                return false;
//            }
//        });

        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker2) {
//                Toast.makeText(CurrentLocation.this, marker2.getTitle(), Toast.LENGTH_SHORT).show();
                showDialog(CurrentLocation.this);

            }
        });
    }


    private void getMarkersGreen() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GREEN,
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
                                lm = displayData.get(i).get(LatenManfu).toString();

//                                MarkerOptions marker = new MarkerOptions().position(new LatLng(latt, logg)).title(ip).snippet("Lat: "+latt+",Longitude: "+logg+" \n Colume Manf: "+ cl+"\n Raise & Lower "+rs+
//                                        "\n Colume Material: "+cm+" \n Colume Type: "+" \n Colume Height: "+chg +" \n Number of Door's: "+nd+" \n Door Dim: "+dd+"\n Foundation type: "+ft+
//                                        "\n Column Bracket:"+bt+" \n Bracket Length:"+bl+"\n Estimated Age of Lat:"+ eage +" \n Lat. Manf: "+ lm );
//                                gMap.addMarker(marker);

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
                        Toast.makeText(CurrentLocation.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void addMarker(final LatLng latLng, final String title) {


        double lat = latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet3 = (" Status : Green " + " \n Column : " + cnum + " \n Lat: " + lat + ",Longitude: " + lng + " \n Colume Manf: " + cl + "\n Raise & Lower: " + rs +
                "\n Colume Material: " + cm + " \n Colume Type: " + ct + " \n Colume Height: " + chg + " \n Number of Door: " + nd + " \n Door Dimen: " + dd + "\n Foundation type: " + ft +
                "\n Column Bracket:" + bt + " \n Bracket Length:" + bl + "\n Estimated Age of Lat:" + eage + " \n Lat. Manf: " + lm);

        MarkerOptions markerOptions3 = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet3)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        gMap.addMarker(markerOptions3);
//

//        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {;
//
//            @Override
//            public boolean onMarkerClick(Marker marker3) {
//
//
//                if (prevMarker2 != null) {
//                    //Set prevMarker back to default color
////                    prevMarker2.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
////                    MarkerOptions markerOptions3 = new MarkerOptions()
////                            .position(latLng)
////                            .title(title)
////                            .snippet(snippet3)
////                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
////                    gMap.addMarker(markerOptions3);
//                    prevMarker2.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
////
//                }
//
//                //leave Marker default color if re-click current Marker
//                if (!marker3.equals(prevMarker2)) {
//                    marker3.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
////                    marker1.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
//                    prevMarker2 = marker3;
//                }
//                prevMarker2 = marker3;
//
////                MarkerOptions markerOptions1 = new MarkerOptions()
////                        .position(latLng)
////                        .title(title)
////                        .snippet(snippet3)
////                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.greycolor));
////                gMap.addMarker(markerOptions1);
//                return false;
//            }
//
//        });


//
//        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
//        {
//            @Override
//            public boolean onMarkerClick(Marker marker)
//            {
//
//                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
//                {
//                    @Override
//                    public boolean onMarkerClick(Marker marker)
//                    {
//                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//                        //Code for on marker click goes here
//                        return false;
//                    }
//                });
//
//
//                //Code for marker deselect goes here
//                return false;
//            }
//        });

        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker3) {
//                Toast.makeText(CurrentLocation.this, marker3.getTitle(), Toast.LENGTH_SHORT).show();
                showDialog(CurrentLocation.this);

            }
        });


    }

//    @Override
//    public void onInfoWindowClick(Marker marker) {
//
//    }

//    public class CustomClusterRenderer extends DefaultClusterRenderer<Items> {
//
//        public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<Items> clusterManager) {
//            super(context, map, clusterManager);
//        }
//
//        @Override
//        protected int getColor(int clusterSize) {
////            return Color.parseColor("#567238");
//            return Color.BLUE;// Return any color you want here. You can base it on clusterSize.
//        }
//
//        @Override
//        protected void onBeforeClusterItemRendered(Items item, MarkerOptions markerOptions) {
//
////            BitmapDescriptor descriptor
////                    = BitmapDescriptorFactory.fromResource(R.drawable.amber);
////            // Replace  R.drawable.ic_map_icon with your drawable 
////
////            markerOptions.icon(descriptor);
//
////            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
////            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.amber));
//        }
////        @Override
////        protected void onBeforeClusterRendered(Cluster<Items> cluster, MarkerOptions markerOptions) {
////
////            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.amber));
////
////        }
//    }

    private void getMarkersRed() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_RED,
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
                                lm = product.getString(LatenManfu);
                                addMarker1(latLng, title);
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
                                lm = displayData.get(i).get(LatenManfu).toString();

//                                MarkerOptions marker = new MarkerOptions().position(new LatLng(latt, logg)).title(ip).snippet("Lat: "+latt+",Longitude: "+logg+" \n Colume Manf: "+ cl+"\n Raise & Lower "+rs+
//                                        "\n Colume Material: "+cm+" \n Colume Type: "+" \n Colume Height: "+chg +" \n Number of Door's: "+nd+" \n Door Dim: "+dd+"\n Foundation type: "+ft+
//                                        "\n Column Bracket:"+bt+" \n Bracket Length:"+bl+"\n Estimated Age of Lat:"+ eage +" \n Lat. Manf: "+ lm );
//                                gMap.addMarker(marker);

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
                        Toast.makeText(CurrentLocation.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void addMarker1(final LatLng latLng, final String title) {
        double lat = latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet4 = (" Status : Red " + " \n Column : " + cnum + " \n Lat: " + lat + ",Longitude: " + lng + " \n Colume Manf: " + cl + "\n Raise & Lower: " + rs +
                "\n Colume Material: " + cm + " \n Colume Type: " + ct + " \n Colume Height: " + chg + " \n Number of Door: " + nd + " \n Door Dimen: " + dd + "\n Foundation type: " + ft +
                "\n Column Bracket:" + bt + " \n Bracket Length:" + bl + "\n Estimated Age of Lat:" + eage + " \n Lat. Manf: " + lm);

// Create a cluster item for the marker and set the title and snippet using the constructor.
        //  Items infoWindowItem = new Items(lat,lng, title, snippet);
        MarkerOptions markerOptions4 = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet4)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        gMap.addMarker(markerOptions4);

//        MarkerOptions markerOptions1 = new MarkerOptions()
//                .position(latLng)
//                .title(title)
//                .snippet(snippet4)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.greycolor));
//        gMap.addMarker(markerOptions1);

//        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//
//            @Override
//            public boolean onMarkerClick(Marker marker4) {
//
//
////                if (prevMarker3 != null) {
////                    //Set prevMarker back to default color
//////                    MarkerOptions markerOptions4 = new MarkerOptions()
//////                            .position(latLng)
//////                            .title(title)
//////                            .snippet(snippet4)
//////                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//////                    gMap.addMarker(markerOptions4);
//////                    marker4.setI
////                    marker4.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
////                }
////
////                //leave Marker default color if re-click current Marker
////                if (!marker4.equals(prevMarker3)) {
////                    marker4.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//////                    marker1.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.greycolor));
////                    prevMarker3 = marker4;
////
//////                 marker4.setIcon(getMarkerIcon(getResources().getColor(R.color.grey)));
////                }
////                prevMarker3 = marker4;
//
//
//
//                if (prevMarker3 != null) {
//                    //Set prevMarker back to default color
////                    MarkerOptions markerOptions4 = new MarkerOptions()
////                            .position(latLng)
////                            .title(title)
////                            .snippet(snippet4)
////                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
////                    gMap.addMarker(markerOptions4);
////                    marker4.setI
//                    prevMarker3.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                }
//
//                //leave Marker default color if re-click current Marker
//                if (!marker4.equals(prevMarker3)) {
//                    marker4.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//                    prevMarker3 = marker4;
//
//                }
//
//
//
//
//                prevMarker3 = marker4;
//
//                return false;
//            }
//
//        });

//
//        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
//        {
//            @Override
//            public boolean onMarkerClick(Marker marker)
//            {
//                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
//                {
//                    @Override
//                    public boolean onMarkerClick(Marker marker)
//                    {
//                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//                        //Code for on marker click goes here
//                        return false;
//                    }
//                });


//                //Code for marker deselect goes here
//                return false;
//            }
//        });


        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker4) {
//                Toast.makeText(CurrentLocation.this, marker4.getTitle(), Toast.LENGTH_SHORT).show();
                showDialog(CurrentLocation.this);

            }
        });

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
                                JSONObject catobj = array.getJSONObject(i);
                                SuggestGetSet colman = new SuggestGetSet(catobj.getString(ID1), catobj.getString(Station11));
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
                        Toast.makeText(CurrentLocation.this, error.getMessage(), Toast.LENGTH_LONG).show();

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lables);
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

//        Intent intent = new Intent(AvaliableData.this, listview.class);
//        startActivity(intent);


        return true;
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

        Toast.makeText(this, items.getTitle(), Toast.LENGTH_SHORT).show();
        showDialog(CurrentLocation.this);
        showStartDialog();

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


    private void showStartDialog2() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        builder1.setMessage("You are in Data view Mode for editing Click on different mode button");
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    private void showStartDialogallData() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        builder1.setMessage("You are in Data view Mode for editing Click on different mode button");
        AlertDialog alert11 = builder1.create();
        alert11.show();

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



