package com.example.dh.tpmusicagrupo3.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarFragment extends Fragment {


    private String sectionString = "Buscar";

    public BuscarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle(sectionString);
        return view;
    }

}
