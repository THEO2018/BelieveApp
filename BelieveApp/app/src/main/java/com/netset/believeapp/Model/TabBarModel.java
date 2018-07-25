package com.netset.believeapp.Model;

import java.io.Serializable;

/**
 * Created by netset on 18/1/18.
 */

public class TabBarModel implements Serializable {
    String catName;
    boolean isSelected;
    String id;

    public TabBarModel(){

    }

    public TabBarModel(String id,String catName, boolean isSelected) {
        this.catName = catName;
        this.isSelected = isSelected;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
