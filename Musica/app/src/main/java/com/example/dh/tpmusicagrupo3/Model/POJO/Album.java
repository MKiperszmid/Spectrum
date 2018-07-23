package com.example.dh.tpmusicagrupo3.Model.POJO;

import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DH on 15/6/2018.
 */

public class Album implements Serializable {

    private Integer id;
    private String title;
    private String cover_big;
    private String cover_medium;
    private String tracklist;
    private Artist artist;
    private TrackContainer tracks;
    private Integer nb_tracks;

    public Album(Integer id, String title, String cover_big, String tracklist, Artist artist, TrackContainer tracks, String cover_medium) {
        this.id = id;
        this.title = title;
        this.cover_big = cover_big;
        this.tracklist = tracklist;
        this.artist = artist;
        this.tracks = tracks;
        this.cover_medium = cover_medium;
    }

    public Integer getNb_tracks() {
        return nb_tracks;
    }

    public void setNb_tracks(Integer nb_tracks) {
        this.nb_tracks = nb_tracks;
    }

    public String getCover_medium() {
        return cover_medium;
    }

    public void setCover_medium(String cover_small) {
        this.cover_medium = cover_small;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_big() {
        return cover_big;
    }

    public void setCover_big(String cover_big) {
        this.cover_big = cover_big;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public TrackContainer getTracks() {
        return tracks;
    }

    public void setTracks(TrackContainer tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return title;
    }
}
