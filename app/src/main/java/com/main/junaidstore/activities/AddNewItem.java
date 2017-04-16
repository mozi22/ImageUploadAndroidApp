package com.main.junaidstore.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.junaidstore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Muazzam on 4/16/2017.
 */

public class AddNewItem extends AppCompatActivity {

    @BindView(R.id.add_item_img) ImageView add_item_img;
    @BindView(R.id.add_item_img_upload_box) TextView add_item_img_upload_box;
    @BindView(R.id.add_item_real_price_txtbox) EditText add_item_real_price;
    @BindView(R.id.add_item_retail_price_txtbox) EditText add_item_retail_price_txtbox;
    @BindView(R.id.add_item_submit_btn) Button add_item_submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("New Item");

    }
}
