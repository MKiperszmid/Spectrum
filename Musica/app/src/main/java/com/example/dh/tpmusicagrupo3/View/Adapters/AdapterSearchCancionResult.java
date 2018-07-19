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

import java.util.List;

public class AdapterSearchCancionResult extends RecyclerView.Adapter{

    private List<Track> tracks;

    public AdapterSearchCancionResult(List<Track> tracks) {
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View vw = inflater.inflate(R.layout.celda_cancion_resultado_buscador, parent, false);
        SearchCancionResultViewHolder searchCancionResultViewHolder = new SearchCancionResultViewHolder(vw);
        return searchCancionResultViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Track track = tracks.get(position);
        SearchCancionResultViewHolder searchCancionResultViewHolder = (SearchCancionResultViewHolder) holder;
        searchCancionResultViewHolder.bindTrack(track);
    }

    @Override
    public int getItemCount() {
        if (tracks!=null){
            return tracks.size();
        }else{
            return 0;
        }
    }

    private class SearchCancionResultViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;

        public SearchCancionResultViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.celdaImagenCancionBusqueda);
            title = itemView.findViewById(R.id.celdaTituloCancionBusqueda);
        }

        public void bindTrack(Track track) {
            title.setText(track.getTitle_short());
            GlideController.loadImageFade(itemView, track.getAlbum().getCover_medium(), image);
        }
    }

}
