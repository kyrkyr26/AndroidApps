package com.example.kpizani.afinal;

import android.graphics.Bitmap;

/**
 * Created by kpizani on 5/8/2017.
 */

public class Game {

        private String title;
        private String rating;
        private String dir;
        private String prod;
        private String date;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getDir() {return dir;  }

        public void setDir(String dir) {
        this.dir = dir;
    }

        public String getProd() {
        return prod;
    }

        public void setProd(String prod) { this.prod = prod; }

        public String getDate() { return date;}

        public void setDate(String date) {this.date = date;}



}
