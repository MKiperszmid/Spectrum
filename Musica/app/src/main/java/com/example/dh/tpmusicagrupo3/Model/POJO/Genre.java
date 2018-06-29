package com.example.dh.tpmusicagrupo3.Model.POJO;

import java.io.Serializable;

public class Genre implements Serializable{
    public static final int NO_NUMBER = -1;
    private Integer id;
    private String name;
    private int number;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
        number = NO_NUMBER;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
