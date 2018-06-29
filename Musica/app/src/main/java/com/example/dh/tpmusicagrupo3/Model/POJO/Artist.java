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
    private Integer nb_fan;

    public Artist(Integer id, String name, String picture_big, String tracklist, Integer nb_fan) {
        this.id = id;
        this.name = name;
        this.picture_big = picture_big;
        this.tracklist = tracklist;
        this.nb_fan = nb_fan;
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

    public Integer getNb_fan() {
        return nb_fan;
    }

    public void setNb_fan(Integer nb_fan) {
        this.nb_fan = nb_fan;
    }

    @Override
    public String toString() {
        return name;
    }
}
