package com.example.zainabishaqmusa.postemoji;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    public static String Name;
    public static String Domine_name;

    CheckBox hangout, whattsapp, lineChat, viber, tango, wechat, all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        hangout = (CheckBox) findViewById(R.id.hangout);
        hangout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validation();
            }
        });

        whattsapp = (CheckBox) findViewById(R.id.whattsapp);
        whattsapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validation();
            }
        });

        lineChat = (CheckBox) findViewById(R.id.line_chart);
        lineChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validation();
            }
        });

        viber = (CheckBox) findViewById(R.id.viber);
        viber.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validation();
            }
        });

        tango = (CheckBox) findViewById(R.id.tango);
        tango.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validation();
            }
        });

        wechat = (CheckBox) findViewById(R.id.wechat);
        wechat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validation();
            }
        });

        all = (CheckBox) findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validation();
            }
        });


        ImageView ok = (ImageView) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (go()) {
                    startActivity(new Intent(Setting.this, Categories.class));
                    finish();
                }
            }
        });
    }

    protected boolean validation() {

        if (hangout.isChecked()) {
            checkone(" ", " ", whattsapp, lineChat, viber, tango, wechat, all);
        } else if (whattsapp.isChecked()) {

            checkone(" ", " ", hangout, lineChat, viber, tango, wechat, all);

        } else if (lineChat.isChecked()) {

            checkone(" ", " ", whattsapp, hangout, viber, tango, wechat, all);

        } else if (viber.isChecked()) {

            checkone(" ", " ", whattsapp, lineChat, hangout, tango, wechat, all);

        } else if (tango.isChecked()) {

            checkone(" ", " ", whattsapp, lineChat, viber, hangout, wechat, all);

        } else if (wechat.isChecked()) {

            checkone(" ", " ", whattsapp, lineChat, viber, tango, hangout, all);

        } else if (all.isChecked()) {
            Name = "all";
            whattsapp.setChecked(true);
            lineChat.setChecked(true);
            viber.setChecked(true);
            tango.setChecked(true);
            wechat.setChecked(true);
            hangout.setChecked(true);
        } else {

            return false;
        }
        return true;
    }

    public  boolean go() {
        if (validation()){
            return true;
        } else {
            Toast.makeText(Setting.this.getApplicationContext(), "Kindly select an option above  or select All to Automatically check all ", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public String[] checkone(String name, String domina, CheckBox a1, CheckBox a2, CheckBox a3, CheckBox a4, CheckBox a5, CheckBox a6) {
        Name = name;
        Domine_name = domina;
        a1.setChecked(false);
        a2.setChecked(false);
        a3.setChecked(false);
        a4.setChecked(false);
        a5.setChecked(false);
        a6.setChecked(false);

        return new String[]{Name, Domine_name};
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage("Do you really want to quit?")
                    .setIcon(R.drawable.alert)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.finishAffinity(Setting.this);
                            finish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })

                    .show();
        }
        return false;
    }

}
