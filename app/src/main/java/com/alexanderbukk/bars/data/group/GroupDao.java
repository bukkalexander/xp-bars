package com.alexanderbukk.bars.data.group;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM `group`")
    LiveData<List<Group>> getAll();

    @Query("SELECT * FROM `group` WHERE uid IN (:rowIds)")
    LiveData<List<Group>> getRowsByIds(int[] rowIds);

    @Query("SELECT * FROM `group` WHERE name = :name")
    LiveData<Group> getRowByName(String name);

    @Insert
    void insertAll(Group... groups);

    @Delete
    void delete(Group group);

    @Query("DELETE FROM `group`")
    void deleteAll();

}
