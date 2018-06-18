package com.example.dh.tpmusicagrupo3.Model.POJO;

import java.io.Serializable;

/**
 * Created by DH on 15/6/2018.
 */

public class Track implements Serializable {
    private Integer id;
    private String title_short;
    private Integer duration;
    private String preview;
    private Artist artist;
    private Album album;

    public Track(Integer id, String title, Integer duration, String preview) {
        this.id = id;
        this.title_short = title;
        this.duration = duration;
        this.preview = preview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle_short() {
        return title_short;
    }

    public void setTitle_short(String title_short) {
        this.title_short = title_short;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Track)) return false;
        Track track = (Track) obj;
        return track.getId().equals(this.id) && track.getTitle_short().equals(this.title_short) && track.getArtist().getName().equals(this.artist.getName());
    }

    @Override
    public String toString() {
        return title_short;
    }
}
