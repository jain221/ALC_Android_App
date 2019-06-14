package com.amazonaws.youruserpools;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.youruserpools.HttpJsonParser;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class showsingleData extends AppCompatActivity {
    private static Object URL = true;
    HttpParse httpParse = new HttpParse();

    private static final String KEY_MOVIE_ID = "ipaddress";
    public static final String LAT = "latitude";
    public static final String LNG = "longitude";
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
    // Http Url For Filter Student Data from Id Sent from previous activity.


    private static final Object JSONObject = null;

    public static final String ID = "id";


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

    List<Product> productList;
    TextView ipaddress;
    TextView lat;
    TextView log;
    TextView cl1;
    TextView rs1;
    TextView cm1;
    TextView ct1;
    TextView chg1;
    TextView nd1;
    TextView dd1;
    TextView ft1;
    TextView bt1;
    TextView bl1;
    TextView eage1;
    TextView lm1;
    String ip, latt, logg, cl, rs, cm, ct, chg, nd, dd, ft, bt, bl, eage, lm;
    String e1, e2, e3;

    String lat3, lng3;

    String lat2, logg2;
    private static final String KEY_DATA = "data";

    private static final String BASE_URL = "https://o5yklvu3td.execute-api.eu-west-1.amazonaws.com/default/fetchData";
    private String movieId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showsingle_data);
//        TempItem = getIntent().getStringExtra("ListViewValue");

        movieId = getIntent().getStringExtra("doubleValue_e1");
//        latt = getIntent().getStringExtra("doubleValue_e2");
//        logg = getIntent().getStringExtra("doubleValue_e3");
//        cl = getIntent().getStringExtra("doubleValue_e14");
////        cl = getIntent().getStringExtra("ListViewValue");
//        rs= getIntent().getStringExtra("doubleValue_e15");
//        cm = getIntent().getStringExtra("doubleValue_e4");
//        ct= getIntent().getStringExtra("doubleValue_e5");
//        chg = getIntent().getStringExtra("doubleValue_e6");
//        nd= getIntent().getStringExtra("doubleValue_e7");
//        dd = getIntent().getStringExtra("doubleValue_e8");
//        ft = getIntent().getStringExtra("doubleValue_e9");
//        bt= getIntent().getStringExtra("doubleValue_e10");
//        bl= getIntent().getStringExtra("doubleValue_e11");
//        eage = getIntent().getStringExtra("doubleValue_e12");
//        lm = getIntent().getStringExtra("doubleValue_e13");

        ipaddress = (TextView) findViewById(R.id.t1);
        lat = (TextView) findViewById(R.id.t2);
        log = (TextView) findViewById(R.id.t3);
        cl1 = (TextView) findViewById(R.id.t5);
        rs1 = (TextView) findViewById(R.id.t6);
        cm1 = (TextView) findViewById(R.id.t7);
        ct1 = (TextView) findViewById(R.id.t8);
        chg1 = (TextView) findViewById(R.id.t9);
        nd1 = (TextView) findViewById(R.id.t10);
        dd1 = (TextView) findViewById(R.id.t11);
        ft1 = (TextView) findViewById(R.id.t12);
        bt1 = (TextView) findViewById(R.id.t13);
        bl1 = (TextView) findViewById(R.id.t14);
        eage1 = (TextView) findViewById(R.id.t15);
        lm1 = (TextView) findViewById(R.id.t16);

        new FetchMovieDetailsAsyncTask().execute();
        productList = new ArrayList<>();
//        HttpWebCall(movieId);


        //   URL = new HTTPAsyncTask().execute("https://o5yklvu3td.execute-api.eu-west-1.amazonaws.com/default/fetchData");

        ipaddress.setText(movieId);
        lat.setText(lat3);
        log.setText(lng3);
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

        //     new FetchMovieDetailsAsyncTask().execute();


//        updateButton = (Button) findViewById(R.id.btnUpdate);
//        updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
//                    updateMovie();
//
//                } else {
//                    Toast.makeText(MovieUpdateDeleteActivity.this,
//                            "Unable to connect to internet",
//                            Toast.LENGTH_LONG).show();
//
//                }
//
//            }
//        });


    }

    private class FetchMovieDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(showsingleData.this);
            pDialog.setMessage("Loading Movie Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_MOVIE_ID, movieId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_movie_details.php", "GET", httpParams);
            try {
                org.json.JSONObject jsonArray = jsonObject;
                JSONObject product;
                for(int i=0; i<jsonArray.length(); i++)
                        {
                            product  = jsonArray.getJSONObject(String.valueOf(i));
                            lat3 = String.valueOf((Double.parseDouble(product.getString(LAT))));
                    lng3 = String.valueOf((Double.parseDouble(product.getString(LNG))));
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
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing


                    ipaddress.setText(movieId);
                    lat.setText(lat3);
                    log.setText(lng3);
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

                }
            });
        }

    }
}





//

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
//        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("ipaddress", e1);
//
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
//
//
//
//
//
//
//
//    //
//    //Method to show current record Current Selected Record
//    public void HttpWebCall(final String PreviousListViewClickedItem){
//
//        class HttpWebCallFunction extends AsyncTask<String,Void,String> {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//                pDialog = ProgressDialog.show(showsingleData.this,"Loading Data",null,true,true);
//            }
//
//            @Override
//            protected void onPostExecute(String httpResponseMsg) {
//
//                super.onPostExecute(httpResponseMsg);
//
//                pDialog.dismiss();
//
//                //Storing Complete JSon Object into String Variable.
//                FinalJSonObject = httpResponseMsg ;
//
//                //Parsing the Stored JSOn String to GetHttpResponse Method.
//                new GetHttpResponse(showsingleData.this).execute();
//
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                ResultHash.put("ipaddress2",params[0]);
//
//                ParseResult = httpParse.postRequest(ResultHash,BASE_URL);
//
//                return ParseResult;
//            }
//        }
//
//        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();
//
//        httpWebCallFunction.execute(PreviousListViewClickedItem);
//    }
//
//
//    // Parsing Complete JSON Object.
//    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
//    {
//        public Context context;
//
//        public GetHttpResponse(Context context)
//        {
//            this.context = context;
//        }
//
//        @Override
//        protected void onPreExecute()
//        {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0)
//        {
//            try
//            {
//                if(FinalJSonObject != null)
//                {
//                    JSONArray jsonArray = null;
//
//                    try {
//                        jsonArray = new JSONArray(FinalJSonObject);
//
//                        JSONObject product ;
//
//                        for(int i=0; i<jsonArray.length(); i++)
//                        {
//                            product  = jsonArray.getJSONObject(i);
//
//
//                            double lat2 = (Double.parseDouble(product.getString(LAT)));
//                            double logg2 = (Double.parseDouble(product.getString(LNG)));
//                            cl = product.getString(columeManf);
//                            rs = product.getString(RaiseandLow);
//                            cm = product.getString(columeMaterial) ;
//                            ct = product.getString(ColumeType) ;
//                            chg = product.getString(ColumeHight) ;
//                            nd = product.getString(NumDoors) ;
//                            dd = product.getString(DoorDimen) ;
//                            ft = product.getString(Foundation) ;
//                            bt = product.getString(ColumeBracket) ;
//                            bl = product.getString(BracketLenth) ;
//                            eage = product.getString(EstimatedAge) ;
//                            lm = product.getString(LatenManfu) ;
//                            productList.add(new Product(movieId,lat2,logg2,cl,rs,cm,ct,chg,nd,dd,ft,bt,bl,eage,lm));
//
//                        }
//                    }
//                    catch (JSONException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//            catch (Exception e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result)
//        {
//
//
////            String lat = lat3;
////            String lng = lng3;
////            String c11 =String.valueOf(cl);
////            String rs1 =String.valueOf(rs);
////            String cm1 =String.valueOf(cm);
////            String ct1 =String.valueOf(ct);
////            String chg1 =String.valueOf(chg);
////            String nd1 =String.valueOf(nd);
////            String dd1 =String.valueOf(dd);
////            String ft1 =String.valueOf(ft);
////            String bt1 =String.valueOf(bt);
////            String b11 =String.valueOf(bl);
////            String eage1 =String.valueOf(eage);
////            String lm1 =String.valueOf(lm);
//
////            ipaddress.setText(movieId);
////            lat.setText(lat3);
////            log.setText(lng3);
////            cl1.setText(cl);
////            rs1.setText(rs);
////            cm1.setText(cm);
////            ct1.setText(ct);
////            chg1.setText(chg);
////            nd1.setText(nd);
////            dd1.setText(dd);
////            ft1.setText(ft);
////            bt1.setText(bt);
////            bl1.setText(bl);
////            eage1.setText(eage);
////            lm1.setText(lm);
//
//            Product product = productList.get(1);
//                    ipaddress.setText(product.getipaddress());
//                    lat.setText(String.valueOf(product.getlatitude()));
//                    log.setText(String.valueOf(product.getlongitude()));
//                    cl1.setText(product.getColume_Manfucture());
//                    rs1.setText(product.getRaise_and_Lower());
//                    cm1.setText(product.getColume_Material());
//                    ct1.setText(product.getColume_Type());
//                    chg1.setText(product.getcolumn_height_from_ground());
//                    nd1.setText(product.getnumber_of_door());
//                    dd1.setText(product.getdoor_dimensions());
//                    ft1.setText(product.getfoundation_type());
//                    bt1.setText(product.getbracket_type());
//                    bl1.setText(product.getbracket_length());
//                    eage1.setText(product.getestimated_column_age());
//                    lm1.setText(product.getlantern_manufacture());
//        }
//
//
//        }
//

//


//
//    /**
//     * Fetches single movie details from the server
//     */
//    private class FetchMovieDetailsAsyncTask extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //Display progress bar
//            pDialog = new ProgressDialog(showsingleData.this);
//            pDialog.setMessage("Loading Movie Details. Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            HttpJsonParser httpJsonParser = new HttpJsonParser();
//            Map<String, String> httpParams = new HashMap<>();
//            httpParams.put(KEY_MOVIE_ID, movieId);
//            final org.json.JSONObject[] product = {httpJsonParser.makeHttpRequest(
//                    BASE_URL + "get_movie_details.php", "GET", httpParams)};
////            try {
////                int success = jsonObject.getInt(String.valueOf(1));
////                JSONObject product;
////                if (success == 1) {
////                    //Parse the JSON response
////
//
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                //converting the string to json array object
//                                JSONArray array = new JSONArray(response);
//
//                                //traversing through all the object
//                                for (int i = 0; i < array.length(); i++) {
//
////                                    JSONObject product = array.getJSONObject(i);
////
////                    //traversing through all the object
////                    for (int i = 0; i < array.length(); i++) {
//
//                                    //getting product object from json array
//                                    product[0] = array.getJSONObject(i);
//                                    String ipadddress = product[0].getString(KEY_MOVIE_ID);
//                                    double lat2 = (Double.parseDouble(product[0].getString(LAT)));
//                                    double logg2 = (Double.parseDouble(product[0].getString(LNG)));
//                                    cl = product[0].getString(columeManf).toString();
//                                    rs = product[0].getString(RaiseandLow).toString();
//                                    cm = product[0].getString(columeMaterial).toString();
//                                    ct = product[0].getString(ColumeType).toString();
//                                    chg = product[0].getString(ColumeHight).toString();
//                                    nd = product[0].getString(NumDoors).toString();
//                                    dd = product[0].getString(DoorDimen).toString();
//                                    ft = product[0].getString(Foundation).toString();
//                                    bt = product[0].getString(ColumeBracket).toString();
//                                    bl = product[0].getString(BracketLenth).toString();
//                                    eage = product[0].getString(EstimatedAge).toString();
//                                    lm = product[0].getString(LatenManfu).toString();
//
//                                productList.add(new Product(ipadddress,lat2,logg2,cl,rs,cm,ct,chg,nd,dd,ft,bt,bl,eage,lm));
//
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(showsingleData.this, error.getMessage(), Toast.LENGTH_LONG).show();
//
//                        }
//                    });
//
//            //adding our stringrequest to queue
//            Volley.newRequestQueue(showsingleData.this).add(stringRequest);
//            return null;
//        }
//
//            protected void onPostExecute(final String result) {
//            pDialog.dismiss();
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    //Populate the Edit Texts once the network activity is finished executing
//
//
//                    ipaddress.setText(product.getipaddress());
//                    lat.setText(String.valueOf(product.getlatitude()));
//                    log.setText(String.valueOf(product.getlongitude()));
//                    cl1.setText(product.getColume_Manfucture());
//                    rs1.setText(product.getRaise_and_Lower());
//                    cm1.setText(product.getColume_Material());
//                    ct1.setText(product.getColume_Type());
//                    chg1.setText(product.getcolumn_height_from_ground());
//                    nd1.setText(product.getnumber_of_door());
//                    dd1.setText(product.getdoor_dimensions());
//                    ft1.setText(product.getfoundation_type());
//                    bt1.setText(product.getbracket_type());
//                    bl1.setText(product.getbracket_length());
//                    eage1.setText(product.getestimated_column_age());
//                    lm1.setText(product.getlantern_manufacture());
//                }
//            });
//        }
//
//
//


//        getData();


    //Calling method to filter Student Record and open selected record.
//        HttpWebCall(TempItem);


    //  .......................................................................................
//    private void getData() {
//
//
////        StringRequest stringRequest = new StringRequest(Request.Method.GET, (String) URL,
////                new Response.Listener<String>() {
////
////                    HttpJsonParser httpJsonParser = new HttpJsonParser();
////                    Map<String, String> httpParams = new HashMap<>();
////                    httpParams.put(KEY_MOVIE_ID, movieId);
////                    JSONObject jsonObject = httpJsonParser.makeHttpRequest(
////                            BASE_URL + "get_movie_details.php", "GET", httpParams);
////                    @Override
////                    public void onResponse(String response) {
////                        try {
////
//////                                JSONObject product = new JSONObject(response);
//////                                JSONArray jsonarray = product.getJSONArray(TempItem);
//////                                JSONObject data = jsonarray.getJSONObject(0);
////
////
////                            String s = new String(response);
////                            JSONObject jsonObject = new JSONObject(s);
////
////                            if (jsonObject.has(" movieId")) {
////                                JSONArray country = jsonObject.getJSONArray(" movieId");
////                                for (int i = 0; i < country.length(); i++) {
////                                    JSONObject product = country.getJSONObject(i);
////                                    lat3 = String.valueOf((Double.parseDouble(product.getString(LAT))));
////                                    lng3 = String.valueOf((Double.parseDouble(product.getString(LNG))));
////                                    cl = product.getString(columeManf);
////                                    rs = product.getString(RaiseandLow);
////                                    cm = product.getString(columeMaterial);
////                                    ct = product.getString(ColumeType);
////                                    chg = product.getString(ColumeHight);
////                                    nd = product.getString(NumDoors);
////                                    dd = product.getString(DoorDimen);
////                                    ft = product.getString(Foundation);
////                                    bt = product.getString(ColumeBracket);
////                                    bl = product.getString(BracketLenth);
////                                    eage = product.getString(EstimatedAge);
////                                    lm = product.getString(LatenManfu);
////
////                                }
////
////                            }
//
//
//
//        com.amazonaws.youruserpools.HttpJsonParser httpJsonParser = new HttpJsonParser();
//        Map<String, String> httpParams = new HashMap<>();
//        httpParams.put(KEY_MOVIE_ID, movieId);
//        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                BASE_URL + "get_movie_details.php", "GET", httpParams);
//        try {
//            int success = jsonObject.getInt(String.valueOf(1));
//            JSONObject product;
//            if (success == 1) {
//                //Parse the JSON response
//                product = jsonObject.getJSONObject(KEY_DATA);
//                lat3 = String.valueOf((Double.parseDouble(product.getString(LAT))));
//                                    lng3 = String.valueOf((Double.parseDouble(product.getString(LNG))));
//                                    cl = product.getString(columeManf);
//                                    rs = product.getString(RaiseandLow);
//                                    cm = product.getString(columeMaterial);
//                                    ct = product.getString(ColumeType);
//                                    chg = product.getString(ColumeHight);
//                                    nd = product.getString(NumDoors);
//                                    dd = product.getString(DoorDimen);
//                                    ft = product.getString(Foundation);
//                                    bt = product.getString(ColumeBracket);
//                                    bl = product.getString(BracketLenth);
//                                    eage = product.getString(EstimatedAge);
//                                    lm = product.getString(LatenManfu);
//
//            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//
//                }
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(showsingleData.this, error.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }
//
//                };

        //adding our stringrequest to queue
//        Volley.newRequestQueue(this).add(httpJsonParser);





//
//    //Method to show current record Current Selected Record
//    public void HttpWebCall(final String PreviousListViewClickedItem){
//
//        class HttpWebCallFunction extends AsyncTask<String,Void,String> {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//                pDialog = ProgressDialog.show(showsingleData.this,"Loading Data",null,true,true);
//            }
//
//            @Override
//            protected void onPostExecute(String httpResponseMsg) {
//
//                super.onPostExecute(httpResponseMsg);
//
//                pDialog.dismiss();
//
//                //Storing Complete JSon Object into String Variable.
//                FinalJSonObject = httpResponseMsg ;
//
//                //Parsing the Stored JSOn String to GetHttpResponse Method.
//                new GetHttpResponse(showsingleData.this).execute();
//
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                ResultHash.put("ipaddress",params[0]);
//
//                ParseResult = httpParse.postRequest(ResultHash, HttpURL);
//
//                return ParseResult;
//            }
//        }
//
//        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();
//
//        httpWebCallFunction.execute(PreviousListViewClickedItem);
//    }
//
//
//    // Parsing Complete JSON Object.
//    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
//    {
//        public Context context;
//
//        public GetHttpResponse(Context context)
//        {
//            this.context = context;
//        }
//
//        @Override
//        protected void onPreExecute()
//        {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0)
//        {
//            try
//            {
//                if(FinalJSonObject != null)
//                {
//                    JSONArray jsonArray = null;
//
//                    try {
//                        jsonArray = new JSONArray(FinalJSonObject);
//
//                        JSONObject product ;
//
//                        for(int i=0; i<jsonArray.length(); i++)
//                        {
//                            product  = jsonArray.getJSONObject(i);
//
////                            // Storing Student Name, Phone Number, Class into Variables.
////                            NameHolder = jsonObject.getString("name").toString() ;
////                            NumberHolder = jsonObject.getString("phone_number").toString() ;
////                            ClassHolder = jsonObject.getString("class").toString() ;
////
////      /
//
//                            cl = product.getString(columeManf).toString() ;
//                            rs = product.getString(RaiseandLow).toString() ;
//                            cm = product.getString(columeMaterial).toString() ;
//                            ct = product.getString(ColumeType).toString() ;
//                            chg = product.getString(ColumeHight).toString() ;
//                            nd = product.getString(NumDoors).toString() ;
//                            dd = product.getString(DoorDimen).toString() ;
//                            ft = product.getString(Foundation).toString() ;
//                            bt = product.getString(ColumeBracket).toString() ;
//                            bl = product.getString(BracketLenth).toString() ;
//                            eage = product.getString(EstimatedAge).toString() ;
//                            lm = product.getString(LatenManfu).toString() ;
//
//                        }
//                    }
//                    catch (JSONException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//            catch (Exception e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result)
//        {
//
////            // Setting Student Name, Phone Number, Class into TextView after done all process .
////            NAME.setText(NameHolder);
////            PHONE_NUMBER.setText(NumberHolder);
////            CLASS.setText(ClassHolder);
//            ipaddress.setText(TempItem);
//            lat.setText(latt);
//            log.setText(logg);
//            cl1.setText(cl);
//            rs1.setText(rs);
//            cm1.setText(cm);
//            ct1.setText(ct);
//            chg1.setText(chg);
//            nd1.setText(nd);
//            dd1.setText(dd);
//            ft1.setText(ft);
//            bt1.setText(bt);
//            bl1.setText(bl);
//            eage1.setText(eage);
//            lm1.setText(lm);
//
//
//        }
//    }
//


   //     UpdateButton = (Button)findViewById(R.id.button2);
        //Receiving the ListView Clicked item value send by previous activity.
       // TempItem = getIntent().getStringExtra("ListViewValue");

        //Calling method to filter Student Record and open selected record.
   //     HttpWebCall(TempItem);

//
//        UpdateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(showsingleData.this,upDateDataBase.class);
//
//                // Sending Student Id, Name, Number and Class to next UpdateActivity.
////                intent.putExtra("Id", TempItem);
////                intent.putExtra("Name", NameHolder);
////                intent.putExtra("Number", NumberHolder);
////                intent.putExtra("Class", ClassHolder);
//                intent.putExtra("doubleValue_e1", TempItem );
//                intent.putExtra("doubleValue_e2", latt);
//                intent.putExtra("doubleValue_e3", logg);
//                startActivity(intent);
//
//                // Finishing current activity after opening next activity.
//                finish();
//
//            }
//        });
//




//
//    //Method to show current record Current Selected Record
//    public void HttpWebCall(final String PreviousListViewClickedItem){
//
//        class HttpWebCallFunction extends AsyncTask<String,Void,String> {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//                pDialog = ProgressDialog.show(showsingleData.this,"Loading Data",null,true,true);
//            }
//
//            @Override
//            protected void onPostExecute(String httpResponseMsg) {
//
//                super.onPostExecute(httpResponseMsg);
//
//                pDialog.dismiss();
//
//                //Storing Complete JSon Object into String Variable.
//                FinalJSonObject = httpResponseMsg ;
//
//                //Parsing the Stored JSOn String to GetHttpResponse Method.
//                new GetHttpResponse(showsingleData.this).execute();
//
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                ResultHash.put("ipaddress",params[0]);
//
//                ParseResult = httpParse.postRequest(ResultHash, URL_Get );
//
//                return ParseResult;
//            }
//        }
//
//        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();
//
//        httpWebCallFunction.execute(PreviousListViewClickedItem);
//    }
//
//
//    // Parsing Complete JSON Object.
//    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
//    {
//        public Context context;
//
//        public GetHttpResponse(Context context)
//        {
//            this.context = context;
//        }
//
//        @Override
//        protected void onPreExecute()
//        {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0)
//        {
//            try
//            {
//                if(FinalJSonObject != null)
//                {
//                    JSONArray jsonArray = null;
//
//                    try {
//                        jsonArray = new JSONArray(FinalJSonObject);
//
//                        JSONObject jsonObject;
//
//                        for(int i=0; i<jsonArray.length(); i++)
//                        {
//                            jsonObject = jsonArray.getJSONObject(i);
//
//
////                              ip=jsonObject.getString("ipaddress").toString();
//////                              latt=jsonObject.getString("latitude").toString();
//////                              logg=jsonObject.getString("longitude").toString();
//////                              cl = jsonObject.getString("Colume_Manfucture").toString();
//////                             rs = jsonObject.getString("Raise_and_Lower").toString();
//////                             cm = jsonObject.getString("Colume_Material").toString();
//////                             ct = jsonObject.getString("Colume_Type").toString();
//////                           chg = jsonObject.getString("column_height_from_ground").toString();
//////                           nd = jsonObject.getString("number_of_door").toString();
//////                             dd = jsonObject.getString("door_dimensions").toString();
//////                               ft = jsonObject.getString("foundation_type").toString();
//////                            bt = jsonObject.getString("bracket_type").toString();
//////                                  bl = jsonObject.getString("bracket_length").toString();
//////                           eage = jsonObject.getString("estimated_column_age").toString();
//////                             lm = jsonObject.getString("lantern_manufacture").toString();
//
//
////                            ip=jsonObject.getString("ipaddress");
//                            latt=jsonObject.getString("latitude");
//                            logg=jsonObject.getString("longitude");
//                            cl = jsonObject.getString("Colume_Manfucture");
//                            rs = jsonObject.getString("Raise_and_Lower");
//                            cm = jsonObject.getString("Colume_Material");
//                            ct = jsonObject.getString("Colume_Type");
//                            chg = jsonObject.getString("column_height_from_ground");
//                            nd = jsonObject.getString("number_of_door");
//                            dd = jsonObject.getString("door_dimensions");
//                            ft = jsonObject.getString("foundation_type");
//                            bt = jsonObject.getString("bracket_type");
//                            bl = jsonObject.getString("bracket_length");
//                            eage = jsonObject.getString("estimated_column_age");
//                            lm = jsonObject.getString("lantern_manufacture");
//                        }
//                    }
//                    catch (JSONException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//            catch (Exception e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result)
//        {
//
//
//
//
//        }
//    }
//
