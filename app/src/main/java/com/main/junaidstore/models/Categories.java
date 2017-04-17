package com.main.junaidstore.models;

/**
 * Created by Muazzam on 4/15/2017.
 */

public class Categories {

    private int id;
    private String category;
    private String updated_at;
    private String created_at;


    public String getCategory(){ return this.category; }
    public String getUpdatedAt(){ return this.updated_at; }
    public String getCreatedAt(){ return this.created_at; }
    public int getID(){ return this.id; }
}
