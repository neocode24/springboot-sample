package com.javasampleapproach.spring.frontend.model;

import java.io.Serializable;

public class Book implements Serializable {

    private long id;

    private String title;

    private String author;

    private String description;

    private int published;

    protected Book() {
    }


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }
}
