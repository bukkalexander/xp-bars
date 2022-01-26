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
                    "#4285F4",  //  0:blue
                    "#FF0080",  //  1:pink
                    "#8E24AA",  //  2:purple
                    "#F6BF26",  //  3:light yellow
                    "#7F1F17",  //  4:brown
                    "#7986CB",  //  5:light blue
                    "#F09300",  //  6:orange
                    "#3F51B5",  //  7:deep blue
                    "#000000",  //  8:black
                    "#33B679",  //  9:green
                    "#494F52",  // 10:gray
                    "#D50000",  // 11:red
                    "#B83309",  // 12:brown red
                };
                int[] c = new int[colorString.length];
                for(int i = 0; i < colorString.length; i++) {
                    c[i] = Color.parseColor(colorString[i]);
                }

                groupDao.insertAll(
                    new Group("Chores", c[2]),
                    new Group("Mental", c[9]),
                    new Group("Nutrition", c[8]),
                    new Group("Social", c[6]),
                    new Group("Workout", c[12]),
                    new Group("Hygiene", c[1]),
                    new Group("Study", c[3]),
                    new Group("Work", c[10]),
                    new Group("Project", c[5]),
                    new Group("Business", c[11])
                );

                EventDao eventDao = INSTANCE.EventDao();
                eventDao.insertAll(
                        new Event("Chores","Laundry start","",1,0,0,0,10),
                        new Event("Chores","Laundry up","",1,0,0,0,10),
                        new Event("Chores","Laundry down","",1,0,0,0,10),
                        new Event("Chores","Dishwasher start ","",1,0,0,0,10),
                        new Event("Chores","Dishwasher out","",1,0,0,0,10),
                        new Event("Chores","Empty trash","",1,0,0,0,5),
                        new Event("Chores","Putting things in order","",2,0,0,0,20),
                        new Event("Chores","Vacuum cleaning","",2,0,0,0,20),
                        new Event("Chores","Toilet cleaning","",2,0,0,0,20),
                        new Event("Chores","Doing dishes","",2,0,0,0,20),
                        new Event("Chores","Buying groceries","",3,0,0,0,30),
                        new Event("Chores","Cooking meals","",0,6,0,0,60),
                        new Event("Chores","Emptying recycling","",6,0,0,0,60),
                        new Event("Chores","Refrigerator cleaning","",10,0,0,0,100),
                        new Event("Chores","Björn chores","",0,6,0,0,15),
                        new Event("Workout","Gym","",2,4,4,0,60),
                        new Event("Workout","Running","",2,4,0,0,30),
                        new Event("Workout","Walking","",0,4,0,4,45),
                        new Event("Mental","Meditation","",1,6,2,20,10),
                        new Event("Nutrition","Making bed + vitamins","",1,0,1,2,10),
                        new Event("Nutrition","Omega3 + creatine","",1,0,0,1,5),
                        new Event("Hygiene","Shower","",2,0,0,1,15),
                        new Event("Hygiene","Flossing","",2,0,1,3,10),
                        new Event("Hygiene","Trimming nails","",1,0,0,1,10),
                        new Event("Hygiene","Shaving beard - Machine","",1,0,0,1,10),
                        new Event("Hygiene","Shaving beard - Razor","",1,0,0,1,10),
                        new Event("Hygiene","Shaving body - Machine","",2,0,0,2,20),
                        new Event("Hygiene","Shaving beard - Razor","",2,0,0,2,20),
                        new Event("Hygiene","Going to dentist","",20,0,0,0,100),
                        new Event("Work","Volvo","No points for meetings that doesn't include actual, important work, such as stand-up or weekly demos",0,5,0,0,60),
                        new Event("Study","Algorithms","",0,10,10,0,60),
                        new Event("Business","Developer course","",0,10,10,0,60),
                        new Event("Project","XP-Bars","",0,5,0,0,60),
                        new Event("Project","AndroidStudio2DGameDevelopment","",0,5,0,0,60),
                        new Event("Social","Phone call","conversation with family and close friends, per person per week",5,0,0,5,60),
                        new Event("Social","Massage","",5,0,0,5,15)
                );
            });
        }
    };
}
