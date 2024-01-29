package com.twitteranalog.Analog.of.Twitter;

import spock.lang.Specification
import spock.lang.AutoCleanup
import spock.lang.Shared
import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Services.UserService
import com.twitteranalog.Analog.of.Twitter.Services.PostService

class PostTest extends Specification {

    @Shared
    @AutoCleanup
    UserService userService = new UserService()

    @Shared
    @AutoCleanup
    PostService postService = new PostService()

    // Test case to create a post
    def "createPost should successfully create a post"() {
        given:
        User author = new User(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
                followers: [],
                following: []
        )
        def savedAuthor = userService.createUser(author)

        when:
        def postContent = "This is a test post."
        def createdPost = postService.createPost(savedAuthor.id, postContent)

        then:
        createdPost != null
        createdPost.id != null
        createdPost.author.id == savedAuthor.id
        createdPost.content == postContent
        createdPost.comments == []
        createdPost.likes == []
    }
    // Test case for editing a post

    def "editPost should successfully update post content"() {
        given:
        User author = new User(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
                followers: [],
                following: []
        )
        def savedAuthor = userService.createUser(author)

        def postContent = "This is a test post."
        def createdPost = postService.createPost(savedAuthor.id, postContent)

        when:
        def newContent = "Updated content for the test post."
        createdPost.content = newContent
        def updatedPost = postService.editPost(createdPost)

        then:
        updatedPost != null
        updatedPost.id == createdPost.id
        updatedPost.author.id == savedAuthor.id
        updatedPost.content == newContent
        updatedPost.comments == createdPost.comments
        updatedPost.likes == createdPost.likes
    }

    //Test case to Deleting a post
    def "deletePost should successfully delete a post"() {
        given:
        User author = new User(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
                followers: [],
                following: []
        )
        def savedAuthor = userService.createUser(author)

        def postContent = "This is a test post."
        def createdPost = postService.createPost(savedAuthor.id, postContent)

        when:
        postService.deletePost(createdPost.id)

        then:
        def deletedPost = postService.getPostById(createdPost.id)
        deletedPost == null
    }
    // Test case for Leave/delete a post favorite

    def "leaveFavorite should successfully remove post from user's favorites"() {
        given:
        User user = new User(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
                followers: [],
                following: []
        )
        def savedUser = userService.createUser(user)

        def postContent = "This is a test post."
        def createdPost = postService.createPost(savedUser.id, postContent)

        when:
        postService.leaveFavorite(savedUser.id, createdPost.id)

        then:
        def updatedUser = userService.getUserById(savedUser.id)
        !updatedUser.favoritePosts.contains(createdPost.id)
    }

    //Test case for Subscription to a user

    def "subscribeToUser should add user to the subscriber's following list"() {
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

        when:
        userService.subscribeToUser(savedSubscriber.id, savedTargetUser.id)

        then:
        def updatedSubscriber = userService.getUserById(savedSubscriber.id)
        updatedSubscriber.following.contains(savedTargetUser.id)
    }

    //Test case for Commenting on a post

    def "commentOnPost should successfully add a comment to the post"() {
        given:
        User author = new User(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
                followers: [],
                following: []
        )
        def savedAuthor = userService.createUser(author)

        def postContent = "This is a test post."
        def createdPost = postService.createPost(savedAuthor.id, postContent)

        User commenter = new User(
                username: "commenterUser",
                password: "commenterPassword",
                email: "commenter@example.com",
                followers: [],
                following: []
        )
        def savedCommenter = userService.createUser(commenter)

        when:
        def commentContent = "Great post!"
        postService.commentOnPost(savedCommenter.id, createdPost.id, commentContent)

        then:
        def updatedPost = postService.getPostById(createdPost.id)
        updatedPost.comments.size() == 1
        updatedPost.comments[0].content == commentContent
        updatedPost.comments[0].author.id == savedCommenter.id
    }

    //Test case to Get post comments
    def "getPostComments should retrieve comments for a post"() {
        given:
        User author = new User(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
                followers: [],
                following: []
        )
        def savedAuthor = userService.createUser(author)

        def postContent = "This is a test post."
        def createdPost = postService.createPost(savedAuthor.id, postContent)

        User commenter = new User(
                username: "commenterUser",
                password: "commenterPassword",
                email: "commenter@example.com",
                followers: [],
                following: []
        )
        def savedCommenter = userService.createUser(commenter)

        def commentContent1 = "Great post!"
        def commentContent2 = "Nice one!"
        postService.commentOnPost(savedCommenter.id, createdPost.id, commentContent1)
        postService.commentOnPost(savedCommenter.id, createdPost.id, commentContent2)

        when:
        def postComments = postService.getPostComments(createdPost.id)

        then:
        postComments.size() == 2
        postComments[0].content == commentContent1
        postComments[0].author.id == savedCommenter.id
        postComments[1].content == commentContent2
        postComments[1].author.id == savedCommenter.id
    }
}
