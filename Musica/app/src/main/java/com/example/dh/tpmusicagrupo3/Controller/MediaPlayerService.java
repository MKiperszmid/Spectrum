package com.example.dh.tpmusicagrupo3.Controller;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.Utils.App;
import com.example.dh.tpmusicagrupo3.View.Activities.MainActivity;

import java.io.IOException;

public class MediaPlayerService extends Service {

    // TODO: Estoy testeando un par de cosas
    // Se supone que el Service es lo que hay que usar, en vez de el MediaPlayerController.
    // Pero en un futuro.


    private MediaPlayer mediaPlayer;
    private Track currentPlaying;
    private NotificationManagerCompat notificationManagerCompat;

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
    public void onCreate() {
        super.onCreate();
        notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    public void sendNotification(){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_spectrum)
                .setContentTitle(currentPlaying.getTitle_short())
                .setContentText(currentPlaying.getArtist().getName())
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentIntent(pendingIntent)
                .build();
        notificationManagerCompat.notify(App.NOTIFICATION_ID.REPRODUCTOR_SERVICE, notification);
    }

    public void showNotification(){
        Intent intent = new Intent(this, MainActivity.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Track track = (Track)intent.getExtras().getSerializable(PLAY_TRACK);
        if(track != null)
            startSong((Track) intent.getExtras().getSerializable(PLAY_TRACK));
/*
        if(intent.getAction().equals(App.ACTION.START_FOREGROUND)){
            Log.i("MPS", "START FOREGROUND");
            //Mostrar notificacion que contiene al reproductor
        }
        else if(intent.getAction().equals(App.ACTION.PREVIOUS_ACTION)){
            Log.i("MPS", "PREVIOUS ACTION");
        }
        else if(intent.getAction().equals(App.ACTION.NEXT_ACTION)){
            Log.i("MPS", "NEXT ACTION");
        }
        else if(intent.getAction().equals(App.ACTION.PLAY_ACTION)){
            Log.i("MPS", "PLAY ACTION");
        }
        else if(intent.getAction().equals(App.ACTION.STOP_FOREGROUND)){
            Log.i("MPS", "STOP FOREGROUND");
            stopForeground(true);
            stopSelf();
        }
*/
        return START_REDELIVER_INTENT;
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
            //showNotification();
            sendNotification();
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
