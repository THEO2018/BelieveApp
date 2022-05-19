package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 1/5/18.
 */

public class MarriagesModel {


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

        @SerializedName("betrotheds")
        @Expose
        private List<Betrothed> betrotheds = null;
        @SerializedName("weddingLists")
        @Expose
        private List<WeddingList> weddingLists = null;
        @SerializedName("upcomingMarriages")
        @Expose
        private List<UpcomingMarriage> upcomingMarriages = null;
        @SerializedName("advice")
        @Expose
        private List<Advice> advice = null;

        public List<Betrothed> getBetrotheds() {
            return betrotheds;
        }

        public void setBetrotheds(List<Betrothed> betrotheds) {
            this.betrotheds = betrotheds;
        }

        public List<WeddingList> getWeddingLists() {
            return weddingLists;
        }

        public void setWeddingLists(List<WeddingList> weddingLists) {
            this.weddingLists = weddingLists;
        }

        public List<UpcomingMarriage> getUpcomingMarriages() {
            return upcomingMarriages;
        }

        public void setUpcomingMarriages(List<UpcomingMarriage> upcomingMarriages) {
            this.upcomingMarriages = upcomingMarriages;
        }

        public List<Advice> getAdvice() {
            return advice;
        }

        public void setAdvice(List<Advice> advice) {
            this.advice = advice;
        }

    }

    public class Advice {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("updatedAt")
        @Expose
        public String updatedAt;
        @SerializedName("createdAt")
        @Expose
        public String createdAt;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("blog_title")
        @Expose
        public String blogTitle;
        @SerializedName("category")
        @Expose
        public String category;
        @SerializedName("blog")
        @Expose
        public String blog;
        @SerializedName("blog_image")
        @Expose
        public String blogImage;
        @SerializedName("__v")
        @Expose
        public Integer v;
        @SerializedName("time_ago")
        @Expose
        public String timeAgo;

    }


    public class Betrothed {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("first_user_id")
        @Expose
        private FirstUserId firstUserId;
        @SerializedName("second_user_id")
        @Expose
        private SecondUserId secondUserId;
        @SerializedName("visibility_status")
        @Expose
        private String visibilityStatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("__v")
        @Expose
        private Integer v;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public FirstUserId getFirstUserId() {
            return firstUserId;
        }

        public void setFirstUserId(FirstUserId firstUserId) {
            this.firstUserId = firstUserId;
        }

        public SecondUserId getSecondUserId() {
            return secondUserId;
        }

        public void setSecondUserId(SecondUserId secondUserId) {
            this.secondUserId = secondUserId;
        }

        public String getVisibilityStatus() {
            return visibilityStatus;
        }

        public void setVisibilityStatus(String visibilityStatus) {
            this.visibilityStatus = visibilityStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }

    public class FirstUserId {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

    }

    public class SecondUserId {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

    }

    public class UpcomingMarriage {

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
        @SerializedName("event_category")
        @Expose
        public EventCategory eventCategory;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("venue")
        @Expose
        public String venue;
        @SerializedName("event_cover")
        @Expose
        public String eventCover;
        @SerializedName("venue_latitude")
        @Expose
        public String venueLatitude;
        @SerializedName("venue_longitude")
        @Expose
        public String venueLongitude;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("start_time")
        @Expose
        public String startTime;
        @SerializedName("end_time")
        @Expose
        public String endTime;
        @SerializedName("price_status")
        @Expose
        public String priceStatus;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("__v")
        @Expose
        public Integer v;
        @SerializedName("group_id")
        @Expose
        public List<GroupId> groupId = null;

    }


    public class GroupId {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("group_name")
        @Expose
        public String groupName;
        @SerializedName("users")
        @Expose
        public List<String> users = null;

    }


    public class EventCategory {

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
        @SerializedName("__v")
        @Expose
        public Integer v;

    }

    public class WeddingList {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("cover_photo")
        @Expose
        private String coverPhoto;
        @SerializedName("url_online_site")
        @Expose
        private String urlOnlineSite;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("__v")
        @Expose
        private Integer v;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoverPhoto() {
            return coverPhoto;
        }

        public void setCoverPhoto(String coverPhoto) {
            this.coverPhoto = coverPhoto;
        }

        public String getUrlOnlineSite() {
            return urlOnlineSite;
        }

        public void setUrlOnlineSite(String urlOnlineSite) {
            this.urlOnlineSite = urlOnlineSite;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }

}
