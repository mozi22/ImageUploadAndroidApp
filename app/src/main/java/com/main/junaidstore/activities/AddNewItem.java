package com.main.junaidstore.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.main.junaidstore.R;
import com.main.junaidstore.interfaces.AsyncCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.bitmap;

/**
 * Created by Muazzam on 4/16/2017.
 */

public class AddNewItem extends AppCompatActivity implements AsyncCallback{

    @BindView(R.id.add_item_img) ImageView add_item_img;
    @BindView(R.id.add_item_img_upload_box) TextView add_item_img_upload_box;
    @BindView(R.id.add_item_real_price_txtbox) EditText add_item_real_price;
    @BindView(R.id.add_item_retail_price_txtbox) EditText add_item_retail_price_txtbox;
    @BindView(R.id.add_item_submit_btn) Button add_item_submit_btn;

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;

    public static final int CODE_SAVE_ITEM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("New Item");


        add_item_submit_btn.setOnClickListener(clickListener);
        add_item_img_upload_box.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.equals(add_item_img_upload_box)){
                final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddNewItem.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Take Photo")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        } else if (items[item].equals("Choose from Library")) {
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent,SELECT_FILE);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
            else if(view.equals(add_item_submit_btn)){
                if(proceed()){

                }
            }
        }
    };

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


            try{
                add_item_img.setVisibility(View.VISIBLE);
                add_item_img_upload_box.setVisibility(View.INVISIBLE);
                Uri u = imageReturnedIntent.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), u);
                modifyOrientation(bitmap,u.getPath());
                add_item_img.setImageURI(u);
            }
            catch (IOException e){

            }
    }
    private boolean proceed(){

        if(add_item_retail_price_txtbox.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Fill in the retail price",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(add_item_real_price.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Fill in the real price",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(add_item_img.getDrawable() == null){
            Toast.makeText(getApplicationContext(),"Select an image first",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void AsyncCallback(int resultCode, Parcelable rf) {
        if(CODE_SAVE_ITEM == resultCode){

        }
    }




}
