package com.example.dh.tpmusicagrupo3.View.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dh.tpmusicagrupo3.Controller.GlideController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.R;

import java.util.List;

/**
 * Created by DH on 26/6/2018.
 */

public class AdapterPlaylistItem extends RecyclerView.Adapter {
    private List<Playlist> playlists;
    private NotificadorPlaylistCelda notificadorPlaylistCelda;

    public AdapterPlaylistItem(List<Playlist> playlists, NotificadorPlaylistCelda notificadorPlaylistCelda){
        this.playlists = playlists;
        this.notificadorPlaylistCelda = notificadorPlaylistCelda;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.celda_playlist_item, parent, false);
        PlaylistViewHolder playlistViewHolder = new PlaylistViewHolder(view);
        return playlistViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        PlaylistViewHolder playlistViewHolder = (PlaylistViewHolder)holder;
        playlistViewHolder.bindPlaylist(playlist);
    }

    @Override
    public int getItemCount() {
        if(playlists != null){
            return playlists.size();
        }else{
            return 0;
        }
    }

    private class PlaylistViewHolder extends RecyclerView.ViewHolder {
        ImageView celdaplaylistImagen;
        TextView celdaplaylistCanciones, celdaplaylistNombre;

        public PlaylistViewHolder(View itemView) {
            super(itemView);
            celdaplaylistImagen = itemView.findViewById(R.id.celdaplaylistImagen);
            celdaplaylistCanciones = itemView.findViewById(R.id.celdaplaylistCanciones);
            celdaplaylistNombre = itemView.findViewById(R.id.celdaplaylistNombre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificadorPlaylistCelda.notificarPlaylistClickeada(playlists.get(getAdapterPosition()));
                }
            });
        }

        public void bindPlaylist(Playlist playlist) {
            GlideController.loadImageFade(itemView, playlist.getPicture_big(), celdaplaylistImagen);
            String canciones = playlist.getNb_tracks() + " canciones";
            celdaplaylistCanciones.setText(canciones);
            celdaplaylistNombre.setText(playlist.getTitle());
        }
    }

    public interface NotificadorPlaylistCelda{
        void notificarPlaylistClickeada(Playlist playlist);
    }
}