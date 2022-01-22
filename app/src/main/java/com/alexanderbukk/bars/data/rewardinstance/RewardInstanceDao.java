package com.alexanderbukk.bars.data.rewardinstance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexanderbukk.bars.data.reward.Reward;

import java.util.List;

@Dao
public interface RewardInstanceDao {

    @Query("SELECT * FROM `reward_instance`")
    LiveData<List<RewardInstance>> getAll();

    @Query("SELECT * FROM `reward_instance` WHERE uid IN (:rowIds)")
    LiveData<List<RewardInstance>> getRowsByIds(int[] rowIds);

    @Query("SELECT * FROM `reward_instance` WHERE name LIKE :name LIMIT 1")
    LiveData<RewardInstance> getRowByName(String name);

    @Insert
    void insertAll(RewardInstance... rewardInstances);

    @Delete
    void delete(RewardInstance rewardInstance);

    @Query("DELETE FROM `reward_instance`")
    void deleteAll();

}
