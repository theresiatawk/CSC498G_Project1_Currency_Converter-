package com.lau.currencyconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

    }

}