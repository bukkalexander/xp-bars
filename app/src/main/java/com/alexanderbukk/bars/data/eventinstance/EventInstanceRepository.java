package com.alexanderbukk.bars.data.eventinstance;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alexanderbukk.bars.data.AppDatabase;

import java.util.List;

public class EventInstanceRepository {
    
    private EventInstanceDao eventInstanceDao;
    private LiveData<List<EventInstance>> allEventInstances;
    
    public EventInstanceRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        eventInstanceDao = db.EventInstanceDao();
        allEventInstances = eventInstanceDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<EventInstance>> getAllEvents() {
        return allEventInstances;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(EventInstance eventInstance) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            eventInstanceDao.insertAll(eventInstance);
        });
    }

    public LiveData<List<EventInstance>> getAllEventInstancesByGroupAndName(String groupName,
                                                                            String eventName) {
        return eventInstanceDao.getRowsByGroupAndName(groupName, eventName);
    }
}
