package com.example.dh.tpmusicagrupo3.Model.DAO;

import com.example.dh.tpmusicagrupo3.Model.POJO.Chart;
import com.example.dh.tpmusicagrupo3.Model.POJO.Track;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by DH on 15/6/2018.
 */

public interface DeezerService {

    @GET("chart")
    Call<Chart> getChart();

    @GET("chart/0/tracks")
    Call<TrackContainer> getTracksChart();

    @GET("track/{id}")
    Call<Track> getTrack(@Path("id") String id);
}
