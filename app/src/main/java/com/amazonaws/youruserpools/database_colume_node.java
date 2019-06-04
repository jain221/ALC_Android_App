package com.amazonaws.youruserpools;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.*;
//
//public class database_colume_node extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

public class database_colume_node extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {


    public static final String TAG = database_colume_node.class.getSimpleName();

    TextView ipaddress,lat,logg ;
    String e1,e2,e3;
    private Button btnAddNewCategory;
    private TextView txtCategory;
    private Spinner spinnerColumeManf;
    private Spinner spinnerRaiseandLow;
    private Spinner spinnerColumeMat;
    private Spinner spinnerColumeType;
    private Spinner spinnerColumeHight;
    private Spinner spinnerNumDoors;
    private Spinner spinnerDoorDimen;
    private Spinner spinnerFoundation;
    private Spinner spinnerColumeBracketType;
    private Spinner spinnerBracketLength;
    private Spinner spinnerEstimatedColAGE;
    private Spinner spinnerlaternManuf;
    // array list for spinner adapter

    EditText edit;
    private SharedPreferences mMyPrefs;
    private SharedPreferences.Editor mMyEdit;

    private ArrayList<Category> categoriesList;
    private ArrayList<Category1> categoriesList1;
    private ArrayList<Category2> categoriesList2;
    private ArrayList<Category3> categoriesList3;
    private ArrayList<Category4> categoriesList4;
    private ArrayList<Category5> categoriesList5;
    private ArrayList<Category6> categoriesList6;
    private ArrayList<Category7> categoriesList7;
    private ArrayList<Category8> categoriesList8;
    private ArrayList<Category9> categoriesList9;
    private ArrayList<Category10> categoriesList10;
    private ArrayList<Category11> categoriesList11;

    private boolean isSpinnerTouched = false;

    public static final String ID = "id";
    public static final String columeManf = "manufacturer_name";
    public static final String RaiseandLow = "flag";
    public static final String columeMaterial = "material_name";
    public static final String ColumeType = "type_name";
    public static final String ColumeHight = "column_height";
    public static final String NumDoors = "number_of_door";
    public static final String DoorDimen = "number_of_door";
    public static final String Foundation = "foundation_type";
    public static final String ColumeBracket = "bracket_type";
    public static final String BracketLenth = "bracket_length";
    public static final String EstimatedAge = "column_ages";
    public static final String LatenManfu = "lantern_manufacturer";
    ProgressDialog pDialog;
    String FileName = "hello_file";
    // API urls
    // Url to create new category
    private String URL_NEW_CATEGORY = "https://8jpt28d8fk.execute-api.eu-west-1.amazonaws.com/SendData/ISD";
    private String URL_ColumeManfucture = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/column_manufacture";
    private String URL_RaiseandLow = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/Raise_and_Lower";
    private String URL_columeMaterial = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/column_material";
    private String URL_ColumeType = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/column_type";
    private String URL_ColumeHight = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/column_height_from_ground";
    private String URL_NumDoors = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/number_of_door";
    private String URL_DoorDimen = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/door_dimensions";
    private String URL_Foundation = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/foundation_type";
    private String URL_ColumeBracketType = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/bracket_type";
    private String URL_BracketLen = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/bracket_length";
    private String URL_EstimateAge = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/estimated_age";
    private String URL_LateManfu = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/lantern_manufacturer";
    int spinnerPos;
    TextView mTvCountry;
    TextView text12;
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt(" spinnerColumeManf", spinnerColumeManf.getSelectedItemPosition());
//        // do this for each or your Spinner
//        // You might consider using Bundle.putStringArray() instead
//    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colume_list);
        SharedPreferences spinnersaving = getSharedPreferences("spinnerstate",0);
        btnAddNewCategory = (Button) findViewById(R.id.btnSave);
        spinnerColumeManf = (Spinner) findViewById(R.id.ColumManf);
        spinnerRaiseandLow = (Spinner) findViewById(R.id.RaiseandLow);
        spinnerColumeMat = (Spinner) findViewById(R.id.columeMaterial);
        spinnerColumeType = (Spinner) findViewById(R.id.ColumeType);
        spinnerColumeHight = (Spinner) findViewById(R.id.ColumeHeight);
        spinnerNumDoors = (Spinner) findViewById(R.id.NumberDoor);
        spinnerDoorDimen = (Spinner) findViewById(R.id.DoorDimension);
        spinnerFoundation = (Spinner) findViewById(R.id.FoundatType);
        spinnerColumeBracketType = (Spinner) findViewById(R.id.BracketType);
        spinnerBracketLength = (Spinner) findViewById(R.id.BracketLength);
        spinnerEstimatedColAGE = (Spinner) findViewById(R.id.ColumeAge);
        spinnerlaternManuf = (Spinner) findViewById(R.id.lantern_manufacturer);


      //              EditText edit=(EditText) findViewById(R.id.ed);
//        TextView ipaddress = (TextView) findViewById(R.id.ipadddress);
//        TextView lat = (TextView) findViewById(R.id.lat);
//        TextView logg = (TextView) findViewById(R.id.log);




//
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            e1 =extras.getString("doubleValue_e1");
//            e2 = extras.getString("doubleValue_e2");
//            e3 = extras.getString("doubleValue_e3");
//            ipaddress.setText("IP_Add. " + e1);
//            lat.setText("Latitude " + e2);
//            logg.setText("Longitude " + e3);
//
//        }
        ActionBar actionBar =getSupportActionBar();
//        actionBar.setTitle("                             Add Data");
        actionBar.setTitle("Add Data");

//
//        Intent intent = null;
//       String LatLng  =intent.getExtras().getParcelable("ipaddress");
//       lat.setText("IP - " + LatLng );

        categoriesList = new ArrayList<Category>();
        categoriesList1 = new ArrayList<Category1>();
        categoriesList2 = new ArrayList<Category2>();
        categoriesList3 = new ArrayList<Category3>();
        categoriesList4 = new ArrayList<Category4>();
        categoriesList5 = new ArrayList<Category5>();
        categoriesList6 = new ArrayList<Category6>();
        categoriesList7 = new ArrayList<Category7>();
        categoriesList8 = new ArrayList<Category8>();
        categoriesList9 = new ArrayList<Category9>();
        categoriesList10 = new ArrayList<Category10>();
        categoriesList11 = new ArrayList<Category11>();

        // spinner item select listener

        spinnerColumeManf.setOnItemSelectedListener(this);
        spinnerRaiseandLow.setOnItemSelectedListener(this);
        spinnerColumeMat.setOnItemSelectedListener(this);
        spinnerColumeType.setOnItemSelectedListener(this);
        spinnerColumeHight.setOnItemSelectedListener(this);
        spinnerNumDoors.setOnItemSelectedListener(this);
        spinnerDoorDimen.setOnItemSelectedListener(this);
        spinnerFoundation.setOnItemSelectedListener(this);
        spinnerColumeBracketType.setOnItemSelectedListener(this);
        spinnerBracketLength.setOnItemSelectedListener(this);
        spinnerEstimatedColAGE.setOnItemSelectedListener(this);
        spinnerlaternManuf.setOnItemSelectedListener(this);
//        Parcelable state = spinnerColumeManf.onSaveInstanceState();
//        spinnerColumeManf.onRestoreInstanceState(state);

      //  mTvCountry = (TextView) findViewById(R.id.Col);

//    //     Add new category click event
//        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(database_colume_node.this, "Data Saved SuccessFully!!!", Toast.LENGTH_SHORT).show();
//                // perform HTTP POST request
//                if (checkNetworkConnection()) {
//                    new HTTPAsyncTask().execute(" https://8jpt28d8fk.execute-api.eu-west-1.amazonaws.com/SendData/ISD");
//  //                  onBackPressed();
////                    onResume();
//
//                }
//                else
//                    Toast.makeText(database_colume_node.this, "Not Connected!", Toast.LENGTH_SHORT).show();
//
//            }
//        });




        // Add new category click event
        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(database_colume_node.this, Displayactivity.class);

                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    e1 = extras.getString("doubleValue_e1");
                    e2 = extras.getString("doubleValue_e2");
                    e3 = extras.getString("doubleValue_e3");
                }

                intent.putExtra("ipaddress", e1);
                intent.putExtra("latitude", e2);
                intent.putExtra("longitude", e3);
                intent.putExtra("Colume_Manfucture", spinnerColumeManf.getSelectedItem().toString());
                intent.putExtra("Raise_and_Lower", spinnerRaiseandLow.getSelectedItem().toString());
                intent.putExtra("Colume_Material", spinnerColumeMat.getSelectedItem().toString());
                intent.putExtra("Colume_Type", spinnerColumeType.getSelectedItem().toString());
                intent.putExtra("column_height_from_ground", spinnerColumeHight.getSelectedItem().toString());
                intent.putExtra("number_of_door", spinnerNumDoors.getSelectedItem().toString());
                intent.putExtra("door_dimensions", spinnerDoorDimen.getSelectedItem().toString());
                intent.putExtra("foundation_type", spinnerFoundation.getSelectedItem().toString());
                intent.putExtra("bracket_type", spinnerColumeBracketType.getSelectedItem().toString());
                intent.putExtra("bracket_length", spinnerBracketLength.getSelectedItem().toString());
                intent.putExtra("estimated_column_age", spinnerEstimatedColAGE.getSelectedItem().toString());
                intent.putExtra("lantern_manufacturer", spinnerlaternManuf.getSelectedItem().toString());

                startActivity(intent);

            }
        });

        GetCategories();
        GetCategories1();
        GetCategories2();
        GetCategories3();
        GetCategories4();
        GetCategories5();
        GetCategories6();
        GetCategories7();
        GetCategories8();
        GetCategories9();
        GetCategories10();
        GetCategories11();




    }


//    protected void onResume(){
//        super.onResume();
//        spinnerColumeManf.setSelection(spinnerPos);
//    }
//
//    protected void onStop() {
//        super.onStop();
//        SharedPreferences spinnersaving = getSharedPreferences("spinnerstate", 0);
//
//        SharedPreferences.Editor editor = spinnersaving.edit();
//        editor.putInt("spinnerPos", spinnerPos);
//        editor.commit();
//    }



protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt("spinnerIndex", spinnerPos);
    Log.d("SpinnerState", "Spinner at position " + spinnerPos + " was saved");

//    Log.d("Instance state", "onSaveInstanceState");
//}
}


    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        spinnerPos = savedInstanceState.getInt("spinnerIndex");

        Log.d("SpinnerState", "Spinner at position " + spinnerPos + " was restored");

//
//        Log.d("InstsetPromptance state", "onRestoreInstanceState");
    }
    /**
     * Addiphpng spinner data
     */
    private void populateSpinner() {


        final List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList.size(); i++) {
            lables.add(categoriesList.get(i).getName());


            spinnerColumeManf.setSelection(i);

            }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinerrgb, lables);


        spinnerAdapter.setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerColumeManf.setAdapter(spinnerAdapter);
        //       spinnerColumeManf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//
//                Log.d(TAG, "TextView Value before spinner change: " + hiddenTextView.getText());
//
//                String spinnerValue = String.valueOf(   spinnerColumeManf.getSelectedItem());
//
//                hiddenTextView.setText(spinnerValue);
//                Log.d(TAG, "TextView Value after spinner change: " + hiddenTextView.getText());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                return;
//            }
//
//        });
//
//        AdapterView.OnItemSelectedListener countrySelectedListener = new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> spinner, View container,
//                                       int position, long id) {
//                mTvCountry.setText(lables.get(position));
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//            }
//        };
//
//        // Setting ItemClick Handler for Spinner Widget
//        spinnerColumeManf.setOnItemSelectedListener(countrySelectedListener);
//    }


//        spinnerColumeManf.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                isSpinnerTouched = true;
//                return false;
//            }
//        });
//        spinnerColumeManf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapter, View arg1,
//                                       int arg2, long arg3) {
//                if (!isSpinnerTouched) return;
//                // do what you want
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


//
//    @Override
//    public void onPause()
//    {
//        // get Spinner Slected text here
//        String selectedtext = spinnerColumeManf.getSelectedItem().toString();
//
//        //Create SharedPreferences to store selected value
//
//        SharedPreferences spinnerPrefs = this.getSharedPreferences("spinnerPrefs",
//                MODE_WORLD_READABLE);
//        SharedPreferences.Editor prefsEditor = spinnerPrefs.edit();
//        prefsEditor.putString("spinner_selectedtext", selectedtext);
//        prefsEditor.commit();
//
//        super.onPause();
//
//    }

//
    }
    private void populateSpinner1() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList1.size(); i++) {
            lables.add(categoriesList1.get(i).getName1());
        }

        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.spinerrgb,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerRaiseandLow.setAdapter(spinnerAdapter);


    }

    private void ColumeMat() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList2.size(); i++) {
            lables.add(categoriesList2.get(i).getName2());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.spinerrgb,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);
        // attaching data adapter to spinner
        spinnerColumeMat.setAdapter(spinnerAdapter);


    }

    private void ColumeType() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList3.size(); i++) {
            lables.add(categoriesList3.get(i).getName3());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.spinerrgb,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);
        // attaching data adapter to spinner

        spinnerColumeType.setAdapter(spinnerAdapter);


    }

    private void ColumeHight() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList4.size(); i++) {
            lables.add(categoriesList4.get(i).getName4());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.spinerrgb,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerColumeHight.setAdapter(spinnerAdapter);

    }
    private void NumberDoors() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList5.size(); i++) {
            lables.add(categoriesList5.get(i).getName5());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.spinerrgb,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);
        // attaching data adapter to spinner
        spinnerNumDoors.setAdapter(spinnerAdapter);

    }
    private void DoorDimen() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList6.size(); i++) {
            lables.add(categoriesList6.get(i).getName6());
        }

        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.spinerrgb,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);
        // attaching data adapter to spinner
        spinnerDoorDimen.setAdapter(spinnerAdapter);

    }
    private void Foundation() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList7.size(); i++) {
            lables.add(categoriesList7.get(i).getName7());
        }
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.spinerrgb,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerFoundation.setAdapter(spinnerAdapter);

    }

    private void BracketType() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList8.size(); i++) {
            lables.add(categoriesList8.get(i).getName8());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.spinerrgb,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerColumeBracketType.setAdapter(spinnerAdapter);

    }
    private void BracketLenght() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList9.size(); i++) {
            lables.add(categoriesList9.get(i).getName9());
        }

        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.spinerrgb,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerBracketLength.setAdapter(spinnerAdapter);

    }

    private void EstimatedAge() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList10.size(); i++) {
            lables.add(categoriesList10.get(i).getName10());
        }

        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.spinerrgb,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerEstimatedColAGE.setAdapter(spinnerAdapter);

    }
    private void LatentManfu() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList11.size(); i++) {
            lables.add(categoriesList11.get(i).getName11());
        }
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.spinerrgb,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerlaternManuf.setAdapter(spinnerAdapter);

    }

    /**
     * Async task to get all food categories
     */
    private void GetCategories() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ColumeManfucture,
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
                                Category colman = new Category(catobj.getInt(ID), catobj.getString(columeManf));
                                categoriesList.add(colman);
                                populateSpinner();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }

    private void GetCategories1() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_RaiseandLow,
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
                                Category1 colman1 = new Category1(catobj.getInt(ID), catobj.getString(RaiseandLow));
                                categoriesList1.add(colman1);
                                populateSpinner1();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }

    private void GetCategories2() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_columeMaterial,
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
                                Category2 colman = new Category2(catobj.getInt(ID), catobj.getString(columeMaterial));
                                categoriesList2.add(colman);
                                ColumeMat();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }

    private void GetCategories3() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ColumeType,
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
                                Category3 colman1 = new Category3(catobj.getInt(ID), catobj.getString(ColumeType));
                                categoriesList3.add(colman1);
                                ColumeType();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void GetCategories4() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ColumeHight,
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
                                Category4 colman = new Category4(catobj.getInt(ID), catobj.getString(ColumeHight));
                                categoriesList4.add(colman);
                                ColumeHight();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }
    private void GetCategories5() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL_NumDoors,
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
                                Category5 colman1 = new Category5(catobj.getInt(ID), catobj.getString(NumDoors));
                                categoriesList5.add(colman1);
                                NumberDoors();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }
    private void GetCategories6() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL_DoorDimen,
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
                                Category6 colman = new Category6(catobj.getInt(ID), catobj.getString(DoorDimen));
                                categoriesList6.add(colman);
                                DoorDimen();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }

    private void GetCategories7() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Foundation,
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
                                Category7 colman1 = new Category7(catobj.getInt(ID), catobj.getString(Foundation));
                                categoriesList7.add(colman1);
                                Foundation();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void GetCategories8() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ColumeBracketType ,
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
                                Category8 colman = new Category8(catobj.getInt(ID), catobj.getString(ColumeBracket));
                                categoriesList8.add(colman);
                                BracketType();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }

    private void GetCategories9() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET,  URL_BracketLen,
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
                                Category9 colman1 = new Category9(catobj.getInt(ID), catobj.getString(BracketLenth));
                                categoriesList9.add(colman1);
                                BracketLenght();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }

    private void GetCategories10() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_EstimateAge,
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
                                Category10 colman = new Category10(catobj.getInt(ID), catobj.getString(EstimatedAge));
                                categoriesList10.add(colman);
                                EstimatedAge();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }

    private void GetCategories11() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_LateManfu,
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
                                Category11 colman1 = new Category11(catobj.getInt(ID), catobj.getString(LatenManfu));
                                categoriesList11.add(colman1);
                                LatentManfu();

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
                        Toast.makeText(database_colume_node.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


//
//
//    // check network connection
//    public boolean checkNetworkConnection() {
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        boolean isConnected = false;
//        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
//            // show "Connected" & type of network "WIFI or MOBILE"
//
//
//        } else {
//            // show "Not Connected"
//
//        }
//
//        return isConnected;
//    }
//
//
//    private String httpPost(String myUrl) throws IOException, JSONException {
//        String result = "";
//
//        URL url = new URL(myUrl);
//
//        // 1. create HttpURLConnection
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//
//        // 2. build JSON object
//        JSONObject jsonObject = buidJsonObject();
//
//        // 3. add JSON content to POST request body
//        setPostRequestContent(conn, jsonObject);
//
//        // 4. make POST request to the given URL
//        conn.connect();
//
//        // 5. return response message
//        return conn.getResponseMessage() + "";
//
//    }
//
//
//    class HTTPAsyncTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//            // params comes from the execute() call: params[0] is the url.
//            try {
//                try {
//                    return httpPost(urls[0]);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    return "Error!";
//                }
//            } catch (IOException e) {
//                return "Unable to retrieve web page. URL may be invalid.";
//            }
//        }
//
//        // onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//
//        }
//    }
//
//
//    private JSONObject buidJsonObject() throws JSONException {
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            e1 = extras.getString("doubleValue_e1");
//            e2 = extras.getString("doubleValue_e2");
//            e3 = extras.getString("doubleValue_e3");
//        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("ipaddress", e1);
//        jsonObject.put("latitude", e2);
//        jsonObject.put("longitude", e3);
//        jsonObject.accumulate("Colume_Manfucture", spinnerColumeManf.getSelectedItem().toString());
//        jsonObject.accumulate("Raise_and_Lower", spinnerRaiseandLow.getSelectedItem().toString());
//        jsonObject.accumulate("Colume_Material", spinnerColumeMat.getSelectedItem().toString());
//        jsonObject.accumulate("Colume_Type", spinnerColumeType.getSelectedItem().toString());
//        jsonObject.accumulate("column_height_from_ground", spinnerColumeHight.getSelectedItem().toString());
//        jsonObject.accumulate("number_of_door", spinnerNumDoors.getSelectedItem().toString());
//        jsonObject.accumulate("door_dimensions", spinnerDoorDimen.getSelectedItem().toString());
//        jsonObject.accumulate("foundation_type", spinnerFoundation.getSelectedItem().toString());
//        jsonObject.accumulate("bracket_type", spinnerColumeBracketType.getSelectedItem().toString());
//        jsonObject.accumulate("bracket_length", spinnerBracketLength.getSelectedItem().toString());
//        jsonObject.accumulate("estimated_column_age", spinnerEstimatedColAGE.getSelectedItem().toString());
//        jsonObject.accumulate("lantern_manufacturer", spinnerlaternManuf.getSelectedItem().toString());
//        return jsonObject;
//    }
//
//    private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {
//
//        OutputStream os = conn.getOutputStream();
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//        writer.write(jsonObject.toString());
//        Log.i(MainActivity.class.toString(), jsonObject.toString());
//        writer.flush();
//        writer.close();
//        os.close();
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position,
//                               long id) {
////        Toast.makeText(
////                getApplicationContext(),
////                parent.getItemAtPosition(position).toString() + " Save" ,
////                Toast.LENGTH_LONG).show();
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> arg0) {
//    }

}


