package com.example.festfinder.service.response;

import androidx.annotation.NonNull;

public interface Response {

    // Response class for signup
    class SignUpResponse {
        private String message;

        public String getMessage() {
            return message;
        }
    }

    // Response class for login
    class LoginResponse {
        private String message;

        public String getMessage() {
            return message;
        }

        public class User {
            private String username;
            private int userId;

            public int getUserId() {
                return userId;
            }

            private String emailid;

            public String getEmailid() {
                return emailid;
            }

            public String getUser_type() {
                return user_type;
            }

            public String getUsername() {
                return username;
            }

            private String user_type;

        }

        User user;



        public User getUser() {
            return user;
        }
    }
    class ErrorResponse{

       private String message;
       private int status;

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }

    public class EventAddResponse {
        private int status;
        private String message;
        private int eventId;

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public int getEventId() {
            return eventId;
        }
    }

}
