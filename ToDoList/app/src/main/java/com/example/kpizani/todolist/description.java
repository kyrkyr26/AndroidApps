package com.example.kpizani.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class description extends AppCompatActivity {
    String des;
    String file = "";
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Intent myLocalIntent = getIntent();
        Bundle myBundle =  myLocalIntent.getExtras();
        String text = myBundle.getString("text");
        des = "";
        //Toast.makeText(getApplicationContext(), "we got: " + text, Toast.LENGTH_SHORT).show();
        et = (EditText) findViewById(R.id.editT);
        file = text + ".txt";

        try {
            Scanner scan = new Scanner(openFileInput(file));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                //Toast.makeText(getApplicationContext(), "line"+line+"end", Toast.LENGTH_SHORT).show();
                if(line.equals("")){
                    des = des + "";
                    et.setText(des);
                }
                else if(scan.hasNextLine()) {
                    des = des + line + "\n";
                    et.setText(des);
                }
                else{
                    des = des + line;
                    et.setText(des);
                }

            }
        }
        catch(FileNotFoundException exception){
            System.out.println("NOT FOUND");
        }
    }

    public void onClick(View v){
        EditText et = (EditText) findViewById(R.id.editT);
        et.setText("");
    }

    public void onBackPressed(){
        des = et.getText().toString();
        clearFile(file);
        saveStringToFile(file,des);
        finish();
    }

    private void clearFile(String fileName){
        FileOutputStream fos;
        try {
            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(("").getBytes());
            fos.close();
        }
        catch(IOException exception){
            System.out.println("Exception");
        }
    }

    private void saveStringToFile(String fileName, String stringToBeSaved) {
        FileOutputStream fos;
        try {
            //fos = openFileOutput(fileName, Context.MODE_PRIVATE); //try this
            fos = openFileOutput(fileName, Context.MODE_PRIVATE | Context.MODE_APPEND);
            stringToBeSaved = stringToBeSaved + "\n";
            //System.out.println(stringToBeSaved);
            fos.write(stringToBeSaved.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
