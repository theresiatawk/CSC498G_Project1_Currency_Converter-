package com.lau.currencyconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class HomeActivity extends AppCompatActivity {

    Spinner spinner1, spinner2;
    EditText amount;
    Button convert;
    TextView result;
    String currency;
    int lira_rate;

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
//                        }
//                        URL url_post = new URL("http://localhost/CSC498G_Project1_Currency_Converter-/backend/post.php");
//
//                        HttpURLConnection connection = (HttpURLConnection)url_post.openConnection();
//
//                        connection.setRequestMethod("POST");
//                        connection.setRequestProperty("Content-Type", "application/json; utf-8");
//                        connection.setRequestProperty("Accept", "application/json");
//                        connection.setDoOutput(true);
//                        String jsonInputString = "{\"currency_rate\": lira_rate, \"amount_to_be_converted\": amount_entered, \"currency\":currency}";
//                        try(OutputStream os = connection.getOutputStream()) {
//                            byte[] input = jsonInputString.getBytes("utf-8");
//                            os.write(input, 0, input.length);
                            String urlParameters  = "currency_amount=lira_rate&amount_to_be_converted=amount_entered&currency=currency";
                            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
                            int    postDataLength = postData.length;
                            String request        = "http://localhost/CSC498G_Project1_Currency_Converter-/backend/post.php";
                            URL    url            = new URL( request );
                            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                            conn.setDoOutput( true );
                            conn.setInstanceFollowRedirects( false );
                            conn.setRequestMethod( "POST" );
                            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                            conn.setRequestProperty( "charset", "utf-8");
                            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                            conn.setUseCaches( false );
                            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                                wr.write( postData );
                            }
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Error: The format is incorrect. Please enter a correct number", Toast.LENGTH_LONG).show();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}