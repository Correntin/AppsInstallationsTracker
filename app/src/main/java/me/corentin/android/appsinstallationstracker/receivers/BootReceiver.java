package me.corentin.android.appsinstallationstracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import me.corentin.android.appsinstallationstracker.services.TrackerService;

/**
 * Created by corentin on 12/09/15.
 */
public class BootReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        context.startService(new Intent(context, TrackerService.class));
    }
}
