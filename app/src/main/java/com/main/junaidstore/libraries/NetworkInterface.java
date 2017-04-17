package com.main.junaidstore.libraries;

import android.app.Activity;

import com.main.junaidstore.interfaces.INetworkInterface;

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
}
