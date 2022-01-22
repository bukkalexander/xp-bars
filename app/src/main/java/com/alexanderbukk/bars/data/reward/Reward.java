package com.alexanderbukk.bars.data.reward;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "reward")
public class Reward {

    @PrimaryKey(autoGenerate = true)
    public int uid = 0;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "cost")
    public int cost;

    @ColumnInfo(name = "date_time_created")
    public LocalDateTime localDateTimeCreated;

    public Reward(@NonNull String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.localDateTimeCreated = LocalDateTime.now();
    }
}
