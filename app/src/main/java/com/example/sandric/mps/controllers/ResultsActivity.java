package com.example.sandric.mps.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.sandric.mps.R;


public class ResultsActivity extends AppCompatActivity {

    public static final String EXTRA_GROUP = "ResultsActivityExtraGroup";
    public static final String EXTRA_SCORE = "ResultsActivityExtraScore";


    private TextView gameGroupTextView;
    private TextView gameScoreTextView;

    private TextView gameResultTextView;

    private TextView previousGameGroupTextView;
    private TextView previousGameScoreTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        this.gameGroupTextView = (TextView) findViewById(R.id.game_group_text_view);
        this.gameScoreTextView = (TextView) findViewById(R.id.game_score_text_view);

        this.gameResultTextView = (TextView) findViewById(R.id.game_result_text_view);

        this.previousGameGroupTextView = (TextView) findViewById(R.id.previous_game_group_text_view);
        this.previousGameScoreTextView = (TextView) findViewById(R.id.previous_score_group_text_view);


        Bundle extras = getIntent().getExtras();
        String group = extras.getString(EXTRA_GROUP);
        int score = extras.getInt(EXTRA_SCORE);


        this.gameGroupTextView.setText(group);
        this.gameScoreTextView.setText("" + score);
    }
}
