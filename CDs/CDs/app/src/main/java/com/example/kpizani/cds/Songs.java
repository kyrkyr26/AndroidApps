package com.example.kpizani.cds;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.kpizani.cds.MainActivity.cdDB;

public class Songs extends AppCompatActivity {

    private static String SONGS_TABLE = "";
    //private SQLiteDatabase cdDB = null;
    private Cursor cursor = null;
    private ListView myList;
    private List<String> songs = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    Intent localIntent;
    Bundle myBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        localIntent = getIntent();
        myBundle =  localIntent.getExtras();
        String b = myBundle.getString("table");
        b = b.replace(" ","");
        b = b.replace(".","");
        b = b.replace(",","");
        b = b.replace("!","");
        b = b.replace("?","");
        SONGS_TABLE = b;
        //Toast.makeText(getApplicationContext(), SONGS_TABLE , Toast.LENGTH_SHORT).show();

        try
        {
            cdDB = openOrCreateDatabase("NAME", MODE_PRIVATE, null);
            createTable();
        }
        catch(SQLiteException se)
        {
            Log.e(getClass().getSimpleName(),
                    "Could not create or Open the database");
        }

        myList = (ListView) findViewById(R.id.list);

        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, songs);
        myList.setAdapter(adapter);

        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent edit = new Intent(Songs.this, Edit.class);
                Bundle myData3 = new Bundle();
                String ws = songs.get(pos);
                String []w2 = ws.split(", ");
                myData3.putString("table",SONGS_TABLE);
                myData3.putString("album", w2[0]);
                myData3.putString("artist", w2[1]);
                myData3.putString("rating", w2[2]);
                edit.putExtras(myData3);
                startActivityForResult(edit,114);
                return true;
            }
        });

        lookupData();
    }

    private void createTable()
    {
        Log.d(getLocalClassName(), "in create table");

        cdDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                SONGS_TABLE +
                " (TITLE VARCHAR, " +
                "  TIME VARCHAR, " +
                "  RATING VARCHAR);");
    }


    private void insertData(String cdName, String cdArtist, String cdRating)
    {
        ContentValues values = new ContentValues();
        values.put("TITLE", cdName);
        values.put("TIME", cdArtist);
        values.put("RATING", cdRating);
        MainActivity.cdDB.insert(SONGS_TABLE, null, values);
    }

    private void lookupData()
    {
        //lists all records
        songs.clear();
        cursor = cdDB.rawQuery("SELECT TITLE, TIME, RATING FROM " +
                SONGS_TABLE , null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    String title = cursor.getString(cursor.getColumnIndex("TITLE"));
                    String artist = cursor.getString(cursor.getColumnIndex("TIME"));
                    String rating = cursor.getString(cursor.getColumnIndex("RATING"));
                    songs.add("" + title + ", " + artist + ", " + rating);
                    //Toast.makeText(getApplicationContext(), "should have added to cds", Toast.LENGTH_SHORT).show();
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
        //Toast.makeText(getApplicationContext(), "end lookupData()", Toast.LENGTH_SHORT).show();
    }

    private void deleteData(String name)
    {
        cdDB.delete(SONGS_TABLE, "TITLE=?", new String[] {name});
    }

    private void update(String oname, String nname){
        cdDB.update(SONGS_TABLE, null, "TITLE=" + oname, new String[] {nname});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_delete:
                Intent del = new Intent(Songs.this, SongDelete.class);
                Bundle myData1 = new Bundle();
                myData1.putString("table",SONGS_TABLE);
                del.putExtras(myData1);
                startActivityForResult(del,111);
                return true;
            case R.id.menu_insert:
                Intent insert = new Intent(Songs.this, InsertSong.class);
                Bundle myData = new Bundle();
                myData.putString("table",SONGS_TABLE);
                insert.putExtras(myData);
                startActivityForResult(insert,112);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if ((requestCode == 111) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults0 = data.getExtras();
                String cd = myResults0.getString("song");
                deleteData(cd);
                lookupData();
            }
            if ((requestCode == 112) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults = data.getExtras();
                String title = myResults.getString("title");
                String duration = myResults.getString("artist");
                String rating = myResults.getString("rating");
                //Toast.makeText(getApplicationContext(), title + " ~cd", Toast.LENGTH_SHORT).show();
                songs.add(title + ", " + duration + ", " + rating);
                adapter.notifyDataSetChanged();
                insertData(title, duration, rating);
            }
            if ((requestCode == 114) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults2 = data.getExtras();
                String cd = myResults2.getString("nalbum");
                String artist = myResults2.getString("nartist");
                String rating = myResults2.getString("nrating");
                deleteData(cd);
                lookupData();
                songs.add(cd + ", " + artist + ", " + rating);
                adapter.notifyDataSetChanged();
                insertData(cd, artist, rating);
            }
        }
        catch (Exception e) {
            //lblResult1.setText("Problems - " + requestCode + " " + resultCode);
        }
    }
}
