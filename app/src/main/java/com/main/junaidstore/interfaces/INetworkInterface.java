package com.main.junaidstore.interfaces;

import com.main.junaidstore.models.Categories;
import com.main.junaidstore.models.Posts;
import com.main.junaidstore.models.Users;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Muazzam on 4/17/2017.
 */

public interface INetworkInterface {

    @FormUrlEncoded
    @POST("_category/insert_category")
    Call<com.main.junaidstore.models.Response> insertCategory(@Field("category") String category,
                                    @Field("userid") String userid,
                                    @Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("_category/delete_category")
    Call<com.main.junaidstore.models.Response> deleteCategory(@Field("categoryid") String categoryid,
                                    @Field("userid") String userid,
                                    @Field("access_token") String access_token);

    @GET("_category/get_categories")
    Call<com.main.junaidstore.models.Response> getCategories(@Query("userid") String userid,
                                   @Query("access_token") String access_token);

    @FormUrlEncoded
    @POST("_post/insert_post")
    Call<com.main.junaidstore.models.Response> insertPost(@Field("image") String image,
                           @Field("retail_price") String retail_price,
                           @Field("original_price") String original_price,
                           @Field("userid") String userid,
                           @Field("access_token") String access_token,
                           @Field("categoryid") String categoryid
                           );

    @FormUrlEncoded
    @POST("_user/login")
    Call<com.main.junaidstore.models.Response> login(@Field("username") String username,
                           @Field("password") String password);
}
