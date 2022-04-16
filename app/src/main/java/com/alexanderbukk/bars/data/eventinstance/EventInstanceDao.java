package com.alexanderbukk.bars.data.eventinstance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventInstanceDao {

    @Query("SELECT * FROM `event_instance`")
    LiveData<List<EventInstance>> getAll();

    @Query("SELECT * FROM `event_instance` WHERE uid IN (:rowIds)")
    LiveData<List<EventInstance>> getRowsByIds(int[] rowIds);

    @Query("SELECT * FROM `event_instance` WHERE name LIKE :name LIMIT 1")
    LiveData<EventInstance> getRowByName(String name);

    @Insert
    void insertAll(EventInstance... eventInstances);

    @Delete
    void delete(EventInstance eventInstance);

    @Query("DELETE FROM `event_instance`")
    void deleteAll();

    @Query("SELECT * FROM `event_instance` WHERE name LIKE :name AND `group` LIKE :group")
    LiveData<List<EventInstance>> getRowsByGroupAndName(String group, String name);
}
