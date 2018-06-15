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

import com.example.dh.tpmusicagrupo3.Controller.CancionController;
import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Cancion;
import com.example.dh.tpmusicagrupo3.Controller.MediaPlayerController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Chart;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Activities.SongActivity;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterCancionArtistaPortada;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterCancionArtistaPortada.NotificadorCancionCelda {

    private NotificadorActivity notificadorActivity;
    public static Cancion cancionActual;
    private static List<Track> tracks;
    private static List<Track> tracksFragment;
    private static List<Cancion> canciones;
    private static List<Cancion> cancionesFragment;
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


    public HomeFragment() {
        // Required empty public constructo
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.notificadorActivity = (NotificadorActivity) context;
    }

    public static List<Cancion> getCancionesFragment(){
        return cancionesFragment;
    }

    private void LoadCancionesFragment(){
        cancionesFragment = new ArrayList<>();
        cancionesFragment.add(canciones.get(canciones.size() - 1));
        cancionesFragment.addAll(canciones);
        cancionesFragment.add(canciones.get(0));
    }

    private void LoadCanciones(){
        CancionController controller = new CancionController();
       canciones = controller.GetCanciones();

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
            }
        });
/*
        musicController.getChartTracks(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                if(track != null){
                    tracks = track.getData();
                    Toast.makeText(getActivity(), track.getData().get(0).getTitle(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        */


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LoadCanciones();

        LoadCancionesFragment();

        RelativeLayout queue = view.findViewById(R.id.relativeQueue);

        cancionPlaying = view.findViewById(R.id.cancionCurrentPlayingID);
        artistaPlaying = view.findViewById(R.id.artistCurrentPlayingID);
        separatorPlaying = view.findViewById(R.id.separatorCurrentPlayingID);

        RecyclerView rvPopular = view.findViewById(R.id.recyclerPopularAhora);
        RecyclerView rvAgregado = view.findViewById(R.id.recyclerAgregadoRecientemente);

        /*
        musicController.getTrack(new TrackListener<Track>() {
            @Override
            public void finish(Track track) {
                if(track == null){
                    //No hay internet, o no se pudo conectar a la API
                    Toast.makeText(getActivity(), "Error al conectar con la API", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), track.getTitle(), Toast.LENGTH_LONG).show();
                }
            }
        }, "917717");
*/
        //ToDo: Cambiar estos para que cada uno tenga su distinto adapter (cada uno con distintas canciones)

        rvPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        AdapterCancionArtistaPortada adapterCancionArtistaPortada = new AdapterCancionArtistaPortada(canciones, this);
        rvPopular.setAdapter(adapterCancionArtistaPortada);

        rvAgregado.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvAgregado.setAdapter(adapterCancionArtistaPortada);

        RecyclerView rvArgentina = view.findViewById(R.id.recyclerTrendingArgentina);
        rvArgentina.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvArgentina.setAdapter(adapterCancionArtistaPortada);

        /* Feed */

        queue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (cancionActual != null){
                    notificadorActivity.recibirCancion(cancionActual);
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
                    notificadorActivity.recibirCancion(cancionActual);
                }
            }
        });
        
        return view;
    }

    @Override
    public void notificarCancionClickeada(Cancion cancionClickeada) {
        if(cancionActual != cancionClickeada){
            MediaPlayerController.create(getActivity(), cancionClickeada.getCancionID());
            cancionActual = cancionClickeada;
            SongActivity.index = cancionClickeada.getId();
        }
        notificadorActivity.recibirCancion(cancionClickeada);
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
        void recibirCancion(Cancion cancion);
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
