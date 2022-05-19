package com.netset.believeapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 23/4/18.
 */

public class SmallGroupsModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class User {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

    }


    public class Request {

        @SerializedName("request_status")
        @Expose
        private String requestStatus;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getRequestStatus() {
            return requestStatus;
        }

        public void setRequestStatus(String requestStatus) {
            this.requestStatus = requestStatus;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    public class Datum {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("small_group_name")
        @Expose
        private String smallGroupName;
        @SerializedName("small_group_image")
        @Expose
        private String smallGroupImage;
        @SerializedName("days")
        @Expose
        private String days;
        @SerializedName("venue")
        @Expose
        private String venue;
        @SerializedName("small_group_description")
        @Expose
        private String smallGroupDescription;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("venue_latitude")
        @Expose
        private String venueLatitude;
        @SerializedName("venue_longitude")
        @Expose
        private String venueLongitude;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("__v")
        @Expose
        private Integer v;
        @SerializedName("zip_code")
        @Expose
        private String zipCode;
        @SerializedName("requests")
        @Expose
        private List<Request> requests = null;
        @SerializedName("admin_users")
        @Expose
        private List<String> adminUsers = null;
        @SerializedName("users")
        @Expose
        private List<User> users = null;
        @SerializedName("total_members")
        @Expose
        private Integer totalMembers;
        @SerializedName("join_status")
        @Expose
        private String joinStatus;
        @SerializedName("miles_distance")
        @Expose
        private String milesDistance;

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

        public String getSmallGroupName() {
            return smallGroupName;
        }

        public void setSmallGroupName(String smallGroupName) {
            this.smallGroupName = smallGroupName;
        }

        public String getSmallGroupImage() {
            return smallGroupImage;
        }

        public void setSmallGroupImage(String smallGroupImage) {
            this.smallGroupImage = smallGroupImage;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getVenue() {
            return venue;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }

        public String getSmallGroupDescription() {
            return smallGroupDescription;
        }

        public void setSmallGroupDescription(String smallGroupDescription) {
            this.smallGroupDescription = smallGroupDescription;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getVenueLatitude() {
            return venueLatitude;
        }

        public void setVenueLatitude(String venueLatitude) {
            this.venueLatitude = venueLatitude;
        }

        public String getVenueLongitude() {
            return venueLongitude;
        }

        public void setVenueLongitude(String venueLongitude) {
            this.venueLongitude = venueLongitude;
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

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public List<Request> getRequests() {
            return requests;
        }

        public void setRequests(List<Request> requests) {
            this.requests = requests;
        }

        public List<String> getAdminUsers() {
            return adminUsers;
        }

        public void setAdminUsers(List<String> adminUsers) {
            this.adminUsers = adminUsers;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

        public Integer getTotalMembers() {
            return totalMembers;
        }

        public void setTotalMembers(Integer totalMembers) {
            this.totalMembers = totalMembers;
        }

        public String getJoinStatus() {
            return joinStatus;
        }

        public void setJoinStatus(String joinStatus) {
            this.joinStatus = joinStatus;
        }

        public String getMilesDistance() {
            return milesDistance;
        }

        public void setMilesDistance(String milesDistance) {
            this.milesDistance = milesDistance;
        }

    }

}

