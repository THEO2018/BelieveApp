package com.netset.believeapp.Model;

import java.io.Serializable;

/**
 * Created by netset on 31/1/18.
 */

public class PollOptionsModel implements Serializable {
    String optionName;
    boolean isSelected;

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
