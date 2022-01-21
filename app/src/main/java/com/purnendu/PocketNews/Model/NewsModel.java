package com.purnendu.PocketNews.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsModel {

    @SerializedName("articles")
    @Expose
    private ArrayList<articles> data;


    public NewsModel(ArrayList<articles> data) {
        this.data = data;
    }

    public ArrayList<articles> getData() {
        return data;
    }

    public void setData(ArrayList<articles> data) {
        this.data = data;
    }

    public static class articles
    {
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("url")
        @Expose
        private String newsUrl;
        @SerializedName("urlToImage")
        @Expose
        private String imageUrl;
        @SerializedName("publishedAt")
        @Expose
        private String date;

        public articles(String title, String description,String imageUrl,String newsUrl,String date) {
            this.title = title;
            this.description = description;
            this.newsUrl=newsUrl;
            this.imageUrl=imageUrl;
            this.date=date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getNewsUrl() {
            return newsUrl;
        }

        public void setNewsUrl(String newsUrl) {
            this.newsUrl = newsUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
