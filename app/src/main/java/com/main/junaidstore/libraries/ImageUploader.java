package com.main.junaidstore.libraries;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.main.junaidstore.activities.AddNewItem;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by Muazzam on 4/22/2017.
 */

public class ImageUploader extends AsyncTask<Bitmap,Void,String>{

    private String retail_price;
    private String original_price;
    private String catid;
    private String userid;
    private String access_token;

    private Activity activity;

    private NetworkInterface networkInterface;

    public ImageUploader(String originalprice, String retailprice, String catid,String userid,String access_token, Activity activity){
        this.retail_price = retailprice;
        this.original_price = originalprice;
        this.userid = userid;
        this.access_token = access_token;
        this.catid = catid;

        this.activity = activity;

        this.networkInterface = new NetworkInterface(activity);
    }

    @Override
    protected String doInBackground(Bitmap... params) {
        Bitmap bitmap = params[0];
        String uploadImage = getStringImage(bitmap);

        this.networkInterface.insertPost(uploadImage,this.retail_price,this.original_price,this.userid,this.access_token, this.catid,AddNewItem.CODE_INSERT_POST);
        return uploadImage;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(this.activity,"Uploading...",Toast.LENGTH_SHORT).show();
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
