package com.example.dh.tpmusicagrupo3.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.View.Activities.SongActivity;

/**
 * Created by DH on 20/7/2018.
 */

public class NotificationReceiver extends BroadcastReceiver {
    public static final String ACTION_KEY = "action";
    public static final String PLAY_ACTION = "play";
    private MediaPlayerService mediaPlayerService;

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentString = intent.getStringExtra(ACTION_KEY);
        if(intentString == null) return;
        mediaPlayerService = MediaPlayerService.getInstance();

        switch (intentString){
            case PLAY_ACTION:
                mediaPlayerService.togglePlayer();
                break;
        }
    }
}
