package com.example.sandric.mps.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.sandric.mps.R;

public class MainActivity extends AppCompatActivity {

    private Button gameButton;
    private Button profileButton;
    private Button topButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gameButton = (Button)this.findViewById(R.id.game_button);
        this.gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OpeningGroupListActivity.class);

                startActivity(intent);
            }
        });

        this.profileButton = (Button)this.findViewById(R.id.profile_button);
        this.profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);

                startActivity(intent);
            }
        });

        this.topButton = (Button)this.findViewById(R.id.top_button);
        this.topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TopActivity.class);

                startActivity(intent);
            }
        });
    }
}
