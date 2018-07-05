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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dh.tpmusicagrupo3.Controller.DatosControllers.TypeController;
import com.example.dh.tpmusicagrupo3.Controller.GlideController;
import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.GenreContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Genre;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterArtistaCanciones;
import com.example.dh.tpmusicagrupo3.View.Fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenreFragment extends Fragment implements AdapterArtistaCanciones.NotificadorCancionArtista{

    private TextView nombreGeneroTV;
    private ImageView portadaGeneroIV;
    private RecyclerView rvCancionesPopularesGenero;
    private TextView reproducirBtn;

    private MusicController musicController;
    private Genre currentGenre;

    private List<Track> tracks;

    private HomeFragment.NotificadorActivity notificadorActivity;

    public GenreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_genre, container, false);

        musicController = new MusicController();

        // Declaro las variables con los views
        nombreGeneroTV = view.findViewById(R.id.nombre_tv_generofragment);
        portadaGeneroIV = view.findViewById(R.id.portada_iv_generofragment);
        rvCancionesPopularesGenero = view.findViewById(R.id.cancionpopulares_rl_genrefragment);
        reproducirBtn = view.findViewById(R.id.reproducirBtn_tv_genrefragment);

        reproducirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificarCancion(tracks.get(0));
            }
        });

        // Obtengo el bundle
        Bundle bundle = getArguments();
        Genre genre = (Genre)bundle.getSerializable(TypeController.KEY_T);

        currentGenre = genre;
        cargarGeneroInfo(view);

        return view;
    }

    private void cargarGeneroInfo(View view){
        // Seteo los datos en los views
        nombreGeneroTV.setText(currentGenre.getName());
        portadaGeneroIV.setImageResource(currentGenre.getGradient());

        // Obtengo lista de canciones populares del genero
        obtenerGeneroCanciones();
    }

    private void obtenerGeneroCanciones() {
        final AdapterArtistaCanciones.NotificadorCancionArtista notificadorCancionArtista = this;

        musicController.getGenreTracks(new TrackListener<TrackContainer>() {
                @Override
                public void finish(TrackContainer track) {
                    tracks = track.getData();
                    AdapterArtistaCanciones adapterArtistaCanciones = new AdapterArtistaCanciones(tracks, notificadorCancionArtista);
                    rvCancionesPopularesGenero.setAdapter(adapterArtistaCanciones);
                    rvCancionesPopularesGenero.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                }
            }, currentGenre.getId());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorActivity = (HomeFragment.NotificadorActivity)context;
    }

    @Override
    public void notificarCancion(Track cancion) {
        ArrayList<Track> newTrack = new ArrayList<>();
        newTrack.addAll(tracks);
        notificadorActivity.recibirCancion(cancion, newTrack);
    }
}
