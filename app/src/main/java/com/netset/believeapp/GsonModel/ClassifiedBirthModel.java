package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 7/5/18.
 */

public class ClassifiedBirthModel {

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
        @SerializedName("classified_title")
        @Expose
        public String classifiedTitle;
        @SerializedName("category")
        @Expose
        public String category;
        @SerializedName("classified")
        @Expose
        public String classified;
        @SerializedName("classified_image")
        @Expose
        public String classifiedImage;
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
        @SerializedName("time_ago")
        @Expose
        public String timeAgo;

    }
}
