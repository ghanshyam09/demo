package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splash extends AppCompatActivity {
    private  static  int splash_Time_out=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String st = check();
                if (st.equals("off")) {
                    Intent intent = new Intent(splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(splash.this, FourActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },splash_Time_out);

    }
    public  String check() {
        SharedPreferences prefer = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String status=prefer.getString("login_Status","off");
        return  status;
    }

}
