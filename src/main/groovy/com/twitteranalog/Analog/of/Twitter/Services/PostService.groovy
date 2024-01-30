package com.twitteranalog.Analog.of.Twitter.Services

import com.twitteranalog.Analog.of.Twitter.Models.Post
import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Repositories.PostRepository
import com.twitteranalog.Analog.of.Twitter.Repositories.UserRepository
import com.twitteranalog.Analog.of.Twitter.Components.Comment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class PostService {

    @Autowired
    private PostRepository postRepository

    @Autowired
    private UserRepository userRepository


    Post createPost(Post post) {
      post.setLikes(new ArrayList<String>())
      post.setComments(new ArrayList<Comment>())
        return postRepository.save(post)
    }

    Post deletePost(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId) 
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get() 
            postRepository.delete(existingPost) 
            return existingPost 
        } else {
            throw new Exception("Post not found with Id "+postId)
        }
    }

    Post updatePost(String postId, Post updatedPost) {
        Optional<Post> optionalPost = postRepository.findById(postId) 

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get() 

            if (updatedPost.getText() != null) {
                existingPost.setText(updatedPost.getText()) 
            }

            return postRepository.save(existingPost) 

        } else {
            throw new Exception("Post not found with Id "+postId)
        }
    }

    List<Comment> getAllComments(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId) 
        if (optionalPost.isPresent()) {
            return optionalPost.get().getComments() 
        } else {
            throw new Exception("Post not found with Id "+postId)
        }
    }

    Post getPost(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId) 
        if (optionalPost.isPresent()) {
            return optionalPost.get() 
        } else {
            throw new Exception("Post not found with Id "+postId)
        }
    }

    Post addComment(String postId, Comment comment) {
        Optional<Post> optionalPost = postRepository.findById(postId) 
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get() 
            comment.setId(UUID.randomUUID().toString())
            existingPost.getComments().add(comment) 
            return postRepository.save(existingPost) 
        } else {
            throw new Exception("Post not found with Id "+postId)
        }
    }

    Post deleteComment(String postId, String commentId) {
        Optional<Post> optionalPost = postRepository.findById(postId) 
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get() 
            existingPost.getComments().removeIf(comment -> comment.getId() == commentId) 
            return postRepository.save(existingPost) 
        } else {
            throw new Exception("Post not found with Id "+postId)
        }
    }

    Post addLike(String postId, String userId) {
        Optional<Post> optionalPost = postRepository.findById(postId) 
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get() 
            existingPost.getLikes().add(userId) 
            return postRepository.save(existingPost) 
        } else {
            throw new Exception("Post not found with Id "+postId)
        }
    }

    Post removeLike(String postId, String userId) {
        Optional<Post> optionalPost = postRepository.findById(postId) 
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get() 
            existingPost.getLikes().remove(userId) 
            return postRepository.save(existingPost) 
        } else {
            throw new Exception("Post not found with Id "+postId)

        }
    }

    List<Post>getFeed(String userId) {

        List<Post> posts = postRepository.findByUserId(userId)

        Optional<User> optionalUser = userRepository.findById(userId) 
        if (optionalUser.isPresent()) {
            List<String> subscribers = optionalUser.get().getSubscribedTo() 
            postRepository.findByUserIdIn(subscribers).forEach(posts::add)

        }
        return posts
    }




}
