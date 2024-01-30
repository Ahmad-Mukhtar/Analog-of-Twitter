package com.twitteranalog.Analog.of.Twitter.Models


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "User")
class User {
    @Id
    private String id
    private String username
    private String password
    private String email
    List<String> subscribedBy
    List<String> favouritePosts

    List<String> getFavouritePosts() {
        return favouritePosts
    }

    void setFavouritePosts(List<String> favouritePosts) {
        this.favouritePosts = favouritePosts
    }

    void setId(String id) {
        this.id = id
    }


    List<String> getSubscribedBy() {
        return subscribedBy
    }

    void setSubscribedBy(List<String> following) {
        this.subscribedBy = following
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
