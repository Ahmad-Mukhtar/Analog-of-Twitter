package com.twitteranalog.Analog.of.Twitter.Models


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "User")
class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    List<String> followers;
    List<String> following

    void setId(String id) {
        this.id = id
    }

    List<String> getFollowers() {
        return followers
    }

    void setFollowers(List<String> followers) {
        this.followers = followers
    }

    List<String> getFollowing() {
        return following
    }

    void setFollowing(List<String> following) {
        this.following = following
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getId() {
        return id
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

}
