package com.example.dh.tpmusicagrupo3.Controller;

import com.example.dh.tpmusicagrupo3.Model.DAO.RetrofitConnector;
import com.example.dh.tpmusicagrupo3.Model.POJO.Chart;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;

/**
 * Created by DH on 15/6/2018.
 */

public class MusicController {
    private RetrofitConnector connector = new RetrofitConnector();


    //IF track == null , devolver una lista descargada.

    public void getChart(final TrackListener<Chart> listener){
        connector.getChart(new TrackListener<Chart>() {
            @Override
            public void finish(Chart track) {
                if(track != null)
                {
                    listener.finish(track);
                }
                else {
                    listener.finish(new Chart());
                    //OFFLINE
                }
            }
        });
    }

    public void getTracksRadio(final TrackListener<TrackContainer> listener, String id){
        //31061
        connector.getTracksRadio(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                if(track != null)
                {
                    listener.finish(track);
                }
                else {
                    listener.finish(new TrackContainer());
                    //OFFLINE
                }
            }
        }, id);
    }

    public void getTracksPlaylist(final TrackListener<TrackContainer> listener, String id){
        connector.getTracksPlaylist(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                if(track != null)
                {
                    listener.finish(track);
                }
                else {
                    listener.finish(new TrackContainer());
                    //OFFLINE
                }
            }
        }, id);
    }

    public void getTopArgentina(final TrackListener<TrackContainer> listener){
        getTracksPlaylist(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                if(track != null)
                {
                    listener.finish(track);
                }
                else {
                    listener.finish(new TrackContainer());
                    //OFFLINE
                }
            }
        }, "1279119721");
    }

    public void getTopUsa(final TrackListener<TrackContainer> listener){
        getTracksPlaylist(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                if(track != null)
                {
                    listener.finish(track);
                }
                else {
                    listener.finish(new TrackContainer());
                    //OFFLINE
                }
            }
        }, "2097558104");
    }

    public void getTrack(final TrackListener<Track> listener, String id){

        connector.getTrack(new TrackListener<Track>() {
            @Override
            public void finish(Track track) {
                listener.finish(track);
            }
        }, id);
    }
}
