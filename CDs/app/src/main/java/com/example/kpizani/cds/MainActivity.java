package com.example.kpizani.cds;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
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

public class MainActivity extends AppCompatActivity {

    private static String CD_TABLE = "CD_TABLE";
    protected static SQLiteDatabase cdDB = null;
    private Cursor cursor = null;
    private ListView myList;
    private List<String> cds = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private SQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myList = (ListView) findViewById(R.id.list);
        cds = new ArrayList<>();
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


        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, cds);
            myList.setAdapter(adapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                //handleClick(results, index);
                //gonna start new activity
                String words = cds.get(index);
                String[] w = words.split(", ");
                String album = w[0];
                album = album + "_TABLE";
                Intent seeSongs = new Intent(MainActivity.this, Songs.class);
                Bundle myData0 = new Bundle();
                myData0.putString("table",album);
                seeSongs.putExtras(myData0);
                startActivityForResult(seeSongs,102);

            }
        });

        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent edit = new Intent(MainActivity.this, Edit.class);
                Bundle myData3 = new Bundle();
                String ws = cds.get(pos);
                String []w2 = ws.split(", ");
                myData3.putString("table",CD_TABLE);
                myData3.putString("album", w2[0]);
                myData3.putString("artist", w2[1]);
                myData3.putString("rating", w2[2]);
                edit.putExtras(myData3);
                startActivityForResult(edit,105);
                return true;
            }
        });


        lookupData();

    }

    private void createTable()
    {
        Log.d(getLocalClassName(), "in create table");

        cdDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                CD_TABLE +
                " (TITLE VARCHAR, " +
                "  ARTIST VARCHAR, " +
                "  RATING VARCHAR);");
    }



    private void insertData(String cdName, String cdArtist, String cdRating)
    {
        ContentValues values = new ContentValues();
        values.put("TITLE", cdName);
        values.put("ARTIST", cdArtist);
        values.put("RATING", cdRating);
        cdDB.insert(CD_TABLE, null, values);
    }

    private void deleteData(String name)
    {
        cdDB.delete(CD_TABLE, "TITLE=?", new String[] {name});
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
                Intent del = new Intent(MainActivity.this, Delete.class);
                Bundle myData1 = new Bundle();
                myData1.putString("table",CD_TABLE);
                del.putExtras(myData1);
                startActivityForResult(del,103);
                return true;
            case R.id.menu_insert:
                Intent insert = new Intent(MainActivity.this, InsertCD.class);
                Bundle myData = new Bundle();
                myData.putString("table",CD_TABLE);
                insert.putExtras(myData);
                startActivityForResult(insert,104);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void lookupData()
    {
        //lists all records
        cds.clear();
        cursor = cdDB.rawQuery("SELECT TITLE, ARTIST, RATING FROM " +
                CD_TABLE , null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("TITLE"));
                    @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex("ARTIST"));
                    @SuppressLint("Range") String rating = cursor.getString(cursor.getColumnIndex("RATING"));
                    cds.add("" + title + ", " + artist + ", " + rating);
                    //Toast.makeText(getApplicationContext(), "should have added to cds", Toast.LENGTH_SHORT).show();
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
        //Toast.makeText(getApplicationContext(), "end lookupData()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if ((requestCode == 103) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults0 = data.getExtras();
                String cd = myResults0.getString("album");
                deleteData(cd);
                lookupData();
            }
            if ((requestCode == 104) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults = data.getExtras();
                String cd = myResults.getString("title");
                String artist = myResults.getString("artist");
                String rating = myResults.getString("rating");
                cds.add(cd + ", " + artist + ", " + rating);
                adapter.notifyDataSetChanged();
                insertData(cd, artist, rating);
            }
            if ((requestCode == 105) && (resultCode == Activity.RESULT_OK)) {
                Bundle myResults2 = data.getExtras();
                String cd = myResults2.getString("nalbum");
                String artist = myResults2.getString("nartist");
                String rating = myResults2.getString("nrating");
                deleteData(cd);
                lookupData();
                cds.add(cd + ", " + artist + ", " + rating);
                adapter.notifyDataSetChanged();
                insertData(cd, artist, rating);
            }
        }
        catch (Exception e) {
            //lblResult1.setText("Problems - " + requestCode + " " + resultCode);
        }
    }
}
