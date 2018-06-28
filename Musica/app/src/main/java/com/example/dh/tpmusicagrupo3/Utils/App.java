package com.example.dh.tpmusicagrupo3.Utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "notiReproductor";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Reproductor Noti", NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setDescription("Permite el uso de los controles del reproductor, desde las notificaciones y la pantalla bloqueada.");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private static String appDirectory = "com.example.dh.tpmusicagrupo3.";
    private static String action = "action.";
    public interface ACTION {
        public String MAIN_ACTION = appDirectory + action + "main";
        public String PREVIOUS_ACTION = appDirectory + action + "previous";
        public String NEXT_ACTION = appDirectory + action + "next";
        public String PLAY_ACTION = appDirectory + action + "play";
        public String START_FOREGROUND = appDirectory + action + "startForeground";
        public String STOP_FOREGROUND = appDirectory + action + "stopForeground";
    }
    public interface NOTIFICATION_ID {
        public static Integer REPRODUCTOR_SERVICE = 1;
    }
}
