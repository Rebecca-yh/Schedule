package com.example.yanghan.schedule;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private int NOTIFICATION_ID = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        NOTIFICATION_ID=intent.getIntExtra("key_id",-1);
        if (intent.getAction().equals("NOTIFICATION")) {

            Log.i("receiver","true");

            String subject=intent.getStringExtra("subject");
            if (Build.VERSION.SDK_INT >= 26)
            {
                NotificationManager manager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent2 = new Intent(context, MainActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent2, 0);
                String id = "channel_1";
                String description = "123";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(id, "123", importance);
                manager.createNotificationChannel(mChannel);

                Notification notification = new Notification.Builder(context, id).setContentTitle("Title")
                        .setSmallIcon(R.drawable.calendar)
                        .setContentTitle("Schedule")
                        .setContentText(subject)
                        .setAutoCancel(true)
                        .setFullScreenIntent(pendingIntent,true)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .build();
                notification.flags |=Notification.FLAG_AUTO_CANCEL;
                Notification.Builder notificationBuilder = new Notification.Builder(context);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    notificationBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
                    // 关联PendingIntent
                    notificationBuilder.setFullScreenIntent(pendingIntent, false);// 横幅
                    }

                manager.notify(NOTIFICATION_ID, notification);
            }

            else
            {

                NotificationManager manager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent2 = new Intent(context, MainActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent2, 0);
                Notification notify = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.bell)
                        .setContentTitle("Schedule")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(subject))
                        .setFullScreenIntent(pendingIntent,true)
                        .setAutoCancel(true)
                        .setNumber(1).build();
                manager.notify(NOTIFICATION_ID, notify);


            }

        }

    }
}
