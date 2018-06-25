package com.example.dh.tpmusicagrupo3.Controller;

import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;

import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

import java.io.IOException;

public class MediaPlayerController {

    private static MediaPlayer mp;
    private static MediaPlayerController mediaPlayerController;
    private static Track currentPlaying;
    private static Boolean isPlaying;
    private static NotificadorEstadoCancion notificadorEstadoCancion;

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

    public static MediaPlayerController getInstance(NotificadorEstadoCancion nec){
        if(mediaPlayerController == null){
            mediaPlayerController = new MediaPlayerController();
        }
        notificadorEstadoCancion = nec;
        return mediaPlayerController;
    }

    private static boolean exists(){
        return mp != null;
    }

    public boolean isPlaying(){
        return exists() && mp.isPlaying();
    }

    private MediaPlayerController(){

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

    public void create(Track track){
        clear();
        isPlaying = true;
        mp = new MediaPlayer();
        try {
            mp.setDataSource(track.getPreview());
            mp.prepareAsync();
            currentPosition = 0;
            currentPlaying = track;
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mp.start();
                    notificadorEstadoCancion.cambiarEstado();
                }
            });
        }
        catch (IOException e){
            isPlaying = false;
        }
    }

    // Ir a una posicion de la cancion
    public static void goToPosition(int posicion){
        if(!exists()) return;
        mp.seekTo(posicion);
    }

    // Obtener duracion de la cancion en milisegundos
    public Integer getDuration(){
        Integer durationMP = mp.getDuration();
        return durationMP;
    }

    // Obtener la duracion actual
    public static Integer getCurrentDuration(){
        Integer currentDurationMP = mp.getCurrentPosition();
        return currentDurationMP;
    }

    public interface NotificadorEstadoCancion{
        public void cambiarEstado();
    }
}
