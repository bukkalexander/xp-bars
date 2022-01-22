package com.alexanderbukk.bars.data.eventinstance;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "event_instance")
public class EventInstance {

    @PrimaryKey(autoGenerate = true)
    public int uid = 0;

    @ColumnInfo(name = "name")
    public String name = "";

    @ColumnInfo(name = "event")
    public String event;

    @ColumnInfo(name = "group")
    public String group;

    @ColumnInfo(name = "comment")
    public String comment = "";

    @ColumnInfo(name = "bars_per_occurrence")
    public int barsPerOccurrence;

    @ColumnInfo(name = "bars_per_hour")
    public int barsPerHour;

    @ColumnInfo(name = "bars_for_yesterday")
    public int barsForYesterday;

    @ColumnInfo(name = "bars_daily_limit")
    public int barsDailyLimit;
    // 0 means unlimited

    @ColumnInfo(name = "started")
    public LocalDateTime localDateTimeStarted;

    @ColumnInfo(name = "duration")
    public int durationMinutes;

    @ColumnInfo(name = "created")
    public LocalDateTime localDateTimeCreated;

    public EventInstance(@NonNull String name, @NonNull String event, @NonNull String group,
                         @NonNull String comment, int barsPerOccurrence, int barsPerHour,
                         int barsForYesterday, int barsDailyLimit, int durationMinutes,
                         @NonNull LocalDateTime localDateTimeStarted) {
        this.name = name;
        this.event = event;
        this.group = group;
        this.comment = comment;
        this.barsPerOccurrence = barsPerOccurrence;
        this.barsPerHour = barsPerHour;
        this.barsForYesterday = barsForYesterday;
        this.barsDailyLimit = barsDailyLimit;
        this.durationMinutes = durationMinutes;
        this.localDateTimeStarted =localDateTimeStarted;
        this.localDateTimeCreated = LocalDateTime.now();
    }
}
