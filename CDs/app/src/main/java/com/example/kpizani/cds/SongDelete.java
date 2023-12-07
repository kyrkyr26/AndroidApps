package com.example.kpizani.cds;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SongDelete extends AppCompatActivity {
    EditText title;
    Button submit;
    Bundle myBundle;
    Intent localIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_delete);

        title = (EditText) findViewById(R.id.et);
        submit = (Button) findViewById(R.id.b);

        localIntent = getIntent();
        myBundle = localIntent.getExtras();
    }

    public void onClick(View v) {
        if (!title.getText().toString().equals("")) {
            myBundle.putString("song", title.getText().toString());
            localIntent.putExtras(myBundle);
            setResult(Activity.RESULT_OK, localIntent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "You didn't fill something out correctly", Toast.LENGTH_SHORT).show();
        }
    }
}
