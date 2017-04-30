package com.main.junaidstore.models;

import android.os.StrictMode;

import org.parceler.Parcel;

/**
 * Created by Muazzam on 4/17/2017.
 */
@Parcel
public class Posts {

    private String image_path;
    private String original_price;
    private String retail_price;
    private String id;
    private String updated_at;
    private String created_at;
    private String category;
    private String categoryid;

    // The user can rotate image to make it straight
    private String rotation;

    public String getID(){ return this.id; }
    public String getUpdatedAt(){ return this.updated_at; }
    public String getCreatedAt(){ return this.created_at; }
    public String getOriginalPrice(){ return this.original_price; }
    public String getRetailPrice(){ return this.retail_price; }
    public String getImage(){ return this.image_path; }
    public String getCategory(){ return this.category; }
    public String getCategoryID(){ return this.categoryid; }
    public String getRotation() { return this.rotation; }

}
