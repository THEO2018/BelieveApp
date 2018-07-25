package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 3/5/18.
 */

public class PollsModel {

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
        @SerializedName("poll_title")
        @Expose
        public String pollTitle;
        @SerializedName("question")
        @Expose
        public String question;
        @SerializedName("end_time")
        @Expose
        public String endTime;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("setting_multiple_choice")
        @Expose
        public Boolean settingMultipleChoice;
        @SerializedName("setting_hide_results_after_voting")
        @Expose
        public Boolean settingHideResultsAfterVoting;
        @SerializedName("setting_hide_results")
        @Expose
        public Boolean settingHideResults;
        @SerializedName("share_title")
        @Expose
        public Boolean shareTitle;
        @SerializedName("share_question")
        @Expose
        public Boolean shareQuestion;
        @SerializedName("share_answer")
        @Expose
        public Boolean shareAnswer;
        @SerializedName("share_results")
        @Expose
        public Boolean shareResults;
        @SerializedName("share_platform_facebook")
        @Expose
        public Boolean sharePlatformFacebook;
        @SerializedName("share_platform_whatsapp")
        @Expose
        public Boolean sharePlatformWhatsapp;
        @SerializedName("share_platform_twitter")
        @Expose
        public Boolean sharePlatformTwitter;
        @SerializedName("share_platform_text")
        @Expose
        public Boolean sharePlatformText;
        @SerializedName("poll_image")
        @Expose
        public String pollImage;
        @SerializedName("start_time")
        @Expose
        public Object startTime;
        @SerializedName("options_count")
        @Expose
        public Integer optionsCount;
        @SerializedName("__v")
        @Expose
        public Integer v;
        @SerializedName("time_ago")
        @Expose
        public String timeAgo;

    }



}
