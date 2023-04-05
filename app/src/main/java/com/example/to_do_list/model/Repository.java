package com.example.to_do_list.model;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {


    //live data
    private LiveData<List<List_Table>> alltask;

    private ListDao getlistDao;


    //roomdatabase object
    private Room_list room_list;

    public Repository(Application application) {
        room_list=Room_list.getInstance(application);
        getlistDao=room_list.listDao();
    }

    //get task
    public LiveData<List<List_Table>>getAlltask(){
    alltask=getlistDao.gettasks();
    return  alltask;
    }



    //other implementations

    public void addtask(List_Table list_table){
        ExecutorService executorService= Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                getlistDao.inserttask(list_table);
            }
        });
    }

    public void deletetask(List_Table list_table){
        ExecutorService executorService= Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                getlistDao.deletetask(list_table);
            }
        });
    }

    public void updatetask(List_Table list_table){
        ExecutorService executorService= Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                getlistDao.updatetask(list_table);
            }
        });
    }
}
