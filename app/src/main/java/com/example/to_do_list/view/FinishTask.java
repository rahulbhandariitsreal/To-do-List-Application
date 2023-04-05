package com.example.to_do_list.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.to_do_list.R;
import com.example.to_do_list.braodcast.MyBroadcast;
import com.example.to_do_list.model.List_Table;
import com.example.to_do_list.viewmodel.Task_Viewmodel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FinishTask extends AppCompatActivity {


    Ringtone ringtone;
    TextView textView;
    List_Table task= null;

    private Task_Viewmodel viewmodel;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finish_task);

        viewmodel=new ViewModelProvider(this).get(Task_Viewmodel.class);
        time=getIntent().getIntExtra("time",0);
textView=findViewById(R.id.textView2);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            task = getIntent().getParcelableExtra("task", List_Table.class);
        }

        textView.setText(task.getTask());
        playalarm();


    }

    private void playalarm() {


        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // we will use vibrator first
                Vibrator vibrator = (Vibrator) FinishTask.this.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(4000);

                Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if (alarmUri == null) {
                    alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                }


                // setting default ringtone
                ringtone = RingtoneManager.getRingtone(FinishTask.this, alarmUri);
                // play ringtone
                ringtone.play();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ringtone.setLooping(true);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FinishTask.this, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
                    }
                });
            }


        });
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void stopme(View view) {
        ringtone.stop();
        viewmodel.deleteTask(task);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(),
                MyBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), time, myIntent, PendingIntent.FLAG_MUTABLE);
        alarmManager.cancel(pendingIntent);
startActivity(new Intent(FinishTask.this,MainActivity.class));
    }
}