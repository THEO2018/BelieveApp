package com.netset.believeapp.Model;

import java.io.Serializable;

/**
 * Created by netset on 31/1/18.
 */

public class PollPercentModel implements Serializable {
    String itemName;
    int itemPercentage;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPercentage() {
        return itemPercentage;
    }

    public void setItemPercentage(int itemPercentage) {
        this.itemPercentage = itemPercentage;
    }
}
