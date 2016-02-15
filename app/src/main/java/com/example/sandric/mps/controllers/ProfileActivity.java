package com.example.sandric.mps.controllers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.sandric.mps.R;
import com.example.sandric.mps.services.ProfileService;
import com.example.sandric.mps.services.TopService;
import com.example.sandric.mps.tables.GameModel;
import com.example.sandric.mps.tables.ProfileModel;
import com.example.sandric.mps.tables.TopGameModel;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private TextView userNameTextView;


    private TextView bestGameGroupTextView;
    private TextView bestGameScoreTextView;

    private TextView bestOpenScoreTextView;
    private TextView bestSemiOpenScoreTextView;
    private TextView bestClosedScoreTextView;
    private TextView bestSemiClosedScoreTextView;
    private TextView bestIndianDefenceScoreTextView;
    private TextView bestFlankScoreTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.userNameTextView = (TextView)findViewById(R.id.username_text_view);

        this.bestGameGroupTextView = (TextView)findViewById(R.id.best_game_group_text_view);
        this.bestGameScoreTextView = (TextView)findViewById(R.id.best_game_score_text_view);

        this.bestOpenScoreTextView = (TextView)findViewById(R.id.best_open_score_text_view);
        this.bestSemiOpenScoreTextView = (TextView)findViewById(R.id.best_semi_open_score_text_view);
        this.bestClosedScoreTextView = (TextView)findViewById(R.id.best_closed_score_text_view);
        this.bestSemiClosedScoreTextView = (TextView)findViewById(R.id.best_semi_closed_score_text_view);
        this.bestIndianDefenceScoreTextView = (TextView)findViewById(R.id.best_indian_defence_score_text_view);
        this.bestFlankScoreTextView = (TextView)findViewById(R.id.best_flank_score_text_view);


        this.drawProfile();


        //this.getProfileFromRequest();
    }



    private void saveProfile (ProfileModel profileModel) {

        SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();

        editor.putString("name", profileModel.name);

        editor.putString("bestGameGroup", profileModel.best_game.group);
        editor.putInt("bestGameScore", profileModel.best_game.score);

        for (GameModel game : profileModel.best_games_by_group) {
            editor.putInt("best" + game.group + "GameScore", game.score);
        }

        editor.commit();

        this.drawProfile();
    }

    private void drawProfile () {

        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);

        this.userNameTextView.setText(prefs.getString("name", ""));

        this.bestGameGroupTextView.setText(prefs.getString("bestGameGroup", ""));
        this.bestGameScoreTextView.setText("" + prefs.getInt("bestGameScore", 0));

        this.bestOpenScoreTextView.setText("" + prefs.getInt("bestOpenGameScore", 0));
        this.bestSemiOpenScoreTextView.setText("" + prefs.getInt("bestSemiOpenGameScore", 0));
        this.bestClosedScoreTextView.setText("" + prefs.getInt("bestClosedGameScore", 0));
        this.bestSemiClosedScoreTextView.setText("" + prefs.getInt("bestSemiClosedGameScore", 0));
        this.bestIndianDefenceScoreTextView.setText("" + prefs.getInt("bestIndianDefenceGameScore", 0));
        this.bestFlankScoreTextView.setText("" + prefs.getInt("bestFlankGameScore", 0));
    }



    private void getProfileFromRequest () {

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

        ProfileService service = retrofit.create(ProfileService.class);

        Call<ProfileModel> call = service.getProfile();
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                ProfileActivity.this.saveProfile(response.body());
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.d("MYTAG", "NOOOOO");
            }
        });
    }
}