package com.learning.api.demo.models;

public class ResponseObject {
    private String status;
    private String message;
    private Object data;

    public ResponseObject() {};

    public ResponseObject(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }
}
