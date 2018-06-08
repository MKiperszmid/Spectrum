package com.example.dh.tpmusicagrupo3.Model.DAO;

import com.example.dh.tpmusicagrupo3.Model.POJO.Cancion;
import com.example.dh.tpmusicagrupo3.R;

import java.util.ArrayList;
import java.util.List;

public class CancionDao {

    public List<Cancion> GetCanciones(){
        List<Cancion> canciones = new ArrayList<>();
        canciones.add(new Cancion(1, "La Nube", "La Vela Puerca", R.drawable.lavelapuercalanube, R.raw.lanube));
        canciones.add(new Cancion(2, "This is America", "Childish Gambino", R.drawable.childishgambinothisisamerica, R.raw.thisisamerica));
        canciones.add(new Cancion(3, "X", "Nicky Jam - J Balvin", R.drawable.nickyjamjbalvinx, R.raw.x));
        canciones.add(new Cancion(4, "Dimelo", "Paulo Londra", R.drawable.paulolondradimelo, R.raw.dimelo));
        canciones.add(new Cancion(5, "Me Niego", "Reik ft Osuna y Wisin", R.drawable.reikftozunawisinmeniego, R.raw.meniego));
        canciones.add(new Cancion(6, "Bella", "Wolfine", R.drawable.wolfinebella, R.raw.bella));
        return canciones;
    }
}
