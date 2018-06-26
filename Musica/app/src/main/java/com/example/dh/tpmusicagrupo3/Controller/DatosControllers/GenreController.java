package com.example.dh.tpmusicagrupo3.Controller.DatosControllers;

import android.support.v4.app.Fragment;

import com.example.dh.tpmusicagrupo3.Model.POJO.Genre;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

import java.util.List;

public class GenreController extends TypeController <Genre> {

    public GenreController(List<Genre> genres){
        this.setData(genres);
    }

    @Override
    public Fragment getFragment() {
        return new SongFragment();
    } //TODO: Cambiar por GenreFragment en un futuro.
}
