package com.example.impy.home3;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class MyService extends IntentService {

    final static String BROADCAST_NAME = "IMAGE_LOADED";
    public MyService() {
        super("MyService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "StartService");
        goMessage("Garold is downloading", getApplicationContext());
        URL url = null;
        try {
            url = new URL("https://static31.cmtt.ru/club/d4/d0/3c/6f40757620c1ca.jpg");
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            FileOutputStream fout = openFileOutput("imagee.png", Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fout);
            fout.close();
            goMessage("Garold has been downloaded", getApplicationContext());
            sendBroadcast(new Intent(BROADCAST_NAME));
        } catch (Exception e) {
            Log.d(TAG, "Exception");
        }
    }

    private static final int NOTIFY_ID = 101;
    public static void goMessage(String mes, Context context) {

        Intent intent = new Intent(context, MyService.class);

        Notification.Builder builder = new Notification.Builder(context);

        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("LoadMessage")
                .setContentText(mes);

        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFY_ID, notification);
    }

}
