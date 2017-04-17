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
import com.main.junaidstore.libraries.GeneralFunctions;
import com.main.junaidstore.libraries.NetworkInterface;
import com.main.junaidstore.models.Categories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muazzam on 4/15/2017.
 */

public class CategoriesAdapter  extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>  {

    List<Categories> categories = new ArrayList<>();
    private int focusedItem = 0;
    private Activity ac;

    NetworkInterface networkInterface;

    public CategoriesAdapter(List<Categories> categories,Activity ac){

        this.categories = categories;
        this.ac = ac;

        this.networkInterface = new NetworkInterface(ac);
    }

    public List<Categories> getCategories(){
        return this.categories;
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
                .setMessage("All the related posts to this category will be deleted. Are you sure ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        networkInterface.deleteCategory(
                                GeneralFunctions.getSessionValue(ac,ac.getResources().getString(R.string.userid)),
                                GeneralFunctions.getSessionValue(ac,ac.getResources().getString(R.string.access_token)),
                                categories.get(itemPosition).getID(),
                                com.main.junaidstore.activities.Categories.CODE_DELETE_CATEGORY);
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.itemName.setText(categories.get(position).getCategory());
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
