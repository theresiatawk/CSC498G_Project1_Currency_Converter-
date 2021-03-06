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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;


public class HomeActivity extends AppCompatActivity {

    Spinner spinner1, spinner2;
    EditText amount;
    Button convert;
    TextView result, rate_result;
    String currency;
    int lira_rate;

    // Implementing the post request using this class
    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String first_param = params[0];
            String second_param= params[1];
            String third_param= params[2];

            URL url;
            HttpURLConnection http;

            try{
                url = new URL(params[3]);

                // Opening a connection between android app and the url
                http = (HttpURLConnection) url.openConnection();

                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                // I need an Output Stream to sent params to the API
                OutputStream out_stream = http.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out_stream, "UTF-8"));

                String post1 = URLEncoder.encode("currency_rate", "UTF-8")+"="+ URLEncoder.encode(first_param, "UTF-8")+"&"+URLEncoder.encode("amount_to_be_converted", "UTF-8")+"="+ URLEncoder.encode(second_param, "UTF-8") +"&"+ URLEncoder.encode("currency", "UTF-8")+"="+ URLEncoder.encode(third_param, "UTF-8");
                bw.write(post1);
                bw.flush();
                bw.close();
                out_stream.close();

                InputStream in_stream = http.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in_stream, "iso-8859-1"));
                String result = "";
                String line = "";
                while((line = br.readLine())!= null){
                    result += line;
                }
                br.close();
                in_stream.close();
                http.disconnect();
                return result;
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(String s){
            super.onPostExecute(s);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent x = getIntent();
        lira_rate = x.getIntExtra("lira_rate", 0);
        String rate = lira_rate + "";

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        // Referencing our texts and the button we have
        amount = (EditText) findViewById(R.id.amount);
        convert = (Button) findViewById(R.id.convert);
        result = (TextView) findViewById(R.id.result);
        rate_result = (TextView) findViewById(R.id.textViewRate);



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
                String value_entered = amount.getText().toString().replaceAll(" ", "");

                String url = "http://192.168.106.1/CSC498G_Project1_Currency_Converter-/backend/post.php";
                DownloadTask task = new DownloadTask();

                // In case no value was entered by the user
                if (value_entered.length() == 0) {
                    String message = "Error: You should fill the amount to convert!";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    // Try catch method in case the user has entered a wrong number format
                    try {
                        Double amount_entered = Double.parseDouble(value_entered);
                        currency = spinner1.getSelectedItem().toString();
                        // Converting from USD to USD
                        if (spinner1.getSelectedItem().toString().equals("USD") && spinner2.getSelectedItem().toString().equals("USD")) {
                            answer = amount_entered;
                            result.setText(answer + "  $");
                            rate_result.setText("LIRA RATE is "+lira_rate+ "L.L");
                        }
                        // Converting from USD to LBP
                        else if (spinner1.getSelectedItem().toString().equals("USD") && spinner2.getSelectedItem().toString().equals("LBP")) {
                            answer = (Double) amount_entered * lira_rate;
                            result.setText((new DecimalFormat("##.####").format(answer)) + "  L.L");
                            rate_result.setText("LIRA RATE is "+lira_rate+ "L.L");
                            task.execute(rate, value_entered,currency,url);
                        }
                        // Converting from LBP to LBP
                        else if (spinner1.getSelectedItem().toString().equals("LBP") && spinner2.getSelectedItem().toString().equals("LBP")) {
                            answer = amount_entered;
                            result.setText(answer + "  L.L");
                            rate_result.setText("LIRA RATE is "+lira_rate+ "L.L");
                        }
                        // Converting from LBP to USD
                        else if (spinner1.getSelectedItem().toString().equals("LBP") && spinner2.getSelectedItem().toString().equals("USD")) {
                            answer = (Double) amount_entered / lira_rate;
                            result.setText((new DecimalFormat("##.####").format(answer)) + "  $");
                            task.execute(rate, value_entered,currency,url);
                            rate_result.setText("LIRA RATE is "+lira_rate+ "L.L");
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Error: The format is incorrect. Please enter a correct number", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
