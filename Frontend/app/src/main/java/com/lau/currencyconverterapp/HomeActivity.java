package com.lau.currencyconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class HomeActivity extends AppCompatActivity {

    Spinner spinner1, spinner2;
    EditText amount;
    Button convert;
    TextView result;
    String currency;
    int lira_rate;
    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {

            URL url;
            HttpURLConnection http;
            String urlParameters;

            try{
                url = new URL(urls[0]);
                urlParameters = "currency_amount=lira_rate&amount_to_be_converted=amount_entered&currency=currency";

                // Opening a connection between android app and the url
                http = (HttpURLConnection) url.openConnection();

                http.setRequestMethod("POST");
                http.setDoOutput(true);

                // I need an Input Stream to write the output to the API
                OutputStream out = http.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(out);

                // Cursor that will read the output of the api
                writer.write(urlParameters);

                // The data cursor did not reach the end of the file repeat

            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String s){
            Log.i("Result", s);

            super.onPostExecute(s);

            try{
                // Convert the string that we have to a json object
                JSONObject json = new JSONObject(s);
                String rate = json.getString("Rate");
                Log.i("Final rate", rate);

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent x = getIntent();
        lira_rate = x.getIntExtra("lira_rate", 0);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        // Referencing our texts and the button we have
        amount = (EditText) findViewById(R.id.amount);
        convert = (Button) findViewById(R.id.convert);
        result = (TextView) findViewById(R.id.result);


        // Referencing the spinners we have
        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        // The items we need for the spinner
        String[] items = {"USD", "LBP"};

        // Fills our spinners with the array we just created
        ArrayAdapter adapter1 = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);

        // Connecting spinners with the adapter
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter1);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            /*Once clicking on the convert button this method will be executed
             & it will convert either from LBP to USD or vice versa */
            public void onClick(View view) {
                Double answer;
                // Getting the object amount and converting it to string with removing spaces
                String value_entered = amount.getText().toString().replaceAll(" ","");

                // In case no value was entered by the user
                if (value_entered.length() == 0) {
                    String message = "Error: You should fill the amount to convert!";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                else {
                    // Try catch method in case the user has entered a wrong number format
                    try {
                        Double amount_entered = Double.parseDouble(value_entered);
                        currency = spinner1.getSelectedItem().toString();
                        // Converting from USD to USD
                        if (spinner1.getSelectedItem().toString().equals("USD") && spinner2.getSelectedItem().toString().equals("USD")) {
                            answer = amount_entered;
                            result.setText(answer + "  $");
                        }
                        // Converting from USD to LBP
                        else if (spinner1.getSelectedItem().toString().equals("USD") && spinner2.getSelectedItem().toString().equals("LBP")) {
                            answer = (Double) amount_entered * lira_rate;
                            result.setText(answer + "  L.L");
                        }
                        // Converting from LBP to USD
                        else if (spinner1.getSelectedItem().toString().equals("LBP") && spinner2.getSelectedItem().toString().equals("LBP")) {
                            answer = amount_entered;
                            result.setText(answer + "  L.L");
                        }
                        // Converting from LBP to LBP
                        else if (spinner1.getSelectedItem().toString().equals("LBP") && spinner2.getSelectedItem().toString().equals("USD")) {
                            answer = (Double) amount_entered / lira_rate;
                            result.setText(answer + "  $");
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Error: The format is incorrect. Please enter a correct number", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}