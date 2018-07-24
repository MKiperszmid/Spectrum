package com.example.dh.tpmusicagrupo3.View.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Controller.GlideController;
import com.example.dh.tpmusicagrupo3.Controller.MediaPlayerService;
import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;
import com.example.dh.tpmusicagrupo3.Utils.MiliSecondsToTimer;
import com.example.dh.tpmusicagrupo3.View.Activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SongFragment extends Fragment {

    public static final String cancionKey = "CANCION";
    public static final String CANCIONESKEY = "CANCIONES";
    public static String CANCIONPOS = "POSITION";
    private FloatingActionButton pauseplayClick;
    private Track cancion;
    private FirebaseAuth mAuth;
    private NotificadorCambioCancion notificadorCambioCancion;
    private MediaPlayerService mediaPlayerService;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean playing = intent.getExtras().getBoolean(MediaPlayerService.IS_PLAYING, false);
            changeImage(playing);
        }
    };

    private TextView currentDurationTV;
    private SeekBar seekBar;

    private FrameLayout menuMasOpciones;

    private Boolean menuMasOpcionesStatus = false;

    public static final String CANCIONKEY = "cancion";
    private NotificadorFragmentActivity notificadorFragmentActivity;

    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorCambioCancion = (NotificadorCambioCancion) context;
        this.notificadorFragmentActivity = (NotificadorFragmentActivity) context;
    }

    private Boolean isLoggedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        //TODO: Agregar FB.
        if (user != null) {
            return true;
        }
        return false;
    }

    public Track getCancion() {
        return this.cancion;
    }

    public static SongFragment dameFragment(Track cancion) {
        SongFragment fragment = new SongFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CANCIONKEY, cancion);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        Bundle bundle = getArguments();
        cancion = (Track) bundle.getSerializable(CANCIONKEY);

        mAuth = FirebaseAuth.getInstance();

        // Declaración de elementos visuales y carga de datos/contenido
        TextView descripcionCancionNombre = view.findViewById(R.id.descripcionCancionNombre);
        descripcionCancionNombre.setText(cancion.getTitle_short());
        TextView descripcionCancionArtista = view.findViewById(R.id.descripcionCancionArtista);
        descripcionCancionArtista.setText(cancion.getArtist().getName());
        ImageView descripcionCancionPortada = view.findViewById(R.id.descripcionCancionPortada);

        menuMasOpciones = view.findViewById(R.id.menuMasOpciones);
        FrameLayout cerrarMenuMasOpciones = view.findViewById(R.id.cerrarMenuMasOpciones);
        mediaPlayerService = MediaPlayerService.getInstance();
        View viewCompartirCancion = view.findViewById(R.id.fs_v_compartirCancion);


        View viewAgregarPlaylist = view.findViewById(R.id.fs_v_agregarPlaylist);

        GlideController.loadImages(view, cancion.getAlbum().getCover_big(), descripcionCancionPortada);
        pauseplayClick = view.findViewById(R.id.pauseplayClick);
        ImageView heartClick = view.findViewById(R.id.heartClick);
        ImageView backClick = view.findViewById(R.id.backClick);
        ImageView nextClick = view.findViewById(R.id.nextClick);
        ImageView masOpciones = view.findViewById(R.id.masOpciones);

        // Manejo de la duracion total de la cancion y tiempo escuchado actualmente
        TextView totalDurationTV = view.findViewById(R.id.totalDurationSong);
        currentDurationTV = view.findViewById(R.id.currentDurationSong);

        MiliSecondsToTimer miliSecondsToTimer = new MiliSecondsToTimer();
        //String duracionCancion = miliSecondsToTimer.milliSecondsToTimer(mediaPlayerController.getDuration());
        Integer duracion = cancion.getDuration();
        String duracionCancion = segundosToTiempo(duracion);
        final String currentduracionCancion = miliSecondsToTimer.milliSecondsToTimer(MainActivity.mediaPlayerService.getCurrentDuration()); //MediaPlayerController.getCurrentDuration()

        totalDurationTV.setText(duracionCancion);
        currentDurationTV.setText(currentduracionCancion);
        seekBar = view.findViewById(R.id.seekBarSong);
        seekBar.setMax(duracion);
        pauseplayClick.setImageResource(R.drawable.play);


        // Listener de click de elementos
        // Ver Artista
        descripcionCancionArtista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificadorFragmentActivity.notificarArtista(cancion.getArtist());
            }
        });

        // Dar Me Gusta a canción
        heartClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLoggedIn()) {
                    Toast.makeText(getActivity(), "Me Gusta", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Necesitas estar logueado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Ir hacia la canción anterior
        backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificadorCambioCancion.retroceder();
            }
        });

        // Pausar canción
        pauseplayClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerService.togglePlayer();
                // mediaPlayerController.playPause(pauseplayClick);
            }
        });

        // Ir hacia la canción que sigue
        nextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificadorCambioCancion.adelantar();
            }
        });

        // Abrir menu más opciones
        masOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Abrir más opciones", Toast.LENGTH_SHORT).show();
                if (!menuMasOpcionesStatus) {
                    Animation animation;
                    animation = AnimationUtils.loadAnimation(getContext(),
                            R.anim.slide_up);
                    menuMasOpciones.startAnimation(animation);
                    menuMasOpciones.setVisibility(View.VISIBLE);
                    menuMasOpciones.setClickable(true);
                    menuMasOpciones.setEnabled(true);
                    menuMasOpcionesStatus = true;
                    setClickable(menuMasOpciones, true);
                }
            }
        });

        // Cerrar menu más opciones
        cerrarMenuMasOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "cerrar más opciones", Toast.LENGTH_SHORT).show();
                if (menuMasOpcionesStatus) {
                    Animation animation;
                    animation = AnimationUtils.loadAnimation(getContext(),
                            R.anim.slide_down);
                    menuMasOpciones.startAnimation(animation);
                    menuMasOpciones.setVisibility(View.GONE);
                    menuMasOpciones.setClickable(false);
                    menuMasOpciones.setEnabled(false);
                    menuMasOpcionesStatus = false;
                    setClickable(menuMasOpciones, false);
                }
            }
        });

        viewAgregarPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoggedIn()) {
                    Toast.makeText(getActivity(), "Agregar a Playlist", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Necesitas estar logueado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewCompartirCancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Te recomiendo esta canción!");
                String cancionInfo = cancion.getArtist().toString() + " " + cancion.getTitle_short().toString();

                String cancionURL = "https://www.deezer.com/en/track/" + cancion.getId();
                share.putExtra(Intent.EXTRA_TEXT, "Te recomiendo esta canción: " + cancionInfo + " - " + cancionURL);
                startActivity(Intent.createChooser(share, "Compartir " + cancionInfo));
            }
        });

        // Llamo al Runnable
        actualizarMusicaInfo();

        MediaPlayerService mediaPlayerService = MediaPlayerService.getInstance();
        mediaPlayerService.closeNotification();

        return view;
    }

    private String segundosToTiempo(Integer segundos) {
        Integer minutos = segundos / 60;
        String segundosText, minutosText;
        if (minutos > 1) {
            segundos -= minutos * 60;
        }

        segundosText = segundos.toString();
        if (segundos < 10) {
            segundosText = "0" + segundos;
        }
        minutosText = minutos.toString();
        return minutosText + ":" + segundosText;
    }


    public interface NotificadorCambioCancion {
        // Envia a SongActivity
        public void retroceder();

        public void adelantar();
    }

    public interface NotificadorFragmentActivity {
        void notificarArtista(Artist artist);
    }

    // Cada 1 segundo actualiza el textview de por que segundo va la cancion y la seekbar
    public void actualizarMusicaInfo() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentDuration;
                //currentDuration = MediaPlayerController.getCurrentDuration();
                currentDuration = mediaPlayerService.getCurrentDuration();
                updateSeekBar(currentDuration);
                handler.postDelayed(this, 1000);
            }
        }, 0); // si delay 0 para arrancar
    }

    private void updateSeekBar(int currentDuration) {
        MiliSecondsToTimer miliSecondsToTimer = new MiliSecondsToTimer();
        currentDurationTV.setText(miliSecondsToTimer.milliSecondsToTimer((long) currentDuration));
        seekBar.setProgress(currentDuration / 1000);
    }

    public void changeImage(Boolean playing) {
        if (playing)
            pauseplayClick.setImageResource(R.drawable.stop);
        else
            pauseplayClick.setImageResource(R.drawable.play);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mediaPlayerService.isPlaying()) {
            pauseplayClick.setImageResource(R.drawable.stop);
        } else {
            pauseplayClick.setImageResource(R.drawable.play);
        }

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter(MediaPlayerService.CHANGEIMAGE));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    // Recibe un view contenedor y un estado, y hace unclickeable a todos sus childs
    public void setClickable(View view, boolean status) {
        if (view != null) {
            view.setClickable(status);
            if (view instanceof ViewGroup) {
                ViewGroup vg = ((ViewGroup) view);
                for (int i = 0; i < vg.getChildCount(); i++) {
                    setClickable(vg.getChildAt(i), status);
                }
            }
        }
    }

}
