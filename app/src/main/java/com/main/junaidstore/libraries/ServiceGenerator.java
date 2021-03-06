package com.main.junaidstore.libraries;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Muazzam on 4/17/2017.
 */

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://www.celinemoden.de/index.php/";
    public static final String API_IMAGE_LOAD_URL = "http://www.celinemoden.de/uploads/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static retrofit2.Retrofit.Builder builder =
            new retrofit2.Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        retrofit2.Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
