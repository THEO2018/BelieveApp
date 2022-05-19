package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by netset on 27/4/18.
 */

public class NewsListModel {


    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("news_url")
        @Expose
        public String newsUrl;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("updatedAt")
        @Expose
        public String updatedAt;

    }
}
