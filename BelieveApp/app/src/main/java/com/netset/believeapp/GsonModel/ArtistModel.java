package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 27/6/18.
 */

public class ArtistModel {

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
        @SerializedName("artist_name")
        @Expose
        public String artistName;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("artist_image")
        @Expose
        public String artistImage;
        @SerializedName("__v")
        @Expose
        public Integer v;

    }

}
