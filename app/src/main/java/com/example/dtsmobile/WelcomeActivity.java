package com.example.dtsmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends AppCompatActivity {
    //Activity ini adlah splash screen activity
    private int time_load=3000;
    //3000= 3 detik


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //setelah loading maka langsung pindah ke home
                Intent home=new Intent(WelcomeActivity.this, home.class);
                startActivity(home);
                finish();
            }
        },time_load);


            }
    }

