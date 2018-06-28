package com.example.dh.tpmusicagrupo3.Controller;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;

import com.example.dh.tpmusicagrupo3.Controller.DatosControllers.ArtistController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Fragments.PlaybarbottomFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

import java.io.IOException;

public class MediaPlayerController {

    private static MediaPlayer mp;
    private static MediaPlayerController mediaPlayerController;
    private static Track currentPlaying;
    private static Boolean isPlaying;
    private static Boolean isNew;

    private static int currentPosition = 0;

    public static MediaPlayerController getInstance(){
        if(mediaPlayerController == null){
            mediaPlayerController = new MediaPlayerController();
        }
        return mediaPlayerController;
    }

    public Track getCurrentPlaying(){
        return currentPlaying;
    }

    private static boolean exists(){
        return mp != null;
    }

    public boolean isPlaying(){
        return exists() && mp.isPlaying();
    }

    private MediaPlayerController(){

    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void retroceder(FloatingActionButton btn){
        currentPosition = 0;
        mp.pause();
        btn.setImageResource(R.drawable.stop);
        play();
    }

    private static void clear(){
        if(exists())
            mp.release();
        isNew = true;
    }

    private static void pause(){
        mp.pause();
        currentPosition = mp.getCurrentPosition();
        isNew = false;
    }

    private static void loop(){
        mp.setLooping(true);
    }

    private void play(){
        goToPosition(currentPosition);
        mp.start();
        isNew = false;
    }

    public void playPause(ImageView img){
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

    public void playPause(FloatingActionButton btn){
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

    public MediaPlayer getMediaPlayer(){ return mp; }

    public void setIsPlaying(Boolean bool){
        isPlaying = bool;
    }

    public void setCurrentPlaying(Track track){
        currentPlaying = track;
    }

    @SuppressLint("WrongConstant")
    public void create(Track track){
        clear();
        isPlaying = true;
        mp = new MediaPlayer();

        //Esto al parecer es importante declararlo, para mejorar el rendimiento.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mp.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());
        }
        try {
            mp.setDataSource(track.getPreview());
            mp.prepareAsync();
            currentPosition = 0;
            currentPlaying = track;
            isNew = true;
            PlaybarbottomFragment.getPlayBtn().setImageResource(R.drawable.stop);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    play();
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
            isPlaying = false;
        }
    }

    // Ir a una posicion de la cancion
    public void goToPosition(int posicion){
        if(!exists()) return;
        mp.seekTo(posicion);
    }

    // Obtener duracion de la cancion en milisegundos
    public Integer getDuration(){
        return mp.getDuration(); //Track contiene un Duration en Segundos. Es mejor usar eso.
    }

    // Obtener la duracion actual
    public static Integer getCurrentDuration(){
        return mp.getCurrentPosition();
    }
}
