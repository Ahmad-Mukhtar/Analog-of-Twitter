package com.twitteranalog.Analog.of.Twitter.Models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "follow")
class Follow {
    @Id
    String Id
    String userId
    List<String> followers

    String getId() {
        return Id
    }
    List<String> following

    String getUserId() {
        return userId
    }

    void setUserId(String userId) {
        this.userId = userId
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
}
