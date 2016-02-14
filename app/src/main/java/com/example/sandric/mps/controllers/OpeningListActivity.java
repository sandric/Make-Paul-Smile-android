package com.example.sandric.mps.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sandric.mps.R;
import com.example.sandric.mps.models.Opening;

import java.util.ArrayList;

public class OpeningListActivity extends AppCompatActivity {

    public static final String EXTRA = "OpeningListActivityExtra";

    private ArrayList<Opening> openings;

    private ListView openingsListView;

    private String selectedOpeningGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_list);


        this.selectedOpeningGroup = (String)getIntent().getSerializableExtra(OpeningListActivity.EXTRA);

        this.openingsListView = (ListView) findViewById(R.id.openings_list_view);

        this.openings = Opening.getOpeningsByGroupName(this.selectedOpeningGroup);

        this.openingsListView.setAdapter(new ArrayAdapter<Opening>(this, android.R.layout.simple_list_item_1, this.openings));

        this.openingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(OpeningListActivity.this, LearningActivity.class);

                i.putExtra(LearningActivity.EXTRA, OpeningListActivity.this.openings.get(position));

                startActivity(i);
            }
        });

    }

}
