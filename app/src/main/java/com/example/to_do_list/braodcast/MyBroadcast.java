package com.example.to_do_list.braodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.to_do_list.model.List_Table;
import com.example.to_do_list.view.FinishTask;

public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            List_Table task=intent.getParcelableExtra("task",List_Table.class);
            int times=intent.getIntExtra("time",0);
            intent=new Intent(context, FinishTask.class);
            intent.putExtra("time",times);
            intent.putExtra("task",task);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
}
