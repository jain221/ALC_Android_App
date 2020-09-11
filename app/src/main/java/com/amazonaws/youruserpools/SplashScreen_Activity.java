package com.amazonaws.youruserpools;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen_Activity extends AppCompatActivity {

    Handler handler;

    //    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SystemClock.sleep(1000);
//        Intent intent = new Intent(this, Login_Page.class);
//        startActivity(intent);
//        finish();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);

    }
}