package com.main.junaidstore.libraries;

import android.app.Activity;
import android.widget.Toast;

import com.main.junaidstore.interfaces.INetworkInterface;
import com.main.junaidstore.models.Categories;
import com.main.junaidstore.models.Users;

import org.parceler.Parcels;

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
        Call<Categories> call = networkInterface.getCategories(userid,access_token);
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                int code = response.code();

                if(code == 201){
                    Categories req = response.body();
                    ((com.main.junaidstore.activities.Categories)NetworkInterface.this.activity).AsyncCallback(callbackCode, Parcels.wrap(req));
                }
                else{
                    Toast.makeText(activity,"Something went wrong, please try later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {

            }
        });
    }
    public void login(String username,String password,final int callbackCode){
        Call<Users> call = networkInterface.login(username,password);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                int code = response.code();

                if(code == 200){
                    Users req = response.body();
                    ((com.main.junaidstore.activities.login)NetworkInterface.this.activity).AsyncCallback(callbackCode, Parcels.wrap(req));
                }
                else{
                    Toast.makeText(activity,"Something went wrong, please try later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }
}
