package com.example.dh.tpmusicagrupo3.View.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dh.tpmusicagrupo3.Model.POJO.Cancion;
import com.example.dh.tpmusicagrupo3.R;

import java.util.List;

public class AdapterCancionArtistaPortada extends RecyclerView.Adapter {

    private List<Cancion> canciones;
    private NotificadorCancionCelda notificadorCancionCelda;

    public AdapterCancionArtistaPortada(List<Cancion> canciones, NotificadorCancionCelda notificadorCancionCelda){
        this.canciones = canciones;
        this.notificadorCancionCelda = notificadorCancionCelda;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    notificadorCancionCelda.notificarCancionClickeada(canciones.get(pos));
                }
            });
        }

        public void bindCancion(Cancion cancion){
            tvCancion.setText(cancion.getTitle());
            tvArtista.setText(cancion.getArtist().getName());
            ivPortada.setImageResource(cancion.getImagenPortada());
        }
    }
    public interface NotificadorCancionCelda{
        void notificarCancionClickeada(Cancion cancionClickeada);
    }
}
