package com.example.festfinder.service.response;
import java.util.List;
public class MyRequestResponse {

    private String message;
    private List<EventDetails> events;

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Getter for data
    public List<EventDetails> getData() {
        return events;
    }

    // Static inner class for event details
    public static class EventDetails {
        private String organizer;
        private String title;
        private String status;
        private int event_id;

        public int getEvent_id() {
            return event_id;
        }

        private String description;

        // Getter for name
        public String getOrganizer() {
            return organizer;
        }

        // Getter for title
        public String getTitle() {
            return title;
        }

        // Getter for description
        public String getDescription() {
            return description;
        }
        // Getter for description
        public String getStatus() {
            return status;
        }
    }
}


