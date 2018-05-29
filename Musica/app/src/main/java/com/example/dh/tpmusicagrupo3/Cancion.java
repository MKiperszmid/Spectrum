package com.example.dh.tpmusicagrupo3;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by DH on 21/5/2018.
 */

public class Cancion implements Serializable{
    private String nombreCancion;
    private String nombreArtista; //Reemplazar con clase ARTISTA en un futuro
    private int imagenPortada;
    private int cancionID;

    public Cancion(String nombreCancion, String nombreArtista, int imagen, int cancionID){
        this.nombreCancion = nombreCancion;
        this.nombreArtista = nombreArtista;
        this.imagenPortada = imagen;
        this.cancionID = cancionID;
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
