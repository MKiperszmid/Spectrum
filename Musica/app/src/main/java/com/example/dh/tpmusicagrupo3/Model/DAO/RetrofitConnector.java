package com.example.dh.tpmusicagrupo3.Model.DAO;

import com.example.dh.tpmusicagrupo3.Controller.TrackListener;
import com.example.dh.tpmusicagrupo3.Model.POJO.Artist;
import com.example.dh.tpmusicagrupo3.Model.POJO.Chart;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.AlbumContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.ArtistContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.GenreContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.PlaylistContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Genre;
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

    //
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

    //
    public void getTracksPlaylist(final TrackListener<TrackContainer> listener, String id, Integer index){
        Call<TrackContainer> trackCall = service.getTracksPlaylist(id, index);
        trackCall.enqueue(new Callback<TrackContainer>() {
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

    //
    public void getTracksRadio(final TrackListener<TrackContainer> listener, String id){
        Call<TrackContainer> trackListenerCall = service.getTracksRadio(id);
        trackListenerCall.enqueue(new Callback<TrackContainer>() {
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

    // Obtener cancion en especifico
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


    // Obtener lista de Artistas populares
    public void getArtistsChart(final TrackListener<ArtistContainer> listener){
        Call<ArtistContainer> artistChartCall = service.getArtistsChart();
        artistChartCall.enqueue(new Callback<ArtistContainer>() {
            @Override
            public void onResponse(Call<ArtistContainer> call, Response<ArtistContainer> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(Call<ArtistContainer> call, Throwable t) {
                listener.finish(null);
            }
        });
    }

    //
    public void getTracksChart(final TrackListener<TrackContainer> listener){
        Call<TrackContainer> trackContainerCall = service.getTracksChart();
        trackContainerCall.enqueue(new Callback<TrackContainer>() {
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

    // Obtener lista de Generos
    public void getGenreList(final TrackListener<GenreContainer> listener){
        Call<GenreContainer> genreListCall = service.getGenresList();
        genreListCall.enqueue(new Callback<GenreContainer>() {
            @Override
            public void onResponse(Call<GenreContainer> call, Response<GenreContainer> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(Call<GenreContainer> call, Throwable t) {
                listener.finish(null);
            }
        });
    }

    //
    public void getPlaylistsChart(final TrackListener<PlaylistContainer> listener){
        Call<PlaylistContainer> playlistContainerCall = service.getPlaylistsChart();
        playlistContainerCall.enqueue(new Callback<PlaylistContainer>() {
            @Override
            public void onResponse(Call<PlaylistContainer> call, Response<PlaylistContainer> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(Call<PlaylistContainer> call, Throwable t) {
                listener.finish(null);
            }
        });
    }

    // Obtener Artista
    public void getArtist(final TrackListener<Artist> listener, Integer id){
        Call<Artist> artistCall = service.getArtist(id);
        artistCall.enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(Call<Artist> call, Throwable t) {
                listener.finish(null);
            }
        });
    }

    // Obtener lista de canciones de Artista en especifico
    public void getArtistTracks(final TrackListener<TrackContainer> listener, Integer id, Integer limit){
        Call<TrackContainer> trackContainerCall = service.getArtistTracks(id, limit);
        trackContainerCall.enqueue(new Callback<TrackContainer>() {
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

    // Obtener lista de albums de Artista en especifico
    public void getArtistAlbums(final TrackListener<AlbumContainer> listener, Integer id){
        Call<AlbumContainer> albumContainerCall = service.getArtistAlbums(id);
        albumContainerCall.enqueue(new Callback<AlbumContainer>() {
            @Override
            public void onResponse(Call<AlbumContainer> call, Response<AlbumContainer> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(Call<AlbumContainer> call, Throwable t) {
                listener.finish(null);
            }
        });
    }

    // Obtener lista de canciones de Genero en especifico
    public void getGenreTracks(final TrackListener<TrackContainer> listener, Integer id, Integer limit){
        Call<TrackContainer> trackContainerCall = service.getGenreTracks(id, limit);
        trackContainerCall.enqueue(new Callback<TrackContainer>() {
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


    // Buscar cancion
    public void getSearchTracks(final TrackListener<TrackContainer> listener){
        Call<TrackContainer> trackContainerCall = service.getSearchTracks("bote");
        trackContainerCall.enqueue(new Callback<TrackContainer>() {
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




}
