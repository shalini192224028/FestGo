package com.example.festfinder.User;

public class Event {
    private String title;
    private String dateTime;
    private int imageResource;

    public Event(String title, String dateTime, int imageResource) {
        this.title = title;
        this.dateTime = dateTime;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getImageResource() {
        return imageResource;
    }
}

