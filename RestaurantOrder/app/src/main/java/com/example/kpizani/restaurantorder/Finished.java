package com.example.kpizani.restaurantorder;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class Finished extends AppCompatActivity {
    EditText name;
    EditText app;
    EditText pasta;
    EditText meat;
    EditText dessert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
        Toast.makeText(getApplicationContext(), "Order sent!!", Toast.LENGTH_SHORT).show();

        name = (EditText) findViewById(R.id.nameC);
        app = (EditText) findViewById(R.id.appC);
        pasta = (EditText) findViewById(R.id.pastaC);
        meat = (EditText) findViewById(R.id.meatC);
        dessert = (EditText) findViewById(R.id.dessertC);

        Intent myLocalIntent = getIntent();

        // look into the bundle sent to Activity2 for data items
        Bundle myBundle =  myLocalIntent.getExtras();
        String myApp = myBundle.getString("appC");
        String myPasta = myBundle.getString("pastaC");
        String myMeat = myBundle.getString("meatC");
        String myDess = myBundle.getString("dessertC");
        String myName = myBundle.getString("nameC");
        myName = myName.replace("Please Enter Name: ", "Customer: ");

        name.setText(myName);
        app.setText(myApp);
        pasta.setText(myPasta);
        meat.setText(myMeat);
        dessert.setText(myDess);
        setResult(Activity.RESULT_OK, myLocalIntent);
    }
}
