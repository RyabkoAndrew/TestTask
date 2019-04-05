package com.example.testtask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumbnails {


        @SerializedName("medium")
        @Expose
        private Medium medium;


    public Thumbnails(Medium medium) {
        this.medium = medium;
    }



        public Medium getMedium() {
            return medium;
        }

        public void setMedium(Medium medium) {
            this.medium = medium;
        }

    }

