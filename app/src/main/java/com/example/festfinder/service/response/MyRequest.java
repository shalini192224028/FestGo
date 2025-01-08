package com.example.festfinder.service.response;
import java.io.File;
public class MyRequest {

    // Signup Request
    public static class SignupRequest {
        private String username;
        private String emailid;
        private String password;
        private String user_type;

        public SignupRequest(String name, String email, String password,String user_type) {
            this.username = name;
            this.emailid = email;
            this.password = password;
            this.user_type = user_type;
        }
    }

    // loginin Request
    public static class LoginRequest {
        private String emailid;
        private String password;

        public LoginRequest(String email, String password) {
            this.emailid = email;
            this.password = password;
       }
   }

    public class EventAddRequest {
        private String organizer;
        private String title;
        private String date;
        private String time;
        private String location;
        private String description;
        private String event_type;
        private String registrationFee;
        private String email;
        private String phone;
        private File poster; // Poster image file
        private File permissionLetter; // Permission letter file

        public EventAddRequest(String organizer, String title, String date, String time, String location, String description, String event_type,
                               String registrationFee, String email, String phone, File poster, File permissionLetter) {
            this.organizer = organizer;
            this.title = title;
            this.date = date;
            this.time = time;
            this.location = location;
            this.description = description;
            this.event_type = event_type;
            this.registrationFee = registrationFee;
            this.email = email;
            this.phone = phone;
            this.poster = poster;
            this.permissionLetter = permissionLetter;
        }

        // Getters and Setters
    }
    public class EventActionRequest {
        private String event_id;
        private String action;

        public EventActionRequest(String event_id, String action) {
            this.event_id = event_id;
            this.action = action;
        }

        public String getEvent_id() { return event_id; }
        public void setEvent_id(String event_id) { this.event_id = event_id; }
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
    }

    public static class ApproveRejectRequest {
        private int event_id;
        private String action;

        public ApproveRejectRequest(int event_id, String action) {
            this.event_id = event_id;
            this.action = action;
        }

        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }

    public static class RegistrationRequest {
        private String name;
        private String email_id;
        private int event_id;
        private int user_id;
        private int organizer_id;
        private int no_of_member;

        public RegistrationRequest(String name,int user_id, String email_id, int event_id, int organizer_id, int no_of_member, String transaction_id, double payment_amt) {
            this.name = name;
            this.user_id = user_id;
            this.email_id = email_id;
            this.event_id = event_id;
            this.organizer_id = organizer_id;
            this.no_of_member = no_of_member;
            this.transaction_id = transaction_id;
            this.payment_amt = payment_amt;
        }

        private String transaction_id;
        private double payment_amt;

        // Constructor, getters, and setters
    }


}
