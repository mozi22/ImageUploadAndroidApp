package com.main.junaidstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.main.junaidstore.R;
import com.main.junaidstore.interfaces.AsyncCallback;
import com.main.junaidstore.libraries.GeneralFunctions;
import com.main.junaidstore.libraries.NetworkInterface;
import com.main.junaidstore.models.Users;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Muazzam on 4/16/2017.
 */

public class login  extends AppCompatActivity implements AsyncCallback{

    @BindView(R.id.login_btn_login) Button login_btn_login;
    @BindView(R.id.login_txt_password) EditText login_txt_password;
    @BindView(R.id.login_txt_username) EditText login_txt_username;

    public static final int CODE_LOGIN_USER = 1;

    private NetworkInterface networkInterface;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        getSupportActionBar().hide();
        this.networkInterface = new NetworkInterface(this);


        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(proceedForward()){
                    networkInterface.login(login_txt_username.getText().toString(),login_txt_password.getText().toString(),CODE_LOGIN_USER);
                }
            }
        });
    }


    private boolean proceedForward(){

        if(login_txt_password.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Provide a password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(login_txt_username.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Provide a username", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void AsyncCallback(int resultCode, Parcelable rf) {
        if(resultCode == CODE_LOGIN_USER){
            Users user = Parcels.unwrap(rf);

            if(user.getResponse().getType() == "404"){
                Toast.makeText(getApplicationContext(),"Incorrect Username or Password",Toast.LENGTH_SHORT).show();
                return;
            }

            GeneralFunctions.addSessionValue(login.this,getResources().getString(R.string.access_token),user.getAccessToken());
            GeneralFunctions.addSessionValue(login.this,getResources().getString(R.string.usertype),user.getUserType());
            GeneralFunctions.addSessionValue(login.this,getResources().getString(R.string.userid),user.getUserId());

            startActivity(new Intent(login.this,MainActivity.class));
        }
    }
}