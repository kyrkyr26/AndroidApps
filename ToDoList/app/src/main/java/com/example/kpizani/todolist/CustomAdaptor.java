package com.example.kpizani.todolist;

/**
 * Created by kpizani on 3/21/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdaptor extends BaseAdapter {
    private Context context;
    private ArrayList<Model> imageModelArrayList;


    public CustomAdaptor(Context context, ArrayList<Model> imageModelArrayList) {

        this.context = context;
        this.imageModelArrayList = imageModelArrayList;

    }


    public int getViewTypeCount() {
        return 1;
    }

    public int getItemViewType(int position) {

        return position;
    }


    public int getCount() {
        return imageModelArrayList.size();
    }


    public Object getItem(int position) {
        return imageModelArrayList.get(position);
    }


    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listlayout, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.iv = (TextView) convertView.findViewById(R.id.desc);
            holder.button = (Button) convertView.findViewById(R.id.button);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(imageModelArrayList.get(position).getName());
        holder.iv.setText(imageModelArrayList.get(position).getDesc());
        final Button button = (Button) convertView.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout vwParentRow = (LinearLayout)v.getParent().getParent();
                RelativeLayout rel = (RelativeLayout)vwParentRow.getChildAt(0);
                TextView child = (TextView)rel.getChildAt(0);
                String s = child.getText().toString();
                Intent intent = new Intent(parent.getContext(), description.class);
                Bundle myData = new Bundle();
                myData.putString("text", s);
                intent.putExtras(myData);
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
        private TextView iv;
        private Button button;

    }
}
