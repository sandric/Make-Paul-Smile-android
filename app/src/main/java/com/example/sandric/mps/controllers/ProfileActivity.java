package com.example.sandric.mps.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private Button signOutButton;


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


        this.signOutButton = (Button) findViewById(R.id.sign_out_button);
        this.signOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Log.d("MYTAG", "signing out");

                SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();

                editor.clear();

                editor.commit();


                Intent intent = new Intent(ProfileActivity.this, AuthorizationActivity.class);
                startActivity(intent);
            }
        });


        this.drawProfile();


        //this.getProfileFromRequest();
    }

    private void drawProfile () {

        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);

        this.userNameTextView.setText(prefs.getString("name", ""));

        this.bestGameGroupTextView.setText(prefs.getString("bestGameGroup", ""));
        this.bestGameScoreTextView.setText("" + prefs.getInt("bestGameScore", 0));

        this.bestOpenScoreTextView.setText("" + prefs.getInt("Open", 0));
        this.bestSemiOpenScoreTextView.setText("" + prefs.getInt("Semi-open", 0));
        this.bestClosedScoreTextView.setText("" + prefs.getInt("Closed", 0));
        this.bestSemiClosedScoreTextView.setText("" + prefs.getInt("Semi-closed", 0));
        this.bestIndianDefenceScoreTextView.setText("" + prefs.getInt("Indian-defence", 0));
        this.bestFlankScoreTextView.setText("" + prefs.getInt("Flank", 0));
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
                ProfileActivity.this.saveProfile(response.body(), ProfileActivity.this.getApplicationContext());
                ProfileActivity.this.drawProfile();
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.d("MYTAG", "NOOOOO");
            }
        });
    }


    protected static void saveProfile (ProfileModel profileModel, Context context) {

        SharedPreferences.Editor editor = context.getSharedPreferences("MyPref", MODE_PRIVATE).edit();

        editor.clear();

        editor.putString("id", profileModel.id);

        editor.putString("name", profileModel.name);

        editor.putString("bestGameGroup", profileModel.best_game.groupname);
        editor.putInt("bestGameScore", profileModel.best_game.score);

        for (GameModel game : profileModel.best_games) {
            editor.putInt(game.groupname, game.score);
        }

        editor.commit();
    }
}