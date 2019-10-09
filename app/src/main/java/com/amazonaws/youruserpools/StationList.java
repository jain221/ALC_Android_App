package com.amazonaws.youruserpools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StationList extends AppCompatActivity {
    public static final String ID1 = "id";
    public static final String Station11 = "StationName";
    public static final String CRS = "CRS_Code";
    private static final String URL_StationName = "https://mwxi4rs8y9.execute-api.eu-west-1.amazonaws.com/default/GetAvaliableStationName";
    public Context context;
    ArrayList<JSONObject> displayData = new ArrayList<JSONObject>();
    int id;

    private ArrayList<PlaceName>  StationName;


    ArrayList<String> list = new ArrayList<>();
    List<Integer> IdList = new ArrayList<Integer>();
    EditText theFilter;
    ListView StName;
    private ArrayAdapter adapter;
    String station;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        StationName= new ArrayList<PlaceName>();
        StName = (ListView) findViewById(R.id.listview1);
        // button show on map
        theFilter = (EditText) findViewById(R.id.searchFilter);
        getMarkers();



        StName.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub
                String TempListViewClickedValue = StationName.get(position).getStationName();
                String crs = StationName.get(position).getCRS_code();
                Intent intent = new Intent(StationList.this,StationLocation.class);

                // Sending ListView clicked value using intent.
                intent.putExtra("ListViewValue", TempListViewClickedValue);
                intent.putExtra("crs", crs);


                startActivity(intent);

                //Finishing current activity after open next activity.
                finish();

            }
        });
    }

    private void getMarkers() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_StationName,
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
                                PlaceName StationName1 = new PlaceName(product.getInt(ID1), product.getString(Station11), product.getString(CRS));
//                            String title = product.getString("CRS_code");
                                IdList.add(id);
                                StationName.add(StationName1);
                                Station();


                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StationList.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void Station() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < StationName.size(); i++) {
            lables.add(StationName.get(i).getStationName());
        }
        adapter = new ArrayAdapter<String>(StationList.this, android.R.layout.simple_list_item_1, lables);


        StName.setAdapter(adapter);

        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (StationList.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//
    }

}


