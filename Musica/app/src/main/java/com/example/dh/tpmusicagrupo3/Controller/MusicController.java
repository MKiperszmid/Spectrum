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

    public void getTopArgentina(final TrackListener<TrackContainer> listener){
        connector.getTopArgentina(new TrackListener<TrackContainer>() {
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
        });
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
