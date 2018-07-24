package com.example.dh.tpmusicagrupo3.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by DH on 20/7/2018.
 */

public class NotificationReceiver extends BroadcastReceiver {
    public static final String ACTION_KEY = "action";
    public static final String PLAY_ACTION = "play";
    public static final String PREVIOUS_ACTION = "previous";
    public static final String NEXT_ACTION = "next";
    private MediaPlayerService mediaPlayerService;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String intentString = bundle.getString(ACTION_KEY);
        if(intentString == null) return;
        mediaPlayerService = MediaPlayerService.getInstance();

        switch (intentString){
            case PLAY_ACTION:
                mediaPlayerService.togglePlayer();
                mediaPlayerService.sendNotification(true);
                break;
            case PREVIOUS_ACTION:
                mediaPlayerService.previousSong();
                break;
            case NEXT_ACTION:
                mediaPlayerService.nextSong();
                break;
        }
    }
}
