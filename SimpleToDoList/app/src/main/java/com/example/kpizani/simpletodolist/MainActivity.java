package com.example.kpizani.simpletodolist;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.speech.tts.TextToSpeech;

import java.util.Locale;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    List<String> myList = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);
        /*try {
            Scanner scan = new Scanner(openFileInput("mine.txt"));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                System.out.println(line);
                myList.add(line);
            }
        }
        catch(FileNotFoundException exception){
            System.out.println("NOT FOUND");
        }*/
        //String hi = readFromFile("mine.txt");
        try {
            Scanner scan = new Scanner(openFileInput("mine.txt"));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                //System.out.println(line);
                myList.add(line);
            }
        }
        catch(FileNotFoundException exception){
            System.out.println("NOT FOUND");
        }

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        //myList.add(hi);
        //System.out.println(hi);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, myList);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //@Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                remove(pos);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                speak(pos);
            }
        });
    }

    public void speak(int pos){
        String text = myList.get(pos);
        t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void remove(int pos){
        myList.remove(pos);
        adapter.notifyDataSetChanged();
        clearFile("mine.txt");
        //I know this is like an insane way to do it, clearing and rewriting the file every
        //single time but I literally couldn't figure out how else to do it so here you go.
        for(String x: myList){
            saveStringToFile("mine.txt", x);
        }
    }

    public void onClick(View v) {
        EditText et = (EditText) findViewById(R.id.addT);
        String ets = et.getText().toString();
        if (!ets.equals("")) {
            //ets = ets.replace("Add Item: ","");
            myList.add(ets);
            saveStringToFile("mine.txt", ets);
            //System.out.println(myList);
            adapter.notifyDataSetChanged();
            et.setText("");
        }
        else{

        }
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
