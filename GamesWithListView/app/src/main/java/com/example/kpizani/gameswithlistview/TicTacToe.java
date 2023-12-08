package com.example.kpizani.gameswithlistview;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToe extends AppCompatActivity {
    List<Button> unclickedButtons = new ArrayList<Button>();
    List<Button> allButtons = new ArrayList<Button>();
    ArrayList<Button> firstRow = new ArrayList<Button>();
    ArrayList<Button> secondRow = new ArrayList<Button>();
    ArrayList<Button> thirdRow = new ArrayList<Button>();
    List<ArrayList<Button>> C = new ArrayList<ArrayList<Button>>();
    int myScore = 0;
    int compScore = 0;
    int ties = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        Button again = (Button) findViewById(R.id.again);
        LinearLayout lin1 = (LinearLayout) findViewById(R.id.lin1);
        LinearLayout lin2 = (LinearLayout) findViewById(R.id.lin2);
        LinearLayout lin3 = (LinearLayout) findViewById(R.id.lin3);

        for(int i=0; i<lin1.getChildCount(); i++) {
            if (lin1.getChildAt(i) instanceof Button) {
                Button o = (Button) lin1.getChildAt(i);
                allButtons.add(o);
                unclickedButtons.add(o);
                firstRow.add(o);
            }
        }
        for(int i=0; i<lin2.getChildCount(); i++) {
            if (lin2.getChildAt(i) instanceof Button) {
                Button o = (Button) lin2.getChildAt(i);
                allButtons.add(o);
                unclickedButtons.add(o);
                secondRow.add(o);
            }
        }
        for(int i=0; i<lin3.getChildCount(); i++) {
            if (lin3.getChildAt(i) instanceof Button) {
                Button o = (Button) lin3.getChildAt(i);
                allButtons.add(o);
                unclickedButtons.add(o);
                thirdRow.add(o);
            }
        }
        C.add(firstRow);
        C.add(secondRow);
        C.add(thirdRow);
    }

    public void onClick(View v){
        Button b = (Button) v;
        unclickedButtons.remove(b);
        String buttonText = b.getText().toString();
        if(buttonText.equals("Again?")){
            for(Button y:allButtons){
                y.setText("");
                y.setEnabled(true);
                final TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText("Click Any Button To Start");
                unclickedButtons = new ArrayList<Button>(allButtons);
            }
        }
        else if(!buttonText.equals("X") && !buttonText.equals("O")){
            b.setText("O");
            boolean c = checkWin();
            if(c == true){
                for(Button bb:allButtons){
                    bb.setEnabled(false);
                }
            }
            else if(unclickedButtons.isEmpty()){
                final TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText("It's a Draw");
                ties += 1;
                TextView scoreView = (TextView) findViewById(R.id.scoreView);
                scoreView.setText("Wins: " + Integer.toString(myScore) + " Losses: " + Integer.toString(compScore) + " Ties: " + Integer.toString(ties));
            }

            else{
                compMoves(v);
                boolean cc = checkLoss();
                if(cc == true){
                    for(Button lb:allButtons){
                        lb.setEnabled(false);
                    }
                }
            }
        }
    }


    public void compMoves(View v){
        Random randomizer = new Random();
        Button random = unclickedButtons.get(randomizer.nextInt(unclickedButtons.size()));
        unclickedButtons.remove(random);
        random.setText("X");
    }

    public boolean checkWin() {
        if(C.get(0).get(0).getText().toString().equals("O") && C.get(0).get(1).getText().toString().equals("O") && C.get(0).get(2).getText().toString().equals("O")
                || C.get(1).get(0).getText().toString().equals("O") && C.get(1).get(1).getText().toString().equals("O") && C.get(1).get(2).getText().toString().equals("O")
                || C.get(2).get(0).getText().toString().equals("O") && C.get(2).get(1).getText().toString().equals("O") && C.get(2).get(2).getText().toString().equals("O")
                || C.get(0).get(0).getText().toString().equals("O") && C.get(1).get(0).getText().toString().equals("O") && C.get(2).get(0).getText().toString().equals("O")
                || C.get(0).get(1).getText().toString().equals("O") && C.get(1).get(1).getText().toString().equals("O") && C.get(2).get(1).getText().toString().equals("O")
                || C.get(0).get(2).getText().toString().equals("O") && C.get(1).get(2).getText().toString().equals("O") && C.get(2).get(2).getText().toString().equals("O")
                || C.get(0).get(0).getText().toString().equals("O") && C.get(1).get(1).getText().toString().equals("O") && C.get(2).get(2).getText().toString().equals("O")
                || C.get(0).get(2).getText().toString().equals("O") && C.get(1).get(1).getText().toString().equals("O") && C.get(2).get(0).getText().toString().equals("O")){
            final TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText("You Win!");
            myScore += 1;
            TextView scoreView = (TextView) findViewById(R.id.scoreView);
            scoreView.setText("Wins: " + Integer.toString(myScore) + " Losses: " + Integer.toString(compScore) + " Ties: " + Integer.toString(ties));
            return true;
        }
        return false;
    }

    public boolean checkLoss() {
        if(C.get(0).get(0).getText().toString().equals("X") && C.get(0).get(1).getText().toString().equals("X") && C.get(0).get(2).getText().toString().equals("X")
                || C.get(1).get(0).getText().toString().equals("X") && C.get(1).get(1).getText().toString().equals("X") && C.get(1).get(2).getText().toString().equals("X")
                || C.get(2).get(0).getText().toString().equals("X") && C.get(2).get(1).getText().toString().equals("X") && C.get(2).get(2).getText().toString().equals("X")
                || C.get(0).get(0).getText().toString().equals("X") && C.get(1).get(0).getText().toString().equals("X") && C.get(2).get(0).getText().toString().equals("X")
                || C.get(0).get(1).getText().toString().equals("X") && C.get(1).get(1).getText().toString().equals("X") && C.get(2).get(1).getText().toString().equals("X")
                || C.get(0).get(2).getText().toString().equals("X") && C.get(1).get(2).getText().toString().equals("X") && C.get(2).get(2).getText().toString().equals("X")
                || C.get(0).get(0).getText().toString().equals("X") && C.get(1).get(1).getText().toString().equals("X") && C.get(2).get(2).getText().toString().equals("X")
                || C.get(0).get(2).getText().toString().equals("X") && C.get(1).get(1).getText().toString().equals("X") && C.get(2).get(0).getText().toString().equals("X")){
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText("You Lose");
            compScore += 1;
            TextView scoreView = (TextView) findViewById(R.id.scoreView);
            scoreView.setText("Wins: " + Integer.toString(myScore) + " Losses: " + Integer.toString(compScore) + " Ties: " + Integer.toString(ties));
            return true;
        }
        return false;
    }
}
