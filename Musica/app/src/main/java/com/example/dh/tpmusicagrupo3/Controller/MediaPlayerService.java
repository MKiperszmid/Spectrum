package com.example.dh.tpmusicagrupo3.Controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
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

    private MediaPlayer mediaPlayer;
    private Track currentPlaying;
    private NotificationManagerCompat notificationManagerCompat;
    private List<Track> currentTracks;
    private MediaSessionCompat mediaSessionCompat;
    private static MediaPlayerService mediaPlayerService;
    private NotificationCompat.Builder builder;

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
    private PendingIntent previousPending, playPending, nextPending;
    public static Boolean isDisplayed = false;

    public MediaPlayerService() {

    }

    public static MediaPlayerService getInstance() {
        if (mediaPlayerService == null) {
            mediaPlayerService = new MediaPlayerService();
        }
        return mediaPlayerService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        mediaSessionCompat = new MediaSessionCompat(this, "tag");
        previousPending = createPending(NotificationReceiver.PREVIOUS_ACTION, 0);
        playPending = createPending(NotificationReceiver.PLAY_ACTION, 1);
        nextPending = createPending(NotificationReceiver.NEXT_ACTION, 2);
        createNotification();
    }

    public void setDisplayed(Boolean bool) {
        isDisplayed = bool;
    }

    public void closeNotification() {
        try {
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(App.NOTIFICATION_ID.REPRODUCTOR_SERVICE);
        } catch (RuntimeException e) {

        }
    }

    private PendingIntent createPending(String action, Integer requestAction) {
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putString(NotificationReceiver.ACTION_KEY, action);
        intent.putExtras(bundle);
        return PendingIntent.getBroadcast(getApplicationContext(), requestAction, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createNotification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder = new NotificationCompat.Builder(getApplicationContext(), App.CHANNEL_ID)
                .setSmallIcon(R.drawable.spclogo)
                //.setLargeIcon(); //En el SendNotification
                //.setContentTitle(currentPlaying.getTitle_short()) // SN
                //.setContentText(currentPlaying.getArtist().getName()); //SN
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.prevresize, "Previous", previousPending)
                .addAction(R.drawable.playresize, "Play", playPending)
                .addAction(R.drawable.nextresize, "Next", nextPending)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setColor(getResources().getColor(R.color.colorAccent));
    }

    public void sendNotification(final Boolean onGoing) {
        if (currentPlaying == null || !isDisplayed) return;


        Glide.with(this).asBitmap().load(currentPlaying.getAlbum().getCover_big()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Notification songNotification = builder.setContentTitle(currentPlaying.getTitle_short())
                        .setContentText(currentPlaying.getArtist().getName())
                        .setLargeIcon(resource)
                        .setOngoing(onGoing)
                        .build();

                notificationManagerCompat.notify(App.NOTIFICATION_ID.REPRODUCTOR_SERVICE, songNotification);
            }
        });
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

    public void clearPlayer() {
        if (mediaPlayer != null)
            mediaPlayer.release();
        closeNotification();
        changeImage(false);
    }

    public void nextSong() {
        try {
            Track current = currentPlaying;
            Integer index = currentTracks.indexOf(current);
            startSong(currentTracks.get(index + 1), currentTracks);
        } catch (Exception e) {
            startSong(currentTracks.get(0), currentTracks);
        }
    }

    public void previousSong() {
        try {
            Track current = currentPlaying;
            Integer index = currentTracks.indexOf(current);
            startSong(currentTracks.get(index - 1), currentTracks);
        } catch (Exception e) {
            startSong(currentTracks.get(currentTracks.size() - 1), currentTracks);
        }
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
