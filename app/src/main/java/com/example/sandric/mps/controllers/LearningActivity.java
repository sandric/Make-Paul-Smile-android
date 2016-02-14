package com.example.sandric.mps.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sandric.mps.R;
import com.example.sandric.mps.interfaces.BoardActivityInterface;
import com.example.sandric.mps.models.Opening;
import com.example.sandric.mps.views.BoardLayout;

public class LearningActivity extends AppCompatActivity implements BoardActivityInterface {

    public static final String EXTRA = "LearningActivityExtra";


    private TextView openingNameTextView;

    private BoardLayout boardLayout;

    private TextView movesTextView;
    
    private Button restartButton;
    private Button playButton;
    private Button stepButton;

    private Button detailsButton;


    private Opening opening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);


        this.opening = (Opening)getIntent().getSerializableExtra(LearningActivity.EXTRA);

        this.openingNameTextView = (TextView)findViewById(R.id.opening_name_text_view);

        this.boardLayout = (BoardLayout)findViewById(R.id.board_view);

        this.movesTextView = (TextView)findViewById(R.id.moves_text_view);

        this.restartButton = (Button)this.findViewById(R.id.restart_button);
        this.restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LearningActivity.this.boardLayout.stop();
                LearningActivity.this.playButton.setText("Play");
                LearningActivity.this.generateBoardLayout();
            }
        });

        this.playButton = (Button)this.findViewById(R.id.play_button);
        this.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Button)v).getText().toString().equals("Play")) {
                    LearningActivity.this.boardLayout.play();
                    LearningActivity.this.stepButton.setEnabled(false);
                    ((Button) v).setText("Stop");

                    LearningActivity.this.restartButton.setEnabled(true);
                } else {
                    LearningActivity.this.boardLayout.stop();
                    LearningActivity.this.stepButton.setEnabled(true);
                    ((Button) v).setText("Play");
                }
            }
        });

        this.stepButton = (Button)this.findViewById(R.id.step_button);
        this.stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LearningActivity.this.boardLayout.step();

                LearningActivity.this.restartButton.setEnabled(true);
            }
        });


        this.detailsButton = (Button)this.findViewById(R.id.details_button);
        this.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningActivity.this, OpeningDetailsActivity.class);
                intent.putExtra(OpeningDetailsActivity.EXTRA, LearningActivity.this.opening);

                startActivity(intent);
            }
        });



        this.generateBoardLayout();
    }


    private void generateBoardLayout () {

        this.openingNameTextView.setText(this.opening.name);

        this.boardLayout.removeAllViews();

        this.boardLayout.generate("learning", opening, this);

        this.restartButton.setEnabled(false);
        this.stepButton.setEnabled(true);
        this.playButton.setEnabled(true);

        this.movesTextView.setText("");
    }

    // Board activity interface


    public void onBoardGenerated() {
        Log.d("MYTAG", "Board generated learning");
    }

    public void onPlayerMadeMove() {
        Log.d("MYTAG", "Player Made move learning");
        this.boardLayout.draw();
    }

    public void onPlayerMadeMistake() {
        Log.d("MYTAG", "Player Made mistake move learning");
    }

    public void onComputerMadeMove() {
        Log.d("MYTAG", "Computer Made move learning");
        this.boardLayout.draw();
    }

    public void onGameEnded() {
        Log.d("MYTAG", "Game ended learning");

        this.stepButton.setEnabled(false);
        this.playButton.setEnabled(false);
        this.restartButton.setEnabled(true);
    }


    public void displayMessage(String message) {
        Log.d("MYTAG", "Displaying message learning");
    }

    public void displayMoveNotation(String message) {
        Log.d("MYTAG", "Displaying move notation learning");
        this.movesTextView.append(message);
    }
}
