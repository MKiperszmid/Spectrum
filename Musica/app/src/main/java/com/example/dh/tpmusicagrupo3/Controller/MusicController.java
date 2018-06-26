package com.example.dh.tpmusicagrupo3.Controller;

import com.example.dh.tpmusicagrupo3.Model.DAO.RetrofitConnector;
import com.example.dh.tpmusicagrupo3.Model.POJO.Chart;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.ArtistContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.GenreContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DH on 15/6/2018.
 */

public class MusicController {

    private RetrofitConnector connector = new RetrofitConnector();
    // TODO: Hacer 1 de estos v para cada playlist. Ver como hacer para que playlist contenga eso. Asi no crear variables.
    private Integer offsetArgentina;
    private Integer limitArgentina = 25;
    private Boolean hayPaginasArgentina;

    private Integer offsetUsa;
    private Integer limitUsa = 25;
    private Boolean hayPaginasUsa;
    //IF track == null , devolver una lista descargada.

    public MusicController(){
        offsetArgentina = 0;
        hayPaginasArgentina = true;
        offsetUsa = 0;
        hayPaginasUsa = true;
    }

    public void getChart(final TrackListener<Chart> listener){
        connector.getChart(new TrackListener<Chart>() {
            @Override
            public void finish(Chart track) {
                listener.finish(track);
            }
        });
    }

    // Agregado recientemente
    public void getTracksRadio(final TrackListener<TrackContainer> listener, String id){
        connector.getTracksRadio(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                listener.finish(validateTracks(track));
            }
        }, id);
    }

    // Popular Argentina
    public void getTracksPlaylist(final TrackListener<TrackContainer> listener, String id, Integer index){
        connector.getTracksPlaylist(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                listener.finish(track);
            }
        }, id, index);
    }

    // Popular Argentina
    public void getTopArgentina(final TrackListener<TrackContainer> listener){
        getTracksPlaylist(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                if(track != null){
                    if(track.getData().size() < limitArgentina){
                        hayPaginasArgentina = false;
                    }
                    offsetArgentina += track.getData().size();
                }
                listener.finish(validateTracks(track));
            }
        }, "1279119721", offsetArgentina);
    }

    // Trending USA
    public void getTopUsa(final TrackListener<TrackContainer> listener){
        getTracksPlaylist(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                if(track != null){
                    if(track.getData().size() < limitUsa){
                        hayPaginasUsa = false;
                    }
                    offsetUsa += track.getData().size();
                }
                listener.finish(validateTracks(track));

            }
        }, "2097558104", offsetUsa);
    }

    // ??
    public void getTrack(final TrackListener<Track> listener, String id){
        connector.getTrack(new TrackListener<Track>() {
            @Override
            public void finish(Track track) {
                listener.finish(track);
            }
        }, id);
    }

    // Artistas populares
    public void getArtistsChart(final TrackListener<ArtistContainer> listener){
        connector.getArtistsChart(new TrackListener<ArtistContainer>() {
            @Override
            public void finish(ArtistContainer track) {
               listener.finish(track);
            }
        });
    }

    // TOP 10
    public void getTracksChart(final TrackListener<TrackContainer> listener){
        connector.getTracksChart(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                listener.finish(validateTracks(track));
            }
        });
    }

    // Generos
    public void getGenreList(final TrackListener<GenreContainer> listener){
        connector.getGenreList(new TrackListener<GenreContainer>() {
            @Override
            public void finish(GenreContainer track) {
                listener.finish(track);
            }
        });
    }


    public Boolean getHayPaginasArgentina(){ return hayPaginasArgentina; }

    public Boolean getHayPaginasUsa(){ return hayPaginasUsa; }

    private TrackContainer validateTracks(TrackContainer trackContainer){
        if(trackContainer == null)
            return trackContainer;

        List<Track> trackList = new ArrayList<>();
        for(Track t : trackContainer.getData()){
            if(!t.getPreview().equals("")){
                trackList.add(t);
            }
        }
        TrackContainer validatedTracks = new TrackContainer();
        validatedTracks.setData(trackList);
        return validatedTracks;
    }


}
