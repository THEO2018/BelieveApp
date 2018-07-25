package com.netset.believeapp.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 24/4/18.
 */

public class WallPostModel {
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



    public class Datum {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private UserId userId;
        @SerializedName("wall_post_media")
        @Expose
        private String wallPostMedia;
        @SerializedName("wall_post_status")
        @Expose
        private String wallPostStatus;
        @SerializedName("wall_post_media_type")
        @Expose
        private String wallPostMediaType;
        @SerializedName("totalComments")
        @Expose
        private Integer totalComments;
        @SerializedName("lastComment")
        @Expose
        private LastComment lastComment;
        @SerializedName("totalLikes")
        @Expose
        private Integer totalLikes;
        @SerializedName("time_ago")
        @Expose
        private String timeAgo;
        @SerializedName("comment_time_ago")
        @Expose
        private String commentTimeAgo;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public UserId getUserId() {
            return userId;
        }

        public void setUserId(UserId userId) {
            this.userId = userId;
        }

        public String getWallPostMedia() {
            return wallPostMedia;
        }

        public void setWallPostMedia(String wallPostMedia) {
            this.wallPostMedia = wallPostMedia;
        }

        public String getWallPostStatus() {
            return wallPostStatus;
        }

        public void setWallPostStatus(String wallPostStatus) {
            this.wallPostStatus = wallPostStatus;
        }

        public String getWallPostMediaType() {
            return wallPostMediaType;
        }

        public void setWallPostMediaType(String wallPostMediaType) {
            this.wallPostMediaType = wallPostMediaType;
        }

        public Integer getTotalComments() {
            return totalComments;
        }

        public void setTotalComments(Integer totalComments) {
            this.totalComments = totalComments;
        }

        public LastComment getLastComment() {
            return lastComment;
        }

        public void setLastComment(LastComment lastComment) {
            this.lastComment = lastComment;
        }

        public Integer getTotalLikes() {
            return totalLikes;
        }

        public void setTotalLikes(Integer totalLikes) {
            this.totalLikes = totalLikes;
        }

        public String getTimeAgo() {
            return timeAgo;
        }

        public void setTimeAgo(String timeAgo) {
            this.timeAgo = timeAgo;
        }

        public String getCommentTimeAgo() {
            return commentTimeAgo;
        }

        public void setCommentTimeAgo(String commentTimeAgo) {
            this.commentTimeAgo = commentTimeAgo;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

    }

    public class LastComment {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("wall_post_id")
        @Expose
        private String wallPostId;
        @SerializedName("comment_msg")
        @Expose
        private String commentMsg;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("wall_post_comment_img")
        @Expose
        private String wallPostCommentImg;
        @SerializedName("media_status")
        @Expose
        private Boolean mediaStatus;
        @SerializedName("user_id")
        @Expose
        private UserId_ userId;
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

        public String getWallPostId() {
            return wallPostId;
        }

        public void setWallPostId(String wallPostId) {
            this.wallPostId = wallPostId;
        }

        public String getCommentMsg() {
            return commentMsg;
        }

        public void setCommentMsg(String commentMsg) {
            this.commentMsg = commentMsg;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getWallPostCommentImg() {
            return wallPostCommentImg;
        }

        public void setWallPostCommentImg(String wallPostCommentImg) {
            this.wallPostCommentImg = wallPostCommentImg;
        }

        public Boolean getMediaStatus() {
            return mediaStatus;
        }

        public void setMediaStatus(Boolean mediaStatus) {
            this.mediaStatus = mediaStatus;
        }

        public UserId_ getUserId() {
            return userId;
        }

        public void setUserId(UserId_ userId) {
            this.userId = userId;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }

    public class UserId {

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

    public class UserId_ {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("first_name")
        @Expose
        private String firstName;

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

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

    }


}
