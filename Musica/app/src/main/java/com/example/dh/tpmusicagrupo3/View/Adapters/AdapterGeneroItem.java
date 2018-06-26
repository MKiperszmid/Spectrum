package com.example.dh.tpmusicagrupo3.View.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dh.tpmusicagrupo3.Model.POJO.Genre;
import com.example.dh.tpmusicagrupo3.R;

import java.util.List;

public class AdapterGeneroItem extends RecyclerView.Adapter {

    private List<Genre> generos;

    public AdapterGeneroItem(List<Genre> generos) {
        this.generos = generos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_genero_item, parent, false);
        GeneroViewHolder vw = new GeneroViewHolder(view);
        return vw;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Genre genre = generos.get(position);
        GeneroViewHolder generoViewHolder = (GeneroViewHolder) holder;
        generoViewHolder.bindGenero(genre);
    }

    @Override
    public int getItemCount() {
        if(generos != null){
            return generos.size();
        }else{
            return 0;
        }
    }


    private class GeneroViewHolder extends RecyclerView.ViewHolder {

        private TextView generoNombre;

        public GeneroViewHolder(View itemView) {
            super(itemView);
            generoNombre = itemView.findViewById(R.id.nombreGeneroCelda);
        }

        public void bindGenero(Genre genre) {
            generoNombre.setText(genre.getName());
        }
    }


}
