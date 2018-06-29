package com.example.dh.tpmusicagrupo3.View.Activities;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dh.tpmusicagrupo3.Controller.DatosControllers.TypeController;
import com.example.dh.tpmusicagrupo3.Controller.MediaPlayerService;
import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Fragments.BarbottomFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.ExplorarFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.HomeFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.PlaybarbottomFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements HomeFragment.NotificadorActivity, BarbottomFragment.NotificadorActivityBarBottom,
        ExplorarFragment.NotificarClickeado{

    public static MediaPlayerService mediaPlayerService;
    private Boolean isServiceBounded = false;
    private Track cancionActual;
    private Boolean playbarMostrado = false;
    private FrameLayout frameLayoutPlayBar;
    private Integer posicion;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isServiceBounded = true;
            MediaPlayerService.MyBinder binder = (MediaPlayerService.MyBinder) service;
            mediaPlayerService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceBounded = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Inicio");
        LoadFragment(new HomeFragment(), R.id.homeID);
        LoadFragment(new BarbottomFragment(), R.id.barBottom);
        //LoadFragment(new PlaybarbottomFragment(), R.id.playBarBottom);
    }

    public void LoadFragment(Fragment fragment, int id){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }

    public void LoadFragment(Fragment fragment, int id, Bundle bundle){
        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void recibirCancion(Track cancion, int position) {

        frameLayoutPlayBar = findViewById(R.id.playBarBottom);
        frameLayoutPlayBar.setVisibility(View.VISIBLE);
        cancionActual = cancion;

        startSong(cancion);//TODO: Borrar esta linea para sacar el service

        Bundle bundlePB = new Bundle();
        bundlePB.putInt(PlaybarbottomFragment.CLAVE_CANCION, position);
        posicion = position;
        bundlePB.putSerializable(PlaybarbottomFragment.CLAVE_PLAYING, cancion);
        PlaybarbottomFragment playbarbottomFragment = new PlaybarbottomFragment();
        playbarbottomFragment.setArguments(bundlePB);
        LoadFragment(playbarbottomFragment, R.id.playBarBottom);

        Intent intent = new Intent(this, SongActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(SongFragment.CANCIONPOS, position);
        bundle.putSerializable(SongFragment.cancionKey, cancion);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startSong(Track cancion){
        if(mediaPlayerService == null || !mediaPlayerService.getCurrentPlaying().equals(cancion)) {
            Intent playerService = new Intent(this, MediaPlayerService.class);
            Bundle bundleService = new Bundle();
            bundleService.putSerializable(MediaPlayerService.PLAY_TRACK, cancion);
            playerService.putExtras(bundleService);
            startService(playerService);
            bindService(playerService, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
       /* if(isServiceBounded){
            unbindService(serviceConnection);
            isServiceBounded = false;
        }*/
    }

    @Override
    public Track getCurrentPlaying() {
        Track track = cancionActual;
        if(mediaPlayerService != null)
            track = mediaPlayerService.getCurrentPlaying();
        return track;
    }

    @Override
    public Boolean isPlaying() {
        if(mediaPlayerService == null){
            return false;
        }
        return mediaPlayerService.isPlaying();
    }

    @Override
    public void playSong() {
        MainActivity.mediaPlayerService.togglePlayer();
    }

    @Override
    public void recibirSeccion(Fragment fragment) {
        LoadFragment(fragment, R.id.homeID);
    }

    @Override
    public void notificar(TypeController controller) {
        Fragment fragment = controller.getFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TypeController.KEY_T, (Serializable) controller.getData());
        LoadFragment(fragment,R.id.homeID, bundle);
    }
}
