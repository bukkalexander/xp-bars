package com.alexanderbukk.bars.data.reward;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alexanderbukk.bars.data.AppDatabase;
import com.alexanderbukk.bars.data.group.Group;

import java.util.List;

public class RewardRepository {
    
    private RewardDao rewardDao;
    private LiveData<List<Reward>> allRewards;
    
    public RewardRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        rewardDao = db.RewardDao();
        allRewards = rewardDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Reward>> getAllRewards() {
        return allRewards;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Reward reward) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            rewardDao.insertAll(reward);
        });
    }
}
