package com.example.dh.tpmusicagrupo3.View.Activities;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dh.tpmusicagrupo3.Model.POJO.Cancion;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Fragments.HomeFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.NotificadorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Inicio");
        LoadFragment(new HomeFragment(), R.id.homeID);
    }

    public void LoadFragment(Fragment fragment, int id){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }

    @Override
    public void recibirCancion(Cancion cancion) {
        Intent intent = new Intent(this, SongActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(SongFragment.cancionKey, cancion);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
