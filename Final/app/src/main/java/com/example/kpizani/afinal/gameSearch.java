package com.example.kpizani.afinal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class gameSearch extends AppCompatActivity {
    EditText et;
    EditText yr;
    Button b;
    String text;
    String year;
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> games = new ArrayList<>();
    ArrayList<Game> gameList;
    JSONObject jsonobject;
    ArrayList<String> comps;

    Intent intent;
    Bundle myBundle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_search);
        et = (EditText) findViewById(R.id.et);
        b = (Button) findViewById(R.id.button);
        lv = (ListView) findViewById(R.id.list);
        yr = (EditText) findViewById(R.id.yr);

        gameList = new ArrayList<>();

        intent = getIntent();
        myBundle = intent.getExtras();

        comps = new ArrayList<>(Arrays.asList("Warner Bros","Walt Disney","Lionsgate","Universal","20th Century Fox","MGM","Metro-Goldwyn-Mayer","Sony","Columbia","New Line Cinema","Miramax","The Weinstein Company","DreamWorks","Fox","Marvel","Focus Features","Lucafilm","Touchstone Pictures","Pixar","Summit Entertainment"));

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, games);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                String t = gameList.get(index).getTitle();
                String r = gameList.get(index).getRating();
                String d = gameList.get(index).getDir();
                String date = gameList.get(index).getDate();
                myBundle.putString("title",t);
                myBundle.putString("rating",r);
                myBundle.putString("dir",d);
                myBundle.putString("date",date);
                intent.putExtras(myBundle);
                setResult(Activity.RESULT_OK, intent);

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                System.out.println(pos);
                System.out.println(gameList.get(pos).getProd());
                System.out.println(comps.size());
                String prod = gameList.get(pos).getProd();
                int fin = comps.size()+1;
                for(int j = 0; j < comps.size(); j++){
                    if(prod.contains(comps.get(j))){
                        fin = j;
                        break;
                    }
                }
                System.out.println("out of loop");
                if(fin != comps.size()+1){
                String map = comps.get(fin) + " studios";
                    map = map.replace(" ","+");
                    System.out.println(map);
                    //double latitude = 40.714728;
                    //double longitude = -73.998672;
                    String uriBegin = "geo:0,0?q="+map;
                    //String query = "3+harmony+drive";
                    //String encodedQuery = Uri.encode(query);
                    Uri uri = Uri.parse(uriBegin);
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Production Studio not found", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

}


    // Download JSON file AsyncTask
    @SuppressLint("StaticFieldLeak")
    public class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            // JSON file URL address
            text = text.replace(" ","+");
            System.out.println(text);
            String url1 = "http://www.omdbapi.com/?apikey=66803828&t=";
            String url;
            url = url1+text;
            System.out.println(url);
            if(!Objects.equals(year, "")){
                url = url + "&y="+year;
            }


            jsonobject = JSONfunctions.getJSONfromURL(url);

            System.out.println(jsonobject);

            try {
                String title = jsonobject.getString("Title");
                System.out.println(title);
                Game gameInfo = new Game();
                gameInfo.setTitle(jsonobject.getString("Title"));
                gameInfo.setRating(jsonobject.getString("Rated"));
                gameInfo.setDir(jsonobject.getString("Director"));
                if(jsonobject.has("Production")){
                gameInfo.setProd(jsonobject.getString("Production"));}
                else if(!jsonobject.has("Production")){
                    gameInfo.setProd("Unknown");
                }
                gameInfo.setDate(jsonobject.getString("Released"));
                System.out.println(title);

                gameList.add(gameInfo);
                System.out.println("ADDED TO GAMESLIST");

            } catch (Exception e) {
               //Toast.makeText(getApplicationContext(),"Movie not found", Toast.LENGTH_SHORT).show();
                Log.e("ERROR", Objects.requireNonNull(e.getMessage()));
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            System.out.println("ON POST EXEC");
            games.clear();
            for(int i = 0; i<gameList.size(); i++){
                games.add(gameList.get(i).getTitle() + ", " + gameList.get(i).getDir() + ", " + gameList.get(i).getRating() + ", " + gameList.get(i).getDate());
            }
            adapter.notifyDataSetChanged();
            System.out.println(gameList);

        }
    }

    public void onClick(View v){
        if(!et.getText().toString().equals("") && !et.getText().toString().equals("!") && !et.getText().toString().equals(".") && !et.getText().toString().equals("?")) {
            if(!yr.getText().toString().equals("")){
                year = yr.getText().toString();
                yr.setText("");
            }
            text = et.getText().toString();
            et.setText("");
            System.out.println("CLICK");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new DownloadJSON().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                new DownloadJSON().execute();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
