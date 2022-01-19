package com.alexanderbukk.bars;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context, String dbName) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, dbName).build();
            return instance;
        }
        return instance;
    }

    public abstract UserDao userDao();
}
