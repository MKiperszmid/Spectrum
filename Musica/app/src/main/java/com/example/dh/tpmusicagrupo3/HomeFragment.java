package com.example.dh.tpmusicagrupo3;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private NotificadorActivity notificadorActivity;
    private TextView textViewCancion;
    private TextView textViewArtista;
    private ImageView imageViewPortada;
    private Cancion cancion;

    public HomeFragment() {
        // Required empty public constructo
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.notificadorActivity = (NotificadorActivity) context;
    }


    private void LoadCancion(TextView nombreCancion, TextView nombreArtista, int resource){
        cancion = new Cancion(nombreCancion.getText().toString(), nombreArtista.getText().toString(), resource);
        notificadorActivity.recibirCancion(cancion);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout mySong1 = view.findViewById(R.id.mySong1);
        LinearLayout mySong2 = view.findViewById(R.id.mySong2);
        RelativeLayout queue = view.findViewById(R.id.relativeQueue);

        /* Feed */

        /* Canción 1 */
        mySong1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewCancion = view.findViewById(R.id.song1NameID);
                textViewArtista = view.findViewById(R.id.artist1NameID);

                LoadCancion(textViewCancion, textViewArtista, R.drawable.lavelapuercalanube);

                //   SongFragment songFragment = new SongFragment();
             //   cargarFragment(songFragment); // Play canción
            }
        });
        mySong2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewCancion = view.findViewById(R.id.song2NameID);
                textViewArtista = view.findViewById(R.id.artist2NameID);

                LoadCancion(textViewCancion, textViewArtista, R.drawable.paulolondradimelo);
            }
        });

        queue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Ver Cancion", Toast.LENGTH_SHORT).show();// Play canción
            }
        });

        /* Bottom bar */

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
        public void recibirCancion(Cancion cancion);
    }
}
