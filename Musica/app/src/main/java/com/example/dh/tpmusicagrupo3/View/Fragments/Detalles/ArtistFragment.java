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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Controller.DatosControllers.TypeController;
import com.example.dh.tpmusicagrupo3.Controller.GlideController;
import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Album;
import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.AlbumContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Activities.MainActivity;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterArtistaAlbumes;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterArtistaCanciones;
import com.example.dh.tpmusicagrupo3.View.Fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment implements AdapterArtistaCanciones.NotificadorCancionArtista {

    private Artist currentArtist;
    private List<Track> tracks;
    private List<Album> albums;
    private Artist artist;
    private MusicController musicController;
    private RecyclerView rvCanciones;
    private RecyclerView rvAlbumes;
    private RelativeLayout rlCanciones;
    private RelativeLayout rlAlbumes;
    private RelativeLayout rlDatos;
    private TextView tvReproducir;
    private HomeFragment.NotificadorActivity notificadorActivity;

    public ArtistFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_artist, container, false);
        musicController = new MusicController();
        Bundle bundle = getArguments();

        // Declaración de variables con los views find
        artist = (Artist) bundle.getSerializable(TypeController.KEY_T);
        rlDatos = view.findViewById(R.id.fa_rl_datos);
        rlCanciones = view.findViewById(R.id.fa_rl_layoutCanciones);
        rlAlbumes = view.findViewById(R.id.fa_rl_layoutAlbumes);
        rvCanciones = view.findViewById(R.id.fa_rv_cancionesPopulares);
        rvAlbumes = view.findViewById(R.id.fa_rv_albumesPopulares);
        tvReproducir = view.findViewById(R.id.fa_tv_reproducirButton);
        tvReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tracks != null && tracks.size() > 0) {
                    notificarCancion(tracks.get(0));
                }
            }
        });

        // TODO: Ver si hacer esto antes de entrar al fragment?

        // TODO: Iniciar algun loading o algo, para mostrar que esta cargando
        // Pedido de artista
        musicController.getArtist(new TrackListener<Artist>() {
            @Override
            public void finish(Artist track) {
                currentArtist = track;
                loadContent(view);
                //Finalizar el loading.
            }
        }, artist.getId());

        return view;
    }

    // Cargar datos en view
    private void loadContent(View view) {

        TextView tvArtistName = view.findViewById(R.id.fa_tv_artistName);
        TextView tvArtistSeguidores = view.findViewById(R.id.fa_tv_artistFollowers);
        ImageView ivArtistArtwork = view.findViewById(R.id.fa_iv_artwork);
        tvArtistName.setText(currentArtist.getName());

        String fans = currentArtist.getNb_fan().toString();
        fans = addDotsToNumber(fans);
        fans += " seguidores";
        tvArtistSeguidores.setText(fans);

        GlideController.loadImages(view, currentArtist.getPicture_big(), ivArtistArtwork);

        rlDatos.setVisibility(View.VISIBLE);

        getArtistInfo();
    }


    // Agregar puntos a lo numeros
    private String addDotsToNumber(String number) {
        if (number.length() < 3)
            return number;
        int counter = 0;
        String result = "";
        List<Character> chars = new ArrayList<>();
        for (int i = 0; i < number.length(); i++) {
            chars.add(number.charAt(i));
        }

        for (int i = chars.size() - 1; i >= 0; i--) {
            if (counter >= 3) {
                result = "." + result;
                counter = 0;
            }
            result = chars.get(i).toString() + result;

            counter++;
        }
        return result;
    }

    private void getArtistInfo() {
        //TODO: Agregar Paginacion en albums

        final AdapterArtistaCanciones.NotificadorCancionArtista notificadorCancionArtista = this;

        // Obtener canciones del artista seleccionado
        musicController.getArtistTracks(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                tracks = track.getData();
                AdapterArtistaCanciones adapterArtistaCanciones = new AdapterArtistaCanciones(tracks, notificadorCancionArtista);
                rvCanciones.setAdapter(adapterArtistaCanciones);
                rvCanciones.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                rlCanciones.setVisibility(View.VISIBLE);
                tvReproducir.setVisibility(View.VISIBLE);
            }
        }, artist.getId());

        // Obtener albums del artista seleccionado
        musicController.getArtistAlbums(new TrackListener<AlbumContainer>() {
            @Override
            public void finish(AlbumContainer track) {
                albums = track.getData();
                AdapterArtistaAlbumes adapterArtistaAlbumes = new AdapterArtistaAlbumes(albums);
                rvAlbumes.setAdapter(adapterArtistaAlbumes);
                rvAlbumes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                rlAlbumes.setVisibility(View.VISIBLE);
            }
        }, artist.getId());
    }

    @Override
    public void notificarCancion(Track cancion) {
        ArrayList<Track> newTrack = new ArrayList<>();
        newTrack.addAll(tracks);
        notificadorActivity.recibirCancion(cancion, newTrack);
    }
}
