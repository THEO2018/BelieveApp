package com.netset.believeapp.Model;

import java.io.Serializable;

/**
 * Created by netset on 24/1/18.
 */

public class VolumeModel implements Serializable {
    boolean isSelected;
    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
