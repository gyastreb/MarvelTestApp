package com.gyastreb.testproject;

import android.content.Context;
import android.util.Log;


import com.gyastreb.testproject.models.Main;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by gyastreb on 9/9/16.
 */
public class ApiController {

    private RequestApiInterface requestApiInterface;
    private Context mContext;
    private Retrofit retrofit;
    public String serverUrl = "http://gateway.marvel.com:80";


    public ApiController(Context context) {
        this.mContext = context;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        requestApiInterface = retrofit.create(RequestApiInterface.class);
    }

    public Call<Main> getProduct(Map<String, String> parameters) {
        Log.d("param", "" + parameters);
        return requestApiInterface.getCharacters(parameters);
    }


    public interface RequestApiInterface {
        @GET("/v1/public/characters")
        Call<Main> getCharacters(@QueryMap Map<String, String> parameters);
    }
}
