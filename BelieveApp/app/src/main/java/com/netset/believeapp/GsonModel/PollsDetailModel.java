package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 3/5/18.
 */

public class PollsDetailModel {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;


    public class Option {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("updatedAt")
        @Expose
        public String updatedAt;
        @SerializedName("createdAt")
        @Expose
        public String createdAt;
        @SerializedName("poll_id")
        @Expose
        public String pollId;
        @SerializedName("option_no")
        @Expose
        public String optionNo;
        @SerializedName("option")
        @Expose
        public String option;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("__v")
        @Expose
        public Integer v;
        @SerializedName("votesCount")
        @Expose
        public Integer votesCount;
        @SerializedName("myVotePoll")
        @Expose
        public Boolean myVotePoll;
        @SerializedName("percent")
        @Expose
        public Integer percent;

        @SerializedName("isSelected")
        @Expose
        public boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public class UserId {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("first_name")
        @Expose
        public String firstName;

    }

    public class Data {

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
        public String startTime;
        @SerializedName("options_count")
        @Expose
        public Integer optionsCount;
        @SerializedName("__v")
        @Expose
        public Integer v;
        @SerializedName("time_ago")
        @Expose
        public String timeAgo;
        @SerializedName("hoursForPoll")
        @Expose
        public Integer hoursForPoll;
        @SerializedName("options")
        @Expose
        public List<Option> options = null;
        @SerializedName("comments")
        @Expose
        public List<Comment> comments = null;

    }

    public class Comment {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("updatedAt")
        @Expose
        public String updatedAt;
        @SerializedName("createdAt")
        @Expose
        public String createdAt;
        @SerializedName("poll_id")
        @Expose
        public String pollId;
        @SerializedName("comment_msg")
        @Expose
        public String commentMsg;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("user_id")
        @Expose
        public UserId userId;
        @SerializedName("__v")
        @Expose
        public Integer v;

    }
}
