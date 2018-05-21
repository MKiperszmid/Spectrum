package com.example.dh.tpmusicagrupo3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SongFragment extends Fragment {

    public static final String cancionKey = "CANCION";
    private TextView descripcionCancionNombre;
    private TextView descripcionCancionArtista;
    private ImageView descripcionCancionPortada;

    private Cancion cancion;
    public SongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        cancion = (Cancion) bundle.getSerializable(cancionKey);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        descripcionCancionNombre = view.findViewById(R.id.descripcionCancionNombre);
        descripcionCancionNombre.setText(cancion.getNombreCancion());
        descripcionCancionArtista = view.findViewById(R.id.descripcionCancionArtista);
        descripcionCancionArtista.setText(cancion.getNombreArtista());
        descripcionCancionPortada = view.findViewById(R.id.descripcionCancionPortada);
        descripcionCancionPortada.setImageResource(cancion.getImagenPortada());

        return view;
    }
}
