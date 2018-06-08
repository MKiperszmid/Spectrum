package com.example.dh.tpmusicagrupo3.View.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Model.POJO.Cancion;
import com.example.dh.tpmusicagrupo3.Controller.MediaPlayerController;
import com.example.dh.tpmusicagrupo3.R;

public class SongFragment extends Fragment {

    public static final String cancionKey = "CANCION";
    private FloatingActionButton pauseplayClick;
    private Cancion cancion;

    public static final String CANCIONKEY = "cancion";

    public SongFragment() {
        // Required empty public constructor
    }

    public Cancion getCancion(){
        return this.cancion;
    }

    public static SongFragment dameFragment(Cancion cancion){
        SongFragment fragment = new SongFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CANCIONKEY, cancion);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        cancion = (Cancion) bundle.getSerializable(CANCIONKEY);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        TextView descripcionCancionNombre = view.findViewById(R.id.descripcionCancionNombre);
        descripcionCancionNombre.setText(cancion.getNombreCancion());
        TextView descripcionCancionArtista = view.findViewById(R.id.descripcionCancionArtista);
        descripcionCancionArtista.setText(cancion.getNombreArtista());
        ImageView descripcionCancionPortada = view.findViewById(R.id.descripcionCancionPortada);
        descripcionCancionPortada.setImageResource(cancion.getImagenPortada());

        descripcionCancionArtista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Mostrar perfil de " + cancion.getNombreArtista(), Toast.LENGTH_SHORT).show();
            }
        });
        ImageView heartClick = view.findViewById(R.id.heartClick);
        heartClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Me Gusta", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView backClick = view.findViewById(R.id.backClick);
        backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Retroceder", Toast.LENGTH_SHORT).show();
                MediaPlayerController.retroceder(pauseplayClick);
            }
        });
        pauseplayClick = view.findViewById(R.id.pauseplayClick);

        pauseplayClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayerController.playPause(pauseplayClick);
            }
        });

        ImageView nextClick = view.findViewById(R.id.nextClick);
        nextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Adelantar", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView agregarOffline = view.findViewById(R.id.agregarOffline);
        agregarOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Agregar a playlist", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(MediaPlayerController.isPlaying()){
            pauseplayClick.setImageResource(R.drawable.stop);
        }
        else {
            pauseplayClick.setImageResource(R.drawable.play);
        }
    }
}
