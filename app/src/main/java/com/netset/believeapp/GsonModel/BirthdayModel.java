package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 1/5/18.
 */

public class BirthdayModel {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("sendData")
    @Expose
    public SendData sendData;

    public class SendData {

        @SerializedName("todayBirthdays")
        @Expose
        public List<TodayBirthday> todayBirthdays = null;
        @SerializedName("upcomingBirthdays")
        @Expose
        public List<UpcomingBirthday> upcomingBirthdays = null;
        @SerializedName("birthdaylists")
        @Expose
        public List<Birthdaylist> birthdaylists = null;
        @SerializedName("birthNews")
        @Expose
        public List<BirthNews> birthNews = null;

    }

    public class TodayBirthday {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("daysTillBirthday")
        @Expose
        public Integer daysTillBirthday;

    }

    public class UpcomingBirthday {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("daysTillBirthday")
        @Expose
        public Integer daysTillBirthday;

    }


    public class Birthdaylist {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("updatedAt")
        @Expose
        public String updatedAt;
        @SerializedName("createdAt")
        @Expose
        public String createdAt;
        @SerializedName("bday_list_title")
        @Expose
        public String bdayListTitle;
        @SerializedName("bday_list_url_online_site")
        @Expose
        public String bdayListUrlOnlineSite;
        @SerializedName("bday_list_cover_photo")
        @Expose
        public String bdayListCoverPhoto;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("__v")
        @Expose
        public Integer v;

    }

    public class BirthNews {

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
