package com.example.dh.tpmusicagrupo3.View.Fragments.Detalles;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dh.tpmusicagrupo3.Controller.DatosControllers.TypeController;
import com.example.dh.tpmusicagrupo3.Controller.GlideController;
import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterArtistaCanciones;
import com.example.dh.tpmusicagrupo3.View.Fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistFragment extends Fragment implements AdapterArtistaCanciones.NotificadorCancionArtista{

    private Playlist playlist;
    private List<Track> tracks;
    private ImageView ivArtWork;
    private RecyclerView rvPlaylist;
    private TextView tvNombre;
    private TextView tvDescripcion;
    private TextView tvCreador;
    private ProgressBar progressBar;
    private MusicController musicController;
    private HomeFragment.NotificadorActivity notificadorActivity;
    private Boolean isLoading;
    private TextView tvReproducir;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorActivity = (HomeFragment.NotificadorActivity)context;
    }

    public PlaylistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        Bundle bundle = getArguments();
        playlist = (Playlist) bundle.getSerializable(TypeController.KEY_T);

        cargarDatos(view);

        return view;
    }

    private void cargarDatos(View view) {
        musicController = new MusicController();
        tvNombre = view.findViewById(R.id.fp_tv_nombrePlaylist);
        tvDescripcion = view.findViewById(R.id.fp_tv_descripcion);
        tvCreador = view.findViewById(R.id.fp_tv_creador);
        ivArtWork = view.findViewById(R.id.fp_iv_portada);
        rvPlaylist = view.findViewById(R.id.fp_rv_canciones);
        progressBar = view.findViewById(R.id.fp_pb_progress);
        tvReproducir = view.findViewById(R.id.fp_tv_reproducir);

        isLoading = false;
        tracks = new ArrayList<>();

        tvNombre.setText(playlist.getTitle());
        tvDescripcion.setText(playlist.getNb_tracks() + " canciones - Creada por ");
        tvCreador.setText("Spectrum"); //TODO: Cambiar por creador
        GlideController.loadImages(view, playlist.getPicture_big(), ivArtWork);

        final AdapterArtistaCanciones.NotificadorCancionArtista notificadorCancionArtista = this;

        AdapterArtistaCanciones adapterArtistaCanciones = new AdapterArtistaCanciones(notificadorCancionArtista);
        rvPlaylist.setAdapter(adapterArtistaCanciones);
        rvPlaylist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getCanciones(rvPlaylist);

        tvReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tracks != null && tracks.size() > 0) {
                    notificarCancion(tracks.get(0));
                }
            }
        });

/*
//TODO: Arreglar esto.
        rvPlaylist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(!isLoading){
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int initPos = layoutManager.findLastVisibleItemPosition();
                    int finalPos = layoutManager.getItemCount();

                    if(finalPos - initPos <= 3){
                        getCanciones(rvPlaylist);
                    }
                }
            }
        });*/
/*
        musicController.getTracksPlaylist(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                tracks = track.getData();
                AdapterArtistaCanciones adapterArtistaCanciones = new AdapterArtistaCanciones(notificadorCancionArtista);
                rvPlaylist.setAdapter(adapterArtistaCanciones);
                rvPlaylist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                getCanciones(rvPlaylist);
            }
        }, playlist.getId().toString(), 0);
*/
    }

    @Override
    public void notificarCancion(Track cancion) {
        ArrayList<Track> newTrack = new ArrayList<>();
        newTrack.addAll(tracks);
        notificadorActivity.recibirCancion(cancion, newTrack);
    }

    private void getCanciones(final RecyclerView rv) {
        if(musicController.getHayPaginasUsa()){
            isLoading = true;
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            musicController.getTracksPlaylist(new TrackListener<TrackContainer>() {
                @Override
                public void finish(TrackContainer track) {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    isLoading = false;
                    if(track != null){
                        tracks.addAll(track.getData());
                        ((AdapterArtistaCanciones)rv.getAdapter()).addTracks(track.getData());
                    }
                }
            }, playlist.getId().toString(), 0);
        }
    }
}
