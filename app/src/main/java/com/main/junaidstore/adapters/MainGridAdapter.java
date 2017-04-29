package com.main.junaidstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.junaidstore.R;
import com.main.junaidstore.libraries.ServiceGenerator;
import com.main.junaidstore.models.Posts;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muazzam on 4/14/2017.
 */

public class MainGridAdapter extends BaseAdapter{
    private Context mContext;
    private List<Posts> posts = new ArrayList<>();

    public MainGridAdapter(Context c, List<Posts> posts) {
        mContext = c;
        this.posts = posts;
    }

    public int getCount() {
        return posts.size();
    }

    public Object getItem(int position) {
        return this.posts.get(position);
    }
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        ImageView imageView;
        TextView retail_price;
        TextView original_price;

        if (convertView == null) {
            gridView = new View(mContext);
            gridView = inflater.inflate(R.layout.grid_items, null);

            imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            retail_price = (TextView) gridView.findViewById(R.id.grid_item_retail_price);
            original_price = (TextView) gridView.findViewById(R.id.grid_item_original_price);

            retail_price.setText("Retail Price: "+posts.get(position).getRetailPrice());
            original_price.setText("Original Price: "+posts.get(position).getOriginalPrice());

            Picasso.with(this.mContext)
                    .load(ServiceGenerator.API_IMAGE_LOAD_URL + posts.get(position).getImage())
                    .resize(400,400)
                    .placeholder(R.raw.placeholder)
                    .into(imageView);
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
