package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 4/5/18.
 */

public class AlbumModel {

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
        @SerializedName("album_title")
        @Expose
        public String albumTitle;
        @SerializedName("album_image")
        @Expose
        public String albumImage;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("createdAt")
        @Expose
        public String createdAt;
        @SerializedName("updatedAt")
        @Expose
        public String updatedAt;
        @SerializedName("__v")
        @Expose
        public Integer v;

    }
}
