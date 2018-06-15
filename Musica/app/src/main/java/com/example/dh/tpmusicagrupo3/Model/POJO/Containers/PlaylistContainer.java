package com.example.dh.tpmusicagrupo3.Model.POJO.Containers;

import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;

import java.util.List;

/**
 * Created by DH on 15/6/2018.
 */

public class PlaylistContainer {
    private List<Playlist> data;

    public List<Playlist> getData() {
        return data;
    }

    public void setData(List<Playlist> data) {
        this.data = data;
    }
}
