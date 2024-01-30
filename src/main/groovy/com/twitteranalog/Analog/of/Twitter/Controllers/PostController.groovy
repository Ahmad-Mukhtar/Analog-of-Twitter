package com.twitteranalog.Analog.of.Twitter.Controllers

import com.twitteranalog.Analog.of.Twitter.Models.Post
import com.twitteranalog.Analog.of.Twitter.Services.PostService
import com.twitteranalog.Analog.of.Twitter.Components.Comment
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
    ResponseEntity<Post> createUser(@RequestBody Post post) {
        Post createdPost = postService.createPost(post)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost)
    }

    @DeleteMapping("/deletePost/{postId}")
    ResponseEntity<?> deleteUser(@PathVariable(name = "postId") String postId) {
       postService.deletePost(postId)==null
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PatchMapping("/updatePost/{postId}")
    ResponseEntity<?> updatePost(@PathVariable(name = "postId") String postId, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(postId,post)
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost)
    }

    @GetMapping("/getPost/{postId}")
    ResponseEntity<?> getPost(@PathVariable(name = "postId") String postId) {
        Post post = postService.getPost(postId)
        return ResponseEntity.status(HttpStatus.OK).body(post)
    }

    @GetMapping("/getFeed/{userId}")
    ResponseEntity<?> getAllPosts(@PathVariable(name = "userId") String userId) {
        List<Post> posts = postService.getFeed(userId)
        return ResponseEntity.status(HttpStatus.OK).body(posts)
    }

    @GetMapping("/getAllComments/{postId}")
    ResponseEntity<?> getAllComments(@PathVariable(name = "postId") String postId) {
        List<Comment> comments = postService.getAllComments(postId)
        return ResponseEntity.status(HttpStatus.OK).body(comments)
    }

    @PostMapping("/addComment/{postId}")
    ResponseEntity<?> addComment(@PathVariable(name = "postId") String postId, @RequestBody Comment comment) {
        Post post = postService.addComment(postId,comment)
        return ResponseEntity.status(HttpStatus.OK).body("Comment Added Successfully")
    }

    @DeleteMapping("/removeComment/{postId}/{commentId}")
    ResponseEntity<?> removeComment(@PathVariable(name = "postId") String postId, @PathVariable(name = "commentId") String commentId ) {
        Post post = postService.deleteComment(postId,commentId)
        return ResponseEntity.status(HttpStatus.OK).body("Comment deleted SuccessFully")
    }

   @PostMapping("/addLike/{postId}/{userId}")
    ResponseEntity<?> addLike(@PathVariable(name = "postId") String postId, @PathVariable(name = "userId") String userId) {
        Post post = postService.addLike(postId,userId)
        return ResponseEntity.status(HttpStatus.OK).body(post)
    }

    @DeleteMapping("/removeLike/{postId}/{userId}")
    ResponseEntity<?> removeLike(@PathVariable(name = "postId") String postId, @PathVariable(name = "userId") String userId) {
        Post post = postService.removeLike(postId,userId)
        return ResponseEntity.status(HttpStatus.OK).body(post)
    }

}
