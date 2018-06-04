package com.example.dh.tpmusicagrupo3;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Cancion implements Serializable{
    private String nombreCancion;
    private String nombreArtista; //Reemplazar con clase ARTISTA en un futuro
    private int imagenPortada;
    private int cancionID;
    private int id;

    public Cancion(int id, String nombreCancion, String nombreArtista, int imagen, int cancionID){
        this.nombreCancion = nombreCancion;
        this.nombreArtista = nombreArtista;
        this.imagenPortada = imagen;
        this.cancionID = cancionID;
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public int getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(int imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    public void setCancionID(int cancionID){
        this.cancionID = cancionID;
    }

    public int getCancionID(){
        return this.cancionID;
    }
}
