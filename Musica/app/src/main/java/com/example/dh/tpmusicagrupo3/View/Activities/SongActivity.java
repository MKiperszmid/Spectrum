package com.example.dh.tpmusicagrupo3.View.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dh.tpmusicagrupo3.Controller.MediaPlayerService;
import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterSongPager;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

import java.util.ArrayList;
import java.util.List;

public class SongActivity extends AppCompatActivity implements SongFragment.NotificadorCambioCancion, SongFragment.NotificadorFragmentActivity {

    private List<SongFragment> fragments;
    private List<Track> canciones;
    public static Integer index;
    private ViewPager pager;
    private List<Track> tracks;
    private MediaPlayerService mediaPlayerService;
    public static Boolean isDisplayed = false;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean songChange = intent.getExtras().getBoolean(MediaPlayerService.IS_FINISHED, false);
            if (songChange) {
                adelantar();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        MediaPlayerService mediaPlayerService = MediaPlayerService.getInstance();
        mediaPlayerService.closeNotification();
        isDisplayed = true;
        mediaPlayerService.setDisplayed(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isDisplayed = false;
        mediaPlayerService.setDisplayed(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_song);
        fragments = new ArrayList<>();
        tracks = (ArrayList<Track>) getIntent().getExtras().getSerializable(SongFragment.CANCIONESKEY);
        mediaPlayerService = MediaPlayerService.getInstance();
        CargarFragments();
        pager = findViewById(R.id.songactivityViewPager);
        final AdapterSongPager adapter = new AdapterSongPager(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);

        //TODO: Ver como hacer para que esto se llame 1 vez por cancion, y no todo el tiempo que se clickea incluso la misma cancion.
        index = tracks.indexOf(mediaPlayerService.getCurrentPlaying()) + 1; // +1 Ya que el viewpager empieza en 1 (el 0 es la ultima cancion)

        pager.setCurrentItem(index);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //TODO: Mostrar codigo a Nico.
            @Override
            public void onPageSelected(int position) {
                if (position >= adapter.getCount() - 1) {
                    pager.setCurrentItem(1, false);
                } else if (position <= 0) {
                    pager.setCurrentItem(fragments.size() - 2, false);
                }
                mediaPlayerService.startSong(fragments.get(position).getCancion(), tracks);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void CargarFragments() {
        canciones = new ArrayList<>();
        canciones.add(tracks.get(tracks.size() - 1));
        canciones.addAll(tracks);
        canciones.add(tracks.get(0));

        for (Track cancion : canciones) {
            fragments.add(SongFragment.dameFragment(cancion));
        }
    }

    @Override
    public void retroceder() {
        pager.setCurrentItem(pager.getCurrentItem() - 1);
    }

    @Override
    public void adelantar() {
        pager.setCurrentItem(pager.getCurrentItem() + 1);
    }

    @Override
    public void notificarArtista(Artist artist) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.ArtistaKey, artist);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(MediaPlayerService.CHANGESONG));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
