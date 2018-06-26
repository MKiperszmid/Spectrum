package com.example.dh.tpmusicagrupo3.Controller.DatosControllers;

import android.support.v4.app.Fragment;

import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.View.Fragments.SongFragment;

import java.util.List;

public class PlaylistController extends TypeController <Playlist> {

    public PlaylistController(List<Playlist> playlists){
        this.setData(playlists);
    }

    @Override
    public Fragment getFragment() {
        return new SongFragment();
    } //TODO: Cambiar por PlaylistFragment en un futuro.
}
