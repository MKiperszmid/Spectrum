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

    public void getChart(final TrackListener<Chart> listener){

        connector.getChart(new TrackListener<Chart>() {
            @Override
            public void finish(Chart track) {
                listener.finish(track);
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

    public void getChartTracks(final TrackListener<TrackContainer> listener){
        connector.getChartTracks(new TrackListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer track) {
                listener.finish(track);
            }
        });
    }
}
