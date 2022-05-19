package com.netset.believeapp.Model;

/**
 * Created by netset on 12/4/18.
 */

public class PostsModel {

    String id,name,title,image,time,post_id,post_message,post_image,post_thumb,comment_count,
            like_count,other_name,other_id,other_image,other_comment,media_status,like_status,commentid,commentimg,isimagestatus;

    public PostsModel(String id, String name, String title, String image, String time, String postid,String post_message, String post_image, String comment_count, String like_count, String other_name, String other_id, String other_image, String other_comment,String mediastatus,String postthumb,String likestatus,String commentimg,String isimagestatus) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.image = image;
        this.time = time;
        this.post_id = postid;
        this.post_message = post_message;
        this.post_image = post_image;
        this.comment_count = comment_count;
        this.like_count = like_count;
        this.other_name = other_name;
        this.other_id = other_id;
        this.other_image = other_image;
        this.other_comment = other_comment;
        this.media_status = mediastatus;
        this.post_thumb = postthumb;
        this.like_status = likestatus;
        this.commentimg = commentimg;
        this.isimagestatus = isimagestatus;

    }

    public PostsModel(String time, String other_name, String other_id, String other_image, String other_comment,String commentid,String commentimg,String isimagestatus) {
        this.time = time;
        this.commentid = commentid;
        this.other_name = other_name;
        this.other_id = other_id;
        this.other_image = other_image;
        this.other_comment = other_comment;
        this.commentimg = commentimg;
        this.isimagestatus = isimagestatus;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getCommentimg() {
        return commentimg;
    }

    public void setCommentimg(String commentimg) {
        this.commentimg = commentimg;
    }

    public String getIsimagestatus() {
        return isimagestatus;
    }

    public void setIsimagestatus(String isimagestatus) {
        this.isimagestatus = isimagestatus;
    }

    public String getLike_status() {
        return like_status;
    }

    public void setLike_status(String like_status) {
        this.like_status = like_status;
    }

    public String getPost_thumb() {
        return post_thumb;
    }

    public void setPost_thumb(String post_thumb) {
        this.post_thumb = post_thumb;
    }

    public String getMedia_status() {
        return media_status;
    }

    public void setMedia_status(String media_status) {
        this.media_status = media_status;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPost_message() {
        return post_message;
    }

    public void setPost_message(String post_message) {
        this.post_message = post_message;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getOther_id() {
        return other_id;
    }

    public void setOther_id(String other_id) {
        this.other_id = other_id;
    }

    public String getOther_image() {
        return other_image;
    }

    public void setOther_image(String other_image) {
        this.other_image = other_image;
    }

    public String getOther_comment() {
        return other_comment;
    }

    public void setOther_comment(String other_comment) {
        this.other_comment = other_comment;
    }
}
