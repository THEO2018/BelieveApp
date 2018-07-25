package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 7/5/18.
 */

public class PrayerModel {

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
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("category")
        @Expose
        public Category category;
        @SerializedName("post_as_anonymous")
        @Expose
        public Boolean postAsAnonymous;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("answered")
        @Expose
        public Boolean answered;
        @SerializedName("__v")
        @Expose
        public Integer v;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("created_date")
        @Expose
        public String createdDate;
        @SerializedName("created_time")
        @Expose
        public String createdTime;
        @SerializedName("prayesCount")
        @Expose
        public Integer prayesCount;

    }
    public class Category {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;

    }
}
