package com.alexanderbukk.bars.data.rewardinstance;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alexanderbukk.bars.data.AppDatabase;
import com.alexanderbukk.bars.data.reward.Reward;

import java.util.List;

public class RewardInstanceRepository {
    
    private RewardInstanceDao rewardInstanceDao;
    private LiveData<List<RewardInstance>> allRewardInstances;
    
    public RewardInstanceRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        rewardInstanceDao = db.RewardInstanceDao();
        allRewardInstances = rewardInstanceDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<RewardInstance>> getAllRewardInstances() {
        return allRewardInstances;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(RewardInstance rewardInstance) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            rewardInstanceDao.insertAll(rewardInstance);
        });
    }
}
