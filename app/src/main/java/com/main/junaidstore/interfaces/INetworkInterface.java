package com.main.junaidstore.interfaces;

import com.main.junaidstore.models.Categories;
import com.main.junaidstore.models.Posts;
import com.main.junaidstore.models.Users;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Muazzam on 4/17/2017.
 */

public interface INetworkInterface {

    @POST("_category/insert_category")
    Call<Categories> InsertCategory(@Query("category") String category,
                                    @Query("userid") int userid,
                                    @Query("access_token") String access_token);

    @POST("_category/delete_category")
    Call<Categories> DeleteCategory(@Query("categoryid") int categoryid,
                                    @Query("userid") int userid,
                                    @Query("access_token") String access_token);

    @GET("_category/get_categories")
    Call<Categories> getCategories(@Query("userid") int userid,
                                   @Query("access_token") String access_token);

    @POST("_post/insert_post")
    Call<Posts> insertPost(@Query("image") String image,
                           @Query("retail_price") double retail_price,
                           @Query("original_price") double original_price,
                           @Query("userid") int userid,
                           @Query("access_token") String access_token,
                           @Query("categoryid") int categoryid
                           );

    @POST("_user/login")
    Call<Users> insertPost(@Query("username") String username,
                           @Query("password") String password);
}
