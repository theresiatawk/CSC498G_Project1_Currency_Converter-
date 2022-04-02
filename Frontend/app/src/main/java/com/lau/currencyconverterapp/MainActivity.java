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

            try{
                url = new URL(urls[0]);

                // Opening a connection between android app and the url
                http = (HttpURLConnection) url.openConnection();

                // I need an Input Stream to read the output of the API
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                // Cursor that will read the output of the api
                int data = reader.read();

                // The data cursor did not reach the end of the file repeat
                while(data != -1){
                    char current = (char) data;
                    //Appending the current to final String Result
                    result += current;
                    // Move the cursor one character
                    data = reader.read();
                }
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
            return result;
        }
        protected void onPostExecute(String s){
            Log.i("Result", s);

            super.onPostExecute(s);

            try{
                // Convert the string that we have to a json object
                JSONObject json = new JSONObject(s);
                String rate = json.getString("Rate");
                Log.i("Final rate", rate);
                Integer final_rate = Integer.parseInt(rate);

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://192.168.106.1/CSC498G_Project1_Currency_Converter-/backend/get.php";
        DownloadTask task = new DownloadTask();
       task.execute(url);

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