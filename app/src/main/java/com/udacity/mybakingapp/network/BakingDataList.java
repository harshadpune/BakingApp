package com.udacity.mybakingapp.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HARSHAD on 07/07/2018.
 */

public class BakingDataList implements Serializable{

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("ingredients")
    public List<Ingredients> ingredients = new ArrayList();

    @SerializedName("steps")
    public List<Steps> steps = new ArrayList();


    public class Ingredients implements Serializable{
            @SerializedName("quantity")
            public String quantity;

            @SerializedName("measure")
            public String measure;

            @SerializedName("ingredient")
            public String ingredient;

    }

    public class Steps implements Serializable {
        @SerializedName("id")
        public String id;

        @SerializedName("shortDescription")
        public String shortDescription;

        @SerializedName("description")
        public String description;

        @SerializedName("videoURL")
        public String videoURL;

        @SerializedName("thumbnailURL")
        public String thumbnailURL;
    }
}
