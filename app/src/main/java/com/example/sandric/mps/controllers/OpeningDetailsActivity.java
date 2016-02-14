package com.example.sandric.mps.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.sandric.mps.R;
import com.example.sandric.mps.models.Opening;

public class OpeningDetailsActivity extends AppCompatActivity {

    public static final String EXTRA = "OpeningDetailsActivityExtra";


    private TextView openingNameTextView;

    private TextView openingDetailsTextView;


    private Opening opening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_details);


        this.opening = (Opening)getIntent().getSerializableExtra(OpeningDetailsActivity.EXTRA);

        this.openingNameTextView = (TextView)findViewById(R.id.opening_name_text_view);
        this.openingDetailsTextView = (TextView)findViewById(R.id.opening_details_text_view);


        this.openingNameTextView.setText(this.opening.name);
        this.openingDetailsTextView.setText(this.opening.details);
    }
}
