package com.example.dh.tpmusicagrupo3.Model.POJO;

import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.AlbumContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.ArtistContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.PlaylistContainer;
import com.example.dh.tpmusicagrupo3.Model.POJO.Containers.TrackContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DH on 15/6/2018.
 */

public class Chart {
    private TrackContainer tracks;
    private AlbumContainer albums;
    private ArtistContainer artists;
    private PlaylistContainer playlists;

    public Chart(){
        this.tracks = new TrackContainer();
        this.albums = new AlbumContainer();
        this.artists = new ArtistContainer();
        this.playlists = new PlaylistContainer();
    }

    public Chart(TrackContainer tracks, AlbumContainer albums, ArtistContainer artists, PlaylistContainer playlists) {
        this.tracks = tracks;
        this.albums = albums;
        this.artists = artists;
        this.playlists = playlists;
    }

    public TrackContainer getTracks() {
        return tracks;
    }

    public void setTracks(TrackContainer tracks) {
        this.tracks = tracks;
    }

    public AlbumContainer getAlbums() {
        return albums;
    }

    public void setAlbums(AlbumContainer albums) {
        this.albums = albums;
    }

    public ArtistContainer getArtists() {
        return artists;
    }

    public void setArtists(ArtistContainer artists) {
        this.artists = artists;
    }

    public PlaylistContainer getPlaylists() {
        return playlists;
    }

    public void setPlaylists(PlaylistContainer playlists) {
        this.playlists = playlists;
    }
}
