package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 26/6/18.
 */

public class MediaAlbumModel {

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
        @SerializedName("gallary_title")
        @Expose
        public String gallaryTitle;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("__v")
        @Expose
        public Integer v;
        @SerializedName("category")
        @Expose
        public String category;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("media")
        @Expose
        public List<Medium> media = null;

    }


    public class Medium {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("updatedAt")
        @Expose
        public String updatedAt;
        @SerializedName("createdAt")
        @Expose
        public String createdAt;
        @SerializedName("gallary_id")
        @Expose
        public String gallaryId;
        @SerializedName("media_type")
        @Expose
        public String mediaType;
        @SerializedName("thumbnail")
        @Expose
        public String thumbnail;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("media_file")
        @Expose
        public String mediaFile;
        @SerializedName("__v")
        @Expose
        public Integer v;

    }



}
