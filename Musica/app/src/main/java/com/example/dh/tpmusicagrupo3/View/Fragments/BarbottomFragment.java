package com.example.dh.tpmusicagrupo3.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dh.tpmusicagrupo3.Controller.MediaPlayerController;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BarbottomFragment extends Fragment{

    private NotificadorActivityBarBottom notificadorActivityBarBottom;

    // Iconos de bottom bar
    private ImageView homeBtnIcon;
    private ImageView explorarBtnIcon;
    private ImageView buscarBtnIcon;
    private ImageView perfilBtnIcon;

    private TextView homeBtnTxt;
    private TextView explorarBtnTxt;
    private TextView buscarBtnTxt;
    private TextView perfilBtnTxt;





    public BarbottomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorActivityBarBottom = (NotificadorActivityBarBottom) context;
    }


    public interface NotificadorActivityBarBottom{
        void recibirSeccion(Fragment fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barbottom, container, false);



        homeBtnIcon = view.findViewById(R.id.homeIconID);
        explorarBtnIcon = view.findViewById(R.id.exploreIconID);
        buscarBtnIcon = view.findViewById(R.id.searchIconID);
        perfilBtnIcon = view.findViewById(R.id.profileIconID);
        homeBtnTxt = view.findViewById(R.id.homeTxtID);
        explorarBtnTxt = view.findViewById(R.id.explorarTxtID);
        buscarBtnTxt = view.findViewById(R.id.buscarTxtID);
        perfilBtnTxt = view.findViewById(R.id.perfilTxtID);


        /* Bottom bar */
        /* Botón Home */
        LinearLayout homeBtn = view.findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificadorActivityBarBottom.recibirSeccion(new HomeFragment());
                Toast.makeText(getActivity(), "Home", Toast.LENGTH_SHORT).show();
                cambiarIcono("home");
            }
        });

        /* Botón Explorar */
        LinearLayout explorarBtn = view.findViewById(R.id.explorarBtn);
        explorarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificadorActivityBarBottom.recibirSeccion(new ExplorarFragment());
                Toast.makeText(getActivity(), "Explorar", Toast.LENGTH_SHORT).show();
                cambiarIcono("explorar");
            }
        });

        /* Botón Buscar */
        LinearLayout buscarBtn = view.findViewById(R.id.buscarBtn);
        buscarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificadorActivityBarBottom.recibirSeccion(new BuscarFragment());
                Toast.makeText(getActivity(), "Buscar", Toast.LENGTH_SHORT).show();
                cambiarIcono("buscar");
            }
        });

        /* Botón Perfil */
        LinearLayout perfilBtn = view.findViewById(R.id.perfilBtn);
        perfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificadorActivityBarBottom.recibirSeccion(new PerfilFragment());
                Toast.makeText(getActivity(), "Perfil", Toast.LENGTH_SHORT).show();
                cambiarIcono("perfil");
            }
        });




        return view;
    }



    private void cambiarColor(TextView textview, int color){
        textview.setTextColor(getResources().getColor(color));
    }

    private void cambiarIcono(ImageView imageView, int icono){
        imageView.setImageResource(icono);
    }

    private void cambiarTodo(TextView textView, int color, ImageView imageView, int icono){
        cambiarColor(textView, color);
        cambiarIcono(imageView, icono);
    }

    private void cambiarIcono(String seccionNombre) {

        // Reset, pongo todos los iconos en gris
        cambiarTodo(homeBtnTxt, R.color.colorGraySemiWhite, homeBtnIcon, R.drawable.home);
        cambiarTodo(explorarBtnTxt, R.color.colorGraySemiWhite, explorarBtnIcon, R.drawable.explorar);
        cambiarTodo(buscarBtnTxt, R.color.colorGraySemiWhite, buscarBtnIcon, R.drawable.buscar);
        cambiarTodo(perfilBtnTxt, R.color.colorGraySemiWhite, perfilBtnIcon, R.drawable.perfil);

        // Dependiendo cual toco le cambio icono y color de texto
        switch(seccionNombre){
            case "home":
                cambiarTodo(homeBtnTxt, R.color.colorAccent, homeBtnIcon, R.drawable.homeactivo);
            break;
            case "explorar":
                cambiarTodo(explorarBtnTxt, R.color.colorAccent, explorarBtnIcon, R.drawable.exploraractivo);
            break;
            case "buscar":
                cambiarTodo(buscarBtnTxt, R.color.colorAccent, buscarBtnIcon, R.drawable.buscaractivo);
            break;
            case "perfil":
                cambiarTodo(perfilBtnTxt, R.color.colorAccent, perfilBtnIcon, R.drawable.perfilactivo);
            break;
        }
    }


}
