package com.example.sandric.mps.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sandric.mps.R;
import com.example.sandric.mps.models.Opening;


public class OpeningGroupListActivity extends AppCompatActivity {

    private ListView groupsListView;

    private String[] groupNames = { "Open", "Semi-open", "Closed", "Semi-closed", "Indian-defence", "Flank" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_group_list);


        this.groupsListView = (ListView) findViewById(R.id.groups_list_view);

        this.groupsListView.setAdapter(new OpeningGroupAdapter(this.groupNames));

        Opening.getOpeningsFromRequest();
    }


    private class OpeningGroupAdapter extends ArrayAdapter<String> {
        OpeningGroupAdapter(String[] groupNames) {
            super(OpeningGroupListActivity.this, R.layout.group_item, R.id.group_text_view, groupNames);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            TextView groupTextView = (TextView)convertView.findViewById(R.id.group_text_view);

            Button trainButton = (Button)convertView.findViewById(R.id.train_button);

            groupTextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String groupName = getItem(position);

                    Intent intent = new Intent(OpeningGroupListActivity.this, OpeningListActivity.class);

                    intent.putExtra(OpeningListActivity.EXTRA, groupName);

                    startActivity(intent);
                }
            });

            trainButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String groupName = getItem(position);

                    Intent intent = new Intent(OpeningGroupListActivity.this, TrainingActivity.class);

                    intent.putExtra(TrainingActivity.EXTRA, groupName);

                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

}
