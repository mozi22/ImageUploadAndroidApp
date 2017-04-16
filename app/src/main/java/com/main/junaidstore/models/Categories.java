package com.main.junaidstore.models;

/**
 * Created by Muazzam on 4/15/2017.
 */

public class Categories {

    private String name;
    private int id;

    public Categories(String name,int id){
        this.name = name;
        this.id = id;
    }

    public String getName(){ return this.name; }
    public int getID(){ return this.id; }
}
