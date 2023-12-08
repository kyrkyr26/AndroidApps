package com.example.kpizani.hangman;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    GridLayout gridL;
    EditText editText;
    Button buttonA;
    Button ButtonB;
    Button ButtonC;
    String w;
    StringBuilder stars = new StringBuilder("");
    int count = 0;
    int lenS;
    List<String> words = new ArrayList<String>();
    List<Button> clickedButtons = new ArrayList<Button>();
    List<Button> allButtons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout gridL = (GridLayout) findViewById(R.id.myGrid);
        editText = (EditText) findViewById(R.id.editText);
        for(int i=0; i<gridL.getChildCount(); i++) {
            Button child = (Button)gridL.getChildAt(i);
            allButtons.add(child);
        }
        Button res = (Button) findViewById(R.id.buttonReset);
        allButtons.remove(res);

        w = generateWord();
        lenS = w.length();
        for(char x : w.toCharArray()){
            stars = stars.append("*");
        }
        editText.setText(stars + " " + w.length() + " Characters");

    }




    public void onClick(View v){
        count += 1;
        Button b = (Button)v;
        String buttonText = b.getText().toString().toLowerCase();
        //Toast.makeText(this, buttonText, Toast.LENGTH_SHORT).show();
        if (buttonText.equals("reset")){
            for(Button x: allButtons){
                x.setEnabled(true);
                x.setVisibility(View.VISIBLE);
            }
            stars = new StringBuilder("");
            w = generateWord();
            lenS = w.length() + 5;
            count = 0;
            words.clear();
            for(char x : w.toCharArray()){
                stars = stars.append("*");
            }
            editText.setText(stars + " " + w.length() + " Characters");
        }
        if(w.contains(buttonText) && !stars.toString().contains(buttonText) && !buttonText.equals("reset")){
            clickedButtons.add(b);
            for(int i = 0; i < w.length(); i++){
                if(w.charAt(i) == buttonText.charAt(0)){
                    stars.setCharAt(i,buttonText.charAt(0));
                }
            }
            words.add(buttonText);
            editText.setText(stars + " used " + count + " of " + lenS + " guesses");
            b.setVisibility(View.INVISIBLE);
        }
        else if(!w.contains(buttonText) && !buttonText.equals("reset")){
            clickedButtons.add(b);
            //Toast.makeText(this, " not in word ", Toast.LENGTH_SHORT).show();
            words.add(buttonText);
            editText.setText(stars + " used " + count + " of " + lenS + " guesses");
            b.setVisibility(View.INVISIBLE);
        }
       /* else if(stars.toString().contains(buttonText) || words.contains(buttonText)){
            count = count - 1;
            editText.setText(stars + " used " + count + " of " + lenS + " guesses cuz already guessed");
            //Toast.makeText(this, "already guessed that", Toast.LENGTH_SHORT).show();
        }*/
        if(lenS == count && stars.toString().contains("*") && !buttonText.equals("reset")){
            clickedButtons.add(b);
            editText.setText("You lose. The word was " + w);
            for(Button x: allButtons){
                x.setEnabled(false);
                x.setVisibility(View.INVISIBLE);
            }
        }

        if(!stars.toString().contains("*") && count == lenS && !buttonText.equals("reset")){
            clickedButtons.add(b);
            editText.setText("The word was " + w + ". You win! You used " + count + " guesses.");
            for(Button x: allButtons){
                x.setEnabled(false);
                x.setVisibility(View.INVISIBLE);
            }
        }

        if(!stars.toString().contains("*") && count < lenS && !buttonText.equals("reset")){
            clickedButtons.add(b);
            editText.setText("The word was " + w + ". You win! You used " + count + " guesses.");
            for(Button x: allButtons){
                x.setEnabled(false);
                x.setVisibility(View.INVISIBLE);
            }
        }


    }

    private String generateWord(){
        String [] words = {"handler","against","horizon","chops","junkyard","amoeba","academy","roast",
                "countryside","children","strange","best","drumbeat","amnesiac","chant","amphibian","smuggler","fetish"};
        Random r = new Random();
        int index = r.nextInt(words.length);
        return words[index];
    }
}

