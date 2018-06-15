package com.example.dh.tpmusicagrupo3.Model.DAO;

import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Chart;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DH on 15/6/2018.
 */

public class RetrofitConnector {
    private Retrofit retrofit;
    private DeezerService service;

    public RetrofitConnector(){
        retrofit = new Retrofit.Builder().baseUrl("https://api.deezer.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(DeezerService.class);
    }

    //TODO: GetTrack, GetChart, GetPlaylist, etc...

    public void getChart(final TrackListener<Chart> listener){
        Call<Chart> chartCall = service.getChart();
        chartCall.enqueue(new Callback<Chart>() {
            @Override
            public void onResponse(Call<Chart> call, Response<Chart> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(Call<Chart> call, Throwable t) {
                listener.finish(null);
            }
        });
    }

    public void getChartTracks(final TrackListener<TrackContainer> listener){
        Call<TrackContainer> tracks = service.getTracksChart();
        tracks.enqueue(new Callback<TrackContainer>() {
            @Override
            public void onResponse(Call<TrackContainer> call, Response<TrackContainer> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(Call<TrackContainer> call, Throwable t) {
                listener.finish(null);
            }
        });
    }

    public void getTrack(final TrackListener<Track> listener, String id){
        Call<Track> track = service.getTrack(id);
        track.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                listener.finish(null);
            }
        });
    }
}
