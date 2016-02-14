package com.example.sandric.mps.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.sandric.mps.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.userNameTextView = (TextView)findViewById(R.id.username_text_view);
        this.userNameTextView.setText("TOHANCHO");
    }
}
