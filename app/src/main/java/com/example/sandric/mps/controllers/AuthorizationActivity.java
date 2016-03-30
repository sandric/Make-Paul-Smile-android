package com.example.sandric.mps.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sandric.mps.R;

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
                    AuthorizationActivity.this.onSuccess();
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
                    AuthorizationActivity.this.onSuccess();
                }
            }
        });
    }

    private void onSuccess () {
        Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void onError (String error) {
        AuthorizationActivity.this.errorTextView.setText(error);
    }
}

