package com.example.dh.tpmusicagrupo3.View.Adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Model.POJO.Genre;
import com.example.dh.tpmusicagrupo3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdapterGeneroItem extends RecyclerView.Adapter {

    private List<Genre> generos;
    private List<Integer> drawablesGradients;
    private NotificadorGeneroCelda notificadorGeneroCelda;
    private Integer numColorGradient = 0;

    public AdapterGeneroItem(List<Genre> generos, NotificadorGeneroCelda notificadorGeneroCelda) {
        this.generos = generos;
        drawablesGradients = new ArrayList<>();
        drawablesGradients.add(R.drawable.gradientamarillo);
        drawablesGradients.add(R.drawable.gradientazul);
        drawablesGradients.add(R.drawable.gradientnaranja);
        drawablesGradients.add(R.drawable.gradientrojo);
        drawablesGradients.add(R.drawable.gradientvioleta);
        drawablesGradients.add(R.drawable.gradienteverde);
        drawablesGradients.add(R.drawable.gradienteceleste);
        drawablesGradients.add(R.drawable.gradienterosa);
        drawablesGradients.add(R.drawable.gradienteturquesa);
        drawablesGradients.add(R.drawable.gradientebordo);
        drawablesGradients.add(R.drawable.gradienteviolerosa);
        drawablesGradients.add(R.drawable.gradienteazuloscuro);
        drawablesGradients.add(R.drawable.gradientenaranjaamarillo);
        drawablesGradients.add(R.drawable.gradienteverdeoscuro);
        this.notificadorGeneroCelda = notificadorGeneroCelda;
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
        private LinearLayout genreContainer;

        public GeneroViewHolder(View itemView) {
            super(itemView);
            generoNombre = itemView.findViewById(R.id.nombreGeneroCelda);
            genreContainer = itemView.findViewById(R.id.genreContainer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificadorGeneroCelda.notificarGeneroClickeado(generos.get(getAdapterPosition()));
                }
            });
        }

        public void bindGenero(Genre genre) {

            generoNombre.setText(genre.getName());


            // En orden

            if(numColorGradient < drawablesGradients.size()){
                int gradientebg = drawablesGradients.get(numColorGradient);
                genreContainer.setBackgroundResource(gradientebg);
                numColorGradient ++;
            }else{
                numColorGradient = 0;
            }


            /* Random gradient
            int rndnumber = new Random().nextInt(drawablesGradients.size());
            int rndgradient = drawablesGradients.get(rndnumber);
            genreContainer.setBackgroundResource(rndgradient);
            */

        }
    }

    public interface NotificadorGeneroCelda{
        void notificarGeneroClickeado(Genre genre);
    }
}
