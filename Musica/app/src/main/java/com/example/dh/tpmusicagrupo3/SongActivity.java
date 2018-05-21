package com.example.dh.tpmusicagrupo3;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Fragment songFragment = new SongFragment();
        songFragment.setArguments(bundle);
        LoadFragment(songFragment, R.id.songID);
    }

    public void LoadFragment(Fragment fragment, int id){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }
}
