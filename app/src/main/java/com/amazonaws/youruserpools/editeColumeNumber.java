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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class editeColumeNumber extends AppCompatActivity {

    public static final String ID = "id";

    TextView ipaddress, lat, log, cl1, rs1, cm1, ct1, chg1, nd1, dd1, ft1, bt1, bl1, eage1, lm1;
    String ip,latt,logg,cl, rs, cm, ct, chg, nd, dd, ft, bt, bl, eage, lm;
    String e1, e2, e3;
    ProgressDialog pDialog;
    String TempItem;
    private Button btnAddNewCategory;
    private ArrayList<Category11> categoriesList11;



    public static final String LatenManfu = "lantern_manufacturer";

    SharedPreferences.Editor prefEditor;
    int spinnerPosition;
    // API urls
    // Url to create new category

    private String URL_LateManfu = "https://qcqjrkuq8d.execute-api.eu-west-1.amazonaws.com/default/lantern_manufacturer";
    private SharedPreferences sharedPreferences;


    SharedPreferences mPrefs;



    public static final String SHARED_PREFS12 = "sharedPrefs11";
    public static final String TEXT12 = "text11";

    EditText StudentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_colume_number);
        TempItem = getIntent().getStringExtra("doubleValue_e1");
        latt = getIntent().getStringExtra("doubleValue_e2");
        logg = getIntent().getStringExtra("doubleValue_e3");
//


        log = (TextView) findViewById(R.id.t3);

        log.setText("Lat:- "+latt+" Log:-"+logg);

        btnAddNewCategory = (Button) findViewById(R.id.btnSave);
        StudentName = (EditText)findViewById(R.id.editName);
        //mPrefs = getSharedPreferences("label", 0);



        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("                             Add Data");
        actionBar.setTitle("Enter/Edit Asset Number");


        categoriesList11 = new ArrayList<Category11>();







        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(editeColumeNumber.this, "Data Saved SuccessFully!!!", Toast.LENGTH_SHORT).show();
                //      saveData();
                //    perform HTTP POST request
                if (checkNetworkConnection()) {
                    new editeColumeNumber.HTTPAsyncTask().execute("https://svjuuau0x8.execute-api.eu-west-1.amazonaws.com/default/ISDcolumenumberUpdate");
                    onBackPressed();
                } else
                    Toast.makeText(editeColumeNumber.this, "Not Connected!", Toast.LENGTH_SHORT).show();


            }

        });






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
        jsonObject.put("colume_number", StudentName.getText().toString());
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

   


}

