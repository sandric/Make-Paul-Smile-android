package com.example.sandric.mps.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.example.sandric.mps.R;
import com.example.sandric.mps.services.OpeningsService;
import com.example.sandric.mps.services.TopService;
import com.example.sandric.mps.tables.OpeningModel;
import com.example.sandric.mps.tables.TopGameModel;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TopActivity extends AppCompatActivity {

    private ListView topListView;

    //private String[] topGames = { "Tosha 1", "Tosha 2", "Tosha 3", "Tosha 4", "Tosha 5", "Tosha 6" };

    private ArrayList<TopGameModel> topGameModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);


        this.topListView = (ListView) findViewById(R.id.top_list_view);

        this.getTopGamesFromRequest();
    }


    private class TopAdapter extends ArrayAdapter<TopGameModel> {
        TopAdapter(ArrayList<TopGameModel> topGames) {
            super(TopActivity.this, R.layout.top_item, R.id.username_text_view, topGames);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            TextView groupTextView = (TextView)convertView.findViewById(R.id.group_text_view);
            TextView userNameTextView = (TextView)convertView.findViewById(R.id.username_text_view);
            TextView scoreTextView = (TextView)convertView.findViewById(R.id.score_text_view);

            groupTextView.setText(TopActivity.this.topGameModels.get(position).group);
            userNameTextView.setText(TopActivity.this.topGameModels.get(position).username);
            scoreTextView.setText(TopActivity.this.topGameModels.get(position).score);

            return convertView;
        }
    }


    public void getTopGamesFromRequest () {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://10.0.3.2:8080")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .excludeFieldsWithoutExposeAnnotation()
                                .create()
                ))
                .build();

        TopService service = retrofit.create(TopService.class);

        Call<ArrayList<TopGameModel>> call = service.getTopGames();
        call.enqueue(new Callback<ArrayList<TopGameModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TopGameModel>> call, Response<ArrayList<TopGameModel>> response) {
                TopActivity.this.topGameModels = response.body();

                TopActivity.this.topListView.setAdapter(new TopAdapter(TopActivity.this.topGameModels));
            }

            @Override
            public void onFailure(Call<ArrayList<TopGameModel>> call, Throwable t) {
                Log.d("MYTAG", "NOOOOO");
            }
        });
    }
}
