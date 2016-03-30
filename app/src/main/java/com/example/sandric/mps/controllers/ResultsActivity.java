package com.example.sandric.mps.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sandric.mps.R;


public class ResultsActivity extends AppCompatActivity {

    public static final String EXTRA_GROUP = "ResultsActivityExtraGroup";
    public static final String EXTRA_SCORE = "ResultsActivityExtraScore";

    private String group;
    private int score;
    private int bestScore;

    private TextView gameGroupTextView;
    private TextView gameScoreTextView;

    private TextView gameResultTextView;

    private TextView previousBestGameGroupTextView;
    private TextView previousBestGameScoreTextView;


    private Button OKButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        Bundle extras = getIntent().getExtras();
        this.group = extras.getString(EXTRA_GROUP);
        this.score = extras.getInt(EXTRA_SCORE);


        this.gameGroupTextView = (TextView) findViewById(R.id.game_group_text_view);
        this.gameScoreTextView = (TextView) findViewById(R.id.game_score_text_view);

        this.gameResultTextView = (TextView) findViewById(R.id.game_result_text_view);

        this.previousBestGameGroupTextView = (TextView) findViewById(R.id.previous_best_game_group_text_view);
        this.previousBestGameScoreTextView = (TextView) findViewById(R.id.previous_best_game_score_text_view);

        this.OKButton = (Button) findViewById(R.id.OK_button);


        this.gameGroupTextView.setText(this.group);
        this.gameScoreTextView.setText("" + this.score);

        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);

        this.bestScore = prefs.getInt(this.group, 0);


        this.previousBestGameGroupTextView.setText("Previous best " + this.group + " result: ");
        this.previousBestGameScoreTextView.setText("" + this.bestScore);


        if (this.score > this.bestScore) {

            SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
            editor.putInt(this.group, this.score);

            editor.commit();

            this.gameResultTextView.setText("You increased your previous result in " + this.group + " game. Good Job!");
        } else if (this.score == this.bestScore) {
            this.gameResultTextView.setText("You repeated your previous result in " + this.group + " game. Just one point to succeed!");
        } else {
            this.gameResultTextView.setText("You can and actually already did better in " + this.group + " game. I'm disappointed...");
        }


        OKButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
