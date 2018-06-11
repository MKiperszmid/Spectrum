package com.example.dh.tpmusicagrupo3.Model.POJO;

import java.io.Serializable;

/**
 * Created by DH on 11/6/2018.
 */

public class Artista implements Serializable{

    private Integer id;
    private String name;
    private Integer image;

    public Artista(Integer id, String name, Integer image) {
        this.id = id;
        this.name = name;
        this.image = image;
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

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
