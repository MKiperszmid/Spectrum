package com.example.dh.tpmusicagrupo3.Controller.DatosControllers;

import android.support.v4.app.Fragment;

import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.View.Fragments.Detalles.ArtistFragment;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

import java.util.List;

public class ArtistController extends TypeController<Artist> {

    public ArtistController(Artist data) {
        super(data);
    }

    @Override
    public Fragment getFragment() {
        return new ArtistFragment();
    }
}
