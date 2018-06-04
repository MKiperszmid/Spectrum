package com.example.dh.tpmusicagrupo3;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SongActivity extends AppCompatActivity {

    private List<SongFragment> fragments;
    public static Integer index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //getSupportActionBar().setTitle("TITULO"); Sirve para cambiar el Titulo.
        setContentView(R.layout.activity_song);
        fragments = new ArrayList<>();

        /*Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Fragment songFragment = new SongFragment();
        songFragment.setArguments(bundle);
        LoadFragment(songFragment, R.id.songID);*/

        CargarFragments();
        ViewPager pager = findViewById(R.id.songactivityViewPager);
        AdapterSongPager adapter = new AdapterSongPager(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
        pager.setCurrentItem(index);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MediaPlayerController.create(getApplicationContext(), fragments.get(position).getCancion().getCancionID());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void CargarFragments() {

        List<Cancion> canciones = HomeFragment.getCanciones();

        for(Cancion cancion : canciones){
            fragments.add(SongFragment.dameFragment(cancion));
        }
    }

    public void LoadFragment(Fragment fragment, int id){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }
}
