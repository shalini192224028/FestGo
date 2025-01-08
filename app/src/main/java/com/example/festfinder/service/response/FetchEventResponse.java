package com.example.festfinder.service.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchEventResponse {

    private String message;
    private List<EventDetails> events;

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Getter for events
    public List<EventDetails> getEvents() {
        return events;
    }

    // Static inner class for event details
    public static class EventDetails {
        private String poster_url;
        private String title;
        private String date;
        private String time;
        private String status;
        private int event_id;

        public int getEvent_id() {
            return event_id;
        }

        public String getPosterUrl() {
            return poster_url;
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getStatus() {
            return status;
        }
    }
}
