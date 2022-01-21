package com.alexanderbukk.bars.data.group;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexanderbukk.bars.data.group.Group;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM `group`")
    LiveData<List<Group>> getAll();

    @Query("SELECT * FROM `group` WHERE uid IN (:groupIds)")
    LiveData<List<Group>> loadAllByIds(int[] groupIds);

    @Query("SELECT * FROM `group` WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    LiveData<Group> findByName(String first, String last);

    @Insert
    void insertAll(Group... groups);

    @Delete
    void delete(Group group);

    @Query("DELETE FROM `group`")
    void deleteAll();

}
