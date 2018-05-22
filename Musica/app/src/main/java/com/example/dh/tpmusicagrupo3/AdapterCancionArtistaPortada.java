package com.example.dh.tpmusicagrupo3;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by DH on 22/5/2018.
 */

public class AdapterCancionArtistaPortada extends RecyclerView.Adapter {

    private List<Cancion> canciones;

    public AdapterCancionArtistaPortada(List<Cancion> canciones){
        this.canciones = canciones;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.celda_cancion_artista_portada, parent, false);
        CancionViewHolder cvh = new CancionViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Cancion cancion = canciones.get(position);
        CancionViewHolder cvh = (CancionViewHolder) holder;
        cvh.bindCancion(cancion);
    }

    @Override
    public int getItemCount() {
        if(canciones != null)
            return canciones.size();
        return -1;
    }

    private class CancionViewHolder extends RecyclerView.ViewHolder{

        private TextView tvCancion, tvArtista;
        private ImageView ivPortada;

        public CancionViewHolder(View itemView) {
            super(itemView);
            tvCancion = itemView.findViewById(R.id.celdaCancionID);
            tvArtista = itemView.findViewById(R.id.celdaArtistaID);
            ivPortada = itemView.findViewById(R.id.celdaPortadaID);
        }

        public void bindCancion(Cancion cancion){
            tvCancion.setText(cancion.getNombreCancion());
            tvArtista.setText(cancion.getNombreArtista());
            ivPortada.setImageResource(cancion.getImagenPortada());
        }
    }
}