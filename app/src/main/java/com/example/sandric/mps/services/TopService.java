package com.example.sandric.mps.services;

import com.example.sandric.mps.tables.OpeningModel;
import com.example.sandric.mps.tables.TopGameModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sandric on 15.02.16.
 */
public interface TopService {
    @GET("api/top")
    Call<ArrayList<TopGameModel>> getTopGames();
}
