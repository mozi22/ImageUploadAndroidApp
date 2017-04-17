package com.main.junaidstore.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.main.junaidstore.R;
import com.main.junaidstore.activities.Categories;
import com.main.junaidstore.libraries.GeneralFunctions;
import com.main.junaidstore.libraries.NetworkInterface;

/**
 * Created by Muazzam on 4/14/2017.
 */

public class AddCategoryDialog  extends DialogFragment {

        NetworkInterface networkInterface;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            final LayoutInflater inflater = getActivity().getLayoutInflater();

            networkInterface = new NetworkInterface(getActivity());

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            final View v = inflater.inflate(R.layout.add_category_dialog,null);
            builder.setView(v)
                    // Add action buttons
                    .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        networkInterface.insertCategory(GeneralFunctions.getSessionValue(getActivity(),getResources().getString(R.string.userid)),
                                GeneralFunctions.getSessionValue(getActivity(),getResources().getString(R.string.access_token)),
                                ((EditText)v.findViewById(R.id.category_username)).getText().toString(),
                                Categories.CODE_INSERT_CATEGORY);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            return builder.create();
        }

}
