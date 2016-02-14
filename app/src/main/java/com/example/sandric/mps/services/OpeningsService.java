package com.example.sandric.mps.services;

import com.example.sandric.mps.tables.OpeningModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sandric on 08.02.16.
 */
public interface OpeningsService {
    @GET("api/openings")
    Call<List<OpeningModel>> getOpenings();
}
