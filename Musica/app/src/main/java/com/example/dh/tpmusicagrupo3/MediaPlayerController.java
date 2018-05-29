package com.example.dh.tpmusicagrupo3;

import android.content.Context;
import android.media.MediaPlayer;

import static com.example.dh.tpmusicagrupo3.HomeFragment.mp;

/**
 * Created by DH on 29/5/2018.
 */

public class MediaPlayerController {

    //TODO: Hacer que esta clase sea la contenedora del MediaPlayer, y no el HomeFragment.

    private static int currentPosition = 0;

    public static void retroceder(){
        currentPosition = 0;
        mp.pause();
        play();
    }

    private static void clear(){
        if(mp != null)
            mp.release();
    }

    public static void pause(){
        mp.pause();
        currentPosition = mp.getCurrentPosition();
    }

    public static void play(){
        goToPosition(currentPosition);
        mp.start();
    }

    public static void playPause(){
        if(mp.isPlaying())
            pause();
        else
            play();
    }

    public static void create(Context context, int cancionID){
        clear();
        mp = MediaPlayer.create(context, cancionID);
        currentPosition = 0;
        mp.start();
    }

    public static void goToPosition(int posicion){
        mp.seekTo(posicion);
    }
}
