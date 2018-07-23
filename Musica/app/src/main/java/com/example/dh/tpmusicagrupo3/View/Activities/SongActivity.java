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
import com.example.dh.tpmusicagrupo3.View.Fragments.HomeFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.PlaybarbottomFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

import java.util.ArrayList;
import java.util.List;

public class SongActivity extends AppCompatActivity implements SongFragment.NotificadorCambioCancion, SongFragment.NotificadorFragmentService, SongFragment.NotificadorFragmentActivity {

    private List<SongFragment> fragments;
    private List<Track> canciones;
    public static Integer index;
    private ViewPager pager;
    private List<Track> tracks;
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        isDisplayed = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_song);
        fragments = new ArrayList<>();
        index = getIntent().getExtras().getInt(SongFragment.CANCIONPOS) + 1;

        tracks = (ArrayList<Track>) getIntent().getExtras().getSerializable(SongFragment.CANCIONESKEY);
        CargarFragments();
        pager = findViewById(R.id.songactivityViewPager);
        final AdapterSongPager adapter = new AdapterSongPager(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
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
                MainActivity.mediaPlayerService.startSong(fragments.get(position).getCancion(), tracks);
                //mediaPlayerController.create(fragments.get(position).getCancion());
                HomeFragment.cancionActual = canciones.get(position);
                index = position;
                PlaybarbottomFragment.posicion = position - 1; //TODO: Arreglar esto para que no use STATIC.
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
    public void playSong() {
        MainActivity.mediaPlayerService.togglePlayer();
    }

    @Override
    public Integer getCurrentDuration() {
        return MainActivity.mediaPlayerService.getCurrentDuration();
    }

    @Override
    public void startSong(Track track) {
        MainActivity.mediaPlayerService.startSong(track, tracks);
    }

    @Override
    public Track getCurrentSong() {
        return MainActivity.mediaPlayerService.getCurrentPlaying();
    }

    @Override
    public Boolean isPlaying() {
        return MainActivity.mediaPlayerService.isPlaying();
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

        //TODO: Preguntar a Nico como arreglar bug:

        //Escucho cancion en songfragment. Pasa cancion
        //Escucho en home fragment, pasa cancion
        //Escucho en songfragment denuevo, crashea.

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(MediaPlayerService.CHANGESONG));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
