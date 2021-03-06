package com.main.junaidstore.libraries;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.main.junaidstore.activities.AddNewItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Muazzam on 4/22/2017.
 */

public class ImageUploader extends AsyncTask<String,Void,String>{

    private String retail_price;
    private String original_price;
    private String catid;
    private String userid;
    private String access_token;
    private String rotationCount;

    private Activity activity;

    private NetworkInterface networkInterface;

    public ImageUploader(String originalprice, String retailprice, String catid,String userid,String access_token, Activity activity, String rotationCount){
        this.retail_price = retailprice;
        this.original_price = originalprice;
        this.userid = userid;
        this.access_token = access_token;
        this.catid = catid;
        this.rotationCount = rotationCount;

        this.activity = activity;

        this.networkInterface = new NetworkInterface(activity);
    }

    @Override
    protected String doInBackground(String... params) {
        File file = new File(params[0]);
        MultipartBody.Part requestFile = MultipartBody.Part.createFormData("image",file.getName(),RequestBody.create(MediaType.parse("image/*"), file));
        RequestBody retail_price = RequestBody.create(MediaType.parse("multipart/form-data"), this.retail_price);
        RequestBody original_price = RequestBody.create(MediaType.parse("multipart/form-data"), this.original_price);
        RequestBody access_token = RequestBody.create(MediaType.parse("multipart/form-data"), this.access_token);
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), this.userid);
        RequestBody catid = RequestBody.create(MediaType.parse("multipart/form-data"),this.catid);
        RequestBody rotationCount = RequestBody.create(MediaType.parse("multipart/form-data"),this.rotationCount);

        this.networkInterface.insertPost(requestFile,retail_price,original_price,userid,access_token,catid,rotationCount,AddNewItem.CODE_INSERT_POST);

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
