package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 2/5/18.
 */

public class DonationModel {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {

        @SerializedName("giving")
        @Expose
        private Giving giving;
        @SerializedName("teaching")
        @Expose
        private List<Teaching> teaching = null;

        public Giving getGiving() {
            return giving;
        }

        public void setGiving(Giving giving) {
            this.giving = giving;
        }

        public List<Teaching> getTeaching() {
            return teaching;
        }

        public void setTeaching(List<Teaching> teaching) {
            this.teaching = teaching;
        }

    }

    public class Giving {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("giving_url")
        @Expose
        private String givingUrl;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGivingUrl() {
            return givingUrl;
        }

        public void setGivingUrl(String givingUrl) {
            this.givingUrl = givingUrl;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }


    public class Teaching {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("blog_title")
        @Expose
        private String blogTitle;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("blog")
        @Expose
        private String blog;
        @SerializedName("blog_image")
        @Expose
        private String blogImage;
        @SerializedName("__v")
        @Expose
        private Integer v;
        @SerializedName("time_ago")
        @Expose
        private String timeAgo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBlogTitle() {
            return blogTitle;
        }

        public void setBlogTitle(String blogTitle) {
            this.blogTitle = blogTitle;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getBlog() {
            return blog;
        }

        public void setBlog(String blog) {
            this.blog = blog;
        }

        public String getBlogImage() {
            return blogImage;
        }

        public void setBlogImage(String blogImage) {
            this.blogImage = blogImage;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

        public String getTimeAgo() {
            return timeAgo;
        }

        public void setTimeAgo(String timeAgo) {
            this.timeAgo = timeAgo;
        }

    }
}
