package com.alexanderbukk.bars.ui.event;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alexanderbukk.bars.data.event.Event;
import com.alexanderbukk.bars.data.event.EventRepository;

import java.util.ArrayList;
import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EventRepository repository;
    private final LiveData<List<Event>> allEvents;

    public EventViewModel(Application application) {
        super(application);
        repository = new EventRepository(application);
        allEvents = repository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public List<Event> getAllEventsFromGroup(String groupName) {
        List<Event> events = new ArrayList<>();
        if (allEvents.getValue() == null)
            return null;
        for (Event event : allEvents.getValue()) {
            if (event.group.equals(groupName))
                events.add(event);
        }
        return events;
    }
}
