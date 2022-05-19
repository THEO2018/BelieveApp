package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by netset on 16/5/18.
 */

public class ServiceTimeModel {

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

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("day_from")
        @Expose
        private String dayFrom;
        @SerializedName("day_to")
        @Expose
        private String dayTo;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("venue")
        @Expose
        private String venue;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("facebook_link")
        @Expose
        private String facebookLink;
        @SerializedName("twitter_link")
        @Expose
        private String twitterLink;
        @SerializedName("instagram_link")
        @Expose
        private String instagramLink;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDayFrom() {
            return dayFrom;
        }

        public void setDayFrom(String dayFrom) {
            this.dayFrom = dayFrom;
        }

        public String getDayTo() {
            return dayTo;
        }

        public void setDayTo(String dayTo) {
            this.dayTo = dayTo;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getVenue() {
            return venue;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getFacebookLink() {
            return facebookLink;
        }

        public void setFacebookLink(String facebookLink) {
            this.facebookLink = facebookLink;
        }

        public String getTwitterLink() {
            return twitterLink;
        }

        public void setTwitterLink(String twitterLink) {
            this.twitterLink = twitterLink;
        }

        public String getInstagramLink() {
            return instagramLink;
        }

        public void setInstagramLink(String instagramLink) {
            this.instagramLink = instagramLink;
        }

    }
}
