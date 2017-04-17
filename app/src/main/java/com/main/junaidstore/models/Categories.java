package com.main.junaidstore.models;

import org.parceler.Parcel;

/**
 * Created by Muazzam on 4/15/2017.
 */
@Parcel
public class Categories {

    private String id;
    private String category;
    private String updated_at;
    private String created_at;


    public String getCategory(){ return this.category; }
    public String getUpdatedAt(){ return this.updated_at; }
    public String getCreatedAt(){ return this.created_at; }
    public String getID(){ return this.id; }
}
