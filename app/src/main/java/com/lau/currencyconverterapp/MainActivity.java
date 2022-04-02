package com.lau.currencyconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    Timer timer;
    // This class is implemented to run functions on the background of our applictaion
    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection http;
            
            return null;
        }
        protected void onPostExecute(String s){

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        // After 2 seconds it will move automatically from this activity to the home activity (Splash Screen)
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);


    }
}