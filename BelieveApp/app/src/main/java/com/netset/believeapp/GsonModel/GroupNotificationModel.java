package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 16/5/18.
 */

public class GroupNotificationModel {

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

        @SerializedName("notifications")
        @Expose
        public List<Notification> notifications = null;
        @SerializedName("notification_settings")
        @Expose
        public List<NotificationSetting> notificationSettings = null;
        @SerializedName("sound_status")
        @Expose
        public String soundStatus;

    }


    public class NotificationSetting {

        @SerializedName("group_id")
        @Expose
        public String groupId;
        @SerializedName("group_name")
        @Expose
        public String groupName;
        @SerializedName("group_image")
        @Expose
        public String groupImage;
        @SerializedName("status")
        @Expose
        public String status;

    }



    public class Notification {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("updatedAt")
        @Expose
        public String updatedAt;
        @SerializedName("createdAt")
        @Expose
        public String createdAt;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("author_id")
        @Expose
        public AuthorId authorId;
        @SerializedName("notification_type")
        @Expose
        public String notificationType;
        @SerializedName("post_type")
        @Expose
        public String postType;
        @SerializedName("group_id")
        @Expose
        public GroupId groupId;
        @SerializedName("post_id")
        @Expose
        public PostId postId;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("__v")
        @Expose
        public Integer v;
        @SerializedName("time_ago")
        @Expose
        public String timeAgo;

    }

    public class AuthorId {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;

    }


    public class GroupId {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("group_name")
        @Expose
        public String groupName;

    }


    public class PostId {

        @SerializedName("_id")
        @Expose
        public String id;
        @SerializedName("group_post_status")
        @Expose
        public String groupPostStatus;

    }
}
