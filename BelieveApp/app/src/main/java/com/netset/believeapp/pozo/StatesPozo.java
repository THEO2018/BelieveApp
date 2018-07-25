package com.netset.believeapp.pozo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by netset on 2/2/18.
 */

public class StatesPozo implements Serializable {
    @SerializedName("states")
    @Expose
    public List<State> states = null;

    public class State implements Serializable {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("country_id")
        @Expose
        public String countryId;

    }
}
