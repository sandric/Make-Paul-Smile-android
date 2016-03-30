package com.example.sandric.mps.services;

import com.example.sandric.mps.controllers.AuthorizationActivity;
import com.example.sandric.mps.tables.ProfileModel;
import com.example.sandric.mps.tables.TopGameModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by sandric on 15.02.16.
 */
public interface ProfileService {

    @GET("api/users/7")
    Call<ProfileModel> getProfile();

    @POST("api/users/")
    Call<ProfileModel> signUp(@Body AuthorizationActivity.UserParams userParams);

    @POST("api/sessions/")
    Call<ProfileModel> signIn(@Body AuthorizationActivity.UserParams userParams);
}
