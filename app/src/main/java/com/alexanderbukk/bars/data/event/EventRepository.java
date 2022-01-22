package com.alexanderbukk.bars.data.event;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alexanderbukk.bars.data.AppDatabase;

import java.util.List;

public class EventRepository {
    
    private EventDao eventDao;
    private LiveData<List<Event>> allEvents;
    
    public EventRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        eventDao = db.EventDao();
        allEvents = eventDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Event event) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.insertAll(event);
        });
    }
}
