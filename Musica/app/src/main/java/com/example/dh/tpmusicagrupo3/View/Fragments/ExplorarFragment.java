package com.example.dh.tpmusicagrupo3.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Chart;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.ArtistContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterArtistaPortada;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterCancionArtistaPortada;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExplorarFragment extends Fragment implements AdapterCancionArtistaPortada.NotificadorCancionCelda {

    private HomeFragment.NotificadorActivity notificadorActivity;
    private RecyclerView rvPlaylists;
    private List<Track> tracks;
    private List<Track> tracksFragment;
    private AdapterCancionArtistaPortada adapterCancionArtistaPortada;

    private RecyclerView rvArtistChart;
    private List<Artist> artists;
    private AdapterArtistaPortada adapterArtistaPortada;

    public ExplorarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorActivity = (HomeFragment.NotificadorActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_explorar, container, false);

        LoadContent();

        rvPlaylists = view.findViewById(R.id.recyclerPlaylistDestacada);
        rvPlaylists.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvArtistChart = view.findViewById(R.id.recyclerArtistChart);
        rvArtistChart.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        return view;
    }

    private void LoadContent() {
        MusicController musicController = new MusicController();
        tracksFragment = new ArrayList<>(); // ?
        musicController.getChart(new TrackListener<Chart>() {
            @Override
            public void finish(Chart track) {
                tracks = track.getTracks().getData();
                setAdapter(tracks, rvPlaylists);
            }
        });

        musicController.getArtistsChart(new TrackListener<ArtistContainer>() {
            @Override
            public void finish(ArtistContainer track) {
                artists = track.getData();
                setAdapterArtist(artists, rvArtistChart);
            }
        });

    }

    private void setAdapter(List<Track> tracks, RecyclerView recyclerView){
        adapterCancionArtistaPortada = new AdapterCancionArtistaPortada(tracks, this);
        recyclerView.setAdapter(adapterCancionArtistaPortada);
    }

    private void setAdapterArtist(List<Artist> artist, RecyclerView recyclerView){
        adapterArtistaPortada = new AdapterArtistaPortada(artist);
        recyclerView.setAdapter(adapterArtistaPortada);
    }

    @Override
    public void notificarCancionClickeada(Track cancionClickeada) {


        /*
        if(cancionActual != cancionClickeada){
            //queue.setVisibility(View.VISIBLE);
            MediaPlayerController.create(cancionClickeada.getPreview());
            cancionActual = cancionClickeada;
            SongActivity.index = cancionClickeada.getId();
        }
        */

        Toast.makeText(getActivity(), "Implementar.", Toast.LENGTH_SHORT).show();
     //   notificadorActivity.recibirCancion(cancionClickeada, tracks.indexOf(cancionClickeada));
    }
}
