package com.example.kpizani.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.speech.tts.TextToSpeech;

import androidx.appcompat.app.AppCompatActivity;

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
    String file = "";
    String file2;
    ListView listView;
    ArrayList<Model> modelList;
    CustomAdaptor customAdapter;
    List<String> isComp = new ArrayList<String>();
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent myLocalIntent = getIntent();
        Bundle myBundle =  myLocalIntent.getExtras();
        String text = myBundle.getString("text");
        //Toast.makeText(getApplicationContext(), "we got: " + text, Toast.LENGTH_SHORT).show();
        file = text + ".txt";
        file2 = text + "2.txt";
        modelList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list);


        //String hi = readFromFile(file);
         try {
             Scanner scan = new Scanner(openFileInput(file));
             while (scan.hasNextLine()) {
                 String line = scan.nextLine();
                 int i = 0;
                 String first = "";
                 String sec = "";
                 //Toast.makeText(getApplicationContext(), line, Toast.LENGTH_SHORT).show();
                 while(line.charAt(i)!='_'){
                     first = first + line.charAt(i);
                     i++;
                 }
                 sec = line.replace(first+"_","");
                 myList.add(line);
                 modelList.add(new Model(first, sec));
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
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, myList);

        //listView.setAdapter(adapter);

        //modelList = populateList();
        //modelList.add(new Model("test","hi"));
        customAdapter = new CustomAdaptor(getApplicationContext(), modelList);
        listView.setAdapter(customAdapter);

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
                //speak(pos);
                if(myList.get(pos).contains("_incomplete")) {
                    String g = myList.get(pos).replace("_incomplete", "_complete");
                    myList.remove(pos);
                    myList.add(pos, g);
                    modelList.get(pos).setDesc("complete");
                }
                else{
                    String g = myList.get(pos).replace("_complete", "_incomplete");
                    myList.remove(pos);
                    myList.add(pos, g);
                    modelList.get(pos).setDesc("incomplete");
                }
                customAdapter.notifyDataSetChanged();
                clearFile(file);
                for(String x : myList){
                    saveStringToFile(file,x);
                }
                //Toast.makeText(getApplicationContext(), "YOU CLICKED IT " + myList.get(pos), Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void speak(int pos){
        String text = myList.get(pos);
        t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void remove(int pos){
        myList.remove(pos);
        modelList.remove(pos);
        customAdapter.notifyDataSetChanged();
        clearFile(file);
        //I know this is like an insane way to do it, clearing and rewriting the file every
        //single time but I literally couldn't figure out how else to do it so here you go.
        for(String x: myList){
            saveStringToFile(file, x);
        }
    }

    public void onClick(View v) {
        EditText et = (EditText) findViewById(R.id.addT);
        String ets = et.getText().toString();
        if (!ets.equals("")) {
            String ets2 = ets + "_incomplete";
            saveStringToFile(file,ets2);
            myList.add(ets2);
            modelList.add(new Model(ets,"incomplete"));
            et.setText("");
            //ets = ets.replace("Add Item: ","");
            //isComp.add("incomplete");
            /*myList.add(ets);
            saveStringToFile(file, ets);
            isComp.add("incomplete");
            saveDescToFile(file,"incomplete");
            //System.out.println(myList);
            customAdapter.notifyDataSetChanged();
            modelList.add(new Model(ets,"incomplete"));
            et.setText("");*/
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



    /*
    private ArrayList<Model> populateList(){

        ArrayList<Model> list = new ArrayList<>();

        for(int i = 0; i < myList.size(); i++){
            String hi = myList.get(i);
            //Model Model = new Model(hi, hi);
            //Model.setName(hi);
            //Model.setDesc(hi);
            //list.add(Model);
        }
        return list;
    }
*/
}
