package com.example.dh.tpmusicagrupo3.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Chart;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.ArtistContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.GenreContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Genre;
import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterArtistaPortada;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterCancionArtistaPortada;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterGeneroItem;

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

    private RecyclerView rvGenresList;
    private List<Genre> genres;
    private AdapterGeneroItem adapterGeneroItem;

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

        // Playlist Destacadas
        rvPlaylists = view.findViewById(R.id.recyclerPlaylistDestacada);
        rvPlaylists.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Artistas Populares
        rvArtistChart = view.findViewById(R.id.recyclerArtistChart);
        rvArtistChart.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Generos
        rvGenresList = view.findViewById(R.id.recyclerGenreList);
        RecyclerView.LayoutManager gridLayoutManagerGenres = new GridLayoutManager(getActivity(), 2);
        rvGenresList.setLayoutManager(gridLayoutManagerGenres);

        return view;
    }

    private void LoadContent() {
        MusicController musicController = new MusicController();
        tracksFragment = new ArrayList<>(); // ?

        // Playlist Destacadas
        musicController.getTracksChart(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                tracks = track.getData();
                setAdapter(tracks, rvPlaylists);
            }
        });

        // Artistas Destacados
        musicController.getArtistsChart(new TrackListener<ArtistContainer>() {
            @Override
            public void finish(ArtistContainer track) {
                artists = track.getData();
                setAdapterArtist(artists, rvArtistChart);
            }
        });

        // Generos
        musicController.getGenreList(new TrackListener<GenreContainer>() {
            @Override
            public void finish(GenreContainer track) {
                genres = track.getData();
                setAdapterGenres(genres, rvGenresList);
            }
        });



    }

    // Adapter Playlist Destacadas
    private void setAdapter(List<Track> tracks, RecyclerView recyclerView){
        adapterCancionArtistaPortada = new AdapterCancionArtistaPortada(tracks, this);
        recyclerView.setAdapter(adapterCancionArtistaPortada);
    }

    // Adapter Artistas Destacados
    private void setAdapterArtist(List<Artist> artist, RecyclerView recyclerView){
        adapterArtistaPortada = new AdapterArtistaPortada(artist);
        recyclerView.setAdapter(adapterArtistaPortada);
    }

    // Adapter Generos
    private void setAdapterGenres(List<Genre> genre, RecyclerView recyclerView){
        adapterGeneroItem = new AdapterGeneroItem(genre);
        recyclerView.setAdapter(adapterGeneroItem);
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
