package me.corentin.android.appsinstallationstracker.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import me.corentin.android.appsinstallationstracker.R;

/**
 * Created by corentin on 12/09/15.
 */
public class TrackerService extends Service
{

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (intent.getDataString() != null)
            this.handleNewApp(intent.getDataString());
        return START_NOT_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        if (intent.getDataString() != null)
            this.handleNewApp(intent.getDataString());
    }

    private void handleNewApp(String packageName)
    {
        packageName = packageName.substring(8);

        final PackageManager packageManager = this.getPackageManager();

        ApplicationInfo applicationInfo = null;

        try
        {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        }
        catch (Exception e)
        {
            Toast.makeText(this, packageName + " : Not Found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (applicationInfo != null)
        {

            final Intent intentApp = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intentApp.setData(Uri.parse("package:" + packageName));

            final PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intentApp, 0);

            final Notification n = new Notification.Builder(this)
                    .setContentTitle(packageManager.getApplicationLabel(applicationInfo))
                    .setContentText(applicationInfo.packageName)
                    .setLargeIcon(((BitmapDrawable) packageManager.getApplicationIcon(applicationInfo)).getBitmap())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();


            final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            int time = (int) System.currentTimeMillis();

            notificationManager.notify(time, n);
        }
    }
}
