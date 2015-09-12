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

        PackageManager packageManager = this.getPackageManager();

        ApplicationInfo applicationInfo = null;

        try
        {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Log.d("CORENTIN", "handleNewApp packageInfo="+applicationInfo);

        if (applicationInfo != null)
        {

            Intent intentApp = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intentApp.setData(Uri.parse("package:" + packageName));

            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intentApp, 0);

            Notification n = new Notification.Builder(this)
                    .setContentTitle(packageManager.getApplicationLabel(applicationInfo))
                    .setContentText(applicationInfo.packageName)
                    .setLargeIcon(((BitmapDrawable) packageManager.getApplicationIcon(applicationInfo)).getBitmap())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            int time = (int) System.currentTimeMillis();

            Log.d("CORENTIN", "handleNewApp time=" + time);

            notificationManager.notify(time, n);
        }
    }
}
