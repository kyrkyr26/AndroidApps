package com.example.kpizani.restaurantorder;
        import android.app.Activity;
        import android.content.Intent;
        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.EditText;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class Apps extends AppCompatActivity {

    private ListView lv;
    private CustomAdaptor customAdapter;
    private ArrayList<ImageModel> imageModelArrayList;
    private int[] myImageList = new int[]{R.mipmap.snail, R.mipmap.beetle, R.mipmap.rattlesnake, R.mipmap.chickenfeet, R.mipmap.blobfish};
    private String[] myImageNameList = new String[]{"Bacon wrapped snails", "Fried beetles", "Sauté rattlesnake", "Hot and spicy chicken feet", "Stuffed blobfish"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        lv = (ListView) findViewById(R.id.listView);
        final Intent myLocalIntent = getIntent();
        final Bundle myBundle =  myLocalIntent.getExtras();
        String v1 = myBundle.getString("app");

        imageModelArrayList = populateList();
        customAdapter = new CustomAdaptor(this,imageModelArrayList);
        lv.setAdapter(customAdapter);

        // set up event listening for clicks on the list
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                //handleClick(index);  //index is index # of item in listview (position)
                String text = myImageNameList[index];
                Toast.makeText(getApplicationContext(), "you have chosen: " + text, Toast.LENGTH_SHORT).show();
                myBundle.putString("vresult", text);
                myLocalIntent.putExtras(myBundle);
                setResult(Activity.RESULT_OK, myLocalIntent);
                //finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private ArrayList<ImageModel> populateList(){

        ArrayList<ImageModel> list = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setName(myImageNameList[i]);
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }
        return list;
    }
}
