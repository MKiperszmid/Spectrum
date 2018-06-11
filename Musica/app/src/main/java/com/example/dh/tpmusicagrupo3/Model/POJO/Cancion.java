package com.example.dh.tpmusicagrupo3.Model.POJO;

import java.io.Serializable;

public class Cancion implements Serializable{
    private String title;
    private Artista artist;
    private Integer imagenPortada;
    private Integer cancionID;
    private Integer id;

    public Cancion(Integer id, String title, Artista artist, Integer imagen, Integer cancionID){
        this.title = title;
        this.artist = artist;
        this.imagenPortada = imagen;
        this.cancionID = cancionID;
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artista getArtist() {
        return artist;
    }

    public void setArtist(Artista artist) {
        this.artist = artist;
    }

    public int getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(Integer imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    public void setCancionID(Integer cancionID){
        this.cancionID = cancionID;
    }

    public Integer getCancionID(){
        return this.cancionID;
    }

    @Override
    public String toString(){
        return title + " - " + artist;
    }

    @Override
    public boolean equals(Object o){
        if(! (o instanceof Cancion))
            return false;
        return this.title.equals(((Cancion) o).getTitle())
                && this.artist.equals(((Cancion) o).getArtist())
                && this.id == ((Cancion) o).getId();
    }
}
