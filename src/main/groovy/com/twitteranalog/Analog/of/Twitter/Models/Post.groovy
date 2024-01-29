package com.twitteranalog.Analog.of.Twitter.Models

import com.twitteranalog.Analog.of.Twitter.Utils.Comment
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import java.time.LocalDateTime


@Document(collection = "post")
public class Post {
    @Id
    private String id;
    private String userId;
    private String text;
    private LocalDateTime timestamp;
    private List<String> likes;
    private List<Comment> comments;

    String getId() {
        return id
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

    List<String> getLikes() {
        return likes
    }

    void setLikes(List<String> likes) {
        this.likes = likes
    }

    List<Comment> getComments() {
        return comments
    }

    void setComments(List<Comment> comments) {
        this.comments = comments
    }

    String getUserId() {
        return userId
    }

    void setUserId(String userId) {
        this.userId = userId
    }

}

