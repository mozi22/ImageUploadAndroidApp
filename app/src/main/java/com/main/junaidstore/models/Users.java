package com.main.junaidstore.models;

import org.parceler.Parcel;

/**
 * Created by Muazzam on 4/17/2017.
 */
@Parcel
public class Users {
    private String id;
    private String access_token;
    private String user_type;


    public String getUserId() { return this.id; }
    public String getAccessToken() { return this.access_token; }
    public String getUserType() { return this.user_type; }
}
