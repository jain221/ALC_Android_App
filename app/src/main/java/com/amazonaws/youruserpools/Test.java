package com.amazonaws.youruserpools;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent i = getIntent();
        ArrayList<Integer> fromApics = i.getIntegerArrayListExtra("arr");
        for (int y = 0; y < fromApics.size(); y++) {
            System.out.println(NodeMapSingleData.arrPath[fromApics.get(y)].toString());
        }
    }
}
