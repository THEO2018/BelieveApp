package com.netset.believeapp.Model;

/**
 * Created by netset on 13/4/18.
 */

public class PhotosModel {

    String user_id,photo_id,photo,group_id,updated,created;

    public PhotosModel(String user_id, String photo_id, String photo, String group_id) {
        this.user_id = user_id;
        this.photo_id = photo_id;
        this.photo = photo;
        this.group_id = group_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
