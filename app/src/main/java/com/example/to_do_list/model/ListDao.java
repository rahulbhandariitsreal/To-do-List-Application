package com.example.to_do_list.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@SuppressWarnings("AndroidUnresolvedRoomSqlReference")
@Dao
public interface ListDao {

    @Insert
    void inserttask(List_Table list_table);

    @Update
    void updatetask(List_Table list_table);

    @Delete
    void deletetask(List_Table list_table);


    @Query("select *from tasktable")
    public LiveData<List<List_Table>> gettasks();

}
