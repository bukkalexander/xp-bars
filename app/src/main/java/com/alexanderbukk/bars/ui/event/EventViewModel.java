package com.alexanderbukk.bars.ui.event;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alexanderbukk.bars.data.event.Event;
import com.alexanderbukk.bars.data.event.EventRepository;
import com.alexanderbukk.bars.data.group.Group;
import com.alexanderbukk.bars.data.group.GroupRepository;

import java.util.ArrayList;
import java.util.List;

public class EventViewModel extends AndroidViewModel {


    private GroupRepository groupRepository;
    private EventRepository eventRepository;
    private final LiveData<List<Event>> allEvents;
    private final LiveData<List<Group>> allGroups;


    public EventViewModel(Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        groupRepository = new GroupRepository(application);
        allEvents = eventRepository.getAllEvents();
        allGroups = groupRepository.getAllGroups();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }
    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
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

    public Group getGroupByName(String groupName) {
        for (Group group : allGroups.getValue())
            if (group.name.equals(groupName))
                return group;
        return null;
    }
}
