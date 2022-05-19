package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 4/5/18.
 */

public class RecentPlayListModel {


    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;

    public class Datum {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("updatedAt")
        @Expose
        public String updatedAt;
        @SerializedName("createdAt")
        @Expose
        public String createdAt;
        @SerializedName("song_title")
        @Expose
        public String songTitle;
        @SerializedName("album_id")
        @Expose
        public AlbumId albumId;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("duration")
        @Expose
        public String duration;
        @SerializedName("song_file")
        @Expose
        public String songFile;
        @SerializedName("__v")
        @Expose
        public Integer v;

    }

    public class AlbumId {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("album_title")
        @Expose
        public String albumTitle;
        @SerializedName("artist")
        @Expose
        public Artist artist;
        @SerializedName("album_image")
        @Expose
        public String albumImage;

    }

    public class Artist {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("artist_name")
        @Expose
        public String artistName;

    }
}
