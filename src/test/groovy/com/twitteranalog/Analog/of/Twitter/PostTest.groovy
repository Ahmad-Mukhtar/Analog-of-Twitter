package com.twitteranalog.Analog.of.Twitter

import com.twitteranalog.Analog.of.Twitter.Components.Comment
import com.twitteranalog.Analog.of.Twitter.Models.Post
import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Repositories.PostRepository
import com.twitteranalog.Analog.of.Twitter.Repositories.UserRepository
import com.twitteranalog.Analog.of.Twitter.Services.PostService
import com.twitteranalog.Analog.of.Twitter.dtos.PostDto

import java.time.LocalDateTime

class PostTest extends AnalogOfTwitterApplicationTests{

    PostRepository postRepository=Mock()
    UserRepository userRepository=Mock()

    def postService=new PostService(postRepository,userRepository)

    def "createPost should successfully create a post"() {
        given:
        PostDto postDto = new PostDto(
                userId: "testUserId",
                text: "This is a test post.",
                timestamp: LocalDateTime.now()
        )

        Post expectedPost = new Post(
                userId: "testUserId",
                text: "This is a test post.",
                timestamp: LocalDateTime.now()
        )

        postRepository.save(_) >> expectedPost

        when:
        def createdPost = postService.createPost(postDto)

        then:
        createdPost != null
        createdPost.userId == postDto.userId
        createdPost.text == postDto.text
        createdPost.timestamp != null
    }

    //Test case for Delete
    def "deletePost should delete the post if it exists"() {
        given:
        String postId = "testPostId"
        Post existingPost = new Post(
                userId: "testUserId",
                text: "This is a test post.",
                timestamp: LocalDateTime.now()
        )

        postRepository.findById(postId) >> Optional.of(existingPost)

        when:
        postService.deletePost(postId)

        then:
        1 * postRepository.findById(postId) >> Optional.of(existingPost)
        1 * postRepository.delete(existingPost)
    }

    //Update Post
    def "updatePost should update the post text and return the updated PostDto"() {
        given:
        String postId = "testPostId"
        Post existingPost = new Post(
                userId: "testUserId",
                text: "Original post text.",
                timestamp: LocalDateTime.now()
        )

        PostDto updatedPostDto = new PostDto(
                userId: "testUserId",
                text: "Updated post text.",
                timestamp: LocalDateTime.now()
        )

        postRepository.findById(postId) >> Optional.of(existingPost)
        postRepository.save(existingPost) >> existingPost // Assuming save returns the updated post

        when:
        PostDto result = postService.updatePost(postId, updatedPostDto)

        then:
        1 * postRepository.findById(postId) >> Optional.of(existingPost)
        1 * postRepository.save(existingPost) >> existingPost
        result.text == updatedPostDto.text
    }

    //Get All comments
    def "getAllComments should return the list of comments for a given post"() {
        given:
        String postId = "testPostId"
        List<Comment> expectedComments = [
                new Comment(userId: "user1", text: "Comment 1"),
                new Comment(userId: "user2", text: "Comment 2")
        ]

        Post existingPost = new Post(
                userId: "testUserId",
                text: "Original post text.",
                timestamp: LocalDateTime.now(),
                comments: expectedComments
        )

        postRepository.findById(postId) >> Optional.of(existingPost)

        when:
        List<Comment> result = postService.getAllComments(postId)

        then:
        1 * postRepository.findById(postId) >> Optional.of(existingPost)
        result == expectedComments
    }

    //Get Post
    def "getPost should return the PostDto for a given post"() {
        given:
        String postId = "testPostId"
        Post expectedPost = new Post(
                userId: "testUserId",
                text: "This is a test post.",
                timestamp: LocalDateTime.now()
        )

        postRepository.findById(postId) >> Optional.of(expectedPost)

        when:
        PostDto result = postService.getPost(postId)

        then:
        1 * postRepository.findById(postId) >> Optional.of(expectedPost)
        result.userId == expectedPost.userId
        result.text == expectedPost.text
        result.timestamp == expectedPost.timestamp
    }

    //Add Comment
    def "addComment should add a comment to the post and return the updated PostDto"() {
        given:
        String postId = "testPostId"
        Post existingPost = new Post(
                userId: "testUserId",
                text: "Original post text.",
                timestamp: LocalDateTime.now(),
                comments: []
        )

        Comment newComment = new Comment(
                userId: "commentUserId",
                text: "This is a new comment."
        )

        postRepository.findById(postId) >> Optional.of(existingPost)
        postRepository.save(existingPost) >> existingPost // Assuming save returns the updated post

        when:
        PostDto result = postService.addComment(postId, newComment)

        then:
        1 * postRepository.findById(postId) >> Optional.of(existingPost)
        1 * postRepository.save(existingPost) >> existingPost
        result.comments.size() == 1
        result.comments[0].userId == newComment.userId
        result.comments[0].text == newComment.text
    }

    //deleteComment

    def "deleteComment should delete a comment from the post and return the updated PostDto"() {
        given:
        String postId = "testPostId"
        String commentIdToDelete = "commentIdToDelete"
        Comment commentToDelete = new Comment(
                id: commentIdToDelete,
                userId: "commentUserId",
                text: "This is the comment to delete."
        )

        Post existingPost = new Post(
                userId: "testUserId",
                text: "Original post text.",
                timestamp: LocalDateTime.now(),
                comments: [commentToDelete]
        )

        postRepository.findById(postId) >> Optional.of(existingPost)
        postRepository.save(existingPost) >> existingPost // Assuming save returns the updated post

        when:
        PostDto result = postService.deleteComment(postId, commentIdToDelete)

        then:
        1 * postRepository.findById(postId) >> Optional.of(existingPost)
        1 * postRepository.save(existingPost) >> existingPost
        result.comments.size() == 0
    }

    //Add Like
    def "addLike should add a like to the post and return the updated PostDto"() {
        given:
        String postId = "testPostId"
        String userIdToAdd = "userToAdd"

        Post existingPost = new Post(
                userId: "testUserId",
                text: "Original post text.",
                timestamp: LocalDateTime.now(),
                likes: []
        )

        postRepository.findById(postId) >> Optional.of(existingPost)
        postRepository.save(existingPost) >> existingPost // Assuming save returns the updated post

        when:
        PostDto result = postService.addLike(postId, userIdToAdd)

        then:
        1 * postRepository.findById(postId) >> Optional.of(existingPost)
        1 * postRepository.save(existingPost) >> existingPost
        result.likes.size() == 1
        result.likes[0] == userIdToAdd
    }

    //Remove Like

    def "removeLike should remove a like from the post and return the updated PostDto"() {
        given:
        String postId = "testPostId"
        String userIdToRemove = "userToRemove"

        Post existingPost = new Post(
                userId: "testUserId",
                text: "Original post text.",
                timestamp: LocalDateTime.now(),
                likes: [userIdToRemove]
        )

        postRepository.findById(postId) >> Optional.of(existingPost)
        postRepository.save(existingPost) >> existingPost // Assuming save returns the updated post

        when:
        PostDto result = postService.removeLike(postId, userIdToRemove)

        then:
        1 * postRepository.findById(postId) >> Optional.of(existingPost)
        1 * postRepository.save(existingPost) >> existingPost
        result.likes.size() == 0
    }

    //Get Feed
    def "getFeed should return a list of posts for the user's feed"() {
        given:
        String userId = "testUserId"
        User testUser = new User(
                id: userId,
                subscribedTo: ["user1", "user2"]
        )

        List<Post> userPosts = [
                new Post(userId: userId, text: "User's own post 1"),
                new Post(userId: userId, text: "User's own post 2")
        ]

        List<Post> subscribedToPosts = [
                new Post(userId: "user1", text: "Post from subscribed user 1"),
                new Post(userId: "user2", text: "Post from subscribed user 2")
        ]

        userRepository.findById(userId) >> Optional.of(testUser)
        postRepository.findByUserId(userId) >> userPosts
        postRepository.findByUserIdIn(["user1", "user2"]) >> subscribedToPosts

        when:
        List<Post> result = postService.getFeed(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.of(testUser)
        1 * postRepository.findByUserId(userId) >> userPosts
        1 * postRepository.findByUserIdIn(["user1", "user2"]) >> subscribedToPosts
        result.size() == 4
        result*.text == ["User's own post 1", "User's own post 2", "Post from subscribed user 1", "Post from subscribed user 2"]
    }
}
