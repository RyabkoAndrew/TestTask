package com.example.testtask.webservice;

import android.app.Application;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static APIYoutube mApiYoutube;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        final String BASE_URL = "https://www.googleapis.com/youtube/";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();



        mApiYoutube = mRetrofit.create(APIYoutube.class);
    }

    public static APIYoutube getApi(){
        return mApiYoutube;
    }
}
