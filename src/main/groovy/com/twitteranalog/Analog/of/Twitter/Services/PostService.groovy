package com.twitteranalog.Analog.of.Twitter.Services

import com.twitteranalog.Analog.of.Twitter.Exceptions.NotFoundException
import com.twitteranalog.Analog.of.Twitter.Models.Post
import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Repositories.PostRepository
import com.twitteranalog.Analog.of.Twitter.Repositories.UserRepository
import com.twitteranalog.Analog.of.Twitter.Components.Comment
import com.twitteranalog.Analog.of.Twitter.dtos.PostDto
import com.twitteranalog.Analog.of.Twitter.dtos.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.time.LocalDateTime


@Service
class PostService {


    private PostRepository postRepository

    private UserRepository userRepository

    PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository
        this.userRepository = userRepository
    }


    PostDto createPost(PostDto post) {
        if (post.getUserId()!=null&&post.getText()!=null) {
            post.setTimestamp(LocalDateTime.now())
            return new PostDto(postRepository.save(new Post(post)))
        } else {
            throw new IllegalArgumentException("Incomplete or invalid data. Please provide all required data.");
        }
    }

    void deletePost(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId)

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get() 
            postRepository.delete(existingPost)
        } else {
            throw new NotFoundException("Post not found with Id "+postId)
        }
    }

    PostDto updatePost(String postId, PostDto updatedPost) {
        Optional<Post> optionalPost = postRepository.findById(postId) 

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get() 

            if (updatedPost.getText() != null && !Objects.equals(updatedPost.getText(),"")) {
                existingPost.setText(updatedPost.getText())
                return new PostDto(postRepository.save(existingPost))
            }
        } else {
            throw new NotFoundException("Post not found with Id "+postId)
        }
        throw new Exception("Post can not be null or empty")
    }

    List<Comment> getAllComments(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId) 
        if (optionalPost.isPresent()) {
            return optionalPost.get().getComments() 
        } else {
            throw new NotFoundException("Post not found with Id "+postId)
        }
    }

    PostDto getPost(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId) 
        if (optionalPost.isPresent()) {
            return new PostDto(optionalPost.get())
        } else {
            throw new NotFoundException("Post not found with Id "+postId)
        }
    }

    PostDto addComment(String postId, Comment comment) {
        Optional<Post> optionalPost = postRepository.findById(postId)

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get()
            if(comment.getUserId() !=null && !Objects.equals(comment.getUserId(), "")
                    && comment.getText()!=null && !Objects.equals(comment.getText(), ""))
            {
                comment.setId(UUID.randomUUID().toString())
                comment.setTimestamp(LocalDateTime.now())
                existingPost.getComments().add(comment)
                return new PostDto(postRepository.save(existingPost))
            }
        } else {
            throw new NotFoundException("Post not found with Id "+postId)
        }
        throw new Exception("Fields cannot be null or empty")
    }

    PostDto deleteComment(String postId, String commentId) {
        Optional<Post> optionalPost = postRepository.findById(postId)

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get() 
            existingPost.getComments().removeIf(comment -> comment.getId() == commentId) 
            return new PostDto(postRepository.save(existingPost))
        } else {
            throw new NotFoundException("Post not found with Id "+postId)
        }
    }

    PostDto addLike(String postId, String userId) {
        Optional<Post> optionalPost = postRepository.findById(postId) 
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get() 
            existingPost.getLikes().add(userId) 
            return new PostDto(postRepository.save(existingPost))
        } else {
            throw new NotFoundException("Post not found with Id "+postId)
        }
    }

    PostDto removeLike(String postId, String userId) {
        Optional<Post> optionalPost = postRepository.findById(postId) 
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get()
            existingPost.getLikes().remove(userId)
            return new PostDto(postRepository.save(existingPost))
        } else {
            throw new NotFoundException("Post not found with Id "+postId)

        }
    }

    List<Post> getFeed(String userId) {
        List<Post> posts = postRepository.findByUserId(userId)

        Optional<User> optionalUser = userRepository.findById(userId) 
        if (optionalUser.isPresent()) {
            List<String> subscribers = optionalUser.get().getSubscribedTo()
            postRepository.findByUserIdIn(subscribers).forEach(posts::add)
        }
        return posts
    }
}
