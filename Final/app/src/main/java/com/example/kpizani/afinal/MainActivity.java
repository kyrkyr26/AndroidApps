package com.example.kpizani.afinal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.in;

public class MainActivity extends AppCompatActivity {
    ListView list;
    EditText et;
    ArrayList<String> faves = new ArrayList<String>();
    ArrayList<String> faveMovies = new ArrayList<>();
    ArrayList<String> faveShows = new ArrayList<>();
    ArrayList<String> faveEps = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayList<String> comps;

    private static String FAVES_TABLE = "FAVES_TABLE";
    protected static SQLiteDatabase favesDB = null;
    private Cursor cursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        comps = new ArrayList<>(Arrays.asList("Warner Bros","Walt Disney","Lionsgate","Universal","20th Century Fox","MGM","Metro-Goldwyn-Mayer","Sony","Columbia","New Line Cinema","Miramax","The Weinstein Company","DreamWorks","Fox","Marvel","Focus Features","Lucafilm","Touchstone Pictures","Pixar","Summit Entertainment"));
        list = (ListView) findViewById(R.id.list);

        try
        {
            favesDB = openOrCreateDatabase("NAME", MODE_PRIVATE, null);
            createTable();
        }
        catch(SQLiteException se)
        {
            Log.e(getClass().getSimpleName(),
                    "Could not create or Open the database");
        }

        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, faves);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                //Start of implementation of getting production company from homepage - will have to end up
                //passing production company over and making a table column for this variable in the sql table

                /*String prod = faves.get(index);
                int fin = comps.size() + 1;
                for (int j = 0; j < comps.size(); j++) {
                    if (prod.contains(comps.get(j))) {
                        fin = j;
                        break;
                    }
                }
                System.out.println("out of loop");
                if (fin != comps.size() + 1) {
                    String map = comps.get(fin) + " studios";
                    map = map.replace(" ", "+");
                    System.out.println(map);
                    String label = "ABC Label";
                    String uriBegin = "geo:0,0?q=" + map;
                    //String query = "3+harmony+drive";
                    //String encodedQuery = Uri.encode(query);
                    String uriString = uriBegin;
                    Uri uri = Uri.parse(uriString);
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }*/
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                System.out.println(faves.get(pos));
                String x = faves.get(pos).split(", Director:")[0];
                x = x.replace("Title: ","");
                System.out.println(x);
                deleteData(x);
                lookupData();
                return true;
            }
        });


        lookupData();

    }

    private void lookupData()
    {
        //lists all records
        faves.clear();
        cursor = favesDB.rawQuery("SELECT TITLE, DIR, RATING, DATE FROM " +
                FAVES_TABLE , null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("TITLE"));
                    @SuppressLint("Range") String director = cursor.getString(cursor.getColumnIndex("DIR"));
                    @SuppressLint("Range") String rating = cursor.getString(cursor.getColumnIndex("RATING"));
                    @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("DATE"));
                    faves.add("Title: " + title + ", Director: " + director + ", Rating: " + rating + ", Release Date: " + date);
                    //Toast.makeText(getApplicationContext(), "should have added to cds", Toast.LENGTH_SHORT).show();
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
        //Toast.makeText(getApplicationContext(), "end lookupData()", Toast.LENGTH_SHORT).show();
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulayout, menu);
        return true;
    }

    private void deleteData(String name)
    {
        favesDB.delete(FAVES_TABLE, "TITLE=?", new String[] {name});
    }


    private void createTable()
    {
        Log.d(getLocalClassName(), "in create table");

        favesDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                FAVES_TABLE +
                " (TITLE VARCHAR, " +
                "  DIR VARCHAR, " +
                "  RATING VARCHAR," +
                "  DATE VARCHAR);");
    }

    private void insertData(String cdName, String cdArtist, String cdRating, String date)
    {
        ContentValues values = new ContentValues();
        values.put("TITLE", cdName);
        values.put("DIR", cdArtist);
        values.put("RATING", cdRating);
        values.put("DATE", date);
        favesDB.insert(FAVES_TABLE, null, values);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.manu_movie:
                Intent game = new Intent(MainActivity.this, gameSearch.class);
                Bundle myData1 = new Bundle();
                game.putExtras(myData1);
                startActivityForResult(game,103);
                return true;
            case R.id.menu_show:
                Intent show = new Intent(MainActivity.this, showSearch.class);
                Bundle myData2 = new Bundle();
                show.putExtras(myData2);
                startActivityForResult(show,104);
                return true;
            case R.id.menu_ep:
                Intent company = new Intent(MainActivity.this, episodeSearch.class);
                Bundle myData = new Bundle();
                company.putExtras(myData);
                startActivityForResult(company,105);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if ((requestCode == 103) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults0 = data.getExtras();
                String title = myResults0.getString("title");
                String dir = myResults0.getString("dir");
                String rating = myResults0.getString("rating");
                String date = myResults0.getString("date");
                faves.add("Title: " + title + ", Director: " + dir + ", Rating: " + rating + ", Release Date: " + date);
                adapter.notifyDataSetChanged();
                insertData(title,dir,rating,date);

            }
            if ((requestCode == 104) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults1 = data.getExtras();
                String title = myResults1.getString("title");
                String dir = myResults1.getString("dir");
                String rating = myResults1.getString("rating");
                String date = myResults1.getString("date");
                faves.add("Title: " + title + ", Director: " + dir + ", Rating: " + rating + ", Release Date: " + date);
                adapter.notifyDataSetChanged();
                insertData(title,dir,rating,date);

            }
            if ((requestCode == 105) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults2 = data.getExtras();
                String title = myResults2.getString("title");
                String dir = myResults2.getString("dir");
                String rating = myResults2.getString("rating");
                String date = myResults2.getString("date");
                faves.add("Title: " + title + ", Director: " + dir + ", Rating: " + rating + ", Release Date: " + date);
                adapter.notifyDataSetChanged();
                insertData(title,dir,rating,date);
            }
        }
        catch (Exception e) {
            //lblResult1.setText("Problems - " + requestCode + " " + resultCode);
        }
    }
}
