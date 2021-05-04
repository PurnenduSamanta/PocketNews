package com.purnendu.PocketNews;

public class NewsModel {
    private int id;
    private String title;
    private String description;
    private  String posterUrl;
    private  String newsUrl;
    private String date;

    public NewsModel(int id, String title, String description, String posterUrl, String newsUrl, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.newsUrl = newsUrl;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
