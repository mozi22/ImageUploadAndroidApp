package com.main.junaidstore.models;

import com.main.junaidstore.ParcelConverter.ParcelResponseConverter;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

/**
 * Created by Muazzam on 4/17/2017.
 */
@Parcel
public class Users {
    private String id;
    private String access_token;
    private String user_type;

    @ParcelPropertyConverter(ParcelResponseConverter.class)
    private Response response;

    public String getUserId() { return this.id; }
    public String getAccessToken() { return this.access_token; }
    public Response getResponse(){ return this.response; }
    public String getUserType() { return this.user_type; }
}
