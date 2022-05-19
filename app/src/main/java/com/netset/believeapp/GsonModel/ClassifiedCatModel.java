package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 5/5/18.
 */

public class ClassifiedCatModel {

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
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("__v")
        @Expose
        public Integer v;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("disableStatus")
        @Expose
        public String disableStatus;

    }

}
