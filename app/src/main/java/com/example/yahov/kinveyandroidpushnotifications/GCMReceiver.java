package com.example.yahov.kinveyandroidpushnotifications;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GCMReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(), GCMService.class.getName());
        GCMService.enqueueWork(context, (intent.setComponent(comp)), GCMService.class);
    }
}