package com.example.dh.tpmusicagrupo3.View.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dh.tpmusicagrupo3.Controller.MediaPlayerService;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaybarbottomFragment extends Fragment {

    public static final String CLAVE_CANCION = "cancion clave";
    public static final String CLAVE_PLAYING = "Playing clave";
    public static final String CLAVE_CANCIONES = "canciones clave";
    private static ImageView playBtn;
    private TextView cancionPlaying;
    private TextView separatorPlaying;
    private TextView artistaPlaying;

    private RelativeLayout queue;
    private HomeFragment.NotificadorActivity notificadorActivity;
    public static Integer posicion;
    private Track cancion;
    private ArrayList<Track> trackList;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean playing = intent.getExtras().getBoolean(MediaPlayerService.IS_PLAYING, false);
            changeImage(playing);
        }
    };

    public PlaybarbottomFragment() {
        // Required empty public constructor
    }

    public static ImageView getPlayBtn() {
        return playBtn;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notificadorActivity = (HomeFragment.NotificadorActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playbarbottom, container, false);
        Bundle bundle = getArguments();
        posicion = bundle.getInt(CLAVE_CANCION);

        trackList = (ArrayList<Track>)bundle.getSerializable(CLAVE_CANCIONES);
        cancion = (Track)bundle.getSerializable(CLAVE_PLAYING);
        cancionPlaying = view.findViewById(R.id.cancionCurrentPlayingID);
        artistaPlaying = view.findViewById(R.id.artistCurrentPlayingID);
        separatorPlaying = view.findViewById(R.id.separatorCurrentPlayingID);
        queue = view.findViewById(R.id.relativeQueue);

       cancionPlaying.setText(cancion.getTitle_short());
       artistaPlaying.setText(cancion.getArtist().getName());
       separatorPlaying.setText(" - ");

        queue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (cancion != null){
                    notificadorActivity.recibirCancion(cancion, trackList);
                }
            }
        });

        /* Botón Play */
        playBtn = view.findViewById(R.id.playBtn);
        playBtn.setImageResource(R.drawable.play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificadorActivity.playSong();
            }
        });



        /* Botón UP (Ver Canción) */
        ImageView upBtn = view.findViewById(R.id.upBtn);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancion != null){
                    notificadorActivity.recibirCancion(cancion, trackList);
                }
            }
        });

        return view;
    }

    public void changeImage(Boolean playing){
        if(playing)
            playBtn.setImageResource(R.drawable.stop);
        else
            playBtn.setImageResource(R.drawable.play);
    }

    @Override
    public void onResume() {
        super.onResume();
        cancion = notificadorActivity.getCurrentPlaying();
        if(cancion == null) return;

        cancionPlaying.setText(cancion.getTitle_short());
        artistaPlaying.setText(cancion.getArtist().getName());
        try{
            if(notificadorActivity.isPlaying()){
                playBtn.setImageResource(R.drawable.stop);
            }else{
                playBtn.setImageResource(R.drawable.play);
            }
        }
        catch (IllegalStateException e){
            e.printStackTrace();
            playBtn.setImageResource(R.drawable.stop);
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter(MediaPlayerService.CHANGEIMAGE));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }
}
