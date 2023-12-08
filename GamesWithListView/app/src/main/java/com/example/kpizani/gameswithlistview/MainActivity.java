package com.example.kpizani.gameswithlistview;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] games = new String[]{"Hangman", "Tic Tac Toe"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list);


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, games);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                eval(pos);
            }
        });
    }


    private void eval(int index) {
        String text = games[index];
        if(text.equals("Hangman")) {
            Intent intent = new Intent(this, Hangman.class);
            startActivity(intent);
        }
        else if(text.equals("Tic Tac Toe")) {
            Intent intent = new Intent(this, TicTacToe.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Where did you even click?", Toast.LENGTH_SHORT).show();
        }
    }


}
