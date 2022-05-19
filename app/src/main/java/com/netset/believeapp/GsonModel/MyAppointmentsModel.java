package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 28/4/18.
 */

public class MyAppointmentsModel {

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

        @SerializedName("todayAppointments")
        @Expose
        private List<TodayAppointment> todayAppointments = null;
        @SerializedName("upcomingAppointments")
        @Expose
        private List<UpcomingAppointment> upcomingAppointments = null;

        public List<TodayAppointment> getTodayAppointments() {
            return todayAppointments;
        }

        public void setTodayAppointments(List<TodayAppointment> todayAppointments) {
            this.todayAppointments = todayAppointments;
        }

        public List<UpcomingAppointment> getUpcomingAppointments() {
            return upcomingAppointments;
        }

        public void setUpcomingAppointments(List<UpcomingAppointment> upcomingAppointments) {
            this.upcomingAppointments = upcomingAppointments;
        }

    }

    public class TodayAppointment {

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
        @SerializedName("date_of_appointment")
        @Expose
        private String dateOfAppointment;
        @SerializedName("day_of_appointment")
        @Expose
        private String dayOfAppointment;
        @SerializedName("time_from")
        @Expose
        private String timeFrom;
        @SerializedName("time_to")
        @Expose
        private String timeTo;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("user_id")
        @Expose
        private String userId;
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

        public String getDateOfAppointment() {
            return dateOfAppointment;
        }

        public void setDateOfAppointment(String dateOfAppointment) {
            this.dateOfAppointment = dateOfAppointment;
        }

        public String getDayOfAppointment() {
            return dayOfAppointment;
        }

        public void setDayOfAppointment(String dayOfAppointment) {
            this.dayOfAppointment = dayOfAppointment;
        }

        public String getTimeFrom() {
            return timeFrom;
        }

        public void setTimeFrom(String timeFrom) {
            this.timeFrom = timeFrom;
        }

        public String getTimeTo() {
            return timeTo;
        }

        public void setTimeTo(String timeTo) {
            this.timeTo = timeTo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }


    public class UpcomingAppointment {

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
        @SerializedName("date_of_appointment")
        @Expose
        private String dateOfAppointment;
        @SerializedName("day_of_appointment")
        @Expose
        private String dayOfAppointment;
        @SerializedName("time_from")
        @Expose
        private String timeFrom;
        @SerializedName("time_to")
        @Expose
        private String timeTo;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("user_id")
        @Expose
        private String userId;
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

        public String getDateOfAppointment() {
            return dateOfAppointment;
        }

        public void setDateOfAppointment(String dateOfAppointment) {
            this.dateOfAppointment = dateOfAppointment;
        }

        public String getDayOfAppointment() {
            return dayOfAppointment;
        }

        public void setDayOfAppointment(String dayOfAppointment) {
            this.dayOfAppointment = dayOfAppointment;
        }

        public String getTimeFrom() {
            return timeFrom;
        }

        public void setTimeFrom(String timeFrom) {
            this.timeFrom = timeFrom;
        }

        public String getTimeTo() {
            return timeTo;
        }

        public void setTimeTo(String timeTo) {
            this.timeTo = timeTo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }
}
