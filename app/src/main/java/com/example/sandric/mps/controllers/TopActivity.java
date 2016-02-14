package com.example.sandric.mps.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sandric.mps.R;


public class TopActivity extends AppCompatActivity {

    private ListView topListView;

    private String[] topGames = { "Tosha 1", "Tosha 2", "Tosha 3", "Tosha 4", "Tosha 5", "Tosha 6" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);


        this.topListView = (ListView) findViewById(R.id.top_list_view);

        this.topListView.setAdapter(new TopAdapter(this.topGames));
    }


    private class TopAdapter extends ArrayAdapter<String> {
        TopAdapter(String[] topGames) {
            super(TopActivity.this, R.layout.top_item, R.id.username_text_view, topGames);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            TextView groupTextView = (TextView)convertView.findViewById(R.id.group_text_view);
            TextView userNameTextView = (TextView)convertView.findViewById(R.id.username_text_view);
            TextView scoreTextView = (TextView)convertView.findViewById(R.id.score_text_view);

            groupTextView.setText(TopActivity.this.topGames[position] + " group");
            userNameTextView.setText(TopActivity.this.topGames[position] + " name");
            scoreTextView.setText(TopActivity.this.topGames[position] + " score");

            return convertView;
        }
    }
}
