package com.alexanderbukk.bars.data.event;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "event")
public class Event {

    @PrimaryKey(autoGenerate = true)
    public int uid = 0;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "group")
    public String group;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "bars_per_occurrence")
    public int barsPerOccurrence;

    @ColumnInfo(name = "bars_per_hour")
    public int barsPerHour;

    @ColumnInfo(name = "bars_for_yesterday")
    public int barsForYesterday;

    @ColumnInfo(name = "bars_daily_limit")
    public int barsDailyLimit;
    // 0 means unlimited

    @ColumnInfo(name = "duration")
    public int durationMinutes;

    @ColumnInfo(name = "created")
    public LocalDateTime localDateTimeCreated;

    public Event(@NonNull String group, @NonNull String name, @NonNull String description,
                 int barsPerOccurrence, int barsPerHour, int barsForYesterday, int barsDailyLimit,
                 int durationMinutes) {
        this.group = group;
        this.name = name;
        this.description = description;
        this.barsPerOccurrence = barsPerOccurrence;
        this.barsPerHour = barsPerHour;
        this.barsForYesterday = barsForYesterday;
        this.barsDailyLimit = barsDailyLimit;
        this.durationMinutes = durationMinutes;
        this.localDateTimeCreated = LocalDateTime.now();
    }
}
