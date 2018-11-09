package com.review.foodreview.dto;

public class LogDTO {
    private Integer id;
    private String tag;
    private String message;

    public LogDTO() {
    }

    public LogDTO(String tag, String message) {
        this.tag = tag;
        this.message = message;
    }

    public LogDTO(Integer id, String tag, String message) {
        this.id = id;
        this.tag = tag;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
