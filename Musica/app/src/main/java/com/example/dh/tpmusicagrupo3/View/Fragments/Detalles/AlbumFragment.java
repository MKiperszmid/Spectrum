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
import com.example.dh.tpmusicagrupo3.Model.POJO.Album;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterArtistaCanciones;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterCancionArtistaPortada;
import com.example.dh.tpmusicagrupo3.View.Fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment implements AdapterArtistaCanciones.NotificadorCancionArtista {

    private List<Track> tracks;
    private HomeFragment.NotificadorActivity notificadorActivity;
    private MusicController musicController;
    private Boolean isLoading;
    private ProgressBar progressBar;
    private Album album;
    private RecyclerView canciones;
    private TextView descripcion;

    public AlbumFragment() {
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
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        Bundle bundle = getArguments();
        album = (Album) bundle.getSerializable(TypeController.KEY_T);
        ImageView portada = view.findViewById(R.id.fal_iv_portada);
        TextView nombre = view.findViewById(R.id.fal_tv_nombreAlbum);
        descripcion = view.findViewById(R.id.fal_tv_descripcion);
        TextView reproducir = view.findViewById(R.id.fal_tv_reproducir);
        canciones = view.findViewById(R.id.fal_rv_canciones);
        progressBar = view.findViewById(R.id.fal_pb_progress);
        musicController = new MusicController();

        GlideController.loadImages(view, album.getCover_big(), portada);
        nombre.setText(album.getTitle());
        tracks = new ArrayList<>();

        canciones.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getCanciones(album.getId().toString());

        reproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tracks != null && tracks.size() > 0) {
                    notificarCancion(tracks.get(0));
                }
            }
        });

        return view;
    }

    @Override
    public void notificarCancion(Track cancion) {
        ArrayList<Track> newTrack = new ArrayList<>();
        newTrack.addAll(tracks);
        notificadorActivity.recibirCancion(cancion, newTrack);
    }

    private void getCanciones(String id) {
        final AdapterArtistaCanciones.NotificadorCancionArtista notificadorCancionArtista = this;
        musicController.getTracksAlbum(new TrackListener<TrackContainer>() {
                @Override
                public void finish(TrackContainer track) {
                    if (track != null) {
                        tracks = track.getData();
                        descripcion.setText(tracks.size() + " canciones");


                        for (Track t : tracks){
                            t.setAlbum(album);
                        }
                        AdapterArtistaCanciones adapterArtistaCanciones = new AdapterArtistaCanciones(tracks, notificadorCancionArtista);
                        canciones.setAdapter(adapterArtistaCanciones);
                    }
                }
            }, id);
    }
}