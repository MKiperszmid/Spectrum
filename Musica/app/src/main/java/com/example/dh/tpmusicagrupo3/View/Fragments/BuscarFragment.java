package com.example.dh.tpmusicagrupo3.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Activities.MainActivity;
import com.example.dh.tpmusicagrupo3.View.Activities.SongActivity;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterSearchCancionResult;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarFragment extends Fragment implements AdapterSearchCancionResult.NotificadorSearchCancionResult {


    private HomeFragment.NotificadorActivity notificadorActivity;
    private String sectionString = "Buscar";
    private EditText searchET;
    private RecyclerView rvSearch;

    private static List<Track> tracks;

    private AdapterSearchCancionResult adapterSearchCancionResult;


    public BuscarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle(sectionString);

        // Buscador EditText
        searchET = view.findViewById(R.id.searchET_buscarFragment);

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null || s.equals("")){
                    LoadResults(s.toString().toLowerCase());
                }

            }
        });



        // RecyclerView con resultados
        rvSearch = view.findViewById(R.id.rvSearch_buscarFragment);
        rvSearch.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        //LoadResults();

        return view;
    }


    private void LoadResults(String s) {
        MusicController musicController = new MusicController();
        musicController.getSearchTracks(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                if(track != null){
                    tracks = track.getData();
                    setAdapter(tracks, rvSearch);
                }
            }
        }, s);
    }



    private void setAdapter(List<Track> tracks, RecyclerView recyclerView) {
        adapterSearchCancionResult =  new AdapterSearchCancionResult(tracks, this);
        recyclerView.setAdapter(adapterSearchCancionResult);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorActivity = (HomeFragment.NotificadorActivity) context;
    }

    @Override
    public void notificar(Track track, List<Track> myTracks) {
        //cancionActual = cancionClickeada;
        //SongActivity.index = cancionClickeada.getId();

        ArrayList<Track> newTracks = new ArrayList<>();
        newTracks.addAll(myTracks);
        notificadorActivity.recibirCancion(track, newTracks);
    }

    public interface NotificadorActivity{
        // Metodos que implementa MainActivity de HomeFragment
        void recibirCancion(Track cancion, ArrayList<Track> tracks);
        Track getCurrentPlaying();
        Boolean isPlaying();
        void playSong();
    }

}
