package com.example.dh.tpmusicagrupo3.View.Fragments.Detalles;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dh.tpmusicagrupo3.Controller.DatosControllers.TypeController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Genre;
import com.example.dh.tpmusicagrupo3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenreFragment extends Fragment {


    public GenreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genre, container, false);


        TextView textView = view.findViewById(R.id.fg_tv_test);
        Bundle bundle = getArguments();
        Genre genre = (Genre)bundle.getSerializable(TypeController.KEY_T);
        textView.setText(genre.getName());


        return view;
    }

}
