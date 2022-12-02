package com.hummingtech.zetrixwebpractical.retrofit;


import com.hummingtech.zetrixwebpractical.model.CategoryModel;
import com.hummingtech.zetrixwebpractical.model.LoginModel;
import com.hummingtech.zetrixwebpractical.model.LoginResponseModel;
import com.hummingtech.zetrixwebpractical.model.ResponseModel;
import com.hummingtech.zetrixwebpractical.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("login")
    Call<LoginResponseModel> callLogin(
            @Body LoginModel loginModel
    );

    @GET("unknown")
    Call<ResponseModel<CategoryModel>> callCategory(
            @Query("per_page") int perPage
    );

    @GET("users")
    Call<ResponseModel<UserModel>> callUser(
            @Query("per_page") int perPage
    );

}

