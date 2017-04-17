package com.main.junaidstore.models;

import org.parceler.Parcel;

/**
 * Created by Muazzam on 4/17/2017.
 */
@Parcel
public class Response {

    private String type;
    private String message;

    public String getMessage(){ return this.message; }
    public String getType() { return this.type; }
}
