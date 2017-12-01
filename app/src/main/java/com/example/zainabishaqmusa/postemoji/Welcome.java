package com.example.zainabishaqmusa.postemoji;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {


    private static final int SPLASH_DURATION = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(Welcome.this, Setting.class));
                finish();
            }
        }, SPLASH_DURATION * 500);
    }

}
