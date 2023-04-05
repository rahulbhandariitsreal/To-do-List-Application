package com.example.to_do_list.model;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = List_Table.class,version = 1)
public abstract class Room_list extends RoomDatabase {


    //Dao of table
    public abstract ListDao listDao();

    //singleton instance
    public static Room_list instance;

    public static synchronized Room_list getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, Room_list.class, "list_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(instance.room_Callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback room_Callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            initilization();
        }

};
    private static void initilization() {
        //dao object
       ListDao listDao= instance.listDao();

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List_Table list_table=new List_Table();
                list_table.setTask("DO YOUR HOME WROK NICELY");
                list_table.setReqtime("13h:00min");

                listDao.inserttask(list_table);

                List_Table list_table2=new List_Table();
                list_table2.setTask("EAT BREAKFAST");
                list_table2.setReqtime("13h:00min");

                listDao.inserttask(list_table2);
                Log.v("dbharyy","successfully instereted");

            }
        });

    }

}
