package com.example.dh.tpmusicagrupo3.View.Fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Controller.GlideController;
import com.example.dh.tpmusicagrupo3.Controller.MediaPlayerController;
import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.Utils.MiliSecondsToTimer;

import java.io.IOException;

public class SongFragment extends Fragment implements MediaPlayerController.NotificadorEstadoCancion {

    public static final String cancionKey = "CANCION";
    public static String CANCIONPOS = "POSITION";
    private FloatingActionButton pauseplayClick;
    private Track cancion;
    private NotificadorCambioCancion notificadorCambioCancion;
    private MediaPlayerController mediaPlayerController;


    private TextView totalDurationTV;
    private TextView currentDurationTV;
    private SeekBar seekBar;

    public static final String CANCIONKEY = "cancion";

    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorCambioCancion = (NotificadorCambioCancion) context;
    }

    public Track getCancion(){
        return this.cancion;
    }

    public static SongFragment dameFragment(Track cancion){
        SongFragment fragment = new SongFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CANCIONKEY, cancion);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        cancion = (Track) bundle.getSerializable(CANCIONKEY);
        mediaPlayerController = MediaPlayerController.getInstance(this);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        // Declaración de elementos visuales y carga de datos/contenido
        TextView descripcionCancionNombre = view.findViewById(R.id.descripcionCancionNombre);
        descripcionCancionNombre.setText(cancion.getTitle_short());
        TextView descripcionCancionArtista = view.findViewById(R.id.descripcionCancionArtista);
        descripcionCancionArtista.setText(cancion.getArtist().getName());
        ImageView descripcionCancionPortada = view.findViewById(R.id.descripcionCancionPortada);
        //GlideController.loadImage(view, cancion.getArtist().getPicture_big(), descripcionCancionPortada);
        GlideController.loadImage(view, cancion.getAlbum().getCover_big(), descripcionCancionPortada);

        pauseplayClick = view.findViewById(R.id.pauseplayClick);
        ImageView heartClick = view.findViewById(R.id.heartClick);
        ImageView backClick = view.findViewById(R.id.backClick);
        ImageView nextClick = view.findViewById(R.id.nextClick);
        ImageView agregarOffline = view.findViewById(R.id.agregarOffline);

        // Manejo de la duracion total de la cancion y tiempo escuchado actualmente
        totalDurationTV = view.findViewById(R.id.totalDurationSong);
        currentDurationTV = view.findViewById(R.id.currentDurationSong);

        MiliSecondsToTimer miliSecondsToTimer = new MiliSecondsToTimer();
        //String duracionCancion = miliSecondsToTimer.milliSecondsToTimer(mediaPlayerController.getDuration());
        String duracionCancion = "0:30"; // TODO: obtener valor de getDuration
        final String currentduracionCancion = miliSecondsToTimer.milliSecondsToTimer(MediaPlayerController.getCurrentDuration());

        totalDurationTV.setText(duracionCancion);
        currentDurationTV.setText(currentduracionCancion);
        seekBar = view.findViewById(R.id.seekBarSong);
        seekBar.setMax(30); // TODO obtener valor de getDuration


        // Listener de click de elementos
        // Ver Artista
        descripcionCancionArtista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Mostrar perfil de " + cancion.getArtist(), Toast.LENGTH_SHORT).show();
            }
        });

        // Dar Me Gusta a canción
        heartClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Me Gusta", Toast.LENGTH_SHORT).show();
            }
        });

        // Ir hacia la canción anterior
        backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Retroceder", Toast.LENGTH_SHORT).show();
                mediaPlayerController.retroceder(pauseplayClick);
                notificadorCambioCancion.retroceder();
            }
        });

        // Pausar canción
        pauseplayClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerController.playPause(pauseplayClick);
            }
        });

        // Ir hacia la canción que sigue
        nextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Adelantar", Toast.LENGTH_SHORT).show();
                notificadorCambioCancion.adelantar();
            }
        });

        // Agregar canción a playlist
        agregarOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Agregar a playlist", Toast.LENGTH_SHORT).show();
            }
        });


        // Llamo al Runnable
        actualizarMusicaInfo();

        return view;
    }

    @Override
    public void cambiarEstado() {
        // TODO: Hacer que el CREATE del MediaPlayerController, sea parte del SongFragment. Y no del MediaPlayerController.
        onResume(); // <-- No funciona no se porque.
    }

    public interface NotificadorCambioCancion{
        // Envia a SongActivity
        public void retroceder();
        public void adelantar();
    }

    // Cada 1 segundo actualiza el textview de por que segundo va la cancion y la seekbar
    public void actualizarMusicaInfo(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                int currentDuration;
                currentDuration = MediaPlayerController.getCurrentDuration();
                updateSeekBar(currentDuration);
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void updateSeekBar(int currentDuration){
        MiliSecondsToTimer miliSecondsToTimer = new MiliSecondsToTimer();
        currentDurationTV.setText(miliSecondsToTimer.milliSecondsToTimer((long) currentDuration));
        seekBar.setProgress(currentDuration / 1000);

    }


    @Override
    public void onResume() {
        super.onResume();
        if(mediaPlayerController.isPlaying()){
            pauseplayClick.setImageResource(R.drawable.stop);
        }
        else {
            pauseplayClick.setImageResource(R.drawable.play);
        }
    }
}
