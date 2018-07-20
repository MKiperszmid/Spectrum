package com.example.dh.tpmusicagrupo3.Controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.Utils.App;
import com.example.dh.tpmusicagrupo3.View.Activities.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaPlayerService extends Service {

    // TODO: Estoy testeando un par de cosas
    // Se supone que el Service es lo que hay que usar, en vez de el MediaPlayerController.
    // Pero en un futuro.


    private MediaPlayer mediaPlayer;
    private Track currentPlaying;
    private NotificationManagerCompat notificationManagerCompat;
    private List<Track> currentTracks;
    private MediaSessionCompat mediaSessionCompat;
    private static MediaPlayerService mediaPlayerService;

    private final IBinder iBinder = new MyBinder();

    public class MyBinder extends Binder {
        public MediaPlayerService getService() {
            mediaPlayerService = MediaPlayerService.this;
            return MediaPlayerService.this;
        }
    }

    public static final String PLAY_TRACKS = "PLAYTRACKS";
    public static final String PLAY_TRACK = "playtrack";
    public static final String IS_PLAYING = "isplaying";
    public static final String CHANGEIMAGE = "changeImage";
    public static final String CHANGESONG = "changeSong";
    public static final String IS_FINISHED = "isFinished";

    public MediaPlayerService() {

    }

    public static MediaPlayerService getInstance(){
        if(mediaPlayerService == null){
            mediaPlayerService = new MediaPlayerService();
        }
        return mediaPlayerService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        mediaSessionCompat = new MediaSessionCompat(this, "tag");
    }

    public void closeNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(App.NOTIFICATION_ID.REPRODUCTOR_SERVICE);
    }

    public void sendNotification(final Boolean onGoing) {
        if (currentPlaying == null) return;

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        Intent playIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
        playIntent.putExtra(NotificationReceiver.ACTION_KEY, NotificationReceiver.PLAY_ACTION);
        final PendingIntent playPending = PendingIntent.getBroadcast(getApplicationContext(), 0, playIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Glide.with(this).asBitmap().load(currentPlaying.getAlbum().getCover_big()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Notification notification = new NotificationCompat.Builder(getApplicationContext(), App.CHANNEL_ID)
                        .setSmallIcon(R.drawable.icon_spectrum)
                        .setLargeIcon(resource)
                        .setContentTitle(currentPlaying.getTitle_short())
                        .setContentText(currentPlaying.getArtist().getName())
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setCategory(NotificationCompat.CATEGORY_SERVICE)
                        .setContentIntent(pendingIntent)
                        .setOngoing(onGoing)
                        .addAction(R.drawable.prevresize, "Previous", null)
                        .addAction(R.drawable.playresize, "Play", playPending)
                        .addAction(R.drawable.nextresize, "Next", null)
                        .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                                .setShowActionsInCompactView(0, 1, 2)
                                .setMediaSession(mediaSessionCompat.getSessionToken()))
                        .setColor(getResources().getColor(R.color.colorAccent))
                        .build();
                notificationManagerCompat.notify(App.NOTIFICATION_ID.REPRODUCTOR_SERVICE, notification);
            }
        });
    }

    public void showNotification() {
        Intent intent = new Intent(this, MainActivity.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        Track track = (Track) bundle.getSerializable(PLAY_TRACK);
        ArrayList<Track> tracks = (ArrayList<Track>) bundle.getSerializable(PLAY_TRACKS);
        if (track != null) {
            startSong(track, tracks);
        }
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

    private void clearPlayer() {
        if (mediaPlayer != null)
            mediaPlayer.release();
    }

    public void startSong(Track track, List<Track> tracks) {
        clearPlayer();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(track.getPreview());
            mediaPlayer.prepareAsync();
            currentPlaying = track;
            currentTracks = tracks;
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
                    changeSong(true);
                }
            });

            sendNotification(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            //
        }
    }

    public void pausePlayer() {
        mediaPlayer.pause();
        changeImage(false);
    }

    public Track getCurrentPlaying() {
        return this.currentPlaying;
    }

    public void changeImage(Boolean playing) {
        Intent intent = new Intent(CHANGEIMAGE);
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_PLAYING, playing);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void changeSong(Boolean finish) {
        Intent intent = new Intent(CHANGESONG);
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_FINISHED, finish);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void playPlayer() {
        mediaPlayer.start();
        changeImage(true);
    }

    public void togglePlayer() {
        // El boton de play/pausa llama a este metodo.
        // Este metodo llama a play o pausa, y esos metodos cambian el icono del boton.
        if (mediaPlayer.isPlaying())
            pausePlayer();
        else
            playPlayer();
    }

    public void goToPosition(int position) {
        mediaPlayer.seekTo(position);
    }

    public List<Track> getCurrentTracks() {
        return currentTracks;
    }

    public Integer getCurrentDuration() {
        return mediaPlayer.getCurrentPosition();
    }

    public Boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }
}
