package com.example.festfinder.service.response;

public class SingleEventResposne {

    private int status;
    private String message;

    private EventDetail event;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public EventDetail getEvent() {
        return event;
    }

    public static class EventDetail {
        private int event_id;
        private int organizer_id;
        private String organizer;
        private String title;
        private String date;
        private String time;
        private String location;
        private String description;
        private String event_type;
        private int registration_fee;
        private String email;
        private String phone;
        private String poster_url;
        private String permission_letter_url;
        private String status;

        // Getter and Setter for event_id
        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
        }

        // Getter and Setter for organizer_id
        public int getOrganizer_id() {
            return organizer_id;
        }

        public void setOrganizer_id(int organizer_id) {
            this.organizer_id = organizer_id;
        }

        // Getter and Setter for organizer
        public String getOrganizer() {
            return organizer;
        }

        public void setOrganizer(String organizer) {
            this.organizer = organizer;
        }

        // Getter and Setter for title
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        // Getter and Setter for date
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        // Getter and Setter for time
        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        // Getter and Setter for location
        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        // Getter and Setter for description
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        // Getter and Setter for event_type
        public String getEvent_type() {
            return event_type;
        }

        public void setEvent_type(String event_type) {
            this.event_type = event_type;
        }

        // Getter and Setter for registration_fee
        public int getRegistration_fee() {
            return registration_fee;
        }

        public void setRegistration_fee(int registration_fee) {
            this.registration_fee = registration_fee;
        }

        // Getter and Setter for email
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        // Getter and Setter for phone
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        // Getter and Setter for poster_url
        public String getPoster_url() {
            return poster_url;
        }

        public void setPoster_url(String poster_url) {
            this.poster_url = poster_url;
        }

        // Getter and Setter for permission_letter_url
        public String getPermission_letter_url() {
            return permission_letter_url;
        }

        public void setPermission_letter_url(String permission_letter_url) {
            this.permission_letter_url = permission_letter_url;
        }

        // Getter and Setter for status
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
