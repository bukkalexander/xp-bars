package com.alexanderbukk.bars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class EventListActivity extends AppCompatActivity {

    String[] eventNames = {"1", "2", "3", "4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        setTitle(getIntent().getExtras().getString("eventGroup"));



        RecyclerView rvEvent = findViewById(R.id.rv_event);
        RecyclerViewEventAdapter rvea = new RecyclerViewEventAdapter(this, eventNames);
        rvEvent.setAdapter(rvea);
        rvEvent.setLayoutManager(new LinearLayoutManager(this));

    }
}