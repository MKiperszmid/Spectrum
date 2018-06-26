package com.example.dh.tpmusicagrupo3.Controller.DatosControllers;

import android.support.v4.app.Fragment;

import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

import java.util.List;

public class TrackController extends TypeController <Track> {

    public TrackController(List<Track> trackList){
        this.setData(trackList);
    }

    @Override
    public Fragment getFragment() {
        return new SongFragment();
    }
}
