package com.logicdrop.todos.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import com.logicdrop.todos.R;

public class LongRunningService extends Service {
    private static final int mServiceId = 42;

    @Override
    public IBinder onBind(Intent intent) {

        Notification notice;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            notice = APIv11.createNotice(this);
        } else {
            notice = new Notification(R.drawable.icon, "Service Finished", System.currentTimeMillis());
        }

        startForeground(mServiceId, notice);

        return null;
    }

    private static class APIv11 {
        public static Notification createNotice(Service context){
            Notification notice = new Notification.Builder(context.getApplicationContext()).setContentTitle("Service finished").build();
            return notice;
        }
    }

    @Override
     public int onStartCommand(Intent intent, int flags, int startId) {

        return Service.START_REDELIVER_INTENT;
     }

    @Override
    public boolean onUnbind (Intent intent){
        stopForeground(true);
        return false;
    }
}
