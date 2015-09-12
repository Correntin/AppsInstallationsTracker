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
        final Intent intentPackage = new Intent(context, TrackerService.class);
        intentPackage.setData(intent.getData());
        context.startService(intentPackage);
    }
}
