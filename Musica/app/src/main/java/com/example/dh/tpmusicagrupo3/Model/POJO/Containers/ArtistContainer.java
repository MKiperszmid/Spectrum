package com.example.dh.tpmusicagrupo3.Model.POJO.Containers;

import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;

import java.util.List;

/**
 * Created by DH on 15/6/2018.
 */

public class ArtistContainer {
    private List<Artist> data;

    public List<Artist> getData() {
        return data;
    }

    public void setData(List<Artist> data) {
        this.data = data;
    }
}
