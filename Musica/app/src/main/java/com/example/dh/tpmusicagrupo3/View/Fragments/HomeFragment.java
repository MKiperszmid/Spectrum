package com.example.dh.tpmusicagrupo3.View.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Controller.MediaPlayerController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Chart;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Activities.SongActivity;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterCancionArtistaPortada;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterCancionArtistaPortada.NotificadorCancionCelda {

    private NotificadorActivity notificadorActivity;
    public static Track cancionActual;
    private static List<Track> tracks;
    private static List<Track> tracksFragment;
    private ImageView playBtn;
    private TextView cancionPlaying;
    private TextView separatorPlaying;
    private TextView artistaPlaying;

    // Iconos de bottom bar
    private ImageView homeBtnIcon;
    private ImageView explorarBtnIcon;
    private ImageView buscarBtnIcon;
    private ImageView perfilBtnIcon;

    private TextView homeBtnTxt;
    private TextView explorarBtnTxt;
    private TextView buscarBtnTxt;
    private TextView perfilBtnTxt;
    private AdapterCancionArtistaPortada adapterCancionArtistaPortada;
    private RecyclerView rvPopular;
    private RecyclerView rvAgregado;
    private RecyclerView rvArgentina;


    public HomeFragment() {
        // Required empty public constructo
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.notificadorActivity = (NotificadorActivity) context;
    }

    public static List<Track> getCancionesFragment(){
        return tracksFragment;
    }

    private void LoadCancionesFragment(){
        tracksFragment = new ArrayList<>();
        tracksFragment.add(tracks.get(tracks.size() - 1));
        tracksFragment.addAll(tracks);
        tracksFragment.add(tracks.get(0));
    }

    private void LoadCanciones(){

        MusicController musicController = new MusicController();

        musicController.getChart(new TrackListener<Chart>() {
            @Override
            public void finish(Chart track) {
                if(track == null){
                    Toast.makeText(getActivity(), "Error al conectar con Chart.", Toast.LENGTH_SHORT).show();
                    //MOSTRAR OFFLINE
                    return;
                }
                tracks = track.getTracks().getData();
                LoadCancionesFragment();
                Toast.makeText(getActivity(), "CARGO", Toast.LENGTH_SHORT).show();
                setAdapter();
            }
        });
    }

    private void setAdapter(){
        adapterCancionArtistaPortada = new AdapterCancionArtistaPortada(tracks, this);
        rvPopular.setAdapter(adapterCancionArtistaPortada);
        rvAgregado.setAdapter(adapterCancionArtistaPortada);
        rvArgentina.setAdapter(adapterCancionArtistaPortada);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LoadCanciones();

        RelativeLayout queue = view.findViewById(R.id.relativeQueue);

        cancionPlaying = view.findViewById(R.id.cancionCurrentPlayingID);
        artistaPlaying = view.findViewById(R.id.artistCurrentPlayingID);
        separatorPlaying = view.findViewById(R.id.separatorCurrentPlayingID);

        rvPopular = view.findViewById(R.id.recyclerPopularAhora);
        rvAgregado = view.findViewById(R.id.recyclerAgregadoRecientemente);

        //ToDo: Cambiar estos para que cada uno tenga su distinto adapter (cada uno con distintas canciones)

        rvPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        rvAgregado.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        rvArgentina = view.findViewById(R.id.recyclerTrendingArgentina);
        rvArgentina.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        /* Feed */

        queue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (cancionActual != null){
                    notificadorActivity.recibirCancion(cancionActual, tracks.indexOf(cancionActual));
                }
            }
        });

        homeBtnIcon = view.findViewById(R.id.homeIconID);
        explorarBtnIcon = view.findViewById(R.id.exploreIconID);
        buscarBtnIcon = view.findViewById(R.id.searchIconID);
        perfilBtnIcon = view.findViewById(R.id.profileIconID);
        homeBtnTxt = view.findViewById(R.id.homeTxtID);
        explorarBtnTxt = view.findViewById(R.id.explorarTxtID);
        buscarBtnTxt = view.findViewById(R.id.buscarTxtID);
        perfilBtnTxt = view.findViewById(R.id.perfilTxtID);

        /* Bottom bar */

        /* Botón Home */
        LinearLayout homeBtn = view.findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Home", Toast.LENGTH_SHORT).show();
                cambiarIcono("home");
            }
        });

        /* Botón Explorar */
        LinearLayout explorarBtn = view.findViewById(R.id.explorarBtn);
        explorarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Explorar", Toast.LENGTH_SHORT).show();
                cambiarIcono("explorar");
            }
        });

        /* Botón Buscar */
        LinearLayout buscarBtn = view.findViewById(R.id.buscarBtn);
        buscarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Buscar", Toast.LENGTH_SHORT).show();
                cambiarIcono("buscar");
            }
        });

        /* Botón Perfil */
        LinearLayout perfilBtn = view.findViewById(R.id.perfilBtn);
        perfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Perfil", Toast.LENGTH_SHORT).show();
                cambiarIcono("perfil");
            }
        });

        /* Botón Play */
        playBtn = view.findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayerController.playPause(playBtn);
            }
        });


        /* Botón UP (Ver Canción) */
        ImageView upBtn = view.findViewById(R.id.upBtn);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancionActual != null){
                    notificadorActivity.recibirCancion(cancionActual, tracks.indexOf(cancionActual));
                }
            }
        });
        
        return view;
    }

    @Override
    public void notificarCancionClickeada(Track cancionClickeada) {
        if(cancionActual != cancionClickeada){
            MediaPlayerController.create(cancionClickeada.getPreview());
            cancionActual = cancionClickeada;
            SongActivity.index = cancionClickeada.getId();
        }
        notificadorActivity.recibirCancion(cancionClickeada, tracks.indexOf(cancionClickeada));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(cancionActual != null){
            cancionPlaying.setText(cancionActual.getTitle());
            separatorPlaying.setText(" - ");
            artistaPlaying.setText(cancionActual.getArtist().getName());
        }
        if(MediaPlayerController.isPlaying()){
            playBtn.setImageResource(R.drawable.stop);
        }
        else {
            playBtn.setImageResource(R.drawable.play);
        }
    }

    public interface NotificadorActivity{
        void recibirCancion(Track cancion, int position);
    }

    private void cambiarColor(TextView textview, int color){
        textview.setTextColor(getResources().getColor(color));
    }

    private void cambiarIcono(ImageView imageView, int icono){
        imageView.setImageResource(icono);
    }

    private void cambiarTodo(TextView textView, int color, ImageView imageView, int icono){
        cambiarColor(textView, color);
        cambiarIcono(imageView, icono);
    }

    private void cambiarIcono(String seccionNombre) {

        // Reset, pongo todos los iconos en gris
        cambiarTodo(homeBtnTxt, R.color.colorGraySemiWhite, homeBtnIcon, R.drawable.home);
        cambiarTodo(explorarBtnTxt, R.color.colorGraySemiWhite, explorarBtnIcon, R.drawable.explorar);
        cambiarTodo(buscarBtnTxt, R.color.colorGraySemiWhite, buscarBtnIcon, R.drawable.buscar);
        cambiarTodo(perfilBtnTxt, R.color.colorGraySemiWhite, perfilBtnIcon, R.drawable.perfil);

        // Dependiendo cual toco le cambio icono y color de texto
        switch(seccionNombre){
            case "home":
                cambiarTodo(homeBtnTxt, R.color.colorAccent, homeBtnIcon, R.drawable.homeactivo);
            break;
            case "explorar":
                cambiarTodo(explorarBtnTxt, R.color.colorAccent, explorarBtnIcon, R.drawable.exploraractivo);
            break;
            case "buscar":
                cambiarTodo(buscarBtnTxt, R.color.colorAccent, buscarBtnIcon, R.drawable.buscaractivo);
            break;
            case "perfil":
                cambiarTodo(perfilBtnTxt, R.color.colorAccent, perfilBtnIcon, R.drawable.perfilactivo);
            break;
        }
    }
}
