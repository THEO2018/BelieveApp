package com.netset.believeapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by netset on 9/1/18.
 */

public class BlogsModel implements Serializable {
    String blogTitle, blogHashTags, blogTiming,blogMembers,blogImage,blogId,status,userId,userImage;

    ArrayList<String> jsonArray;


    public BlogsModel(String blogTitle, String blogMembers, String blogImage, String blogId, ArrayList<String> jsonArray,String status) {
        this.blogTitle = blogTitle;
        this.blogMembers = blogMembers;
        this.blogImage = blogImage;
        this.blogId = blogId;
        this.jsonArray = jsonArray;
        this.status = status;
    }

    public BlogsModel(String userId, String userImage) {
        this.userId = userId;
        this.userImage = userImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public ArrayList<String> getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(ArrayList<String> jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String getBlogMembers() {
        return blogMembers;
    }

    public void setBlogMembers(String blogMembers) {
        this.blogMembers = blogMembers;
    }

    public String getBlogImage() {
        return blogImage;
    }

    public void setBlogImage(String blogImage) {
        this.blogImage = blogImage;
    }


    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogHashTags() {
        return blogHashTags;
    }

    public void setBlogHashTags(String blogHashTags) {
        this.blogHashTags = blogHashTags;
    }

    public String getBlogTiming() {
        return blogTiming;
    }

    public void setBlogTiming(String blogTiming) {
        this.blogTiming = blogTiming;
    }
}
