package com.example.zainabishaqmusa.postemoji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

public class Categories extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getutilliyy();
    }

    public void  getutilliyy(){

        ImageView emotion= (ImageView) findViewById(R.id.emotion);
        emotion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Categories.this, Emotion.class));
                finish();
            }
        });

        ImageView food = (ImageView) findViewById(R.id.food);
        food.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Categories.this, Food.class));
                finish();
            }
        });

        ImageView religion = (ImageView) findViewById(R.id.religion);
        religion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Categories.this, Religion.class));
                finish();
            }
        });

        ImageView gesture = (ImageView) findViewById(R.id.gesture);
        gesture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Categories.this, Gesture.class));
                finish();
            }
        });

        ImageView sport = (ImageView) findViewById(R.id.sport);
        sport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               startActivity(new Intent(Categories.this, Sport.class));
               finish();
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(Categories.this, Setting.class));
            finish();
        }
        return false;
    }

}