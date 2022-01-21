package com.alexanderbukk.bars.ui.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.alexanderbukk.bars.R;

public class EventActivity extends AppCompatActivity {

    String[] eventNames = {"1", "2", "3", "4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        setTitle(getIntent().getExtras().getString("eventGroup"));



        RecyclerView rvEvent = findViewById(R.id.rv_event);
        EventRecyclerViewAdapter rvea = new EventRecyclerViewAdapter(this, eventNames);
        rvEvent.setAdapter(rvea);
        rvEvent.setLayoutManager(new LinearLayoutManager(this));

    }
}