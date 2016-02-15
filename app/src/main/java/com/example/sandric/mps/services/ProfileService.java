package com.example.sandric.mps.services;

import com.example.sandric.mps.tables.ProfileModel;
import com.example.sandric.mps.tables.TopGameModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sandric on 15.02.16.
 */
public interface ProfileService {
    @GET("api/users/7")
    Call<ProfileModel> getProfile();
}
