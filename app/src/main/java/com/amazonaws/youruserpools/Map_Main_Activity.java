/*
 * Copyright 2013-2017 Amazon.com,
 * Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the
 * License. A copy of the License is located at
 *
 *      http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, express or implied. See the License
 * for the specific language governing permissions and
 * limitations under the License.
 */

package com.amazonaws.youruserpools;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.UpdateAttributesHandler;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Map_Main_Activity extends AppCompatActivity implements OnMapReadyCallback {


    private final String TAG="Map_Main_Activity";
    private NavigationView nDrawer;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private AlertDialog userDialog;
    private ProgressDialog waitDialog;
    public static final String TITLE = "iccid";


    // Cognito user objects
    private CognitoUser user;


    ArrayList<JSONObject> unassigneddata = new ArrayList<JSONObject>();
    ArrayList<JSONObject> displayData = new ArrayList<JSONObject>();

    private String username;
    String title;

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private int ii;
    MapFragment mapFragment;
    GoogleMap gMap;

    CameraPosition cameraPosition;
    LatLng center, latLng;

    private RequestQueue mRequestQueue;
    public static final String TAG1 = Map_Main_Activity.class.getSimpleName();
    public static final String ID = "id";
    public static final String ID1 = "Id";

    public static final String LAT =  "latitude";
    public static final String LNG = "longitude";
    public static final String Station11 = "station_name";
    public static final String Stationcode ="station_cws";
    public static final String columeNumber = "asset_id";
    public static final String assettype="asset_use";
    public static final String CoastKM= "is_costal";
    public static final String columeManf = "column_manufacturer";
    public static final String RaiseandLow = "raise_and_lower";
    public static final String columeMaterial = "column_material";
    public static final String ColumeType = "asset_type";
    public static final String ColumeHight = "column_height";
    public static final String NumDoors = "number_of_doors";
    public static final String DoorDimen = "door_dimension";
    public static final String Foundation = "foundation_type";
    public static final String ColumeBracket = "bracket_type";
    public static final String BracketLenth = "bracket_length";
    public static final String EstimatedAge = "estimated_column_age";
    public static final String LatenManfu = "lantern_manufacturer";
    public static final String IDD = "id";
    public static final String Stat = "station";
    public static final String Crs = "crs";
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17f;

    private static final String URL_UnAssinged = "https://wwf5avjfai.execute-api.eu-west-1.amazonaws.com/ISDMAPDATA/idname";
    private static final int URL_AMBER = R.string.AmberRagStatus;
    private static final int URL_GREEN = R.string.GreenRagStatus;
    private static final int URL_RED_Alert = R.string.RedAlertStatus ;
    private static final int URL_RED_Rag_Status = R.string.RedRagStatus ;
    private static final int URL_Yellow_Rag_Status = R.string.YellowRagStatus ;
    private static final String URL_Data = "https://8z4byrt6fc.execute-api.eu-west-1.amazonaws.com/BackendAPIS/GetAttributeData";


    TextView ipaddress;
    private ArrayList<Class_Get_Station_Name> List;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    String light;
    String ippp;

//    ----------


    String ip, latt, stc,astype,logg, cnum, cl, rs, cm, ct, chg, nd, dd, ft, bt, bl, eage,cstkm, lm;
    private Marker myMarker;
    private ImageView mGps;

    String data;
    AutoCompleteTextView et;

    MenuItem unsassignSize,alertAsset;

    Handler handler = new Handler();
    Runnable refresh;
    String TempItem;

    Menu menu2;
    RadioGroup radioGroup;

    private RadioButton assignn;
    private RadioButton unassign;
    private RadioButton alldata;
    private RadioButton faultalert;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_main);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//         Set toolbar for this screen
//        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//
//        toolbar.setTitle("");

        //        TextView main_title = (TextView) findViewById(R.id.main_toolbar_title);
//        main_title.setText("Monitored Assets");
//        setSupportActionBar(toolbar);

   //-------------------------------------------------------------------------------------------------------
        mDrawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        nDrawer = (NavigationView) findViewById(R.id.nav_view);
        setNavDrawer();
        init();
        View navigationHeader = nDrawer.getHeaderView(0);

        Menu menu3 = nDrawer.getMenu();
        unsassignSize =menu3.findItem(R.id.nav_unassigned_assets);
        alertAsset =menu3.findItem(R.id.nav_alert_assets);

        TextView navHeaderSubTitle = (TextView) navigationHeader.findViewById(R.id.textViewNavUserSub);
        navHeaderSubTitle.setText(username);


        Button menubar = findViewById(R.id.bar);
        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mDrawer.isDrawerOpen(Gravity.LEFT)) {
                    mDrawer.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawer.openDrawer(Gravity.LEFT);
                }
            }

        });
//-----------------------------------------------------------------------------------------------------------



        mGps = (ImageView) findViewById(R.id.ic_gps);
        et = (AutoCompleteTextView) findViewById(R.id.editText);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        assignn = (RadioButton) findViewById(R.id.radioButton1);
        unassign = (RadioButton) findViewById(R.id.radioButton2);
        alldata = (RadioButton) findViewById(R.id.radioButton3);
        faultalert = (RadioButton) findViewById(R.id.alert);
        getLocationPermission();
        if (gMap != null) {

            gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Geocoder geocoder = new Geocoder(Map_Main_Activity.this);
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



        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    et.setShowSoftInputOnFocus(true);
                    String location = et.getText().toString();
                    et.getText().clear();
                    hideSoftKeyboard();
                    Geocoder geocoder = new Geocoder(Map_Main_Activity.this);
                    List<Address> list = null;
                    try {
                        list = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (list.size() > 0) {
                        Address address = list.get(0);
                        String locality = address.getLocality();
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                        gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    }
                }
                return false;
            }
        });

        List = new ArrayList<Class_Get_Station_Name>();
        getAutoComlete();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch( checkedId) {

                    case R.id.alert:
                        gMap.clear();
                        Toast.makeText(Map_Main_Activity.this,"Edit Mode is Selected", Toast.LENGTH_LONG).show();
                        Intent intent4 = new Intent(Map_Main_Activity.this, Map_Alert_Activity.class);
                        startActivity(intent4);


                        break;

                    case R.id.radioButton1:
                        gMap.clear();
                        Toast.makeText(Map_Main_Activity.this,"Edit Mode is Selected", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Map_Main_Activity.this, Map_Assigned_Activity.class);
//                        Intent intent = new Intent(Map_Main_Activity.this, Enter_Assert_Number.class);
                        startActivity(intent);
                        break;

                    case R.id.radioButton2:
                        gMap.clear();
                        Toast.makeText(Map_Main_Activity.this,"Edit Mode is Selected", Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(Map_Main_Activity.this, Map_Unassigned_Activity.class);
                        startActivity(intent1);

                        break;

                    case R.id.radioButton3:
                        gMap.clear();
                        Toast.makeText(Map_Main_Activity.this,"All Data Selected", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });




        if (isServicesOK()) {

        }
        getunsignedSize(URL_UnAssinged);
        getalertSize(getString(URL_RED_Alert));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }


        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }


    }

//    public onBackPressed(){
//
//
//    }

 //--------------------------------------------------------------------------------------------------

    // Handle when the a navigation item is selected
    private void setNavDrawer() {
        nDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                performAction(item);
                return true;
            }
        });
    }

    // Perform the action for the selected navigation item
    private void performAction(MenuItem item) {
        // Close the navigation drawer
        mDrawer.closeDrawers();

        // Find which item was selected
        switch(item.getItemId()) {


            case R.id.nav_user_verify_attribute:
                attributesVerification();
                break;

            case R.id.nav_reassigned_assets:
                Intent reassignd = new Intent(Map_Main_Activity.this,  Map_Assigned_Activity.class);
                startActivity(reassignd);
                break;

            case R.id.nav_unassigned_assets:
                Intent unassigned = new Intent(Map_Main_Activity.this,  Map_All_UnassignedData.class);
                startActivity(unassigned);
                break;

            case R.id.nav_alert_assets:
                Intent intent = new Intent(Map_Main_Activity.this,  Map_All_Alert_Asserts.class);
                startActivity(intent);
                break;
            case R.id.nav_user_about:
                // For the inquisitive
                Intent aboutAppActivity = new Intent(this, Contact_Us_Page.class);
                startActivity(aboutAppActivity);
                break;

            case R.id.nav_user_email:
                // For the inquisitive
                Intent contact = new Intent(this, Email_Activity.class);
                startActivity(contact);
                break;

            case R.id.nav_user_sign_out:

                signOut();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_user_menu, menu);
        return true;
    }


    // Get user details from CIP service
    private void getDetails() {
        Cognito_Connection.getPool().getUser(username).getDetailsInBackground(detailsHandler);
    }


    // Update attributes
    private void updateAttribute(String attributeType, String attributeValue) {

        if(attributeType == null || attributeType.length() < 1) {
            return;
        }
        CognitoUserAttributes updatedUserAttributes = new CognitoUserAttributes();
        updatedUserAttributes.addAttribute(attributeType, attributeValue);
        Toast.makeText(getApplicationContext(), attributeType + ": " + attributeValue, Toast.LENGTH_LONG);
        showWaitDialog("Updating...");
        Cognito_Connection.getPool().getUser(Cognito_Connection.getCurrUser()).updateAttributesInBackground(updatedUserAttributes, updateHandler);
    }



    // Verify attributes
    private void attributesVerification() {
        Intent attrbutesActivity = new Intent(this,UserActivity.class);
        startActivityForResult(attrbutesActivity, 21);
    }



    // Initialize this activity
    private void init() {
        // Get the user name
        Bundle extras = getIntent().getExtras();
        username = Cognito_Connection.getCurrUser();
        user = Cognito_Connection.getPool().getUser(username);

        getDetails();
    }

    GetDetailsHandler detailsHandler = new GetDetailsHandler() {
        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            closeWaitDialog();
            // Store details in the AppHandler
            Cognito_Connection.setUserDetails(cognitoUserDetails);
            //  showAttributes();
            // Trusted devices?
            handleTrustedDevice();
        }

        @Override
        public void onFailure(Exception exception) {
            closeWaitDialog();
            showDialogMessage("Could not fetch user details!", Cognito_Connection.formatException(exception), true);
        }
    };

    private void handleTrustedDevice() {
        CognitoDevice newDevice = Cognito_Connection.getNewDevice();
        if (newDevice != null) {
            Cognito_Connection.newDevice(null);
            trustedDeviceDialog(newDevice);
        }
    }

    private void updateDeviceStatus(CognitoDevice device) {
        device.rememberThisDeviceInBackground(trustedDeviceHandler);
    }

    private void trustedDeviceDialog(final CognitoDevice newDevice) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remember this device?");
        //final EditText input = new EditText(UserActivity.this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //input.setLayoutParams(lp);
        //input.requestFocus();
        //builder.setView(input);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    //String newValue = input.getText().toString();
                    showWaitDialog("Remembering this device...");
                    updateDeviceStatus(newDevice);
                    userDialog.dismiss();
                } catch (Exception e) {
                    // Log failure
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                } catch (Exception e) {
                    // Log failure
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }

    // Callback handlers

    UpdateAttributesHandler updateHandler = new UpdateAttributesHandler() {
        @Override
        public void onSuccess(List<CognitoUserCodeDeliveryDetails> attributesVerificationList) {
            // Update successful
            if(attributesVerificationList.size() > 0) {
                showDialogMessage("Updated", "The updated attributes has to be verified",  false);
            }
            getDetails();
        }

        @Override
        public void onFailure(Exception exception) {
            // Update failed
            closeWaitDialog();
            showDialogMessage("Update failed", Cognito_Connection.formatException(exception), false);
        }
    };

    GenericHandler deleteHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            closeWaitDialog();
            // Attribute was deleted
            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT);

            // Fetch user details from the the service
            getDetails();
        }

        @Override
        public void onFailure(Exception e) {
            closeWaitDialog();
            // Attribute delete failed
            showDialogMessage("Delete failed", Cognito_Connection.formatException(e), false);

            // Fetch user details from the service
            getDetails();
        }
    };

    GenericHandler trustedDeviceHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            // Close wait dialog
            closeWaitDialog();
        }

        @Override
        public void onFailure(Exception exception) {
            closeWaitDialog();
            showDialogMessage("Failed to update device status", Cognito_Connection.formatException(exception), true);
        }
    };



    private void showWaitDialog(String message) {
        closeWaitDialog();
        waitDialog = new ProgressDialog(this);
        waitDialog.setTitle(message);
        waitDialog.show();
    }

    private void showDialogMessage(String title, String body, final boolean exit) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(body).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                    if(exit) {
                        exit();
                    }
                } catch (Exception e) {
                    // Log failure
                    Log.e(TAG," -- Dialog dismiss failed");
                    if(exit) {
                        exit();
                    }
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }

    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            //
        }
    }

    private void exit() {
        Intent intent = new Intent();
        if(username == null)
            username = "";
        intent.putExtra("name",username);
        setResult(RESULT_OK, intent);
        finish();

    }

    private void getunsignedSize(String url) {

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
                                unassigneddata.add(product);

                            }
                            unsassignSize.setTitle(unassigneddata.size()+" Unassigned Assets");

//                            Toast.makeText(Map_Main_Activity.this,  displayData.size()+" Assets are Unassigned ", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Map_Main_Activity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void getalertSize(String url) {

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
                                displayData.add(product);

                            }

                            alertAsset.setTitle(displayData.size()+" Assets Alerts");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Map_Main_Activity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }


    //-------------------------------------------------------------------------------------------------



    ///-------------------------------------------Main_Map_activity_All_Assets-------------------------


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        center = new LatLng(51.52042, -3.23113);



        getMarker(URL_UnAssinged,BitmapDescriptorFactory.HUE_BLUE,"Unassigned Assets");
        getMarker(getString(URL_GREEN),BitmapDescriptorFactory.HUE_GREEN,"Green");
        getMarker(getString(URL_AMBER),BitmapDescriptorFactory.HUE_ORANGE,"Amber Rag Status");
        getMarker(getString(URL_RED_Alert) ,BitmapDescriptorFactory.HUE_VIOLET,"Alert Asset");
        getMarker(getString(URL_RED_Rag_Status) ,BitmapDescriptorFactory.HUE_RED,"Red Rag Status");
        getMarker(getString(URL_Yellow_Rag_Status) ,BitmapDescriptorFactory.HUE_YELLOW,"Yellow Rag Status");
        All_Assets_Info_Wndow markerInfoWindowAdapter = new All_Assets_Info_Wndow(getApplicationContext());
        gMap.setInfoWindowAdapter(markerInfoWindowAdapter);


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
        gMap.setMapType(gMap.MAP_TYPE_SATELLITE);
        //   gMap.setOnCameraChangeListener((GoogleMap.OnCameraChangeListener) mClusterManager);

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG1, "onClick: clicked gps icon");
                getDeviceLocation();
                Referesh();
            }
        });

    }

    private void Referesh() {
        finish();
        startActivity(getIntent());
        handler.post(refresh);
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
                                cnum = product.getString(columeNumber);
                                stc = product.getString(Stationcode);
                                astype = product.getString(assettype);
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
                                addMarkerGreen(latLng, title, color, status);
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
                                cstkm= displayData.get(i).get(EstimatedAge).toString();
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
                        Toast.makeText(Map_Main_Activity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void addMarkerGreen(final LatLng latLng, final String title,float color_node,String status) {


        double lat = latLng.latitude;
        double lng = latLng.longitude;
        // Set he title and snippet strings.


        final String snippet3 = (" Status : " + status  + " \n Asset ID : " + cnum + " \n Lat: " + lat + ",Longitude: " + lng + " \n Colume Manf: " + cl + "\n Raise & Lower: " + rs +
                "\n Colume Material: " + cm + " \n Colume Type: " + ct + " \n Colume Height: " + chg + " \n Number of Door: " + nd + " \n Door Dimen: " + dd + "\n Foundation type: " + ft +
                "\n Column Bracket:" + bt + " \n Bracket Length:" + bl + "\n Estimated Age of Lat:" + eage + "\n Installation coast 5Km ?:" + cstkm + " \n Lat. Manf: " + lm);

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

                showDialog(Map_Main_Activity.this);


                return false;
            }


        });
    }

    private void showDialog(Map_Main_Activity Map_Main_Activity) {


        final Dialog dialog = new Dialog(Map_Main_Activity);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setContentView(R.layout.switchonoff);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlideAnim;
        Button btndialog = (Button) dialog.findViewById(R.id.cancel);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        final Button On = (Button) dialog.findViewById(R.id.on);
        On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light = "1";

                if (checkNetworkConnection()) {
                    new Map_Main_Activity.HTTPAsyncTask().execute("  https://svjuuau0x8.execute-api.eu-west-1.amazonaws.com/default/assetswitch");
//                            onBackPressed();
                } else {
                    Toast.makeText(Map_Main_Activity.this, "Not Connected!", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();

            }



        });



        Button Off = (Button) dialog.findViewById(R.id.off);
        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light = "0";

                if (checkNetworkConnection()) {
                    new Map_Main_Activity.HTTPAsyncTask().execute("  https://svjuuau0x8.execute-api.eu-west-1.amazonaws.com/default/assetswitch");
//                            onBackPressed();
                } else{
                    Toast.makeText(Map_Main_Activity.this, "Not Connected!", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();



            }

        });


        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
//        wmlp.x = 120;   //x position
//        wmlp.y = 5;   //y position

        dialog.show();

    }


    //-------------------------END-----------------------------------------------------------------------


    //------------------------Aws send data connection---------------------------------------------------
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
        JSONObject jsonObject = buidJsonObject();

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

        String ic =ippp;
        String lg =light;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("iccid", ic);
        jsonObject.put("light",lg);

        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(Login_Page.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

 //-------------------------##END-----------------------------------------------------------------------




//---------------------##same in each actvity----------------------------------------------------------


    public void getDeviceLocation() {
        Log.d(TAG1, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Map_Main_Activity.this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG1, "onComplete: found location!");
                            Location mapActivity = (Location) task.getResult();

                            moveCamera(new LatLng(mapActivity.getLatitude(), mapActivity.getLongitude()),
                                    DEFAULT_ZOOM);


                        } else {
                            Log.d(TAG1, "onComplete: current location is null");
                            Toast.makeText(Map_Main_Activity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
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
        mapFragment.getMapAsync(Map_Main_Activity.this);


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

        et.setShowSoftInputOnFocus(true);
        String location = et.getText().toString();
        et.getText().clear();
        hideSoftKeyboard();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        if (list.size() > 0) {
            Address address = list.get(0);
            String locality = address.getLocality();
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

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
                                Class_Get_Station_Name colman = new Class_Get_Station_Name(catobj.getString(IDD), catobj.getString(Stat));
//                                ListData.add(new Class_Get_Station_Name(r.getString("Id"),r.getString("Station")));
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
                        Toast.makeText(Map_Main_Activity.this, error.getMessage(), Toast.LENGTH_LONG).show();

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

//------------------------------------------------------------------------------------------------------


//---------##Service Check------------------------------------------------------------------------------

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Map_Main_Activity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Map_Main_Activity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }





//    @Override
//    public void onBackPressed() {
//
////        moveTaskToBack(true);
//        exit();
//    }



    // Sign out user
    private void signOut() {
        user.signOut();
        exit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 20:
                // Settings
                if(resultCode == RESULT_OK) {
                    boolean refresh = data.getBooleanExtra("refresh", true);
                    if (refresh) {
                       // showAttributes();
                    }
                }
                break;

        }
    }
//--------------------------------------END Activity Program----------------------------------------------

}
