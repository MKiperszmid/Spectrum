package com.example.dh.tpmusicagrupo3.Controller;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.media.MediaPlayer;
import android.support.v4.content.LocalBroadcastManager;

import com.example.dh.tpmusicagrupo3.Model.POJO.Track;

import java.io.IOException;

public class MediaPlayerService extends Service {

    // TODO: Estoy testeando un par de cosas
    // Se supone que el Service es lo que hay que usar, en vez de el MediaPlayerController.
    // Pero en un futuro.


    private MediaPlayer mediaPlayer;
    private Track currentPlaying;

    private final IBinder iBinder = new MyBinder();

    public class MyBinder extends Binder{
        public MediaPlayerService getService(){
            return MediaPlayerService.this;
        }
    }

    public static final String PLAY_TRACK = "playtrack";
    public static final String IS_PLAYING = "isplaying";
    public static final String CHANGEIMAGE = "changeImage";
    public MediaPlayerService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startSong((Track) intent.getExtras().getSerializable(PLAY_TRACK));
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private void clearPlayer(){
        if(mediaPlayer != null)
            mediaPlayer.release();
    }

    public void startSong(Track track){
        clearPlayer();
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(track.getPreview());
            mediaPlayer.prepareAsync();
            currentPlaying = track;
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    playPlayer();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    changeImage(false);
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void pausePlayer(){
        mediaPlayer.pause();
        changeImage(false);
    }

    public Track getCurrentPlaying(){ return this.currentPlaying; }

    public void changeImage(Boolean playing){
        Intent intent = new Intent(CHANGEIMAGE);
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_PLAYING, playing);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void playPlayer() {
        mediaPlayer.start();
        changeImage(true);
    }

    public void togglePlayer(){
        // El boton de play/pausa llama a este metodo.
        // Este metodo llama a play o pausa, y esos metodos cambian el icono del boton.
        if(mediaPlayer.isPlaying())
            pausePlayer();
        else
            playPlayer();
    }

    public void goToPosition(int position){
        mediaPlayer.seekTo(position);
    }

    public Integer getCurrentDuration(){
        return mediaPlayer.getCurrentPosition();
    }

    public Boolean isPlaying() { return mediaPlayer.isPlaying(); }
}
