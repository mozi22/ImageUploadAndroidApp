package com.main.junaidstore.models;

import com.google.gson.annotations.SerializedName;
import com.main.junaidstore.ParcelConverter.ParcelCategoriesConverter;
import com.main.junaidstore.ParcelConverter.ParcelPostsConverter;
import com.main.junaidstore.ParcelConverter.ParcelUsersConverter;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.List;

/**
 * Created by Muazzam on 4/17/2017.
 */
@Parcel
public class Response {

    private String type;
    private String message;


    @ParcelPropertyConverter(ParcelUsersConverter.class)
    private Users users;

    @ParcelPropertyConverter(ParcelCategoriesConverter.class)
    private List<Categories> categories;

    @ParcelPropertyConverter(ParcelPostsConverter.class)
    private  Posts posts;

    public Users getUsers(){ return this.users; }
    public List<Categories> getCategories(){ return  this.categories; }
    public Posts getPosts(){ return this.posts; }
    public String getMessage(){ return this.message; }
    public String getType() { return this.type; }
}
