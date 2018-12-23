package com.example.yahov.kinveyandroidpushnotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.kinvey.android.push.KinveyGCMService;

public class GCMService extends KinveyGCMService {

    @Override
    public void onMessage(String message) {
        displayNotification(message);
    }

    @Override
    public void onError(String error) {
        displayNotification(error);
    }

    @Override
    public void onDelete(String deleted) {
        displayNotification(deleted);
    }

    @Override
    public void onRegistered(String gcmID) {
        displayNotification(gcmID);
    }

    @Override
    public void onUnregistered(String oldID) {
        displayNotification(oldID);
    }

    public Class getReceiver() {
        return GCMReceiver.class;
    }

    private void displayNotification(String message){
        int NOTIFICATION_ID = 1;
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;

        // When you target Android 8.0 (API level 26), you must implement one or more notification channels to display notifications to your users.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel.";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
            builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                    .setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))
                    .setContentText(message);
        } else {
            builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                    .setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))
                    .setContentText(message);
        }

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}