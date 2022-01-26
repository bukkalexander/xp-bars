package com.alexanderbukk.bars.ui.event;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.alexanderbukk.bars.R;
import com.alexanderbukk.bars.data.event.Event;
import com.alexanderbukk.bars.data.group.Group;
import com.alexanderbukk.bars.ui.group.GroupViewModel;

import java.util.List;

public class EventActivity extends AppCompatActivity {

    private EventViewModel eventViewModel;
    private RecyclerView rvEvent;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        groupName = getIntent().getExtras().getString("group");

        setContentView(R.layout.activity_event);
        setTitle(groupName);

        rvEvent = findViewById(R.id.rv_event);
        EventRecyclerViewAdapter rvea = new EventRecyclerViewAdapter(this,
                eventViewModel.getAllEventsFromGroup(groupName));
        rvEvent.setAdapter(rvea);
        rvEvent.setLayoutManager(new LinearLayoutManager(this));

        eventViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {
                rvea.setAllEvents(eventViewModel.getAllEventsFromGroup(groupName));
            }
        });

        eventViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(@Nullable List<Group> group) {
                rvea.setEventColor(eventViewModel.getEventColor(groupName));
            }
        });

    }
}