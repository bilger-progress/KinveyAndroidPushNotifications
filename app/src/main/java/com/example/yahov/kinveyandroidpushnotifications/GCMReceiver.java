package com.example.yahov.kinveyandroidpushnotifications;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GCMReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(), com.example.yahov.kinveyandroidpushnotifications.GCMService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
    }
}