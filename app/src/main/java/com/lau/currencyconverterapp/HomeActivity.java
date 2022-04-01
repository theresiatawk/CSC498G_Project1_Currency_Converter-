package com.lau.currencyconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    Spinner spinner1, spinner2;
    EditText name, amount;
    Button convert;
    TextView result;
    int lira_rate = 25000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        // Referencing our texts and the button we have
        name = (EditText) findViewById(R.id.name);
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
        ArrayAdapter adapter2 = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);

        // Connecting spinners with the adapter
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            /*Once clicking on the convert button this method will be executed
             & it will convert either from LBP to USD or vice versa */
            public void onClick(View view) {
                Double answer;
                Double amount_entered = Double.parseDouble(amount.getText().toString());

                // Converting from USD to USD
                if (spinner1.getSelectedItem().toString().equals("USD") && spinner2.getSelectedItem().toString().equals("USD")){
                    answer = amount_entered;
                    result.setText(answer + "  $");
                }
                // Converting from USD to LBP
                else if (spinner1.getSelectedItem().toString().equals("USD") && spinner2.getSelectedItem().toString().equals("LBP")){
                    answer = (Double) amount_entered * lira_rate;
                    result.setText(answer + "  L.L.");
                }
                // Converting from LBP to USD
                else if (spinner1.getSelectedItem().toString().equals("LBP") && spinner2.getSelectedItem().toString().equals("LBP")){
                    answer = amount_entered;
                    result.setText(answer + "  L.L.");
                }
                // Converting from LBP to LBP
                else if (spinner1.getSelectedItem().toString().equals("LBP") && spinner2.getSelectedItem().toString().equals("USD")){
                    answer = (Double) amount_entered / lira_rate;
                    result.setText(answer + "  $");
                }
            }
        });

    }
}