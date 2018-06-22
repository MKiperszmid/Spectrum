package com.example.dh.tpmusicagrupo3.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dh.tpmusicagrupo3.Controller.MediaPlayerController;
import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.View.Activities.SongActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaybarbottomFragment extends Fragment {

    public static final String CLAVE_CANCION = "cancion clave";
    private ImageView playBtn;
    private TextView cancionPlaying;
    private TextView separatorPlaying;
    private TextView artistaPlaying;

    private RelativeLayout queue;
    private ExplorarFragment.NotificadorActivity notificadorActivity;
    public static Integer posicion;

    public PlaybarbottomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notificadorActivity = (ExplorarFragment.NotificadorActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playbarbottom, container, false);

        Bundle bundle = getArguments();
        posicion = bundle.getInt(CLAVE_CANCION);

        cancionPlaying = view.findViewById(R.id.cancionCurrentPlayingID);
        artistaPlaying = view.findViewById(R.id.artistCurrentPlayingID);
        separatorPlaying = view.findViewById(R.id.separatorCurrentPlayingID);
        queue = view.findViewById(R.id.relativeQueue);

       cancionPlaying.setText(MediaPlayerController.currentPlaying.getTitle_short());
       artistaPlaying.setText(MediaPlayerController.currentPlaying.getArtist().getName());
       separatorPlaying.setText(" - ");

        queue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (MediaPlayerController.currentPlaying != null){
                    notificadorActivity.recibirCancion(MediaPlayerController.currentPlaying, posicion);
                }
            }
        });

        /* Botón Play */
        playBtn = view.findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayerController.playPause(playBtn);
            }
        });

        /* Botón UP (Ver Canción) */
        ImageView upBtn = view.findViewById(R.id.upBtn);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MediaPlayerController.currentPlaying != null){
                    notificadorActivity.recibirCancion(MediaPlayerController.currentPlaying, posicion);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cancionPlaying.setText(MediaPlayerController.currentPlaying.getTitle_short());
        artistaPlaying.setText(MediaPlayerController.currentPlaying.getArtist().getName());
        if(MediaPlayerController.isPlaying()){
            playBtn.setImageResource(R.drawable.stop);
        }else{
            playBtn.setImageResource(R.drawable.play);
        }
    }
}
