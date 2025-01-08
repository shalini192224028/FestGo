package com.example.festfinder.service.response;

public class ApproveRejectResponse {
    private int status;
    private String message;

    // Default Constructor
    public ApproveRejectResponse() {
    }

    // Parameterized Constructor
    public ApproveRejectResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getter and Setter for status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApproveRejectResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
