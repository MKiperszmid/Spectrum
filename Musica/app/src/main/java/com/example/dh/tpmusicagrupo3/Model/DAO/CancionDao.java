package com.example.dh.tpmusicagrupo3.Model.DAO;

import com.example.dh.tpmusicagrupo3.Model.POJO.Artista;
import com.example.dh.tpmusicagrupo3.Model.POJO.Cancion;
import com.example.dh.tpmusicagrupo3.R;

import java.util.ArrayList;
import java.util.List;

public class CancionDao {

    public List<Cancion> GetCanciones(){
        List<Cancion> canciones = new ArrayList<>();
        //ToDo: Cambiar el ID de imagen por otra cosa.
        canciones.add(new Cancion(1, "La Nube", new Artista(0, "La Vela Puerca", 0), R.drawable.lavelapuercalanube, R.raw.lanube));
        canciones.add(new Cancion(2, "This is America", new Artista(1, "Childish Gambino", 1), R.drawable.childishgambinothisisamerica, R.raw.thisisamerica));
        canciones.add(new Cancion(3, "X", new Artista(2, "Nicky Jam - J Balvin",2), R.drawable.nickyjamjbalvinx, R.raw.x));
        canciones.add(new Cancion(4, "Dimelo", new Artista(3, "Paulo Londra", 3), R.drawable.paulolondradimelo, R.raw.dimelo));
        canciones.add(new Cancion(5, "Me Niego", new Artista(4, "Reik ft Osuna y Wisin", 4), R.drawable.reikftozunawisinmeniego, R.raw.meniego));
        canciones.add(new Cancion(6, "Bella", new Artista(5, "Wolfine", 5), R.drawable.wolfinebella, R.raw.bella));
        return canciones;
    }
}
