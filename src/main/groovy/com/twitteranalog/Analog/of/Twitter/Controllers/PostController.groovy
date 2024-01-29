package com.twitteranalog.Analog.of.Twitter.Controllers

import com.twitteranalog.Analog.of.Twitter.Models.Post
import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Services.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/posts")
class PostController {

    @Autowired
    PostService postService

    @PostMapping("/addPost")
    ResponseEntity<Post> createUser(@RequestBody Post post) {
        Post createdPost = postService.createPost(post)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @DeleteMapping("/deletePost/{postId}")
    ResponseEntity<?> deleteUser(@PathVariable(name = "postId") String postId) {
       if( postService.deletePost(postId)==null)
       {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post Id not found: " + postId);
       }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/updatePost/{postId}")
    ResponseEntity<?> updatePost(@PathVariable(name = "postId") String postId, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(postId,post)
        if (updatedPost == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with ID: " + post.getId());
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }



}
