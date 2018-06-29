package com.example.dh.tpmusicagrupo3.View.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
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

    private MusicController musicController;
    private final Integer NUMERO_PARA_NUEVOS_RESULTADOS = 5;
    private ProgressBar progressBar;
    private Boolean isLoading;

    private RelativeLayout rlTop10, rlRecentlyAdded, rlTopArg, rlTopUsa;

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

        // Controller de musica
        musicController = new MusicController();

        // Carga de las diferentes listas
        tracks = new ArrayList<>();

        // TOP 10
        musicController.getTracksChart(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                if(track != null){
                    tracks = track.getData();
                    setAdapter(tracks, rvPopular);
                    rlTop10.setVisibility(View.VISIBLE);
                }
            }
        });

        // Agregado recientemente
        musicController.getTracksRadio(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                if(track != null){
                    tracks = track.getData();
                    setAdapter(tracks, rvAgregado);
                    rlRecentlyAdded.setVisibility(View.VISIBLE);
                }
            }
        }, "31061");

        // Popular en Argentina
        rvArgentina.setAdapter(new AdapterCancionArtistaPortada(this));
        loadNewCancionesArg(rvArgentina);

        // Trending USA
        rvUsa.setAdapter(new AdapterCancionArtistaPortada(this));
        loadNewCancionesUsa(rvUsa);
    }

    private void setAdapter(List<Track> tracks, RecyclerView recyclerView){
        adapterCancionArtistaPortada = new AdapterCancionArtistaPortada(tracks, this);
        recyclerView.setAdapter(adapterCancionArtistaPortada);
        // TODO: Hacer aca el listener del scroll?
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rlTop10 = view.findViewById(R.id.layoutTop10);
        rlRecentlyAdded = view.findViewById(R.id.layoutRecentlyAdded);
        rlTopArg = view.findViewById(R.id.layoutTopArg);
        rlTopUsa = view.findViewById(R.id.layoutTopUsa);

        /* Feed */

        // Declaracion y config de recyclerView's
        rvPopular = view.findViewById(R.id.recyclerPopularAhora);
        rvAgregado = view.findViewById(R.id.recyclerAgregadoRecientemente);
        rvArgentina = view.findViewById(R.id.recyclerTrendingArgentina);
        rvUsa = view.findViewById(R.id.recyclerTrendingUsa);
        rvPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvAgregado.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvArgentina.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvUsa.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        progressBar = view.findViewById(R.id.progressBarHome);
        LoadCanciones();
        rvArgentina.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(!isLoading){
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int initPos = layoutManager.findLastVisibleItemPosition();
                    int finalPos = layoutManager.getItemCount();

                    if(finalPos - initPos <= NUMERO_PARA_NUEVOS_RESULTADOS){
                        loadNewCancionesArg(rvArgentina);
                    }
                }
            }
        });

        rvUsa.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(!isLoading){
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int initPos = layoutManager.findLastVisibleItemPosition();
                    int finalPos = layoutManager.getItemCount();

                    if(finalPos - initPos <= NUMERO_PARA_NUEVOS_RESULTADOS){
                        loadNewCancionesUsa(rvUsa);
                    }
                }
            }
        });

        return view;
    }

    private void loadNewCancionesArg(final RecyclerView rv) {
        if(musicController.getHayPaginasArgentina()){
            isLoading = true;
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            musicController.getTopArgentina(new TrackListener<TrackContainer>() {
                @Override
                public void finish(TrackContainer track) {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    isLoading = false;
                    if(track != null){
                        ((AdapterCancionArtistaPortada)rv.getAdapter()).addTracks(track.getData());
                        rlTopArg.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    private void loadNewCancionesUsa(final RecyclerView rv) {
        if(musicController.getHayPaginasUsa()){
            isLoading = true;
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            musicController.getTopUsa(new TrackListener<TrackContainer>() {
                @Override
                public void finish(TrackContainer track) {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    isLoading = false;
                    if(track != null){
                        ((AdapterCancionArtistaPortada)rv.getAdapter()).addTracks(track.getData());
                        rlTopUsa.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public void notificarCancionClickeada(Track cancionClickeada) {

        // Llega track desde AdapterCancionArtistaPortada y se ejecuta el Media Player
        if(cancionActual != cancionClickeada){
            //mediaPlayerController.create(cancionClickeada); TODO: Borrar este comentario para sacar el service

           //mediaPlayerController.setCurrentPlaying(cancionClickeada);
            cancionActual = cancionClickeada;
            SongActivity.index = cancionClickeada.getId();
        }

        // Se envia a MainActivity la cancion y position
        notificadorActivity.recibirCancion(cancionClickeada, tracks.indexOf(cancionClickeada));
    }

    public interface NotificadorActivity{
        // Metodos que implementa MainActivity de HomeFragment
        void recibirCancion(Track cancion, int position);
        Track getCurrentPlaying();
        Boolean isPlaying();
        void playSong();
    }
}
