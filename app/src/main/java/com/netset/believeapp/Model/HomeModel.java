package com.netset.believeapp.Model;

import java.io.Serializable;

/**
 * Created by netset on 9/1/18.
 */

public class HomeModel implements Serializable {

    String title;
    int menuIcon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(int menuIcon) {
        this.menuIcon = menuIcon;
    }
}
