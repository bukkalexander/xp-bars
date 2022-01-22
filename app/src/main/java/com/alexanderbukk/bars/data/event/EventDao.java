package com.alexanderbukk.bars.data.event;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {

    @Query("SELECT * FROM `event`")
    LiveData<List<Event>> getAll();

    @Query("SELECT * FROM `event` WHERE uid IN (:rowIds)")
    LiveData<List<Event>> getRowsByIds(int[] rowIds);

    @Query("SELECT * FROM `event` WHERE name LIKE :name")
    LiveData<List<Event>> getRowsByName(String name);

    @Query("SELECT * FROM `event` WHERE name LIKE :name LIMIT 1")
    LiveData<Event> getRowByName(String name);

    @Insert
    void insertAll(Event... events);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM `event`")
    void deleteAll();

}
