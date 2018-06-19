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
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
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

    private AdapterCancionArtistaPortada adapterCancionArtistaPortada;
    private RecyclerView rvPopular;
    private RecyclerView rvAgregado;
    private RecyclerView rvArgentina;
    private RecyclerView rvUsa;

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

    public static void LoadCancionesFragment(List<Track> newTracks){
        tracksFragment = new ArrayList<>();
        tracksFragment.add(newTracks.get(newTracks.size() - 1));
        tracksFragment.addAll(newTracks);
        tracksFragment.add(newTracks.get(0));
        tracks = newTracks;
    }

    private void LoadCanciones(){
        MusicController musicController = new MusicController();
        tracks = new ArrayList<>();
        musicController.getChart(new TrackListener<Chart>() {
            @Override
            public void finish(Chart track) {
                tracks = track.getTracks().getData();
                setAdapter(tracks, rvPopular);
            }
        });

        musicController.getTracksRadio(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                tracks = track.getData();
                setAdapter(tracks, rvAgregado);
            }
        }, "31061");

        musicController.getTopArgentina(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                tracks = track.getData();
                setAdapter(tracks, rvArgentina);
            }
        });

        musicController.getTopUsa(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                tracks = track.getData();
                setAdapter(tracks, rvUsa);
            }
        });
    }

    private void setAdapter(List<Track> tracks, RecyclerView recyclerView){
        adapterCancionArtistaPortada = new AdapterCancionArtistaPortada(tracks, this);
        recyclerView.setAdapter(adapterCancionArtistaPortada);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LoadCanciones();

        /* Feed */

        rvPopular = view.findViewById(R.id.recyclerPopularAhora);
        rvAgregado = view.findViewById(R.id.recyclerAgregadoRecientemente);
        rvArgentina = view.findViewById(R.id.recyclerTrendingArgentina);
        rvUsa = view.findViewById(R.id.recyclerTrendingUsa);

        rvPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvAgregado.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvArgentina.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvUsa.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        return view;
    }

    @Override
    public void notificarCancionClickeada(Track cancionClickeada) {

        if(cancionActual != cancionClickeada){
            MediaPlayerController.create(cancionClickeada);
            cancionActual = cancionClickeada;
            SongActivity.index = cancionClickeada.getId();
        }

        notificadorActivity.recibirCancion(cancionClickeada, tracks.indexOf(cancionClickeada));
    }


    public interface NotificadorActivity{
        void recibirCancion(Track cancion, int position);
    }


}
