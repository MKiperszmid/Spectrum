package com.example.dh.tpmusicagrupo3.View.Adapters;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dh.tpmusicagrupo3.Controller.GlideController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.R;

import java.util.List;

public class AdapterArtistaPortada extends RecyclerView.Adapter {

    private List<Artist> artistas;

    public AdapterArtistaPortada(List<Artist> artistas) {
        this.artistas = artistas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.celda_artista_portada, parent, false);
        ArtistaViewHolder artistaViewHolder = new ArtistaViewHolder(view);
        return artistaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Artist artist = artistas.get(position);
        ArtistaViewHolder artistaViewHolder = (ArtistaViewHolder) holder;
        artistaViewHolder.bindArtista(artist);
    }

    @Override
    public int getItemCount() {
        if (artistas != null){
            return artistas.size();
        }else{
            return 0;
        }
    }

    private class ArtistaViewHolder extends RecyclerView.ViewHolder{

        private ImageView portadaArtista;
        private TextView nombreArtista;

        public ArtistaViewHolder(View itemView) {
            super(itemView);
            portadaArtista = itemView.findViewById(R.id.celdaPortadaArtistaID);
            nombreArtista = itemView.findViewById(R.id.celdaNombreArtistaID);

        }

        public void bindArtista(Artist artist){
            nombreArtista.setText(artist.getName());
            GlideController.loadImage(itemView, artist.getPicture_big(), portadaArtista);
        }


    }

}
