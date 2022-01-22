package com.alexanderbukk.bars.data.group;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "group")
public class Group {

    @PrimaryKey(autoGenerate = true)
    public int uid = 0;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "color")
    public int color;

    @ColumnInfo(name = "date_time_created")
    public LocalDateTime localDateTimeCreated;

    public Group(@NonNull String name, @NonNull int color) {
        this.name = name;
        this.color = color;
        this.localDateTimeCreated = LocalDateTime.now();
    }
}
