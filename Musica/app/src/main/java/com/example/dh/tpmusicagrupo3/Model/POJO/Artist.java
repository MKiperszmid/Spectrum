package com.example.dh.tpmusicagrupo3.Model.POJO;

import java.io.Serializable;

/**
 * Created by DH on 15/6/2018.
 */

public class Artist implements Serializable {
    private Integer id;
    private String name;
    private String picture_big;
    private String tracklist;

    public Artist(Integer id, String name, String picture_big, String tracklist) {
        this.id = id;
        this.name = name;
        this.picture_big = picture_big;
        this.tracklist = tracklist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    @Override
    public String toString() {
        return name;
    }
}
