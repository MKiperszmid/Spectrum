package com.example.dh.tpmusicagrupo3;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private NotificadorActivity notificadorActivity;
    private TextView textViewCancion;
    private TextView textViewArtista;
    private ImageView imageViewPortada;
    private Cancion cancion;
    private List<Cancion> canciones;

    public HomeFragment() {
        // Required empty public constructo
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.notificadorActivity = (NotificadorActivity) context;
    }

    private void LoadCanciones(){
        canciones = new ArrayList<>();
        canciones.add(new Cancion("La Nuba", "La Vela Puerca", R.drawable.lavelapuercalanube));
        canciones.add(new Cancion("This is America", "Childish Gambino", R.drawable.childishgambinothisisamerica));
        canciones.add(new Cancion("X", "Nicky Jam - J Balvin", R.drawable.nickyjamjbalvinx));
        canciones.add(new Cancion("Dimelo", "Paulo Londra", R.drawable.paulolondradimelo));
        canciones.add(new Cancion("Me Niego", "Reik ft Osuna y Wisin", R.drawable.reikftozunawisinmeniego));
        canciones.add(new Cancion("Bella", "Wolfine", R.drawable.wolfinebella));
    }

    private void LoadCancion(TextView nombreCancion, TextView nombreArtista, int resource){
        cancion = new Cancion(nombreCancion.getText().toString(), nombreArtista.getText().toString(), resource);
        notificadorActivity.recibirCancion(cancion);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        LoadCanciones();

        RelativeLayout queue = view.findViewById(R.id.relativeQueue);

        RecyclerView rvPopular = view.findViewById(R.id.recyclerPopularAhora);
        RecyclerView rvAgregado = view.findViewById(R.id.recyclerAgregadoRecientemente);

        rvPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        AdapterCancionArtistaPortada adapterCancionArtistaPortada = new AdapterCancionArtistaPortada(canciones);
        rvPopular.setAdapter(adapterCancionArtistaPortada);

        rvAgregado.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvAgregado.setAdapter(adapterCancionArtistaPortada);


        /* Feed */

        queue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Ver Cancion", Toast.LENGTH_SHORT).show();// Play canción
            }
        });

        /* Bottom bar */

        LinearLayout homeBtn = view.findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Home", Toast.LENGTH_SHORT).show();
            }
        });

        /* Botón Explorar */
        LinearLayout explorarBtn = view.findViewById(R.id.explorarBtn);
        explorarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Explorar", Toast.LENGTH_SHORT).show();
            }
        });

        /* Botón Buscar */
        LinearLayout buscarBtn = view.findViewById(R.id.buscarBtn);
        buscarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Buscar", Toast.LENGTH_SHORT).show();
            }
        });

        /* Botón Perfil */
        LinearLayout perfilBtn = view.findViewById(R.id.perfilBtn);
        perfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Perfil", Toast.LENGTH_SHORT).show();
            }
        });

        /* Botón Play */
        ImageView playBtn = view.findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Play", Toast.LENGTH_SHORT).show();
            }
        });


        /* Botón UP (Ver Canción) */
        ImageView upBtn = view.findViewById(R.id.upBtn);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Ver Cancion", Toast.LENGTH_SHORT).show();
            }
        });
        
        return view;
    }

    private void cargarFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.homeID, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public interface NotificadorActivity{
        void recibirCancion(Cancion cancion);
    }
}
