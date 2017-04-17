package com.main.junaidstore.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.main.junaidstore.R;
import com.main.junaidstore.models.Categories;

import java.util.ArrayList;

/**
 * Created by Muazzam on 4/15/2017.
 */

public class CategoriesAdapter  extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>  {

    ArrayList<Categories> categories = new ArrayList<>();
    private int focusedItem = 0;
    private Activity ac;
    public CategoriesAdapter(ArrayList<Categories> categories,Activity ac){

        this.categories = categories;
        this.ac = ac;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_listview,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void showConfirmDialog(final int itemPosition){

        new AlertDialog.Builder(ac)
                .setTitle("Confirm")
                .setMessage("Are you sure you want to delete this category ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        // this item will be deleted.
//                        Toast.makeText(ac,"Item "+categories.get(itemPosition).getName(),Toast.LENGTH_SHORT);
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.itemName.setText(categories.get(position).getName());
        holder.closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog(position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView itemName;
        public ImageView closeIcon;
        public ViewHolder(View v) {
            super(v);
            itemName = (TextView)v.findViewById(R.id.categories_ls_catname);
            closeIcon = (ImageView)v.findViewById(R.id.categories_ls_closeicon);
        }
    }
}
