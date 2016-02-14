package com.example.sandric.mps.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sandric.mps.R;
import com.example.sandric.mps.interfaces.BoardActivityInterface;
import com.example.sandric.mps.models.Opening;
import com.example.sandric.mps.views.BoardLayout;

import java.util.ArrayList;
import java.util.Random;

public class TrainingActivity extends AppCompatActivity implements BoardActivityInterface {

    public static final String EXTRA = "TrainingActivityExtra";


    private static Random random = new Random();


    private Opening opening;

    private String selectedOpeningsGroup;
    private ArrayList<Opening> openings;

    private TextView openingNameTextView;
    private BoardLayout boardLayout;
    private TextView movesTextView;

    private Button endGameButton;
    private Button skipButton;
    private Button hintButton;

    private TextView openingsLeftTextView;
    private TextView scoreTextView;


    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);


        this.selectedOpeningsGroup = (String)getIntent().getSerializableExtra(TrainingActivity.EXTRA);

        this.openings = Opening.getOpeningsByGroupName(this.selectedOpeningsGroup);

        this.openingNameTextView = (TextView)findViewById(R.id.opening_name_text_view);

        this.boardLayout = (BoardLayout)findViewById(R.id.board_view);

        this.movesTextView = (TextView)findViewById(R.id.moves_text_view);

        this.scoreTextView = (TextView)this.findViewById(R.id.score_text_view);

        this.openingsLeftTextView = (TextView)this.findViewById(R.id.openings_left_text_view);


        this.clearScore();


        this.endGameButton = (Button)this.findViewById(R.id.end_game_button);
        this.endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingActivity.this.endGame();
            }
        });

        this.skipButton = (Button)this.findViewById(R.id.skip_button);
        this.skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingActivity.this.nextOpening();
            }
        });

        this.hintButton = (Button)this.findViewById(R.id.hint_button);
        this.hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingActivity.this.boardLayout.board.highlightHint();
                TrainingActivity.this.boardLayout.draw();
                TrainingActivity.this.decreaseScore();
            }
        });


        this.generateBoardLayout();
    }


    private void generateBoardLayout () {

        this.getRandomOpening();

        this.openingNameTextView.setText(this.opening.name);

        this.boardLayout.removeAllViews();

        this.boardLayout.generate("training", this.opening, this);

        this.openingsLeftTextView.setText("" + this.openings.size());

        this.movesTextView.setText("");
    }


    private void nextOpening () {
        if (this.openings.size() > 0)
            this.generateBoardLayout();
        else
            this.endGame();
    }

    private void endGame () {
        Intent intent = new Intent(this, ResultsActivity.class);
        Bundle extras = new Bundle();

        extras.putString(ResultsActivity.EXTRA_GROUP, this.selectedOpeningsGroup);
        extras.putInt(ResultsActivity.EXTRA_SCORE, this.score);

        intent.putExtras(extras);

        startActivity(intent);
    }


    private void updateScoreLabel () {
        this.scoreTextView.setText("" + this.score);
    }

    private void increaseScore () {
        this.score++;
        this.updateScoreLabel();
    }

    private void decreaseScore () {
        this.score--;
        this.updateScoreLabel();
    }

    private void clearScore () {
        this.score = 0;
        this.updateScoreLabel();
    }


    private void getRandomOpening () {
        TrainingActivity.random = new Random();
        Opening randomOpening = this.openings.get(TrainingActivity.random.nextInt(this.openings.size()));
        this.openings.remove(randomOpening);
        this.opening = randomOpening;
    }

    // Board activity interface


    public void onBoardGenerated() {
        Log.d("MYTAG", "Board generated learning");
    }

    public void onPlayerMadeMove() {
        Log.d("MYTAG", "Player Made move learning");
        this.boardLayout.draw();
        this.increaseScore();
    }

    public void onPlayerMadeMistake() {
        Log.d("MYTAG", "Player Made mistake move learning");
        this.decreaseScore();
    }

    public void onComputerMadeMove() {
        Log.d("MYTAG", "Computer Made move learning");
        this.boardLayout.draw();
    }

    public void onGameEnded() {
        Log.d("MYTAG", "Game ended learning");

        this.nextOpening();
    }


    public void displayMessage(String message) {
        Log.d("MYTAG", "Displaying message learning");
    }

    public void displayMoveNotation(String message) {
        Log.d("MYTAG", "Displaying move notation learning");
        this.movesTextView.append(message);
    }
}
