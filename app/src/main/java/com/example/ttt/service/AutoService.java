package com.example.ttt.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.example.ttt.R;
import com.example.ttt.activity.ChooseActivity;

/**
 * Created by 1 on 2015/10/4.
 */
public class AutoService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updata();
            }
        });
        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        int Hour=4*60*60*1000;
        long riggerAtTime= SystemClock.elapsedRealtime()+Hour;
        Intent i =new Intent(this,AutoService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,riggerAtTime,pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }
    private void updata(){
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification=new Notification(R.drawable.ic_notifications_on_blue_400_18dp,"查看最近同城活动",System.currentTimeMillis());
        manager.notify(1,notification);
    }
}
