package com.main.junaidstore.libraries;

import android.app.Activity;
import android.widget.Toast;

import com.main.junaidstore.activities.AddNewItem;
import com.main.junaidstore.interfaces.INetworkInterface;
import com.main.junaidstore.models.Categories;
import com.main.junaidstore.models.Users;

import org.parceler.Parcels;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Muazzam on 4/17/2017.
 */

public class NetworkInterface {
    INetworkInterface networkInterface;
    Activity activity;


    public NetworkInterface(Activity ac){
        networkInterface = ServiceGenerator.createService(INetworkInterface.class);
        activity = ac;
    }
    public void getCategories(String userid,String access_token,final int callbackCode){
        Call<com.main.junaidstore.models.Response> call = networkInterface.getCategories(userid,access_token);
        call.enqueue(new Callback<com.main.junaidstore.models.Response>() {

            @Override
            public void onResponse(Call<com.main.junaidstore.models.Response> call, Response<com.main.junaidstore.models.Response> response) {
                int code = response.code();

                if(code == 200){
                    com.main.junaidstore.models.Response req = response.body();

                    if(callbackCode == AddNewItem.CODE_GET_POSTING_PAGE_CATEGORIES){
                        ((com.main.junaidstore.activities.AddNewItem)NetworkInterface.this.activity).AsyncCallback(callbackCode, Parcels.wrap(req));
                    }
                    else{
                        ((com.main.junaidstore.activities.Categories)NetworkInterface.this.activity).AsyncCallback(callbackCode, Parcels.wrap(req));
                    }
                }
                else{
                    Toast.makeText(activity,"Something went wrong, please try later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.main.junaidstore.models.Response> call, Throwable t) {
            }
        });
    }
    public void insertCategory(String userid,String access_token,String category,final int callbackCode){
        Call<com.main.junaidstore.models.Response> call = networkInterface.insertCategory(category,userid,access_token);
        call.enqueue(new Callback<com.main.junaidstore.models.Response>() {

            @Override
            public void onResponse(Call<com.main.junaidstore.models.Response> call, Response<com.main.junaidstore.models.Response> response) {
                int code = response.code();

                if(code == 200){
                    com.main.junaidstore.models.Response req = response.body();
                    ((com.main.junaidstore.activities.Categories)NetworkInterface.this.activity).AsyncCallback(callbackCode, Parcels.wrap(req));
                }
                else{
                    Toast.makeText(activity,"Something went wrong, please try later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.main.junaidstore.models.Response> call, Throwable t) {

            }
        });
    }
    public void deleteCategory(String userid,String access_token,String categoryid,final int callbackCode){
        Call<com.main.junaidstore.models.Response> call = networkInterface.deleteCategory(categoryid,userid,access_token);
        call.enqueue(new Callback<com.main.junaidstore.models.Response>() {

            @Override
            public void onResponse(Call<com.main.junaidstore.models.Response> call, Response<com.main.junaidstore.models.Response> response) {
                int code = response.code();

                if(code == 200){
                    com.main.junaidstore.models.Response req = response.body();
                    ((com.main.junaidstore.activities.Categories)NetworkInterface.this.activity).AsyncCallback(callbackCode, Parcels.wrap(req));
                }
                else{
                    Toast.makeText(activity,"Something went wrong, please try later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.main.junaidstore.models.Response> call, Throwable t) {

            }
        });
    }

    public void insertPost(MultipartBody.Part image, RequestBody retail_price, RequestBody original_price, RequestBody userid, RequestBody access_token, RequestBody catid, final int callbackCode){

        Call<com.main.junaidstore.models.Response> call = networkInterface.insertPost(image,retail_price,original_price,userid,access_token,catid);
        call.enqueue(new Callback<com.main.junaidstore.models.Response>() {
            @Override
            public void onResponse(Call<com.main.junaidstore.models.Response> call, Response<com.main.junaidstore.models.Response> response) {
                int code = response.code();

                if(code == 200){
                    com.main.junaidstore.models.Response req = response.body();
                    ((com.main.junaidstore.activities.AddNewItem)NetworkInterface.this.activity).AsyncCallback(callbackCode, Parcels.wrap(req));
                }
                else{
                    Toast.makeText(activity,"Something went wrong, please try later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.main.junaidstore.models.Response> call, Throwable t) {

            }
        });
    }

    public void login(String username,String password,final int callbackCode){
        Call<com.main.junaidstore.models.Response> call = networkInterface.login(username,password);
        call.enqueue(new Callback<com.main.junaidstore.models.Response>() {
            @Override
            public void onResponse(Call<com.main.junaidstore.models.Response> call, Response<com.main.junaidstore.models.Response> response) {
                int code = response.code();

                if(code == 200){
                    com.main.junaidstore.models.Response req = response.body();
                    ((com.main.junaidstore.activities.login)NetworkInterface.this.activity).AsyncCallback(callbackCode, Parcels.wrap(req));
                }
                else{
                    Toast.makeText(activity,"Something went wrong, please try later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.main.junaidstore.models.Response> call, Throwable t) {

            }
        });
    }
}
