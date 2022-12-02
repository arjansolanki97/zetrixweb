package com.hummingtech.zetrixwebpractical.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static OkHttpClient okHttpClient;
    public static ApiInterface apiInterface;

    public static Retrofit getClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        if (BuildConfig.DEBUG)
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        try {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(500, TimeUnit.SECONDS)
                    .writeTimeout(500, TimeUnit.SECONDS)
                    .connectTimeout(500, TimeUnit.SECONDS)
                    .addInterceptor(chain -> {
                        Request.Builder builder = chain.request().newBuilder();

                        builder.addHeader("Accept", "application/json").build();
                        Request request = builder.build();
                        return chain.proceed(request);
                    })
                    .hostnameVerifier((s, sslSession) -> true)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient.newBuilder().addInterceptor(httpLoggingInterceptor).build())
                    .baseUrl("https://reqres.in/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiInterface getApiInterface(){

        if(apiInterface==null){
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
        }
        return apiInterface;
    }
}
