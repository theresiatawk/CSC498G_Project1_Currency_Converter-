package com.lau.currencyconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner1, spinner2;
    EditText name, amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hiding the Action Bar from the layout
        getSupportActionBar().hide();

        // Referencing our plain texts we have
        name = (EditText) findViewById(R.id.name);
        amount = (EditText) findViewById(R.id.amount);

        // Referencing the spinners we have
        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        // Fills our spinners with the array we just created in strings.xml file
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.strings, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Connecting spinners with the adapter
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        // To be able to click on one of the items of spinners
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String from = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),from, Toast.LENGTH_LONG ).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}