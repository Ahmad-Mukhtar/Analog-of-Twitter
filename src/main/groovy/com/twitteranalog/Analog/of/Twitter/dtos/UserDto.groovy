package com.twitteranalog.Analog.of.Twitter.dtos

import com.twitteranalog.Analog.of.Twitter.Models.User

class UserDto {
    private String username
    private String password
    private String email
    private List<String> subscribedBy
    private List<String> favouritePosts

    UserDto(){}

    UserDto(User user){
        this.username = user.getUsername()
        this.password = user.getPassword()
        this.email = user.getEmail()
        this.subscribedBy = user.getSubscribedBy()
        this.favouritePosts = user.getFavouritePosts()
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    List<String> getSubscribedBy() {
        return subscribedBy
    }

    void setSubscribedBy(List<String> subscribedBy) {
        this.subscribedBy = subscribedBy
    }

    List<String> getFavouritePosts() {
        return favouritePosts
    }

    void setFavouritePosts(List<String> favouritePosts) {
        this.favouritePosts = favouritePosts
    }
}
