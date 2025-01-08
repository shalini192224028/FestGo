package com.example.festfinder.service.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AdminRequestResponse {

    private String message;
    private List<EventDetail> events;

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Setter for message
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter for events
    public List<EventDetail> getEvents() {
        return events;
    }

    // Setter for events
    public void setEvents(List<EventDetail> events) {
        this.events = events;
    }

    // Static inner class for event details
    public static class EventDetail {
        private int organizerId;
        private String organizer;
        private String title;
        private String description;
        private String date;
        private String time;
        private String location;

        @SerializedName("event_type")
        private String eventType;

        private double registrationFee;
        private String email;
        private String phone;

        @SerializedName("poster_url")
        private String posterUrl;

        @SerializedName("permission_letter_url")
        private String permissionLetterUrl;

        // Getter and setter for organizerId
        public int getOrganizerId() {
            return organizerId;
        }

        public void setOrganizerId(int organizerId) {
            this.organizerId = organizerId;
        }

        // Getter and setter for organizer
        public String getOrganizer() {
            return organizer;
        }

        public void setOrganizer(String organizer) {
            this.organizer = organizer;
        }

        // Getter and setter for title
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        // Getter and setter for description
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        // Getter and setter for date
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        // Getter and setter for time
        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        // Getter and setter for location
        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        // Getter and setter for eventType
        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        // Getter and setter for registrationFee
        public double getRegistrationFee() {
            return registrationFee;
        }

        public void setRegistrationFee(double registrationFee) {
            this.registrationFee = registrationFee;
        }

        // Getter and setter for email
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        // Getter and setter for phone
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        // Getter and setter for posterUrl
        public String getPosterUrl() {
            return posterUrl;
        }

        public void setPosterUrl(String posterUrl) {
            this.posterUrl = posterUrl;
        }

        // Getter and setter for permissionLetterUrl
        public String getPermissionLetterUrl() {
            return permissionLetterUrl;
        }

        public void setPermissionLetterUrl(String permissionLetterUrl) {
            this.permissionLetterUrl = permissionLetterUrl;
        }
    }
}
