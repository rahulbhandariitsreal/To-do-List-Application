package com.example.to_do_list.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.to_do_list.R;
import com.example.to_do_list.databinding.ActivityMainBinding;
import com.example.to_do_list.model.List_Table;
import com.example.to_do_list.viewmodel.Task_Viewmodel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int ADD_REQUEST_RESULT_CODE = 1;

    private static final int EDIT_REQUEST_RESULT_CODE = 2;

    private ActivityMainBinding activityMainBinding;

    private int selected_task_id;
    private MainActivityClickHandlers clickHandlers;
    private Task_Viewmodel viewmodel;
    private ArrayList<List_Table> alltasklist;
    private List_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        clickHandlers = new MainActivityClickHandlers(this);
        activityMainBinding.setClickHandlers(clickHandlers);
        viewmodel = new ViewModelProvider(this).get(Task_Viewmodel.class);

        loadrecyclerview();
        viewmodel.getAlltaskstodisplay().observe(this, new Observer<List<List_Table>>() {
            @Override
            public void onChanged(List<List_Table> list_tables) {
                alltasklist = (ArrayList) list_tables;
                loadrecyclerview();
            }
        });

    }

    private void loadrecyclerview() {
        adapter = new List_Adapter(this);
        RecyclerView recyclerView = activityMainBinding.recyclerview;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setListtask(alltasklist);

        adapter.setClickListener(new List_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(List_Table list_table) {
                selected_task_id=list_table.getList_id();
                Intent i =new Intent(MainActivity.this,AddandEditActivity.class);
                i.putExtra(AddandEditActivity.EXTRA_ID,selected_task_id);
                i.putExtra(AddandEditActivity.EXTRA_TASK,list_table.getTask());
                i.putExtra(AddandEditActivity.EXTRA_REQ_TIME,list_table.getReqtime());
                startActivityForResult(i,EDIT_REQUEST_RESULT_CODE);
            }
        });

        //delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                List_Table deltask = alltasklist.get(viewHolder.getAdapterPosition());
                viewmodel.deleteTask(deltask);

            }
        }).attachToRecyclerView(recyclerView);
    }
    //clickd=handlers
    public class MainActivityClickHandlers {
        Context context;

        public MainActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onfabclick(View view) {
            //  Toast.makeText(context, "fabb clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, AddandEditActivity.class);
            startActivityForResult(i, ADD_REQUEST_RESULT_CODE);
        }

    }

    //coming back to the aactivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //adding the task
        if (requestCode == ADD_REQUEST_RESULT_CODE && resultCode == RESULT_OK) {
            List_Table list_table = new List_Table();
            list_table.setTask(data.getStringExtra(AddandEditActivity.EXTRA_TASK));
            list_table.setReqtime(data.getStringExtra(AddandEditActivity.EXTRA_REQ_TIME));
            viewmodel.addTask(list_table);
            Log.v("TAG", "Inserted" + list_table.getTask());
        }

        //updateing the task
        else if (requestCode == EDIT_REQUEST_RESULT_CODE && resultCode == RESULT_OK) {
            List_Table list_table1 = new List_Table();
            list_table1.setTask(data.getStringExtra(AddandEditActivity.EXTRA_TASK));
            list_table1.setReqtime(data.getStringExtra(AddandEditActivity.EXTRA_REQ_TIME));
            list_table1.setList_id(selected_task_id);
            viewmodel.updateTask(list_table1);

        }
    }


    }
