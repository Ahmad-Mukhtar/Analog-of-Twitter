package com.twitteranalog.Analog.of.Twitter.Controllers

import com.twitteranalog.Analog.of.Twitter.Models.Post
import com.twitteranalog.Analog.of.Twitter.Services.PostService
import com.twitteranalog.Analog.of.Twitter.Components.Comment
import com.twitteranalog.Analog.of.Twitter.dtos.PostDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController {

    @Autowired
    PostService postService

    @PostMapping("/addPost")
    ResponseEntity<?> createUser(@RequestBody PostDto post) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(post))
    }

    @DeleteMapping("/deletePost/{postId}")
    ResponseEntity<?> deleteUser(@PathVariable(name = "postId") String postId) {
        postService.deletePost(postId)
        return ResponseEntity
                .status(HttpStatus.OK)
                .build()
    }

    @PatchMapping("/updatePost/{postId}")
    ResponseEntity<?> updatePost(@PathVariable(name = "postId") String postId, @RequestBody PostDto post) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.updatePost(postId,post))
    }

    @GetMapping("/getPost/{postId}")
    ResponseEntity<?> getPost(@PathVariable(name = "postId") String postId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getPost(postId))
    }

    @GetMapping("/getFeed/{userId}")
    ResponseEntity<?> getAllPosts(@PathVariable(name = "userId") String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getFeed(userId))
    }

    @GetMapping("/getAllComments/{postId}")
    ResponseEntity<?> getAllComments(@PathVariable(name = "postId") String postId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getAllComments(postId))
    }

    @PostMapping("/addComment/{postId}")
    ResponseEntity<PostDto> addComment(@PathVariable(name = "postId") String postId, @RequestBody Comment comment) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.addComment(postId,comment))
    }

    @DeleteMapping("/removeComment/{postId}/{commentId}")
    ResponseEntity<?> removeComment(@PathVariable(name = "postId") String postId, @PathVariable(name = "commentId") String commentId ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.deleteComment(postId,commentId))
    }

   @PostMapping("/addLike/{postId}/{userId}")
    ResponseEntity<?> addLike(@PathVariable(name = "postId") String postId, @PathVariable(name = "userId") String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.addLike(postId,userId))
    }

    @DeleteMapping("/removeLike/{postId}/{userId}")
    ResponseEntity<?> removeLike(@PathVariable(name = "postId") String postId, @PathVariable(name = "userId") String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.removeLike(postId,userId))
    }

}
