package com.unitbv.siipa.utils;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class DateConvertor {
    @TypeConverter
    public static LocalDate fromTimestamp(Long value) {
        return value == null ? null : LocalDate.ofEpochDay(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }
}
