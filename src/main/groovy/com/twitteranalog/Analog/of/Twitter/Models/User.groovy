package com.twitteranalog.Analog.of.Twitter.Models


import lombok.Data
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "User")
class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;

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
