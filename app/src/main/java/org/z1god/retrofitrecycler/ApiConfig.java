package org.z1god.retrofitrecycler;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    public static JsonPlaceHolder getApiService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.10.3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(JsonPlaceHolder.class);
    }
}
