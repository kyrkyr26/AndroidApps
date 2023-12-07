package com.example.kpizani.restaurantorder;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

/**
 * Created by Kevin on 2/13/2017.
 */
public class CustomAdaptor extends BaseAdapter {
    private Context context;
    private ArrayList<ImageModel> imageModelArrayList;


    public CustomAdaptor(Context context, ArrayList<ImageModel> imageModelArrayList) {

        this.context = context;
        this.imageModelArrayList = imageModelArrayList;

    }


    public int getViewTypeCount() {
        return getCount();
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

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.iv = (ImageView) convertView.findViewById(R.id.imgView);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(imageModelArrayList.get(position).getName());
        holder.iv.setImageResource(imageModelArrayList.get(position).getImage_drawable());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
        private ImageView iv;

    }
}
