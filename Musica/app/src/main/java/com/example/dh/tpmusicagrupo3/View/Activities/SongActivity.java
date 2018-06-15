package com.example.dh.tpmusicagrupo3.View.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dh.tpmusicagrupo3.Controller.MediaPlayerController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterSongPager;
import com.example.dh.tpmusicagrupo3.View.Fragments.HomeFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

import java.util.ArrayList;
import java.util.List;

public class SongActivity extends AppCompatActivity {

    private List<SongFragment> fragments;
    private List<Track> canciones;
    public static Integer index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_song);
        fragments = new ArrayList<>();
        index = getIntent().getExtras().getInt(SongFragment.CANCIONPOS) + 1;

        /*Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Fragment songFragment = new SongFragment();
        songFragment.setArguments(bundle);
        LoadFragment(songFragment, R.id.songID);*/

        CargarFragments();
        final ViewPager pager = findViewById(R.id.songactivityViewPager);
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
                MediaPlayerController.create(fragments.get(position).getCancion().getPreview());
                if(position >= adapter.getCount()  - 1) {
                    pager.setCurrentItem(1, false);
                } else if(position <= 0) {
                    pager.setCurrentItem(fragments.size() - 2, false);
                }
                HomeFragment.cancionActual = canciones.get(position);
                index = position;
        }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void CargarFragments() {

        canciones = HomeFragment.getCancionesFragment();

        for(Track cancion : canciones){
            fragments.add(SongFragment.dameFragment(cancion));
        }
    }
}
