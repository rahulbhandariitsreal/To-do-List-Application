package com.example.to_do_list.view;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.to_do_list.model.List_Table;

import java.util.ArrayList;

public class Task_Diff_call extends DiffUtil.Callback {

    ArrayList<List_Table> oldTaskList;
    ArrayList<List_Table> newTaskList;

    public Task_Diff_call(ArrayList<List_Table> oldTaskList, ArrayList<List_Table> newTaskList)  {
        this.oldTaskList = oldTaskList;
        this.newTaskList = newTaskList;

    }

    @Override
    public int getOldListSize() {
        return oldTaskList==null?0:oldTaskList.size();
    }

    @Override
    public int getNewListSize() {
        return newTaskList==null?0:newTaskList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newTaskList.get(newItemPosition).getList_id()==oldTaskList.get(oldItemPosition).getList_id();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return newTaskList.get(newItemPosition).equals(oldTaskList.get(oldItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
