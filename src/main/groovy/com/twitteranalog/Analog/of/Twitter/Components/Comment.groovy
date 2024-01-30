package com.twitteranalog.Analog.of.Twitter.Components


import java.time.LocalDateTime

class Comment {

    private String id;
    private String userId;
    private String text;
    private LocalDateTime timestamp;

    void setId(String id) {
        this.id = id
    }

    String getId() {
        return id
    }

    String getUserId() {
        return userId
    }

    void setUserId(String userId) {
        this.userId = userId
    }

    String getText() {
        return text
    }

    void setText(String text) {
        this.text = text
    }

    LocalDateTime getTimestamp() {
        return timestamp
    }

    void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp
    }

}
