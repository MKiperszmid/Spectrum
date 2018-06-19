package com.example.dh.tpmusicagrupo3.View.Activities;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Fragments.BarbottomFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.ExplorarFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.HomeFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.PlaybarbottomFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.NotificadorActivity, BarbottomFragment.NotificadorActivityBarBottom, ExplorarFragment.NotificadorActivity{

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


    @Override
    public void recibirCancion(Track cancion, int position) {

        FrameLayout frameLayoutPlayBar = findViewById(R.id.playBarBottom);
        frameLayoutPlayBar.setVisibility(View.VISIBLE);

        Bundle bundlePB = new Bundle();
        bundlePB.putInt(PlaybarbottomFragment.CLAVE_CANCION, position);
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


    @Override
    public void recibirSeccion(Fragment fragment) {
            LoadFragment(fragment, R.id.homeID);
    }
}
