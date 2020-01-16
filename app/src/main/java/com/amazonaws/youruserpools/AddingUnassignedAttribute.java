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
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Iterator;
import java.util.List;
//
//public class database_colume_node extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
public class AddingUnassignedAttribute extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {


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
    private Spinner spinnerCoastKm;

    // array list for spinner adapter
    private Switch switch1;
    Multiple item ;
    String[] e11;
    String[] e12;
    String[] e13;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TE = "text";
    public static final String SWITCH1 = "switch1";
    ArrayList<String> CountryName;
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
    private ArrayList<SuggestGetSet> List;
    public static final String ID = "id";
    public static final String columeManf = "manufacturer_name";
    public static final String RaiseandLow = "flag";
    public static final String columeMaterial = "material_name";
    public static final String ColumeType = "type_name";
    public static final String ColumeHight = "column_height";
    public static final String NumDoors = "number_of_door1";
    public static final String DoorDimen = "number_of_door";
    public static final String Foundation = "foundation_type";
    public static final String ColumeBracket = "bracket_type";
    public static final String BracketLenth = "bracket_length";
    public static final String EstimatedAge = "column_ages";
    public static final String LatenManfu = "lantern_manufacturer";
    public static final String coastKm = "cost_km";
    ProgressDialog pDialog;

    int size;

    private String URL_ColumeManfucture = "https://brh4n8g8q9.execute-api.eu-west-1.amazonaws.com/default/GetAttributeData";

    private SharedPreferences sharedPreferences;

    EditText StudentName;
    SharedPreferences mPrefs;
    AutoCompleteTextView AutostationName;

    //........................
    public static final String ID1 = "Id";
    public static final String Station11 = "Station";

    //* Fields to contain the current position and display contents of the spinner
    ArrayList<ArrayList<String>> myList = new ArrayList<>();
    protected int mPos;
    protected String mSelection;
    /**
     * ArrayAdapter connects the spinner widget to array-based data.
     */
    protected ArrayAdapter<CharSequence> mAdapter;
    protected ArrayAdapter<CharSequence> mAdapter1;
    protected ArrayAdapter<CharSequence> mAdapter2;
    protected ArrayAdapter<CharSequence> mAdapter3;
    protected ArrayAdapter<CharSequence> mAdapter4;
    protected ArrayAdapter<CharSequence> mAdapter5;
    protected ArrayAdapter<CharSequence> mAdapter6;
    protected ArrayAdapter<CharSequence> mAdapter7;
    protected ArrayAdapter<CharSequence> mAdapter8;
    protected ArrayAdapter<CharSequence> mAdapter9;
    protected ArrayAdapter<CharSequence> mAdapter10;
    protected ArrayAdapter<CharSequence> mAdapter11;
    /**

     * These values are used to read and write the properties file.
     * PROPERTY_DELIMITER delimits the key and value in a Java properties file.
     * The "marker" strings are used to write the properties into the file
     */
    public static final String PROPERTY_DELIMITER = "=";
    /**
     * The key or label for "position" in the preferences file
     */
    public static final String POSITION_KEY = "Position";
    /**
     * The key or label for "selection" in the preferences file
     */
    public static final String SELECTION_KEY = "Selection";
    public static final String POSITION_MARKER =
            POSITION_KEY + PROPERTY_DELIMITER;
    public static final String SELECTION_MARKER =
            SELECTION_KEY + PROPERTY_DELIMITER;

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

    public static final String SHARED_PREFS13 = "sharedPrefs12";
    public static final String TEXT13 = "text12";

    private static final String URL_Data = "https://brh4n8g8q9.execute-api.eu-west-1.amazonaws.com/default/GetAttributeData";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_unassigned_attribute);
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
        StudentName = (EditText)findViewById(R.id.editName);
        mPrefs = getSharedPreferences("label", 0);
        CountryName = new ArrayList<>();
        spinnerCoastKm = (Spinner) findViewById(R.id.Coast);
        AutostationName = (AutoCompleteTextView) findViewById(R.id.editStationName);
        TextView ipaddress = (TextView) findViewById(R.id.ipadddress);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            e1 = extras.getString("ListViewValue");
            ipaddress.setText("Unassigned assets" );
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Adding Asset Attribute");

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
        List = new ArrayList<SuggestGetSet>();

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
        spinnerCoastKm.setOnItemSelectedListener(this);
        spinnerlaternManuf.setOnItemSelectedListener(this);
        sharedPreferences = getSharedPreferences(TE, MODE_PRIVATE);
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
        spinnerlaternManuf.setSelection(sharedPreferences.getInt(SHARED_PREFS12, 0));
        spinnerCoastKm.setSelection(sharedPreferences.getInt(SHARED_PREFS13, 0));


        getAutoComlete();
        GetCategories();


        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (checkNetworkConnection()) {
                    new HTTPAsyncTask().execute("https://svjuuau0x8.execute-api.eu-west-1.amazonaws.com/default/ISDColumeUpdate");
                    onBackPressed();
                } else
                    Toast.makeText(AddingUnassignedAttribute.this, "Not Connected!", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(AddingUnassignedAttribute.this, error.getMessage(), Toast.LENGTH_LONG).show();

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
        AutostationName.setAdapter(adapter);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:	finish(); return true;
        }
        return true;
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList.size(); i++) {
//            categoriesList.remove(null);
            String firstArray = categoriesList.get(i).getName();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.color_spinner_layout,lables);

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

            String firstArray = categoriesList1.get(i).getName1();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }

        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this,  R.layout.color_spinner_layout,lables);

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

    private void populateCoast() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList1.size(); i++) {

            String firstArray = categoriesList1.get(i).getName1();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }

        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this,  R.layout.color_spinner_layout,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerCoastKm.setAdapter(spinnerAdapter);


        final String firstItem1 = String.valueOf(spinnerCoastKm.getSelectedItem());

        spinnerCoastKm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem1.equals(String.valueOf(spinnerCoastKm.getSelectedItem()))) {

                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test1 = getSharedPreferences(SHARED_PREFS13, Context.MODE_PRIVATE);
        int spinnerValue1 = test1.getInt(TEXT13, -1);
        if (spinnerValue1 != -1)
            // set the value of the spinner
            spinnerCoastKm.setSelection(spinnerValue1);



    }

    private void ColumeMat() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList2.size(); i++) {

            String firstArray = categoriesList2.get(i).getName2();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this,  R.layout.color_spinner_layout,lables);

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

            String firstArray = categoriesList3.get(i).getName3();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.color_spinner_layout,lables);

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

            String firstArray = categoriesList4.get(i).getName4();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this,  R.layout.color_spinner_layout,lables);

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

            String firstArray = categoriesList5.get(i).getName5();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this,  R.layout.color_spinner_layout,lables);

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

            String firstArray = categoriesList6.get(i).getName6();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }

        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.color_spinner_layout,lables);

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

            String firstArray = categoriesList7.get(i).getName7();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this,  R.layout.color_spinner_layout,lables);

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

            String firstArray = categoriesList8.get(i).getName8();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this,  R.layout.color_spinner_layout,lables);

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


            String firstArray = categoriesList9.get(i).getName9();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }

        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.color_spinner_layout,lables);

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

            String firstArray = categoriesList10.get(i).getName10();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }

        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.color_spinner_layout,lables);

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
    private void LatentManfu() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < categoriesList11.size(); i++) {

            String firstArray = categoriesList11.get(i).getName11();

            if (firstArray != "null" && firstArray.length() > 0) {

                lables.add(firstArray);
            }
        }
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this, R.layout.color_spinner_layout,lables);

        spinnerAdapter.setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerlaternManuf.setAdapter(spinnerAdapter);

        final String firstItem11 = String.valueOf( spinnerlaternManuf.getSelectedItem());

        spinnerlaternManuf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem11.equals(String.valueOf( spinnerlaternManuf.getSelectedItem()))) {

                } else {
//
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences test11 = getSharedPreferences(SHARED_PREFS12, Context.MODE_PRIVATE);
        int spinnerValue11 = test11.getInt(TEXT12, -1);
        if (spinnerValue11 != -1)
            // set the value of the spinner
            spinnerlaternManuf.setSelection(spinnerValue11);

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
                                Category1 colman1 = new Category1(catobj.getInt(ID), catobj.getString(RaiseandLow));
                                categoriesList1.add(colman1);
                                populateSpinner1();
                                populateCoast();
                                Category2 colman3 = new Category2(catobj.getInt(ID), catobj.getString(columeMaterial));
                                categoriesList2.add(colman3);
                                ColumeMat();

                                Category3 colman4 = new Category3(catobj.getInt(ID), catobj.getString(ColumeType));
                                categoriesList3.add(colman4);
                                ColumeType();

                                Category4 colman5 = new Category4(catobj.getInt(ID), catobj.getString(ColumeHight));
                                categoriesList4.add(colman5);
                                ColumeHight();

                                Category5 colman6 = new Category5(catobj.getInt(ID), catobj.getString(NumDoors));
                                categoriesList5.add(colman6);
                                NumberDoors();

                                Category6 colman7 = new Category6(catobj.getInt(ID), catobj.getString(DoorDimen));
                                categoriesList6.add(colman7);
                                DoorDimen();

                                Category7 colman8 = new Category7(catobj.getInt(ID), catobj.getString(Foundation));
                                categoriesList7.add(colman8);
                                Foundation();

                                Category8 colman9 = new Category8(catobj.getInt(ID), catobj.getString(ColumeBracket));
                                categoriesList8.add(colman9);
                                BracketType();

                                Category9 colman10 = new Category9(catobj.getInt(ID), catobj.getString(BracketLenth));
                                categoriesList9.add(colman10);
                                BracketLenght();

                                Category10 colman11 = new Category10(catobj.getInt(ID), catobj.getString(EstimatedAge));
                                categoriesList10.add(colman11);
                                EstimatedAge();

                                Category11 colman12 = new Category11(catobj.getInt(ID), catobj.getString(LatenManfu));
                                categoriesList11.add(colman12);
                                LatentManfu();

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddingUnassignedAttribute.this, error.getMessage(), Toast.LENGTH_LONG).show();

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

        spinnerCoastKm = (Spinner) findViewById(R.id.Coast);
        SharedPreferences.Editor prefEditor13 = getSharedPreferences(SHARED_PREFS13, 0).edit();
        prefEditor13.putInt(TEXT13, spinnerCoastKm.getSelectedItemPosition());
        prefEditor13.apply();

        spinnerlaternManuf  = (Spinner) findViewById(R.id.lantern_manufacturer);
        SharedPreferences.Editor prefEditor11 = getSharedPreferences(SHARED_PREFS12, 0).edit();
        prefEditor11.putInt(TEXT12,   spinnerlaternManuf.getSelectedItemPosition());
        prefEditor11.apply();


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
        jsonObject.put("columeupdate", "5");
        jsonObject.put("iccid", e1);
        jsonObject.put("station_name", AutostationName.getText().toString());
        jsonObject.put("station_cws", "COY");
        jsonObject.put("asset_id", "");
        jsonObject.accumulate("asset_type","CCTV System");
        jsonObject.accumulate("is_costal", spinnerCoastKm.getSelectedItem().toString());
        jsonObject.accumulate("column_manufacturer", spinnerColumeManf.getSelectedItem().toString());
        jsonObject.accumulate("raise_and_lower", spinnerRaiseandLow.getSelectedItem().toString());
        jsonObject.accumulate("column_material", spinnerColumeMat.getSelectedItem().toString());
        jsonObject.accumulate("column_type", spinnerColumeType.getSelectedItem().toString());
        jsonObject.accumulate("column_height", spinnerColumeHight.getSelectedItem().toString());
        jsonObject.accumulate("number_of_doors", spinnerNumDoors.getSelectedItem().toString());
        jsonObject.accumulate("door_dimension", spinnerDoorDimen.getSelectedItem().toString());
        jsonObject.accumulate("foundation_type", spinnerFoundation.getSelectedItem().toString());
        jsonObject.accumulate("bracket_type", spinnerColumeBracketType.getSelectedItem().toString());
        jsonObject.accumulate("bracket_length", spinnerBracketLength.getSelectedItem().toString());
        jsonObject.accumulate("estimated_column_age", spinnerEstimatedColAGE.getSelectedItem().toString());
        jsonObject.accumulate("lantern_manufacturer", spinnerlaternManuf.getSelectedItem().toString());

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


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}
