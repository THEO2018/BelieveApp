package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 7/5/18.
 */

public class JobsModel {

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
        @SerializedName("classified_title")
        @Expose
        public String classifiedTitle;
        @SerializedName("category")
        @Expose
        public String category;

        @SerializedName("venue")
        @Expose
        public String venue;
        @SerializedName("venue_latitude")
        @Expose
        public String venue_latitude;
        @SerializedName("venue_longitude")
        @Expose
        public String venue_longitude;
        @SerializedName("classified")
        @Expose
        public String classified;
        @SerializedName("classified_image")
        @Expose
        public String classifiedImage;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("__v")
        @Expose
        public Integer v;
        @SerializedName("time_ago")
        @Expose
        public String timeAgo;

    }

    //////////////// detail model //////////////////////

}
