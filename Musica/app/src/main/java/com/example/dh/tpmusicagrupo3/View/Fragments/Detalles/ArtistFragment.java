package com.example.dh.tpmusicagrupo3.View.Fragments.Detalles;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {


    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        TextView textView = view.findViewById(R.id.af_tv_test);

        Bundle bundle = getArguments();
        Artist artist = (Artist) bundle.getSerializable("data");

        textView.setText("Nombre: " + artist.getName());


        return view;
    }

}
