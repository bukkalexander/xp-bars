package com.alexanderbukk.bars.data;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alexanderbukk.bars.data.event.Event;
import com.alexanderbukk.bars.data.event.EventDao;
import com.alexanderbukk.bars.data.eventinstance.EventInstance;
import com.alexanderbukk.bars.data.eventinstance.EventInstanceDao;
import com.alexanderbukk.bars.data.group.Group;
import com.alexanderbukk.bars.data.group.GroupDao;
import com.alexanderbukk.bars.data.reward.Reward;
import com.alexanderbukk.bars.data.reward.RewardDao;
import com.alexanderbukk.bars.data.rewardinstance.RewardInstance;
import com.alexanderbukk.bars.data.rewardinstance.RewardInstanceDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Group.class, Event.class, EventInstance.class, Reward.class,
    RewardInstance.class}, version = 1, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract GroupDao GroupDao();
    public abstract EventDao EventDao();
    public abstract EventInstanceDao EventInstanceDao();
    public abstract RewardDao RewardDao();
    public abstract RewardInstanceDao RewardInstanceDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                GroupDao groupDao = INSTANCE.GroupDao();
                groupDao.deleteAll();
                String[] colorString = {
                    "#ff35a0",
                    "#a2af48",
                    "#7cd9b4",
                    "#f5deb3",
                    "#d2afff",
                    "#7692bf",
                    "#ffd15c",
                    "#d32e36",
                    "#58babf",
                    "#f68c20"
                };
                int[] c = new int[colorString.length];
                for(int i = 0; i < colorString.length; i++) {
                    c[i] = Color.parseColor(colorString[i]);
                }

                groupDao.insertAll(
                    new Group("Chores", c[0]),
                    new Group("Fitness", c[1]),
                    new Group("Mental", c[2]),
                    new Group("Nutrition", c[3]),
                    new Group("Hygiene", c[4]),
                    new Group("Work", c[5]),
                    new Group("Study", c[6]),
                    new Group("Business", c[7]),
                    new Group("Project", c[8]),
                    new Group("Social", c[9])
                );
            });
        }
    };
}
