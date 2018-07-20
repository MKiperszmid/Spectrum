package com.example.dh.tpmusicagrupo3.View.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Controller.DatosControllers.PlaylistController;
import com.example.dh.tpmusicagrupo3.Controller.MusicController;
import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.PlaylistContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Playlist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.View.Activities.MainActivity;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterCancionArtistaPortada;
import com.example.dh.tpmusicagrupo3.View.Adapters.AdapterPlaylistItem;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.example.dh.tpmusicagrupo3.R;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment implements AdapterCancionArtistaPortada.NotificadorCancionCelda, AdapterPlaylistItem.NotificadorPlaylistCelda {

    //  private CallbackManager callbackManager;
    //  private LoginButton loginButton;

    private FirebaseAuth mAuth;
    private LinearLayout layoutCreateAccount;
    private LinearLayout layoutProfile;


    private String sectionString = "Perfil";
    private MusicController musicController;
    private RecyclerView rvCanciones, rvPlaylists;

    private TextInputLayout emailContainer;
    private TextInputLayout passContainer;
    private ProgressBar progressBar;

    private ExplorarFragment.NotificarClickeado notificarClickeado;
    private HomeFragment.NotificadorActivity notificadorActivity;

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView loginfacebookCustom;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificarClickeado = (ExplorarFragment.NotificarClickeado) context;
        this.notificadorActivity = (HomeFragment.NotificadorActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(sectionString);
        musicController = new MusicController();
        mAuth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        layoutCreateAccount = view.findViewById(R.id.fp_ll_createAccount);
        layoutProfile = view.findViewById(R.id.fp_ll_profileAccount);

        if (mAuth.getCurrentUser() != null) {
            loggedInContent(view);
        } else {
            loggedOutContent(view);
        }

        return view;
    }

    private void loadFacebook(View view) {

        callbackManager = CallbackManager.Factory.create();
        loginfacebookCustom = view.findViewById(R.id.fp_tv_loginFacebookCustom);

        loginfacebookCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });

        loginButton.setReadPermissions("email", "public_profile");
        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                // App code
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getContext(), "CANCEL", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("CREATEACCOUNT", "createUserWithEmail:success");
                            showLoggedin(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("CREATEACCOUNT", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "No se ha podido registrar, pruebe con otros datos.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void signinAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SIGNINACCOUNT", "signInWithEmail:success");
                            showLoggedin(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SIGNINACCOUNT", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "No se ha podido ingresar a su cuenta, compruebe sus datos.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void showLoggedin(Boolean loggedIn) {
        if (loggedIn) {
            layoutCreateAccount.setVisibility(View.GONE);
            layoutProfile.setVisibility(View.VISIBLE);
            loggedInContent(getView());
        } else {
            layoutCreateAccount.setVisibility(View.VISIBLE);
            layoutProfile.setVisibility(View.GONE);
        }
    }

    private void loggedInContent(View view) {
        ImageView imageBg = view.findViewById(R.id.fp_iv_profileImage);
        CircleImageView imageFg = view.findViewById(R.id.fp_civ_profileImage);
        TextView name = view.findViewById(R.id.fp_tv_profileName);
        rvCanciones = view.findViewById(R.id.fp_rv_cancionesFavoritas);
        rvPlaylists = view.findViewById(R.id.fp_rv_playlistsFavoritas);
        TextView configuracion = view.findViewById(R.id.fp_tv_configuracion);
        TextView logout = view.findViewById(R.id.fp_tv_cerrarSesion);
        progressBar = view.findViewById(R.id.fp_pb_progressBar);

        FirebaseUser user = mAuth.getCurrentUser();

        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        rvCanciones.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvPlaylists.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        loadDatabase();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    showLoggedin(false);
                    mAuth.signOut();
                    AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    if (accessToken != null && !accessToken.isExpired()) {
                        LoginManager.getInstance().logOut();
                    }
                }
            }
        });
    }

    private void loggedOutContent(View view) {
        final TextInputEditText etPass;

        loginButton = view.findViewById(R.id.login_button);
        final TextInputEditText etEmail = view.findViewById(R.id.fp_tiet_email);
        emailContainer = view.findViewById(R.id.fp_til_emailContainer);
        etPass = view.findViewById(R.id.fp_tiet_pass);
        passContainer = view.findViewById(R.id.fp_til_passContainer);

        final Button createAccount = view.findViewById(R.id.fp_btn_createAccount);
        final Button signinAccount = view.findViewById(R.id.fp_btn_signinAccount);


        loadFacebook(view);


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();
                if (!validLogin(email, pass)) {
                    return;
                }
                createAccount(email, pass);
            }
        });


        signinAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();
                if (!validLogin(email, pass)) {
                    return;
                }
                signinAccount(email, pass);
            }
        });


        final Button solapa_iniciarsesion_btn = view.findViewById(R.id.solapa_iniciarsesion_btn);
        final Button solapa_crearcuenta_btn = view.findViewById(R.id.solapa_crearcuenta_btn);

        solapa_iniciarsesion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinAccount.setVisibility(View.VISIBLE);
                createAccount.setVisibility(View.GONE);
                solapa_iniciarsesion_btn.setBackgroundResource(R.color.colorPrimary);
                solapa_iniciarsesion_btn.setTextColor(Color.parseColor("#0bffae"));
                solapa_crearcuenta_btn.setBackgroundResource(R.color.colorPrimaryDark);
                solapa_crearcuenta_btn.setTextColor(Color.parseColor("#a2a3a9"));
            }
        });

        solapa_crearcuenta_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinAccount.setVisibility(View.GONE);
                createAccount.setVisibility(View.VISIBLE);
                solapa_iniciarsesion_btn.setBackgroundResource(R.color.colorPrimaryDark);
                solapa_iniciarsesion_btn.setTextColor(Color.parseColor("#a2a3a9"));
                solapa_crearcuenta_btn.setBackgroundResource(R.color.colorPrimary);
                solapa_crearcuenta_btn.setTextColor(Color.parseColor("#0bffae"));
            }
        });

    }

    private Boolean validLogin(String email, String pass) {
        emailContainer.setError(null);
        passContainer.setError(null);
        if (!email.contains("@")) {
            emailContainer.setError("Email necesita un @");
            return false;
        }
        if (email == null || email.matches("")) {
            emailContainer.setError("Debes completas este campo");
            return false;
        }
        if (pass == null || pass.matches("")) {
            passContainer.setError("Debes completas este campo");
            return false;
        }
        return true;
    }

    private void loadDatabase() {
        getPlaylistsDb();
        getCancionesDb();
    }

    private void getPlaylistsDb() {
        final AdapterPlaylistItem.NotificadorPlaylistCelda notificadorPlaylistCelda = this;
        musicController.getPlaylistsChart(new TrackListener<PlaylistContainer>() {
            @Override
            public void finish(PlaylistContainer track) {
                if (track != null) {
                    AdapterPlaylistItem adapterPlaylistItem = new AdapterPlaylistItem(track.getData(), notificadorPlaylistCelda);
                    rvPlaylists.setAdapter(adapterPlaylistItem);
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void getCancionesDb() {
        final AdapterCancionArtistaPortada.NotificadorCancionCelda notificadorCancionCelda = this;
        musicController.getTracksChart(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                if (track != null) {
                    AdapterCancionArtistaPortada adapterCancionArtistaPortada = new AdapterCancionArtistaPortada(track.getData(), notificadorCancionCelda);
                    rvCanciones.setAdapter(adapterCancionArtistaPortada);
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            showLoggedin(true);
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            showLoggedin(true);
        } else {
            showLoggedin(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void notificarCancionClickeada(Track cancionClickeada, List<Track> tracks) {
        ArrayList<Track> newTracks = new ArrayList<>();
        newTracks.addAll(tracks);
        notificadorActivity.recibirCancion(cancionClickeada, newTracks);
    }

    @Override
    public void notificarPlaylistClickeada(Playlist playlist) {
        notificarClickeado.notificar(new PlaylistController(playlist));
    }
}
