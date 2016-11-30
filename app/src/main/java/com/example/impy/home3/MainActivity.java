package com.example.impy.home3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFY_ID = 101;
    public void onButtom1Click(View view) {
        Intent intent = new Intent(this, MyService.class);
        intent.setAction("Start");
        startService(intent);

    }

    public void onButtom2Click(View view) {

        Log.d(TAG, "Click");
        Intent intent = new Intent(this, MyService.class);
        MyService.goMessage("Why you tried to stop my app?", this);
        stopService(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = (ImageView) findViewById(R.id.myImage);
        final TextView textView = (TextView) findViewById(R.id.myText);
        final String imageFilename = getFilesDir().getPath() + "/imagee.png";
        try {
            File f = new File(imageFilename);
            if (f.exists()) {
                Log.d(TAG, "Downloaded");
                MyService.goMessage("Message already has been downloaded", this);
                imageView.setImageBitmap(BitmapFactory.decodeFile(f.getPath()));
                imageView.setVisibility(VISIBLE);
                textView.setVisibility(INVISIBLE);
            }
        }
        catch (Exception e) {
            Log.d(TAG,"EEEERRR");
        }
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                File f = new File(imageFilename);
                if (f.exists()) {
                    imageView.setImageBitmap(BitmapFactory.decodeFile(f.getPath()));
                }
                imageView.setVisibility(VISIBLE);
                textView.setVisibility(INVISIBLE);
            }
        };
        registerReceiver(receiver, new IntentFilter(MyService.BROADCAST_NAME));
        Log.d(TAG,"Nothing");
    }
}
