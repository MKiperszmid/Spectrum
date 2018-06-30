package com.example.dh.tpmusicagrupo3.Model.POJO;

import com.example.dh.tpmusicagrupo3.R;

import java.io.Serializable;

public class Genre implements Serializable{
    public static final int GRADIENT_DEFAULT = R.drawable.gradientamarillo;
    private Integer id;
    private String name;
    private int gradient;

    public Genre(Integer id, String name, int gradient) {
        this.id = id;
        this.name = name;
        this.gradient = gradient;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGradient() {
        return gradient;
    }

    public void setGradient(int gradient) {
        this.gradient = gradient;
    }
}
