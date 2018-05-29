package com.example.dh.tpmusicagrupo3;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;

/**
 * Created by DH on 29/5/2018.
 */

public class MediaPlayerController {

    //TODO: Hacer que esta clase sea la contenedora del MediaPlayer, y no el HomeFragment.


    private static MediaPlayer mp;

    private static int currentPosition = 0;

    public static boolean exists(){
        return mp != null;
    }

    public static boolean isPlaying(){
        return exists() && mp.isPlaying();
    }

    public static void retroceder(){
        currentPosition = 0;
        mp.pause();
        play();
    }

    private static void clear(){
        if(exists())
            mp.release();
    }

    private static void pause(){
        mp.pause();
        currentPosition = mp.getCurrentPosition();
    }

    private static void play(){
        goToPosition(currentPosition);
        mp.start();
    }

    public static void playPause(ImageView img){
        if(!exists()) return;
        if(mp.isPlaying()){
            img.setImageResource(R.drawable.play);
            pause();
        }
        else{
            img.setImageResource(R.drawable.stop);
            play();
        }
    }

    public static void playPause(FloatingActionButton btn){
        if(!exists()) return;
        if(mp.isPlaying()){
            btn.setImageResource(R.drawable.play);
            pause();
        }
        else{
            btn.setImageResource(R.drawable.stop);
            play();
        }
    }

    public static void create(Context context, int cancionID){
        clear();
        mp = MediaPlayer.create(context, cancionID);
        currentPosition = 0;
        mp.start();
    }

    public static void goToPosition(int posicion){
        if(!exists()) return;
        mp.seekTo(posicion);
    }
}
