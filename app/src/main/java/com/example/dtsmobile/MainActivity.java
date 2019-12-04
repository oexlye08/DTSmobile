package com.example.dtsmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /***membuat variable ***/
    public TextView register;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /***Intent intent ke register activity***/
        login_button=findViewById(R.id.login_button);

            final Context context=this;
            login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, home.class);
                startActivity(intent);
            }
        });

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /**STEP A
                 *
                 *    "i" adalah variable Intent
                 *    kemudian kita akan memanggil method Intent dengan perintah New Intent()
                 *    memasukan parameter Intent() dengan MainActivity kemudian ActivityTujuan
                 */

                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                /**STEP B
                 *
                 *    memulai Aktifitas perintah yang dibuat di bagian A
                 *    yaitu "i"
                 *    dengan method startActivity()
                 */

                startActivity(i);
            }
        });
    }



}