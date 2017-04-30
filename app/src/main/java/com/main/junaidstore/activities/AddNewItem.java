package com.main.junaidstore.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.main.junaidstore.R;
import com.main.junaidstore.interfaces.AsyncCallback;
import com.main.junaidstore.libraries.GeneralFunctions;
import com.main.junaidstore.libraries.ImageUploader;
import com.main.junaidstore.libraries.NetworkInterface;
import com.main.junaidstore.models.Categories;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Muazzam on 4/16/2017.
 */

public class AddNewItem extends AppCompatActivity implements AsyncCallback{

    @BindView(R.id.add_item_img) ImageView add_item_img;
    @BindView(R.id.add_item_img_upload_box) TextView add_item_img_upload_box;
    @BindView(R.id.add_item_real_price_txtbox) EditText add_item_real_price;
    @BindView(R.id.add_item_retail_price_txtbox) EditText add_item_retail_price_txtbox;
    @BindView(R.id.add_item_submit_btn) Button add_item_submit_btn;
    @BindView(R.id.add_item_categories_dropdown) Spinner add_item_categories_dropdown;

    public static final int CODE_GET_POSTING_PAGE_CATEGORIES = 23;
    public static final int CODE_INSERT_POST = 24;
    public NetworkInterface networkInterface;

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private ProgressDialog progress;

    private int imageViewRotationTapCount = 0;
    private String picturePath;

    public List<com.main.junaidstore.models.Categories> categoriesList;

    /** The bitmap shown to the user in imageView */
    private Bitmap scaledBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("New Item");

        networkInterface = new NetworkInterface(this);
        add_item_submit_btn.setOnClickListener(clickListener);
        add_item_img_upload_box.setOnClickListener(clickListener);
        add_item_img.setOnClickListener(clickListener);

        progress = new ProgressDialog(this);
        progress.setTitle("Uploading");
        progress.setMessage("Uploading in progress...");

    }

    @Override
    protected void onResume(){
        super.onResume();
        add_item_submit_btn.setEnabled(true);

        this.networkInterface.getCategories(GeneralFunctions.getSessionValue(this,getResources().getString(R.string.userid)),
                GeneralFunctions.getSessionValue(this,getResources().getString(R.string.access_token)),
                CODE_GET_POSTING_PAGE_CATEGORIES);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.equals(add_item_img_upload_box) || view.equals(add_item_img)){
                final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddNewItem.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Take Photo")) {
                            resetImageViewRotation();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        } else if (items[item].equals("Choose from Library")) {
                            resetImageViewRotation();
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

                    add_item_submit_btn.setEnabled(false);

                    ImageUploader uploader = new ImageUploader(
                            add_item_real_price.getText().toString(),
                            add_item_retail_price_txtbox.getText().toString(),
                            categoriesList.get(add_item_categories_dropdown.getSelectedItemPosition()).getID(),
                            GeneralFunctions.getSessionValue(AddNewItem.this,getResources().getString(R.string.userid)),
                            GeneralFunctions.getSessionValue(AddNewItem.this,getResources().getString(R.string.access_token)),
                            AddNewItem.this,
                            String.valueOf(imageViewRotationTapCount)
                            );

                    uploader.execute(picturePath);
                    resetImageViewRotation();
                    progress.show();
                }
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

            if(resultCode == 0){
                return;
            }

            add_item_img.setVisibility(View.VISIBLE);
            add_item_img_upload_box.setVisibility(View.INVISIBLE);
            Uri selectedImage  = imageReturnedIntent.getData();
            picturePath = getPicturePath(selectedImage);
            Bitmap loadedBitmap = BitmapFactory.decodeFile(picturePath);

            if(requestCode == SELECT_FILE){

                Matrix matrix = new Matrix();
                if (loadedBitmap.getWidth() >= loadedBitmap.getHeight()){
                    matrix.setRectToRect(new RectF(0, 0, loadedBitmap.getWidth(), loadedBitmap.getHeight()), new RectF(0, 0, 400, 300), Matrix.ScaleToFit.CENTER);
                    scaledBitmap = Bitmap.createBitmap(loadedBitmap, 0, 0, loadedBitmap.getWidth(), loadedBitmap.getHeight(), matrix, true);
                } else{
                    matrix.setRectToRect(new RectF(0, 0, loadedBitmap.getWidth(), loadedBitmap.getHeight()), new RectF(0, 0, 300, 400), Matrix.ScaleToFit.CENTER);
                    scaledBitmap = Bitmap.createBitmap(loadedBitmap, 0, 0, loadedBitmap.getWidth(), loadedBitmap.getHeight(), matrix, true);
                }

                File file = new File(getExternalCacheDir(), "image.jpg");
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    Log.e("Image", "Convert");
                }
            }
        else{
                Matrix matrix = new Matrix();
                if (loadedBitmap.getWidth() >= loadedBitmap.getHeight()){
                    matrix.setRectToRect(new RectF(0, 0, loadedBitmap.getWidth(), loadedBitmap.getHeight()), new RectF(0, 0, 400, 300), Matrix.ScaleToFit.CENTER);
                    scaledBitmap = Bitmap.createBitmap(loadedBitmap, 0, 0, loadedBitmap.getWidth(), loadedBitmap.getHeight(), matrix, true);
                } else{
                    matrix.setRectToRect(new RectF(0, 0, loadedBitmap.getWidth(), loadedBitmap.getHeight()), new RectF(0, 0, 300, 400), Matrix.ScaleToFit.CENTER);
                    scaledBitmap = Bitmap.createBitmap(loadedBitmap, 0, 0, loadedBitmap.getWidth(), loadedBitmap.getHeight(), matrix, true);
                }

                File file = new File(getExternalCacheDir(), "image.jpg");
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    Log.e("Image", "Convert");
                }            }
        add_item_img.setImageBitmap(scaledBitmap);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_name) {
            imageViewRotationTapCount = imageViewRotationTapCount + 1;
            add_item_img.setRotation(add_item_img.getRotation() + 90);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void resetImageViewRotation(){
        for(int i= imageViewRotationTapCount; i>0; i--){
            add_item_img.setRotation(add_item_img.getRotation() - 90);
        }
        imageViewRotationTapCount = 0;
    }

    public String getPicturePath(Uri selectedImage){
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
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
    public void AsyncCallback(int resultCode, Parcelable rf){
        com.main.junaidstore.models.Response response = Parcels.unwrap(rf);

        if(CODE_INSERT_POST == resultCode){
            if(response.getType().equals("200")){
                Toast.makeText(this, "Uploaded Successfully !!!", Toast.LENGTH_SHORT).show();
                add_item_submit_btn.setEnabled(true);
                add_item_real_price.setText("");
                add_item_retail_price_txtbox.setText("");
                add_item_img.setVisibility(View.INVISIBLE);
                add_item_img_upload_box.setVisibility(View.VISIBLE);
                progress.dismiss();
            }
        }
        else if(CODE_GET_POSTING_PAGE_CATEGORIES == resultCode){

            if(response.getCategories().size() == 0){
                Toast.makeText(getApplicationContext(),"Add atleast one category first",Toast.LENGTH_SHORT).show();
                finish();
            }

            categoriesList = response.getCategories();
            populateCategoriesDropdown(categoriesList);
        }
    }

    private void populateCategoriesDropdown(List<com.main.junaidstore.models.Categories> categories){

        List<String> str = new ArrayList<>();

        for(Categories cat: categoriesList){
            str.add(cat.getCategory());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, str);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_item_categories_dropdown.setAdapter(adapter);
    }
}
