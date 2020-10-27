package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button mBtnNotification;
    String MY_CHANNEL ="MY_CHANNEL";
    NotificationManager notificationManager;
    int REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Lấy dữ liệu từ notification
        Intent intent = getIntent();

        // Kiểm tra có giá trị trả về không
        if(intent != null){
            String message = intent.getStringExtra("message");
            // Kiểm tra có nhận được dữ liệu không
            if(message != null){
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }

        notificationManager  = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBtnNotification = findViewById(R.id.btnNotification);
        mBtnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                intent.putExtra("message","Hello Main");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,REQUEST_CODE , intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this,MY_CHANNEL)
                        .setContentTitle("Ban cap nhap moi")
                        .setContentText("Phien ban app 15.0")
                        .setShowWhen(true)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ech))
                        .addAction(R.drawable.ic_launcher_background,"Open App",pendingIntent);

                //Kiểm tra phiên bản máy
                // Máy 26 trở lên mới có
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel notificationChannel = new NotificationChannel(
                            MY_CHANNEL, "CHANNEL", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.BLUE);
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);
                }
                notificationManager.notify(1,notification.build());
            }
        });
    }
}