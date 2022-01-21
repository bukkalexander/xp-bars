package com.alexanderbukk.bars.data.group;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alexanderbukk.bars.data.AppDatabase;

import java.util.List;

public class GroupRepository {
    
    private GroupDao groupDao;
    private LiveData<List<Group>> allUsers;
    
    public GroupRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        groupDao = db.UserDao();
        allUsers = groupDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Group>> getAllUsers() {
        return allUsers;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Group group) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.insertAll(group);
        });
    }
}
