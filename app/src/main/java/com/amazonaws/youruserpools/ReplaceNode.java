package com.amazonaws.youruserpools;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class ReplaceNode extends AppCompatActivity {

    HttpParse httpParse = new HttpParse();
    String URL_Get = "http://ec2-35-178-150-70.eu-west-2.compute.amazonaws.com/filter.php";
    //private static final String URL_Get = "https://o5yklvu3td.execute-api.eu-west-1.amazonaws.com/default/fetchData";
    private static final Object JSONObject = null;

    public static final String ID = "id";

    TextView ipaddress, lat, log;
    String ip,latt,logg;
    String e1, e2, e3;
    ProgressDialog pDialog;



    String TempItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_node);

        TempItem = getIntent().getStringExtra("doubleValue_e1");
        latt = getIntent().getStringExtra("doubleValue_e2");
        logg = getIntent().getStringExtra("doubleValue_e3");
//

        ipaddress = (TextView) findViewById(R.id.t1);
        lat = (TextView) findViewById(R.id.t2);
        log = (TextView) findViewById(R.id.t3);


        ipaddress.setText(TempItem);
        lat.setText(latt);
        log.setText(logg);



    }
}
