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

    public Cancion(String nombreCancion, String nombreArtista, int imagen){
        this.nombreCancion = nombreCancion;
        this.nombreArtista = nombreArtista;
        this.imagenPortada = imagen;
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
}
