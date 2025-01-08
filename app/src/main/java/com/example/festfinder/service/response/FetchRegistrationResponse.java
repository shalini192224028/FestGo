package com.example.festfinder.service.response;
import java.util.List;

    public class FetchRegistrationResponse {

        private String message;
        private List<RegistrationDetails> registrations;

        // Getter for message
        public String getMessage() {
            return message;
        }

        // Getter for registrations
        public List<RegistrationDetails> getRegistrations() {
            return registrations;
        }

        // Static inner class for registration details
        public static class RegistrationDetails {
            private String name;
            private String email_id;
            private int event_id;
            private int user_id;
            private int organizer_id;
            private int no_of_member;
            private String transaction_id;
            private double payment_amt;

            // Getters for all fields
            public String getName() {
                return name;
            }

            public String getEmailId() {
                return email_id;
            }

            public int getEventId() {
                return event_id;
            }

            public int getUserId() {
                return user_id;
            }

            public int getOrganizerId() {
                return organizer_id;
            }

            public int getNoOfMember() {
                return no_of_member;
            }

            public String getTransactionId() {
                return transaction_id;
            }

            public double getPaymentAmt() {
                return payment_amt;
            }
        }
    }

