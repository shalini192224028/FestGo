package com.example.festfinder.service.response;

public class RegistrationResponse {
        private String message;           // Message from the server (success or error)
        private int userId;               // ID of the registered user
        private String username;          // Username
        private String emailId;           // Email ID
        private String name;              // Name of the participant
        private String eventId;           // Event ID
        private String organizerId;       // Organizer ID
        private int noOfMembers;          // Number of members
        private String transactionId;     // Transaction ID
        private double paymentAmt;        // Payment amount

        // Getter and Setter methods for all fields
        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public int getUserId() {
                return userId;
        }

        public void setUserId(int userId) {
                this.userId = userId;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getEmailId() {
                return emailId;
        }

        public void setEmailId(String emailId) {
                this.emailId = emailId;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
        public String getEventId() {
                return eventId;
        }

        public void setEventId(String eventId) {
                this.eventId = eventId;
        }

        public String getOrganizerId() {
                return organizerId;
        }

        public void setOrganizerId(String organizerId) {
                this.organizerId = organizerId;
        }

        public int getNoOfMembers() {
                return noOfMembers;
        }

        public void setNoOfMembers(int noOfMembers) {
                this.noOfMembers = noOfMembers;
        }

        public String getTransactionId() {
                return transactionId;
        }

        public void setTransactionId(String transactionId) {
                this.transactionId = transactionId;
        }

        public double getPaymentAmt() {
                return paymentAmt;
        }

        public void setPaymentAmt(double paymentAmt) {
                this.paymentAmt = paymentAmt;
        }

}
