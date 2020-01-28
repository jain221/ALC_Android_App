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



public class Replace_Latern_Attribute extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String ID = "id";

    TextView ipaddress, lat, log, cl1, rs1, cm1, ct1, chg1, nd1, dd1, ft1, bt1, bl1, eage1, lm1;
    String ip,latt,logg,cl, rs, cm, ct, chg, nd, dd, ft, bt, bl, eage, lm;
    String e1, e2, e3;
    ProgressDialog pDialog;
    String TempItem;
    private Button btnAddNewCategory;
    private TextView txtCategory;

    private Spinner spinnerlaternManuf;



    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TE = "text";
    public static final String SWITCH1 = "switch1";




    private ArrayList<Lanten_Manf> categoriesList11;



    public static final String LatenManfu = "lantern_manufacturer";

    SharedPreferences.Editor prefEditor;
    int spinnerPosition;
    // API urls
    // Url to create new category

    private String URL_LateManfu =  "https://brh4n8g8q9.execute-api.eu-west-1.amazonaws.com/default/GetAttributeData";
    private SharedPreferences sharedPreferences;


    SharedPreferences mPrefs;



    public static final String SHARED_PREFS12 = "sharedPrefs11";
    public static final String TEXT12 = "text11";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_latern);

        TempItem = getIntent().getStringExtra("doubleValue_e1");
        latt = getIntent().getStringExtra("doubleValue_e2");
        logg = getIntent().getStringExtra("doubleValue_e3");






        btnAddNewCategory = (Button) findViewById(R.id.btnSave);
        spinnerlaternManuf = (Spinner) findViewById(R.id.lantern_manufacturer);
        //mPrefs = getSharedPreferences("label", 0);



        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("                             Add Data");
        actionBar.setTitle("Lantern Replacement ");


        categoriesList11 = new ArrayList<Lanten_Manf>();


        spinnerlaternManuf.setOnItemSelectedListener(this);

     //   sharedPreferences = getSharedPreferences(TE, MODE_PRIVATE);

      //  spinnerlaternManuf.setSelection(sharedPreferences.getInt(SHARED_PREFS12, 0));





        GetCategories11();

        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(Replace_Latern_Attribute.this, "Data Saved SuccessFully!!!", Toast.LENGTH_SHORT).show();
                //      saveData();
                //    perform HTTP POST request
                if (checkNetworkConnection()) {
                    new HTTPAsyncTask().execute("https://svjuuau0x8.execute-api.eu-west-1.amazonaws.com/default/ISDColumeUpdate");
                    onBackPressed();
                } else
                    Toast.makeText(Replace_Latern_Attribute.this, "Not Connected!", Toast.LENGTH_SHORT).show();

            }

        });






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
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this,  R.layout.color_spinner_layout,lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(R.layout.spinerrgb);

        // attaching data adapter to spinner
        spinnerlaternManuf.setAdapter(spinnerAdapter);


        final String firstItem11 = String.valueOf( spinnerlaternManuf.getSelectedItem());

        spinnerlaternManuf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstItem11.equals(String.valueOf( spinnerlaternManuf.getSelectedItem()))) {

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

        SharedPreferences test11 = getSharedPreferences(SHARED_PREFS12, Context.MODE_PRIVATE);
        int spinnerValue11 = test11.getInt(TEXT12, -1);
        if (spinnerValue11 != -1)
            // set the value of the spinner
            spinnerlaternManuf.setSelection(spinnerValue11);

    }


    /**
     * Async task to get all food categories
     */


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
                                Lanten_Manf colman1 = new Lanten_Manf(catobj.getInt(ID), catobj.getString(LatenManfu));
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
                        Toast.makeText(Replace_Latern_Attribute.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onPause() {
        super.onPause();

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

        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("columeupdate", "3");
        jsonObject.put("iccid", e1);

        jsonObject.accumulate("lantern_manufacturer", spinnerlaternManuf.getSelectedItem().toString());
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




