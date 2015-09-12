package me.corentin.android.appsinstallationstracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import me.corentin.android.appsinstallationstracker.services.TrackerService;

/**
 * Created by corentin on 12/09/15.
 */
public class AppsInstallationsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent intent2 = new Intent(context, TrackerService.class);
        intent2.setData(intent.getData());
        context.startService(intent2);
    }
}
