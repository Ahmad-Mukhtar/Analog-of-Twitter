package com.twitteranalog.Analog.of.Twitter;

import spock.lang.Specification
import spock.lang.AutoCleanup
import spock.lang.Shared
import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Services.UserService

class UserTest extends Specification {

    @Shared
    @AutoCleanup
    UserService userService = new UserService()


    def "createUser should successfully create a user"() {
        given:
        User user = new User(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
                followers: [],
                following: []
        )

        when:
        def createdUser = userService.createUser(user)

        then:
        createdUser != null
        createdUser.id != null
        createdUser.username == user.username
        createdUser.password == user.password
        createdUser.email == user.email
        createdUser.followers == []
        createdUser.following == []
    }
    //Test case to Update User
    def "editUser should successfully update user information"() {
        given:
        User existingUser = new User(
                username: "oldUsername",
                password: "oldPassword",
                email: "old@example.com",
                followers: [],
                following: []
        )
        def savedUser = userService.createUser(existingUser)

        when:
        savedUser.username = "newUsername"
        savedUser.password = "newPassword"
        savedUser.email = "new@example.com"
        def updatedUser = userService.editUser(savedUser)

        then:
        updatedUser != null
        updatedUser.id == savedUser.id
        updatedUser.username == "newUsername"
        updatedUser.password == "newPassword"
        updatedUser.email == "new@example.com"
        updatedUser.followers == savedUser.followers
        updatedUser.following == savedUser.following
    }

    //Test case to delete a user

    def "deleteUser should successfully delete a user"() {
        given:
        User existingUser = new User(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
                followers: [],
                following: []
        )
        def savedUser = userService.createUser(existingUser)

        when:
        userService.deleteUser(savedUser.id)

        then:
        def deletedUser = userService.getUserById(savedUser.id)
        deletedUser == null
    }

    //Test case to Unsubscribe from a user
    def "unsubscribeFromUser should remove user from the subscriber's following list"() {
        given:
        User subscriber = new User(
                username: "subscriberUser",
                password: "subscriberPassword",
                email: "subscriber@example.com",
                followers: [],
                following: []
        )
        def savedSubscriber = userService.createUser(subscriber)

        User targetUser = new User(
                username: "targetUser",
                password: "targetPassword",
                email: "target@example.com",
                followers: [],
                following: []
        )
        def savedTargetUser = userService.createUser(targetUser)

        userService.subscribeToUser(savedSubscriber.id, savedTargetUser.id)

        when:
        userService.unsubscribeFromUser(savedSubscriber.id, savedTargetUser.id)

        then:
        def updatedSubscriber = userService.getUserById(savedSubscriber.id)
        !updatedSubscriber.following.contains(savedTargetUser.id)
    }

    //Test case to Get a user's feed (including likes and comments)
    def "getUserFeed should retrieve user's feed with likes and comments"() {
        given:
        User user = new User(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
                followers: [],
                following: []
        )
        def savedUser = userService.createUser(user)

        User follower = new User(
                username: "followerUser",
                password: "followerPassword",
                email: "follower@example.com",
                followers: [],
                following: []
        )
        def savedFollower = userService.createUser(follower)

        userService.subscribeToUser(savedFollower.id, savedUser.id)

        def postContent = "This is a test post."
        def createdPost = postService.createPost(savedUser.id, postContent)

        def commentContent = "Great post!"
        postService.commentOnPost(savedFollower.id, createdPost.id, commentContent)

        postService.likePost(savedUser.id, createdPost.id)

        when:
        def userFeed = postService.getUserFeed(savedUser.id)

        then:
        userFeed.size() == 1
        def feedPost = userFeed[0]
        feedPost.id == createdPost.id
        feedPost.author.id == savedUser.id
        feedPost.content == postContent
        feedPost.comments.size() == 1
        feedPost.comments[0].content == commentContent
        feedPost.comments[0].author.id == savedFollower.id
        feedPost.likes.size() == 1
        feedPost.likes[0].id == savedUser.id
    }

    //Test case to Get another user's feed
    def "getAnotherUserFeed should retrieve another user's feed with likes and comments"() {
        given:
        User user = new User(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
                followers: [],
                following: []
        )
        def savedUser = userService.createUser(user)

        User anotherUser = new User(
                username: "anotherUser",
                password: "anotherPassword",
                email: "another@example.com",
                followers: [],
                following: []
        )
        def savedAnotherUser = userService.createUser(anotherUser)

        def postContent = "This is a test post."
        def createdPost = postService.createPost(savedAnotherUser.id, postContent)

        def commentContent = "Great post!"
        postService.commentOnPost(savedUser.id, createdPost.id, commentContent)

        postService.likePost(savedAnotherUser.id, createdPost.id)

        when:
        def anotherUserFeed = postService.getAnotherUserFeed(savedAnotherUser.id, savedUser.id)

        then:
        anotherUserFeed.size() == 1
        def feedPost = anotherUserFeed[0]
        feedPost.id == createdPost.id
        feedPost.author.id == savedAnotherUser.id
        feedPost.content == postContent
        feedPost.comments.size() == 1
        feedPost.comments[0].content == commentContent
        feedPost.comments[0].author.id == savedUser.id
        feedPost.likes.size() == 1
        feedPost.likes[0].id == savedAnotherUser.id
    }

}