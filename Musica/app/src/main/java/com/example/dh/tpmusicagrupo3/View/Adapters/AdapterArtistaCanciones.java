package com.example.dh.tpmusicagrupo3.View.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dh.tpmusicagrupo3.Controller.GlideController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterArtistaCanciones extends RecyclerView.Adapter {
    private List<Track> trackList;
    private NotificadorCancionArtista notificadorCancionArtista;

    public AdapterArtistaCanciones(NotificadorCancionArtista notificadorCancionArtista){
        this.notificadorCancionArtista = notificadorCancionArtista;
        trackList = new ArrayList<>();
    }

    public AdapterArtistaCanciones(List<Track> canciones, NotificadorCancionArtista notificadorCancionArtista){
        this.trackList = canciones;
        this.notificadorCancionArtista = notificadorCancionArtista;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.celda_artista_canciones, parent, false);
        ArtistaCancionesViewHolder artistaCancionesViewHolder = new ArtistaCancionesViewHolder(view);
        return artistaCancionesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArtistaCancionesViewHolder artistaCancionesViewHolder = (ArtistaCancionesViewHolder) holder;
        Track track = trackList.get(position);
        artistaCancionesViewHolder.bindTrack(track);
    }

    @Override
    public int getItemCount() {
        if(trackList == null)
            return 0;
        return trackList.size();
    }

    public void addTracks(List<Track> tracks){
        trackList.addAll(tracks);
        notifyDataSetChanged(); //TODO: Cambiar por Range para hacerlo un poco mas performante
    }

    public class ArtistaCancionesViewHolder extends RecyclerView.ViewHolder {
        ImageView ivArtwork;
        TextView tvSongName;
        TextView tvArtistName;

        public ArtistaCancionesViewHolder(View itemView) {
            super(itemView);
            ivArtwork = itemView.findViewById(R.id.cac_iv_artwork);
            tvSongName = itemView.findViewById(R.id.cac_tv_songName);
            tvArtistName = itemView.findViewById(R.id.cac_tv_artistName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notificadorCancionArtista.notificarCancion(trackList.get(getAdapterPosition()));
                }
            });
        }

        public void bindTrack(Track track){
            GlideController.loadImageFade(itemView, track.getAlbum().getCover_big(), ivArtwork);
            tvSongName.setText(track.getTitle_short());
            tvArtistName.setText(track.getArtist().getName());
        }
    }

    public interface NotificadorCancionArtista{
        void notificarCancion(Track cancion);
    }
}
