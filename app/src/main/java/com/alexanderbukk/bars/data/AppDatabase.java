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
                    new Group("Health", c[1]),
                    new Group("Hygiene", c[4]),
                    new Group("Work", c[5]),
                    new Group("Social", c[9])
                );

                EventDao eventDao = INSTANCE.EventDao();
                eventDao.insertAll(
                        new Event("Chores","Starting laundry","",1,0,0,0,10),
                        new Event("Chores","Hanging laundry","",1,0,0,0,10),
                        new Event("Chores","taking down laundry","",1,0,0,0,10),
                        new Event("Chores","Starting dishwasher","",1,0,0,0,10),
                        new Event("Chores","Emptying dishwasher","",1,0,0,0,10),
                        new Event("Chores","Emptying trash","",1,0,0,0,5),
                        new Event("Chores","Putting things in order","",2,0,0,0,20),
                        new Event("Chores","Vacuum cleaning","",2,0,0,0,20),
                        new Event("Chores","Cleaning toilet","",2,0,0,0,20),
                        new Event("Chores","Doing dishes","",2,0,0,0,20),
                        new Event("Chores","Buying groceries","",3,0,0,0,30),
                        new Event("Chores","Cooking meals","",0,6,0,0,60),
                        new Event("Chores","Emptying recycling","",6,0,0,0,60),
                        new Event("Chores","Cleaning refrigerator","",10,0,0,0,100),
                        new Event("Chores","Björn chores","",0,6,0,0,15),
                        new Event("Health","Main exercise","",3,0,2,0,30),
                        new Event("Health","Secondary exercise","",1,0,0,0,10),
                        new Event("Health","Running >= 10 min","",1,0,0,1,10),
                        new Event("Health","Walking >= 30 min","",3,0,0,3,60),
                        new Event("Health","Meditation","",0,12,2,20,10),
                        new Event("Health","Making bed + vitamins","",1,0,1,1,10),
                        new Event("Health","Omega3 + creatine","",1,0,0,1,5),
                        new Event("Hygiene","Shower","",1,0,0,1,15),
                        new Event("Hygiene","Flossing","",1,0,1,1,10),
                        new Event("Hygiene","Trimming nails","",1,0,0,1,10),
                        new Event("Hygiene","Shaving beard - Machine","",1,0,0,1,10),
                        new Event("Hygiene","Shaving beard - Razor","",1,0,0,1,10),
                        new Event("Hygiene","Shaving body - Machine","",2,0,0,2,20),
                        new Event("Hygiene","Shaving beard - Razor","",2,0,0,2,20),
                        new Event("Hygiene","Going to dentist","",20,0,0,0,100),
                        new Event("Work","Volvo","",0,5,0,0,60),
                        new Event("Work","Study","",0,10,10,0,60),
                        new Event("Work","Business","",0,10,10,0,60),
                        new Event("Work","Hobby project","",0,5,0,0,60),
                        new Event("Social","Conversation","conversation with family and close friends, per person per week",3,0,0,3,30),
                        new Event("Social","Meal/series with Therese","",3,0,0,3,30),
                        new Event("Social","Kind to Therese","",5,0,0,5,15)
                );
            });
        }
    };
}
