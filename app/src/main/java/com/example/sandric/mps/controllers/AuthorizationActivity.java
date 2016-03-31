package com.example.sandric.mps.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sandric.mps.R;
import com.example.sandric.mps.services.ProfileService;
import com.example.sandric.mps.tables.ProfileModel;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorizationActivity extends AppCompatActivity {


    private EditText usernameEditText;
    private EditText passwordEditText;

    private TextView errorTextView;


    private Button signInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);


        this.usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        this.passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        this.errorTextView = (TextView) findViewById(R.id.error_text_view);

        this.signInButton = (Button) findViewById(R.id.sign_in_button);
        this.signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Log.d("MYTAG", "signing in username: " + AuthorizationActivity.this.usernameEditText.getText() +
                        "password: " + AuthorizationActivity.this.passwordEditText.getText());

                if (AuthorizationActivity.this.usernameEditText.getText().length() == 0) {
                    AuthorizationActivity.this.onError("Username cannot be empty.");
                } else if (AuthorizationActivity.this.passwordEditText.getText().length() == 0) {
                    AuthorizationActivity.this.onError("Password cannot be empty.");
                } else {
                    AuthorizationActivity.this.signInRequest(AuthorizationActivity.this.usernameEditText.getText().toString(),
                            AuthorizationActivity.this.passwordEditText.getText().toString());
                }
            }
        });

        this.signUpButton = (Button) findViewById(R.id.sign_up_button);
        this.signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Log.d("MYTAG", "signing up username: " + AuthorizationActivity.this.usernameEditText.getText() +
                        "password: " + AuthorizationActivity.this.passwordEditText.getText());

                if (AuthorizationActivity.this.usernameEditText.getText().length() == 0) {
                    AuthorizationActivity.this.onError("Username cannot be empty.");
                } else if (AuthorizationActivity.this.passwordEditText.getText().length() == 0) {
                    AuthorizationActivity.this.onError("Password cannot be empty.");
                } else {
                    AuthorizationActivity.this.signUpRequest(AuthorizationActivity.this.usernameEditText.getText().toString(),
                            AuthorizationActivity.this.passwordEditText.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onStart () {

        Log.d("MYTAG", "ON START CALLED!");

        super.onStart();


        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);

        Log.d("MYTAG", "ID::: " + prefs.getString("id", ""));

        Log.d("MYTAG", "NAME::: " + prefs.getString("name", ""));


        if (getSharedPreferences("MyPref", MODE_PRIVATE).contains("id")) {

            Log.d("MYTAG", "CONTAINS");

            Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    private void onSuccess (ProfileModel profileModel) {

        ProfileActivity.saveProfile(profileModel, this.getApplicationContext());

        Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void onError (String error) {
        AuthorizationActivity.this.errorTextView.setText(error);
    }



    private void signInRequest (String username, String password) {

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

        UserParams userParams = new UserParams(username, password);

        Call<ProfileModel> call = service.signIn(userParams);
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                Log.d("MYTAG", "YESSSS");

                if (response.isSuccess()) {
                    AuthorizationActivity.this.onSuccess(response.body());
                } else {
                    try {
                        AuthorizationActivity.this.onError(response.errorBody().string());
                    } catch (IOException e) {}
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.d("MYTAG", "NOOOOO");

                AuthorizationActivity.this.onError("ERROR");
            }
        });
    }

    private void signUpRequest (String username, String password) {

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

        UserParams userParams = new UserParams(username, password);

        Call<ProfileModel> call = service.signUp(userParams);
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                Log.d("MYTAG", "YESSSS");

                if (response.isSuccess()) {
                    AuthorizationActivity.this.onSuccess(response.body());
                } else {
                    try {
                        AuthorizationActivity.this.onError(response.errorBody().string());
                    } catch (IOException e) {}
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.d("MYTAG", "NOOOOO");

                AuthorizationActivity.this.onError("ERROR");
            }
        });
    }



    public static class UserParams {

        @Expose
        public String username;

        @Expose
        public String password;

        public UserParams(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}