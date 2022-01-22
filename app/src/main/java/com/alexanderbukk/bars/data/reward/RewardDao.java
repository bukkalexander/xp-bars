package com.alexanderbukk.bars.data.reward;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RewardDao {

    @Query("SELECT * FROM `reward`")
    LiveData<List<Reward>> getAll();

    @Query("SELECT * FROM `reward` WHERE uid IN (:rowIds)")
    LiveData<List<Reward>> getRowsByIds(int[] rowIds);

    @Query("SELECT * FROM `reward` WHERE name LIKE :name LIMIT 1")
    LiveData<Reward> getRowByName(String name);

    @Insert
    void insertAll(Reward... rewards);

    @Delete
    void delete(Reward reward);

    @Query("DELETE FROM `reward`")
    void deleteAll();

}
