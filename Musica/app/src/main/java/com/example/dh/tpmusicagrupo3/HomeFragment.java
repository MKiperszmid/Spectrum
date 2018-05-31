package com.example.dh.tpmusicagrupo3;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.AdapterCancionArtistaPortada.NotificadorCancionCelda;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements NotificadorCancionCelda {

    private NotificadorActivity notificadorActivity;
    private Cancion cancionActual;
    private List<Cancion> canciones;
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

    private void LoadCanciones(){
        canciones = new ArrayList<>();
        canciones.add(new Cancion("La Nube", "La Vela Puerca", R.drawable.lavelapuercalanube, R.raw.lanube));
        canciones.add(new Cancion("This is America", "Childish Gambino", R.drawable.childishgambinothisisamerica, R.raw.thisisamerica));
        canciones.add(new Cancion("X", "Nicky Jam - J Balvin", R.drawable.nickyjamjbalvinx, R.raw.x));
        canciones.add(new Cancion("Dimelo", "Paulo Londra", R.drawable.paulolondradimelo, R.raw.dimelo));
        canciones.add(new Cancion("Me Niego", "Reik ft Osuna y Wisin", R.drawable.reikftozunawisinmeniego, R.raw.meniego));
        canciones.add(new Cancion("Bella", "Wolfine", R.drawable.wolfinebella, R.raw.bella));
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

        RecyclerView rvPopular = view.findViewById(R.id.recyclerPopularAhora);
        RecyclerView rvAgregado = view.findViewById(R.id.recyclerAgregadoRecientemente);

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
        MediaPlayerController.create(getActivity(), cancionClickeada.getCancionID());
        cancionActual = cancionClickeada;
        notificadorActivity.recibirCancion(cancionClickeada);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(cancionActual != null){
            cancionPlaying.setText(cancionActual.getNombreCancion());
            separatorPlaying.setText(" - ");
            artistaPlaying.setText(cancionActual.getNombreArtista());
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


    private void cambiarIcono(String seccionNombre) {

        // Reset, pongo todos los iconos en gris
        homeBtnIcon.setImageResource(R.drawable.home);
        explorarBtnIcon.setImageResource(R.drawable.explorar);
        buscarBtnIcon.setImageResource(R.drawable.buscar);
        perfilBtnIcon.setImageResource(R.drawable.perfil);
        homeBtnTxt.setTextColor(getResources().getColor(R.color.colorGraySemiWhite));
        explorarBtnTxt.setTextColor(getResources().getColor(R.color.colorGraySemiWhite));
        buscarBtnTxt.setTextColor(getResources().getColor(R.color.colorGraySemiWhite));
        perfilBtnTxt.setTextColor(getResources().getColor(R.color.colorGraySemiWhite));

        // Dependiendo cual toco le cambio icono y color de texto
        switch(seccionNombre){
            case "home":
                homeBtnIcon.setImageResource(R.drawable.homeactivo);
                homeBtnTxt.setTextColor(getResources().getColor(R.color.colorAccent));
            break;
            case "explorar":
                explorarBtnIcon.setImageResource(R.drawable.exploraractivo);
                explorarBtnTxt.setTextColor(getResources().getColor(R.color.colorAccent));
            break;
            case "buscar":
                buscarBtnIcon.setImageResource(R.drawable.buscaractivo);
                buscarBtnTxt.setTextColor(getResources().getColor(R.color.colorAccent));
            break;
            case "perfil":
                perfilBtnIcon.setImageResource(R.drawable.perfilactivo);
                perfilBtnTxt.setTextColor(getResources().getColor(R.color.colorAccent));
            break;
        }

    }

}
