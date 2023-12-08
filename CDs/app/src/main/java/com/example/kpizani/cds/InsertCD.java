package com.example.kpizani.cds;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertCD extends AppCompatActivity {
    EditText title;
    EditText artist;
    EditText rating;
    Button submit;
    Bundle myBundle;
    Intent localIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_cd);
        title = (EditText) findViewById(R.id.title);
        artist = (EditText) findViewById(R.id.artist);
        rating = (EditText) findViewById(R.id.rating);
        submit = (Button) findViewById(R.id.sub);
        //submit.setOnClickListener(this);

        localIntent = getIntent();
        myBundle =  localIntent.getExtras();



    }


    public void onClick(View v) {
        try {
            int n = Integer.parseInt(rating.getText().toString());
            if (!title.getText().toString().equals("") && !artist.getText().toString().equals("") && !rating.getText().toString().equals("") && n >= 1 && n <=5) {
                myBundle.putString("title", title.getText().toString());
                myBundle.putString("artist", artist.getText().toString());
                myBundle.putString("rating", rating.getText().toString());
                //Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                localIntent.putExtras(myBundle);

                // return sending an OK signal to calling activity
                setResult(Activity.RESULT_OK, localIntent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "You didn't fill something out correctly", Toast.LENGTH_SHORT).show();
            }
        }
        catch(NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "You didn't put a number between 1 and 5 as a rating", Toast.LENGTH_SHORT).show();
        }
    }
}
