package com.main.junaidstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.main.junaidstore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Muazzam on 4/16/2017.
 */

public class login  extends AppCompatActivity {

    @BindView(R.id.login_btn_login) Button login_btn_login;
    @BindView(R.id.login_txt_password) EditText login_txt_password;
    @BindView(R.id.login_txt_username) EditText login_txt_username;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        getSupportActionBar().hide();


        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,MainActivity.class));
            }
        });
    }
}