package com.example.dh.tpmusicagrupo3.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Activities.MainActivity;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterSearchCancionResult;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarFragment extends Fragment {


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

        // RecyclerView con resultados
        rvSearch = view.findViewById(R.id.rvSearch_buscarFragment);
        rvSearch.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        LoadResults();

        return view;
    }

    private void LoadResults() {
        MusicController musicController = new MusicController();
        musicController.getSearchTracks(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                tracks = track.getData();
                setAdapter(tracks, rvSearch);
            }
        });
    }

    private void setAdapter(List<Track> tracks, RecyclerView recyclerView) {
        adapterSearchCancionResult =  new AdapterSearchCancionResult(tracks);
        recyclerView.setAdapter(adapterSearchCancionResult);
    }



}
