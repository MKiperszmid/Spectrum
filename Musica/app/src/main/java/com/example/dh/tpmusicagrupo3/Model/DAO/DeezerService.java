package com.example.dh.tpmusicagrupo3.Model.DAO;

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
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by DH on 15/6/2018.
 */

public interface DeezerService {

    // Lista Top Songs, ¿Creo que no se usa en la app?
    @GET("chart")
    Call<Chart> getChart();

    // Lista Popular Argentina
    @GET("playlist/{id}/tracks")
    Call<TrackContainer> getTracksPlaylist(@Path("id") String id, @Query("index") Integer index);

    // Lista de Agregados recientemente
    @GET("radio/{id}/tracks")
    Call<TrackContainer> getTracksRadio(@Path("id") String id);

    // Obtener una canción en especifico
    @GET("track/{id}")
    Call<Track> getTrack(@Path("id") String id);

    // Lista de Artistas populares
    @GET("chart/0/artists")
    Call<ArtistContainer> getArtistsChart();

    // Lista de de TOP 10
    @GET("chart/0/tracks")
    Call<TrackContainer> getTracksChart();

    // TOP 10 Playlist
    @GET("chart/0/playlists")
    Call<PlaylistContainer> getPlaylistsChart();

    // Lista de generos
    @GET("genre")
    Call<GenreContainer> getGenresList();

    // Artista
    @GET("artist/{id}")
    Call<Artist> getArtist(@Path("id") Integer id);

    // Artista Canciones
    @GET("artist/{id}/top")
    Call<TrackContainer> getArtistTracks(@Path("id") Integer id, @Query("limit") Integer limit);

    // Artista Albumes
    @GET("artist/{id}/albums")
    Call<AlbumContainer> getArtistAlbums(@Path("id") Integer id);

    // Genero Canciones
    @GET("chart/{id}/tracks")
    Call<TrackContainer> getGenreTracks(@Path("id") Integer id, @Query("limit") Integer limit);

    // Buscar Canciones
    @GET("search/track")
    Call<TrackContainer> getSearchTracks(@Query("q") String q);


}
