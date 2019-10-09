package com.amazonaws.youruserpools;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
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
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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
import java.util.ArrayList;
import java.util.List;

public class ReplaceColumn extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

    String e1,e2,e3;
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


    public static final String SHARED_PREFS1 = "sharedPrefs";
    public static final String TEXT = "text";

    public static final String SHARED_PREFS2 = "sharedPrefs2";
    public static final String TEXT2 = "text2";

    public static final String SHARED_PREFS3 = "sharedPrefs3";
    public static final String TEXT3 = "text3";

    public static final String SHARED_PREFS4 = "sharedPrefs4";
    public static final String TEXT4 = "text4";

    public static final String SHARED_PREFS5 = "sharedPrefs5";
    public static final String TEXT5 = "text5";

    public static final String SHARED_PREFS6 = "sharedPrefs6";
    public static final String TEXT6 = "text6";

    public static final String SHARED_PREFS7 = "sharedPrefs7";
    public static final String TEXT7 = "text7";

    public static final String SHARED_PREFS8 = "sharedPrefs8";
    public static final String TEXT8 = "text8";

    public static final String SHARED_PREFS9 = "sharedPrefs9";
    public static final String TEXT9 = "text9";

    public static final String SHARED_PREFS10 = "sharedPrefs10";
    public static final String TEXT10 = "text10";

    public static final String SHARED_PREFS11 = "sharedPrefs11";
    public static final String TEXT11 = "text11";

    public static final String SHARED_PREFS12 = "sharedPrefs11";
    public static final String TEXT12 = "text11";

    SharedPreferences mPrefs;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TE = "text";
    public static final String SWITCH1 = "switch1";

    TextView ipaddress, lat, log;
    String TempItem, latt, logg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_column);

        TempItem = getIntent().getStringExtra("doubleValue_e1");
        latt = getIntent().getStringExtra("doubleValue_e2");
        logg = getIntent().getStringExtra("doubleValue_e3");
//

//        ipaddress = (TextView) findViewById(R.id.t1);
//        lat = (TextView) findViewById(R.id.t2);
//        log = (TextView) findViewById(R.id.t3);

//
//        ipaddress.setText(TempItem);
//        lat.setText(latt);
//        log.setText(logg);

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


        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("                             Add Data");
        actionBar.setTitle("Edit Asset Data");


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

        SharedPreferences sharedPreferences = getSharedPreferences(TE, MODE_PRIVATE);
        spinnerColumeManf.setSelection(sharedPreferences.getInt(SHARED_PREFS, 0));
        spinnerRaiseandLow.setSelection(sharedPreferences.getInt(SHARED_PREFS2, 0));
        spinnerColumeMat.setSelection(sharedPreferences.getInt(SHARED_PREFS3, 0));
        spinnerColumeType.setSelection(sharedPreferences.getInt(SHARED_PREFS4, 0));
        spinnerColumeHight.setSelection(sharedPreferences.getInt(SHARED_PREFS5, 0));
        spinnerNumDoors.setSelection(sharedPreferences.getInt(SHARED_PREFS6, 0));
        spinnerDoorDimen.setSelection(sharedPreferences.getInt(SHARED_PREFS7, 0));
        spinnerFoundation.setSelection(sharedPreferences.getInt(SHARED_PREFS8, 0));
        spinnerColumeBracketType.setSelection(sharedPreferences.getInt(SHARED_PREFS9, 0));
        spinnerBracketLength.setSelection(sharedPreferences.getInt(SHARED_PREFS10, 0));
        spinnerEstimatedColAGE.setSelection(sharedPreferences.getInt(SHARED_PREFS11, 0));


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


        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(ReplaceColumn.this, "Data Saved SuccessFully!!!", Toast.LENGTH_SHORT).show();
                //      saveData();
                //    perform HTTP POST request
                if (checkNetworkConnection()) {
                    new HTTPAsyncTask().execute("  https://svjuuau0x8.execute-api.eu-west-1.amazonaws.com/default/ISDColumeUpdate");
                    onBackPressed();
                } else
                    Toast.makeText(ReplaceColumn.this, "Not Connected!", Toast.LENGTH_SHORT).show();

            }

        });


    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList.size(); i++) {
            lables.add(categoriesList.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.color_spinner_layout, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerColumeManf.setAdapter(spinnerAdapter);

        final String firstItem = String.valueOf(spinnerColumeManf.getSelectedItem());


        spinnerColumeManf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem.equals(String.valueOf(spinnerColumeManf.getSelectedItem()))) {

                } else {
//                    Toast.makeText(parent.getContext(),
//                            "Ko Milih : " + parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test = getSharedPreferences(SHARED_PREFS1, Context.MODE_PRIVATE);
        int spinnerValue = test.getInt(TEXT, -1);
        if (spinnerValue != -1)
            // set the value of the spinner
            spinnerColumeManf.setSelection(spinnerValue);

    }


    private void populateSpinner1() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList1.size(); i++) {
            lables.add(categoriesList1.get(i).getName1());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.color_spinner_layout, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerRaiseandLow.setAdapter(spinnerAdapter);


        final String firstItem1 = String.valueOf(spinnerRaiseandLow.getSelectedItem());

        spinnerRaiseandLow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem1.equals(String.valueOf(spinnerRaiseandLow.getSelectedItem()))) {

                } else {
//                    Toast.makeText(parent.getContext(),
//                            "Ko Milih : " + parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test1 = getSharedPreferences(SHARED_PREFS2, Context.MODE_PRIVATE);
        int spinnerValue1 = test1.getInt(TEXT2, -1);
        if (spinnerValue1 != -1)
            // set the value of the spinner
            spinnerRaiseandLow.setSelection(spinnerValue1);


    }

    private void ColumeMat() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList2.size(); i++) {
            lables.add(categoriesList2.get(i).getName2());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.color_spinner_layout, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);
        // attaching data adapter to spinner
        spinnerColumeMat.setAdapter(spinnerAdapter);


        final String firstItem2 = String.valueOf(spinnerColumeMat.getSelectedItem());

        spinnerColumeMat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem2.equals(String.valueOf(spinnerColumeMat.getSelectedItem()))) {

                } else {
//                    Toast.makeText(parent.getContext(),
//                            "Ko Milih : " + parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test2 = getSharedPreferences(SHARED_PREFS3, Context.MODE_PRIVATE);
        int spinnerValue2 = test2.getInt(TEXT3, -1);
        if (spinnerValue2 != -1)
            // set the value of the spinner
            spinnerColumeMat.setSelection(spinnerValue2);


    }

    private void ColumeType() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList3.size(); i++) {
            lables.add(categoriesList3.get(i).getName3());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.color_spinner_layout, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);
        // attaching data adapter to spinner

        spinnerColumeType.setAdapter(spinnerAdapter);


        final String firstItem3 = String.valueOf(spinnerColumeType.getSelectedItem());


        spinnerColumeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem3.equals(String.valueOf(spinnerColumeType.getSelectedItem()))) {

                } else {
//                    Toast.makeText(parent.getContext(),
//                            "Ko Milih : " + parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test3 = getSharedPreferences(SHARED_PREFS4, Context.MODE_PRIVATE);
        int spinnerValue3 = test3.getInt(TEXT4, -1);
        if (spinnerValue3 != -1)
            // set the value of the spinner
            spinnerColumeType.setSelection(spinnerValue3);


    }

    private void ColumeHight() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList4.size(); i++) {
            lables.add(categoriesList4.get(i).getName4());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.color_spinner_layout, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerColumeHight.setAdapter(spinnerAdapter);


        final String firstItem4 = String.valueOf(spinnerColumeHight.getSelectedItem());


        spinnerColumeHight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position5, long id) {
                if (firstItem4.equals(String.valueOf(spinnerColumeHight.getSelectedItem()))) {

                } else {
//                    Toast.makeText(parent.getContext(),
//                            "Ko Milih : " + parent.getItemAtPosition(position5).toString(),
//                            Toast.LENGTH_LONG).show();

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test4 = getSharedPreferences(SHARED_PREFS5, Context.MODE_PRIVATE);
        int spinnerValue4 = test4.getInt(TEXT5, -1);
        if (spinnerValue4 != -1)
            // set the value of the spinner
            spinnerColumeHight.setSelection(spinnerValue4);


    }

    private void NumberDoors() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList5.size(); i++) {
            lables.add(categoriesList5.get(i).getName5());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.color_spinner_layout, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);
        // attaching data adapter to spinner
        spinnerNumDoors.setAdapter(spinnerAdapter);

        final String firstItem5 = String.valueOf(spinnerNumDoors.getSelectedItem());


        spinnerNumDoors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem5.equals(String.valueOf(spinnerNumDoors.getSelectedItem()))) {

                } else {
//                    Toast.makeText(parent.getContext(),
//                            "Ko Milih : " + parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test5 = getSharedPreferences(SHARED_PREFS6, Context.MODE_PRIVATE);
        int spinnerValue5 = test5.getInt(TEXT6, -1);
        if (spinnerValue5 != -1)
            // set the value of the spinner
            spinnerNumDoors.setSelection(spinnerValue5);


    }

    private void DoorDimen() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList6.size(); i++) {
            lables.add(categoriesList6.get(i).getName6());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.color_spinner_layout, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);
        // attaching data adapter to spinner
        spinnerDoorDimen.setAdapter(spinnerAdapter);


        final String firstItem6 = String.valueOf(spinnerDoorDimen.getSelectedItem());


        spinnerDoorDimen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem6.equals(String.valueOf(spinnerDoorDimen.getSelectedItem()))) {

                } else {
//                    Toast.makeText(parent.getContext(),
//                            "Ko Milih : " + parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test6 = getSharedPreferences(SHARED_PREFS7, Context.MODE_PRIVATE);
        int spinnerValue6 = test6.getInt(TEXT7, -1);
        if (spinnerValue6 != -1)
            // set the value of the spinner
            spinnerDoorDimen.setSelection(spinnerValue6);


    }

    private void Foundation() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList7.size(); i++) {
            lables.add(categoriesList7.get(i).getName7());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.color_spinner_layout, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerFoundation.setAdapter(spinnerAdapter);

        final String firstItem7 = String.valueOf(spinnerFoundation.getSelectedItem());

        spinnerFoundation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem7.equals(String.valueOf(spinnerFoundation.getSelectedItem()))) {

                } else {
//                    Toast.makeText(parent.getContext(),
//                            "Ko Milih : " + parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test7 = getSharedPreferences(SHARED_PREFS8, Context.MODE_PRIVATE);
        int spinnerValue7 = test7.getInt(TEXT8, -1);
        if (spinnerValue7 != -1)
            // set the value of the spinner
            spinnerFoundation.setSelection(spinnerValue7);

    }

    private void BracketType() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList8.size(); i++) {
            lables.add(categoriesList8.get(i).getName8());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.color_spinner_layout, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerColumeBracketType.setAdapter(spinnerAdapter);


        final String firstItem8 = String.valueOf(spinnerColumeBracketType.getSelectedItem());


        spinnerColumeBracketType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem8.equals(String.valueOf(spinnerColumeBracketType.getSelectedItem()))) {

                } else {
//                    Toast.makeText(parent.getContext(),
//                            "Ko Milih : " + parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test8 = getSharedPreferences(SHARED_PREFS9, Context.MODE_PRIVATE);
        int spinnerValue8 = test8.getInt(TEXT9, -1);
        if (spinnerValue8 != -1)
            // set the value of the spinner
            spinnerColumeBracketType.setSelection(spinnerValue8);


    }

    private void BracketLenght() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList9.size(); i++) {
            lables.add(categoriesList9.get(i).getName9());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.color_spinner_layout, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerBracketLength.setAdapter(spinnerAdapter);

        final String firstItem9 = String.valueOf(spinnerBracketLength.getSelectedItem());

        spinnerBracketLength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem9.equals(String.valueOf(spinnerBracketLength.getSelectedItem()))) {

                } else {
//                    Toast.makeText(parent.getContext(),
//                            "Ko Milih : " + parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test9 = getSharedPreferences(SHARED_PREFS10, Context.MODE_PRIVATE);
        int spinnerValue9 = test9.getInt(TEXT10, -1);
        if (spinnerValue9 != -1)
            // set the value of the spinner
            spinnerBracketLength.setSelection(spinnerValue9);

    }

    private void EstimatedAge() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList10.size(); i++) {
            lables.add(categoriesList10.get(i).getName10());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.color_spinner_layout, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerEstimatedColAGE.setAdapter(spinnerAdapter);

        final String firstItem10 = String.valueOf(spinnerEstimatedColAGE.getSelectedItem());

        spinnerEstimatedColAGE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem10.equals(String.valueOf(spinnerEstimatedColAGE.getSelectedItem()))) {

                } else {
//                    Toast.makeText(parent.getContext(),
//                            "Ko Milih : " + parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test10 = getSharedPreferences(SHARED_PREFS10, Context.MODE_PRIVATE);
        int spinnerValue10 = test10.getInt(TEXT10, -1);
        if (spinnerValue10 != -1)
            // set the value of the spinner
            spinnerEstimatedColAGE.setSelection(spinnerValue10);

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
                        Toast.makeText(ReplaceColumn.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ReplaceColumn.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ReplaceColumn.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ReplaceColumn.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ReplaceColumn.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ReplaceColumn.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ReplaceColumn.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ReplaceColumn.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ReplaceColumn.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ReplaceColumn.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ReplaceColumn.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }



    @Override
    public void onPause() {
        super.onPause();
        spinnerColumeManf = (Spinner) findViewById(R.id.ColumManf);
        SharedPreferences.Editor prefEditor = getSharedPreferences(SHARED_PREFS1, 0).edit();
        prefEditor.putInt(TEXT, spinnerColumeManf.getSelectedItemPosition());
        prefEditor.apply();

        spinnerRaiseandLow = (Spinner) findViewById(R.id.RaiseandLow);
        SharedPreferences.Editor prefEditor1 = getSharedPreferences(SHARED_PREFS2, 0).edit();
        prefEditor1.putInt(TEXT2, spinnerRaiseandLow.getSelectedItemPosition());
        prefEditor1.apply();

        spinnerColumeMat = (Spinner) findViewById(R.id.columeMaterial);
        SharedPreferences.Editor prefEditor2 = getSharedPreferences(SHARED_PREFS3, 0).edit();
        prefEditor2.putInt(TEXT3,  spinnerColumeMat.getSelectedItemPosition());
        prefEditor2.apply();


        spinnerColumeType  = (Spinner) findViewById(R.id.ColumeType);
        SharedPreferences.Editor prefEditor3 = getSharedPreferences(SHARED_PREFS4, 0).edit();
        prefEditor3.putInt(TEXT4,  spinnerColumeType .getSelectedItemPosition());
        prefEditor3.apply();


        spinnerColumeHight  = (Spinner) findViewById(R.id.ColumeHeight);
        SharedPreferences.Editor prefEditor4 = getSharedPreferences(SHARED_PREFS5, 0).edit();
        prefEditor4.putInt(TEXT5,  spinnerColumeHight .getSelectedItemPosition());
        prefEditor4.apply();


        spinnerNumDoors  = (Spinner) findViewById(R.id.NumberDoor);
        SharedPreferences.Editor prefEditor5 = getSharedPreferences(SHARED_PREFS6, 0).edit();
        prefEditor5.putInt(TEXT6,  spinnerNumDoors.getSelectedItemPosition());
        prefEditor5.apply();


        spinnerDoorDimen  = (Spinner) findViewById(R.id.DoorDimension);
        SharedPreferences.Editor prefEditor6 = getSharedPreferences(SHARED_PREFS7, 0).edit();
        prefEditor6.putInt(TEXT7,  spinnerDoorDimen.getSelectedItemPosition());
        prefEditor6.apply();

        spinnerFoundation  = (Spinner) findViewById(R.id.FoundatType);
        SharedPreferences.Editor prefEditor7 = getSharedPreferences(SHARED_PREFS8, 0).edit();
        prefEditor7.putInt(TEXT8,  spinnerFoundation.getSelectedItemPosition());
        prefEditor7.apply();

        spinnerColumeBracketType  = (Spinner) findViewById(R.id.BracketType);
        SharedPreferences.Editor prefEditor8 = getSharedPreferences(SHARED_PREFS9, 0).edit();
        prefEditor8.putInt(TEXT9,  spinnerColumeBracketType.getSelectedItemPosition());
        prefEditor8.apply();

        spinnerBracketLength  = (Spinner) findViewById(R.id.BracketLength);
        SharedPreferences.Editor prefEditor9 = getSharedPreferences(SHARED_PREFS10, 0).edit();
        prefEditor9.putInt(TEXT10,  spinnerBracketLength.getSelectedItemPosition());
        prefEditor9.apply();


        spinnerEstimatedColAGE = (Spinner) findViewById(R.id.ColumeAge);
        SharedPreferences.Editor prefEditor10 = getSharedPreferences(SHARED_PREFS10, 0).edit();
        prefEditor10.putInt(TEXT10,  spinnerEstimatedColAGE.getSelectedItemPosition());
        prefEditor10.apply();




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
            // show "Not Connected"

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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            e1 = extras.getString("doubleValue_e1");
            e2 = extras.getString("doubleValue_e2");
            e3 = extras.getString("doubleValue_e3");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("iccid", e1);
        jsonObject.put("latitude", e2);
        jsonObject.put("longitude", e3);
        jsonObject.accumulate("Colume_Manfucture", spinnerColumeManf.getSelectedItem().toString());
        jsonObject.accumulate("Raise_and_Lower", spinnerRaiseandLow.getSelectedItem().toString());
        jsonObject.accumulate("Colume_Material", spinnerColumeMat.getSelectedItem().toString());
        jsonObject.accumulate("Colume_Type", spinnerColumeType.getSelectedItem().toString());
        jsonObject.accumulate("column_height_from_ground", spinnerColumeHight.getSelectedItem().toString());
        jsonObject.accumulate("number_of_door", spinnerNumDoors.getSelectedItem().toString());
        jsonObject.accumulate("door_dimensions", spinnerDoorDimen.getSelectedItem().toString());
        jsonObject.accumulate("foundation_type", spinnerFoundation.getSelectedItem().toString());
        jsonObject.accumulate("bracket_type", spinnerColumeBracketType.getSelectedItem().toString());
        jsonObject.accumulate("bracket_length", spinnerBracketLength.getSelectedItem().toString());
        jsonObject.accumulate("estimated_column_age", spinnerEstimatedColAGE.getSelectedItem().toString());

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
//        Toast.makeText(
//                getApplicationContext(),
//                parent.getItemAtPosition(position).toString() + " Save" ,
//                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}
