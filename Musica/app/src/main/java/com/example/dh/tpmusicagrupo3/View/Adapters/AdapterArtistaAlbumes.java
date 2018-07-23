package com.example.dh.tpmusicagrupo3.View.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Controller.GlideController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Album;
import com.example.dh.tpmusicagrupo3.R;

import java.util.List;

public class AdapterArtistaAlbumes extends RecyclerView.Adapter {
    private List<Album> albums;
    private NotificadorAlbumClickeado notificadorAlbumClickeado;

    public AdapterArtistaAlbumes(List<Album> albums, NotificadorAlbumClickeado notificadorAlbumClickeado) {
        this.albums = albums;
        this.notificadorAlbumClickeado = notificadorAlbumClickeado;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.celda_artista_albumes, parent, false);
        return new ArtistaAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArtistaAlbumViewHolder artistaAlbumViewHolder = (ArtistaAlbumViewHolder) holder;
        Album album = albums.get(position);
        artistaAlbumViewHolder.bindAlbum(album);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ArtistaAlbumViewHolder extends RecyclerView.ViewHolder {
        ImageView ivArtwork;
        TextView tvAlbumName;

        public ArtistaAlbumViewHolder(final View itemView) {
            super(itemView);
            ivArtwork = itemView.findViewById(R.id.caa_iv_artwork);
            tvAlbumName = itemView.findViewById(R.id.caa_tv_albumName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificadorAlbumClickeado.notificarAlbumClickeado(albums.get(getAdapterPosition()));
                }
            });
        }

        public void bindAlbum(Album album) {
            GlideController.loadImageFade(itemView, album.getCover_big(), ivArtwork);
            tvAlbumName.setText(album.getTitle());
        }
    }

    public interface NotificadorAlbumClickeado{
        void notificarAlbumClickeado(Album album);
    }
}
