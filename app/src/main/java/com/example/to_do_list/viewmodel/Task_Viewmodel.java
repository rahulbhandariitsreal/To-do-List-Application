package com.example.to_do_list.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.to_do_list.model.List_Table;
import com.example.to_do_list.model.Repository;

import java.util.List;

public class Task_Viewmodel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<List_Table>> alltasks;


    public Task_Viewmodel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
    }

    public LiveData<List<List_Table>> getAlltaskstodisplay(){
        alltasks=repository.getAlltask();
        return alltasks;
    }

    public void addTask(List_Table list_table){
    repository.addtask(list_table);
    }
    public void deleteTask(List_Table list_table){
        repository.deletetask(list_table);
    }

    public void updateTask(List_Table list_table){
        repository.updatetask(list_table);
    }



}
