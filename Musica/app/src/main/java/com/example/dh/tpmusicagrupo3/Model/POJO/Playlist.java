package com.example.dh.tpmusicagrupo3.Model.POJO;

import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;

import java.io.Serializable;

/**
 * Created by DH on 15/6/2018.
 */

public class Playlist implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private String picture_big;
    private TrackContainer tracks;
    private Integer nb_tracks;

    public Playlist(Long id, String title, String description, Integer duration, String picture_big) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.picture_big = picture_big;
    }

    public Integer getNb_tracks() {
        return nb_tracks;
    }

    public void setNb_tracks(Integer nb_tracks) {
        this.nb_tracks = nb_tracks;
    }

    public TrackContainer getTracks() {
        return tracks;
    }

    public void setTracks(TrackContainer tracks) {
        this.tracks = tracks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }
}
