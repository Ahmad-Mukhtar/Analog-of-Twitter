package com.twitteranalog.Analog.of.Twitter.Models

import com.twitteranalog.Analog.of.Twitter.dtos.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "User")
class User {
    @Id
    private String id
    private String username
    private String password
    private String email
    private List<String> subscribedBy
    private List<String> favouritePosts

    User(){}

    User(UserDto dto){
        this.username = dto.getUsername()
        this.password = dto.getPassword()
        this.email = dto.getEmail()
        this.subscribedBy = dto.getSubscribedBy()
        this.favouritePosts = dto.getFavouritePosts()
    }

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
