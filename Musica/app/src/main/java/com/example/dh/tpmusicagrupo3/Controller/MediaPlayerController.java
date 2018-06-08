package com.example.dh.tpmusicagrupo3.Controller;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;

import com.example.dh.tpmusicagrupo3.R;

public class MediaPlayerController {

    private static MediaPlayer mp;

    private static int currentPosition = 0;

    public static boolean exists(){
        return mp != null;
    }

    public static boolean isPlaying(){
        return exists() && mp.isPlaying();
    }

    public static void retroceder(FloatingActionButton btn){
        currentPosition = 0;
        mp.pause();
        btn.setImageResource(R.drawable.stop);
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

    private static void loop(){
        mp.setLooping(true);
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
