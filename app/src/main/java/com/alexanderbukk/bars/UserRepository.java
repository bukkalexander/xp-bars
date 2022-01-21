package com.alexanderbukk.bars;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    
    private UserDao userDao;
    private LiveData<List<User>> allUsers;
    
    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.UserDao();
        allUsers = userDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insertAll(user);
        });
    }
}
