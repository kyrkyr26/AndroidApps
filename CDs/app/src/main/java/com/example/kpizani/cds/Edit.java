package com.example.kpizani.cds;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Edit extends AppCompatActivity {

    EditText album;
    EditText artist;
    EditText rating;
    Intent localIntent;
    Bundle myBundle;
    String a;
    String ar;
    String r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        album = (EditText) findViewById(R.id.et1);
        artist = (EditText) findViewById(R.id.et2);
        rating = (EditText) findViewById(R.id.et3);

        localIntent = getIntent();
        myBundle = localIntent.getExtras();
        a = myBundle.getString("album");
        ar = myBundle.getString("artist");
        r = myBundle.getString("rating");

        album.setText(a);
        artist.setText(ar);
        rating.setText(r);
    }


    public void onClick(View v){
        if(!album.getText().toString().equals(a) || !artist.getText().toString().equals(ar) || !rating.getText().toString().equals(r)){
            myBundle.putString("nalbum", album.getText().toString());
            myBundle.putString("nartist", artist.getText().toString());
            myBundle.putString("nrating", rating.getText().toString());
            localIntent.putExtras(myBundle);
            setResult(Activity.RESULT_OK, localIntent);
            finish();
        }
        else{
            finish();
        }
    }
}
