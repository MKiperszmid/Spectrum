package com.example.dh.tpmusicagrupo3.View.Fragments.Detalles;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistFragment extends Fragment {


    public PlaylistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        TextView textView = view.findViewById(R.id.fp_tv_test);

        Bundle bundle = getArguments();
        Playlist playlist = (Playlist) bundle.getSerializable("data");

        textView.setText("Nombre: " + playlist.getTitle());


        return view;
    }

}
