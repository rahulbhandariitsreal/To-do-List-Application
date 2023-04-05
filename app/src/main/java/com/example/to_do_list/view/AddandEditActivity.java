package com.example.to_do_list.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.to_do_list.R;

import com.example.to_do_list.braodcast.MyBroadcast;
import com.example.to_do_list.databinding.ActivityAddandEditBinding;
import com.example.to_do_list.model.List_Table;

import java.sql.Time;
import java.util.Calendar;

public class AddandEditActivity extends AppCompatActivity {

    private ActivityAddandEditBinding activityAddandEditBinding;
    private AddandEditClickHandlers clickme;
   long time;
private TextView datepick;
    private AlertDialog.Builder alterdialog;
    private String timeis;
    public static final String EXTRA_TASK="task_extra";
    public static final String EXTRA_REQ_TIME="req_time_extra";
    public static final String EXTRA_ID="id_extra";
    AlarmManager alarmManager;
    private List_Table list_new_task=new List_Table();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addand_edit);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityAddandEditBinding= DataBindingUtil.setContentView(this,R.layout.activity_addand_edit);
        clickme=new AddandEditClickHandlers(this);
        activityAddandEditBinding.setClickme(clickme);
        list_new_task=new List_Table();
datepick=findViewById(R.id.datepicker);
datepick.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialoguebuilder();
        alterdialog.show();
    }
});

        Intent i=getIntent();
        if(i.hasExtra(EXTRA_ID)){
            //RECYCLER ITEM IS CLICKED
             setTitle("EDIT TASK");
            String task_extra=i.getStringExtra(EXTRA_TASK);
            String reqTime=i.getStringExtra(EXTRA_REQ_TIME);
            list_new_task.setTask(task_extra);
            list_new_task.setReqtime(reqTime);
            datepick.setText(reqTime);
        }
        else{
            // FAB IS CLICKED
          setTitle("ADD TASK");
        }
        activityAddandEditBinding.setListTable(list_new_task);
    }

    private void dialoguebuilder() {

         alterdialog=new AlertDialog.Builder(AddandEditActivity.this);
        View view= getLayoutInflater().inflate(R.layout.time_view,null);
        TimePicker alarmTimePicker=view.findViewById(R.id.timepicker);
        alterdialog.setView(view);
        alterdialog.setTitle("Required time");
        alterdialog.setIcon(R.drawable.baseline_access_time_24);
        alterdialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(list_new_task.getTask() == null){
                    Toast.makeText(AddandEditActivity.this, "Task is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                timeis=" "+alarmTimePicker.getCurrentHour()+"h:"+alarmTimePicker.getCurrentMinute()+"min";
                datepick.setText(timeis);
                Calendar calendar = Calendar.getInstance();

                // calendar is called to get current time in hour and minute
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());


                // using intent i have class AlarmReceiver class which inherits
                // BroadcastReceiver
                Intent intent = new Intent(AddandEditActivity.this, MyBroadcast.class);

                time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                if (System.currentTimeMillis() > time) {
                    // setting time as AM and PM
                    if (Calendar.AM_PM == 0)
                        time = time + (1000 * 60 * 60 * 12);
                    else
                        time = time + (1000 * 60 * 60 * 24);
                }

                // we call broadcast using pendingIntent
                intent.putExtra("task",list_new_task);
                intent.putExtra("time",(int)time);

               PendingIntent pendingIntent = PendingIntent.getBroadcast(AddandEditActivity.this, (int) time, intent, PendingIntent.FLAG_MUTABLE);
                // Alarm rings continuously until toggle button is turned off
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            }
        });
        alterdialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddandEditActivity.this, "Not selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class AddandEditClickHandlers{

        Context context;

        public AddandEditClickHandlers(Context context) {
            this.context = context;
        }
        public void addTask(View view){
            Intent i=new Intent();
if(list_new_task.getTask()==null){
    Toast.makeText(context, "Task is empty", Toast.LENGTH_SHORT).show();
}
else{
    String new_task=list_new_task.getTask();
    i.putExtra(EXTRA_TASK,new_task);
    i.putExtra(EXTRA_REQ_TIME,timeis);
    setResult(RESULT_OK,i);
    finish();

}

        }
    }
}