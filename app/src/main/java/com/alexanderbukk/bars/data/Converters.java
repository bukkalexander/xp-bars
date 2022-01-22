package com.alexanderbukk.bars.data;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class Converters {

    @TypeConverter
    public static LocalDateTime timestampToLocalDateTime(Long timestamp) {

        return timestamp == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId());
    }

    @TypeConverter
    public static Long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return localDateTime == null ? null :  ZonedDateTime.of(
                localDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
