package com.netset.believeapp.Model;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by netset on 5/4/18.
 */

public class BaseModel implements Serializable {

    public static BaseModel mInstance;
    Context mContext;
    String access_token,user_id,user_fname,user_lname,profile_status,login_type;
    boolean isLogin;

    public BaseModel(Context context){
        this.mContext = context;
    }

    public static BaseModel getInstance() {
        return mInstance;
    }
    public BaseModel(String access_token, String user_id, String user_fname, String user_lname, String profile_status, String login_type,boolean islogin) {
        this.access_token = access_token;
        this.user_id = user_id;
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.profile_status = profile_status;
        this.login_type = login_type;
        this.isLogin = islogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public String getProfile_status() {
        return profile_status;
    }

    public void setProfile_status(String profile_status) {
        this.profile_status = profile_status;
    }
}
