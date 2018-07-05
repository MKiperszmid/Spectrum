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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Controller.DatosControllers.ArtistController;
import com.example.dh.tpmusicagrupo3.Controller.DatosControllers.GenreController;
import com.example.dh.tpmusicagrupo3.Controller.DatosControllers.PlaylistController;
import com.example.dh.tpmusicagrupo3.Controller.DatosControllers.TypeController;
import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Chart;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.ArtistContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.GenreContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.PlaylistContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Genre;
import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Activities.MainActivity;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterArtistaPortada;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterCancionArtistaPortada;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterGeneroItem;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterPlaylistItem;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExplorarFragment extends Fragment implements AdapterArtistaPortada.NotificadorArtistaCelda, AdapterGeneroItem.NotificadorGeneroCelda, AdapterPlaylistItem.NotificadorPlaylistCelda {

    private HomeFragment.NotificadorActivity notificadorActivity;
    private RecyclerView rvPlaylists;
    private List<Playlist> playlists;
    private AdapterPlaylistItem adapterPlaylistItem;

    private RecyclerView rvArtistChart;
    private List<Artist> artists;
    private AdapterArtistaPortada adapterArtistaPortada;

    private RecyclerView rvGenresList;
    private List<Genre> genres;
    private AdapterGeneroItem adapterGeneroItem;

    private RelativeLayout rlPlaylist, rlGenero, rlArtista;
    private ProgressBar progressBar;

    private NotificarClickeado notificarClickeado;
    private String sectionString = "Explorar";

    public ExplorarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorActivity = (HomeFragment.NotificadorActivity) context;
        this.notificarClickeado = (NotificarClickeado) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_explorar, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle(sectionString);
        progressBar = view.findViewById(R.id.explorarProgressbar);

        // Recycler View  Playlist Destacadas
        rvPlaylists = view.findViewById(R.id.recyclerPlaylistDestacada);
        rvPlaylists.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Recycler View Artistas Populares
        rvArtistChart = view.findViewById(R.id.recyclerArtistChart);
        rvArtistChart.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Recycler View  Generos
        rvGenresList = view.findViewById(R.id.recyclerGenreList);
        rvGenresList.setLayoutManager(new GridLayoutManager(getActivity(), 4, LinearLayoutManager.HORIZONTAL, false));

        // RelativesLayouts
        rlPlaylist = view.findViewById(R.id.explorarLayoutPlaylist);
        rlGenero = view.findViewById(R.id.explorarLayoutGeneros);
        rlArtista = view.findViewById(R.id.explorarLayoutArtistas);

        LoadContent();
        return view;
    }

    private void progressbarVisible(){

    }

    private void LoadContent() {
        MusicController musicController = new MusicController();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        // Playlist Destacadas
        musicController.getPlaylistsChart(new TrackListener<PlaylistContainer>() {
            @Override
            public void finish(PlaylistContainer track) {
                if(track != null){
                    rlPlaylist.setVisibility(View.VISIBLE);
                    playlists = track.getData();
                    setAdapter(playlists, rvPlaylists);
                }
            }
        });

        // Generos
        musicController.getGenreList(new TrackListener<GenreContainer>() {
            @Override
            public void finish(GenreContainer track) {
                if(track != null){
                    rlGenero.setVisibility(View.VISIBLE);
                    genres = track.getData();
                    setAdapterGenres(genres, rvGenresList);
                }
            }
        });

        // Artistas Destacados
        musicController.getArtistsChart(new TrackListener<ArtistContainer>() {
            @Override
            public void finish(ArtistContainer track) {
                if(track != null){
                    rlArtista.setVisibility(View.VISIBLE);
                    artists = track.getData();
                    setAdapterArtist(artists, rvArtistChart);
                }

                // TODO: Hacer esto v , cuando no haya internet (y tambien dejarlo aca)
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setIndeterminate(false);
            }
        });
    }

    // Adapter Playlist Destacadas
    private void setAdapter(List<Playlist> playlists, RecyclerView recyclerView){
        adapterPlaylistItem = new AdapterPlaylistItem(playlists, this);
        recyclerView.setAdapter(adapterPlaylistItem);
    }

    // Adapter Artistas Destacados
    private void setAdapterArtist(List<Artist> artist, RecyclerView recyclerView){
        adapterArtistaPortada = new AdapterArtistaPortada(artist, this);
        recyclerView.setAdapter(adapterArtistaPortada);
    }

    // Adapter Generos
    private void setAdapterGenres(List<Genre> genre, RecyclerView recyclerView){
        adapterGeneroItem = new AdapterGeneroItem(genre, this);
        recyclerView.setAdapter(adapterGeneroItem);
    }

    @Override
    public void notificarArtistaClickeado(Artist artist) {
        notificarClickeado.notificar(new ArtistController(artist));
    }

    @Override
    public void notificarGeneroClickeado(Genre genre) {
        notificarClickeado.notificar(new GenreController(genre));
    }

    @Override
    public void notificarPlaylistClickeada(Playlist playlist) {
        notificarClickeado.notificar(new PlaylistController(playlist));
    }

    public interface NotificarClickeado {
        public void notificar(TypeController controller);
    }
}
