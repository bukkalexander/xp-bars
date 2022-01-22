package com.alexanderbukk.bars.data.rewardinstance;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "reward_instance")
public class RewardInstance {

    @PrimaryKey(autoGenerate = true)
    public int uid = 0;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "reward")
    public String reward;

    @ColumnInfo(name = "cost")
    public int cost;

    @ColumnInfo(name = "comment")
    public String comment;

    @ColumnInfo(name = "date_time_created")
    public LocalDateTime localDateTimeCreated;

    public RewardInstance(@NonNull String name, @NonNull String reward, int cost,
                          @NonNull String comment) {
        this.name = name;
        this.reward = reward;
        this.cost = cost;
        this.comment = comment;
        this.localDateTimeCreated = LocalDateTime.now();
    }
}
