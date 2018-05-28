package com.example.dh.tpmusicagrupo3;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SongFragment extends Fragment {

    public static final String cancionKey = "CANCION";
    private TextView descripcionCancionNombre;
    private TextView descripcionCancionArtista;
    private ImageView descripcionCancionPortada;
    private ImageView heartClick;
    private ImageView backClick;
    private ImageView nextClick;
    private ImageView agregarOffline;
    private FloatingActionButton pauseplayClick;
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

        descripcionCancionArtista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Mostrar perfil de " + cancion.getNombreArtista(), Toast.LENGTH_SHORT).show();
            }
        });
        heartClick = view.findViewById(R.id.heartClick);
        heartClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Me Gusta", Toast.LENGTH_SHORT).show();
            }
        });
        backClick = view.findViewById(R.id.backClick);
        backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Retroceder", Toast.LENGTH_SHORT).show();
            }
        });
        pauseplayClick = view.findViewById(R.id.pauseplayClick);
        pauseplayClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Pausa/Play", Toast.LENGTH_SHORT).show();
            }
        });

        nextClick = view.findViewById(R.id.nextClick);
        nextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Adelantar", Toast.LENGTH_SHORT).show();
            }
        });
        agregarOffline = view.findViewById(R.id.agregarOffline);
        agregarOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Agregar a playlist", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
