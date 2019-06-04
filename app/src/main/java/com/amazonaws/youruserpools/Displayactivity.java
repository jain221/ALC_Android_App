package com.amazonaws.youruserpools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Displayactivity extends AppCompatActivity {

    private Button btnMap,btnSend,editData;

    String ip,latt,logg,cl, rs, cm, ct, chg, nd, dd, ft, bt, bl, eage, lm;
    TextView ipaddress, lat, log, cl1, rs1, cm1, ct1, chg1, nd1, dd1, ft1, bt1, bl1, eage1, lm1;
    String e1,e2,e3;
    String cl2;
    String gameState;
    File store;

    public static final String LAST_TEXT = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayactivity);
        // recovering the instance state
        cl1 = (TextView) findViewById(R.id.ColumManf1);
        rs1 = (TextView) findViewById(R.id.RaiseandLow1);
        cm1 = (TextView) findViewById(R.id.columeMaterial1);
        ct1 = (TextView) findViewById(R.id.ColumeType1);
        chg1 = (TextView) findViewById(R.id.ColumeHeight1);
        nd1 = (TextView) findViewById(R.id.NumberDoor1);
        dd1 = (TextView) findViewById(R.id.DoorDimension1);
        ft1 = (TextView) findViewById(R.id.FoundatType1);
        bt1 = (TextView) findViewById(R.id.BracketType1);
        bl1 = (TextView) findViewById(R.id.BracketLength1);
        eage1 = (TextView) findViewById(R.id.ColumeAge1);
        lm1 = (TextView) findViewById(R.id.lantern_manufacturer1);
        btnMap = (Button) findViewById(R.id.btnSave);
        btnSend = (Button) findViewById(R.id.senddata);
        editData=  (Button) findViewById(R.id.editeColume);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            cl = getIntent().getStringExtra("Colume_Manfucture");
            rs= getIntent().getStringExtra("Raise_and_Lower");
            cm = getIntent().getStringExtra("Colume_Material");
            ct= getIntent().getStringExtra("Colume_Type");
            chg = getIntent().getStringExtra("column_height_from_ground");
            nd= getIntent().getStringExtra("number_of_door");
            dd = getIntent().getStringExtra("door_dimensions");
            ft = getIntent().getStringExtra("foundation_type");
            bt= getIntent().getStringExtra("bracket_type");
            bl= getIntent().getStringExtra("bracket_length");
            eage = getIntent().getStringExtra("estimated_column_age");
            lm = getIntent().getStringExtra("lantern_manufacturer");
        }


        cl1.setText(cl);
        rs1.setText(rs);
        cm1.setText(cm);
        ct1.setText(ct);
        chg1.setText(chg);
        nd1.setText(nd);
        dd1.setText(dd);
        ft1.setText(ft);
        bt1.setText(bt);
        bl1.setText(bl);
        eage1.setText(eage);
        lm1.setText(lm);

        btnMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Displayactivity.this, CurrentNode.class);
                startActivity(intent);

            }
        });


        editData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Displayactivity.this, database_colume_node.class);
                startActivity(intent);

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(Displayactivity.this, "Data Saved SuccessFully!!!", Toast.LENGTH_SHORT).show();
                // perform HTTP POST request
                if (checkNetworkConnection()) {
                    new HTTPAsyncTask().execute(" https://8jpt28d8fk.execute-api.eu-west-1.amazonaws.com/SendData/ISD");
                    onBackPressed();
//                    onResume();

                }
                else
                    Toast.makeText(Displayactivity.this, "Not Connected!", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(Displayactivity.this, CurrentNode.class);
        startActivity(intent);
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
        jsonObject.put("ipaddress", e1);
        jsonObject.put("latitude", e2);
        jsonObject.put("longitude", e3);
        jsonObject.accumulate("Colume_Manfucture", cl1.getText().toString());
        jsonObject.accumulate("Raise_and_Lower", rs1.getText().toString());
        jsonObject.accumulate("Colume_Material", cm1.getText().toString());
        jsonObject.accumulate("Colume_Type",  ct1.getText().toString());
        jsonObject.accumulate("column_height_from_ground", chg1.getText().toString());
        jsonObject.accumulate("number_of_door",   nd1.getText().toString());
        jsonObject.accumulate("door_dimensions",dd1.getText().toString());
        jsonObject.accumulate("foundation_type", ft1.getText().toString());
        jsonObject.accumulate("bracket_type", bt1.getText().toString());
        jsonObject.accumulate("bracket_length",  bl1.getText().toString());
        jsonObject.accumulate("estimated_column_age", eage1.getText().toString());
        jsonObject.accumulate("lantern_manufacturer",  lm1.getText().toString());
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
