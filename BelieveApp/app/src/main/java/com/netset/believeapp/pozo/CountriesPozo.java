package com.netset.believeapp.pozo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by netset on 2/2/18.
 */

public class CountriesPozo implements Serializable {

    @SerializedName("countries")
    @Expose
    public List<Country> countries = null;

    public class Country implements Serializable {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("sortname")
        @Expose
        public String sortname;
        @SerializedName("name")
        @Expose
        public String name;

    }
}
