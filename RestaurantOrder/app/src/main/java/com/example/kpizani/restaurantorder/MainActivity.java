package com.example.kpizani.restaurantorder;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText app;
    EditText pasta;
    EditText meat;
    EditText dessert;
    Button button1, button2, button3, button4, button5, button6;
    String v1;
    String appC;
    String pastaC;
    String meatC;
    String dessC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (EditText) findViewById(R.id.app);
        pasta = (EditText) findViewById(R.id.pasta);
        meat = (EditText) findViewById(R.id.meat);
        dessert = (EditText) findViewById(R.id.dess);
        name = (EditText) findViewById(R.id.name);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);

        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // get values from the UI
                v1 = app.getText().toString();
                // create intent to call Activity2
                Intent IntentApp = new Intent(MainActivity.this,
                        Apps.class);
                // create a container to ship data
                Bundle myData = new Bundle();
                // add <key,value> data items to the container
                myData.putString("app", v1);
                // attach the container to the intent
                IntentApp.putExtras(myData);
                // call Activity2, tell your local listener to wait response
                startActivityForResult(IntentApp, 102);
            }

        });

        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // get values from the UI
                v1 = pasta.getText().toString();
                // create intent to call Activity2
                Intent IntentApp = new Intent(MainActivity.this,
                        Pasta.class);
                // create a container to ship data
                Bundle myData = new Bundle();
                // add <key,value> data items to the container
                myData.putString("app", v1);
                // attach the container to the intent
                IntentApp.putExtras(myData);
                // call Activity2, tell your local listener to wait response
                startActivityForResult(IntentApp, 103);
            }

        });

        button3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // get values from the UI
                v1 = meat.getText().toString();
                // create intent to call Activity2
                Intent IntentApp = new Intent(MainActivity.this,
                        Meat.class);
                // create a container to ship data
                Bundle myData = new Bundle();
                // add <key,value> data items to the container
                myData.putString("app", v1);
                // attach the container to the intent
                IntentApp.putExtras(myData);
                // call Activity2, tell your local listener to wait response
                startActivityForResult(IntentApp, 104);
            }

        });

        button4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // get values from the UI
                v1 = dessert.getText().toString();
                // create intent to call Activity2
                Intent IntentApp = new Intent(MainActivity.this,
                        Dessert.class);
                // create a container to ship data
                Bundle myData = new Bundle();
                // add <key,value> data items to the container
                myData.putString("app", v1);
                // attach the container to the intent
                IntentApp.putExtras(myData);
                // call Activity2, tell your local listener to wait response
                startActivityForResult(IntentApp, 105);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if ((requestCode == 102) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults = data.getExtras();
                String vresult = myResults.getString("vresult");
                appC = vresult;
                app.setText("Appetizer: " + vresult);
            }
            if ((requestCode == 103) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults = data.getExtras();
                String vresult = myResults.getString("vresult");
                pastaC = vresult;
                pasta.setText("Pasta: " + vresult);
            }
            if ((requestCode == 104) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults = data.getExtras();
                String vresult = myResults.getString("vresult");
                meatC = vresult;
                meat.setText("Meat/Fish: " + vresult);
            }
            if ((requestCode == 105) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults = data.getExtras();
                String vresult = myResults.getString("vresult");
                dessC = vresult;
                dessert.setText("Dessert: " + vresult);
            }
        } catch (Exception e) {
            app.setText("Problems - " + requestCode + " " + resultCode);
        }
    }//onActivityResult

    public void clear(View v) {
        app.setText("Appetizer: ");
        pasta.setText("Pasta: ");
        meat.setText("Meat/Fish: ");
        dessert.setText("Dessert: ");
    }

    public void confirm(View v) {
        if (name.getText().toString().equals("Please Enter Name: ")){
            Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
        }
        else if (app.getText().toString().equals("Appetizer:") || pasta.getText().toString().equals("Pasta:") || meat.getText().toString().equals("Meat/Fish:") || dessert.getText().toString().equals("Dessert:")) {
            Toast.makeText(getApplicationContext(), "Please Finish Selections", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(MainActivity.this, Finished.class);
            Bundle myData = new Bundle();
            // add <key,value> data items to the container
            myData.putString("appC", app.getText().toString());
            myData.putString("pastaC", pasta.getText().toString());
            myData.putString("meatC", meat.getText().toString());
            myData.putString("dessertC", dessert.getText().toString());
            myData.putString("nameC", name.getText().toString());
            // attach the container to the intent
            intent.putExtras(myData);
            // call Activity2, tell your local listener to wait response
            startActivityForResult(intent, 106);
        }
    }
}