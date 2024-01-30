package com.twitteranalog.Analog.of.Twitter.Services

import com.twitteranalog.Analog.of.Twitter.Exceptions.NotFoundException
import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private UserRepository userRepository

    User createUser(User user) {
        if(user.getEmail()!=null&&user.getUsername()!=null&&user.getPassword()!=null)
        {
            user.setSubscribedBy(new ArrayList<String>())
            user.setFavouritePosts(new ArrayList<String>())
            return userRepository.save(user)
        }

        else
        {
            throw new IllegalArgumentException("Incomplete or invalid data. Please provide all required data.");
        }

    }

    User updateUser(String userId, User updatedUser)
    {
        Optional<User> optionalUser = userRepository.findById(userId) 

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get() 

            if (updatedUser.getUsername() != null) {
                existingUser.setUsername(updatedUser.getUsername()) 
            }
            if (updatedUser.getPassword() != null) {
                existingUser.setPassword(updatedUser.getPassword()) 
            }

            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail()) 
            }
            return userRepository.save(existingUser) 

        } else {
            throw new NotFoundException("User not found with Id "+userId)

        }
    }

    User deleteUser(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId) 
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get() 
            userRepository.delete(existingUser) 
            return existingUser 
        } else {
            throw new NotFoundException("User not found with Id "+userId)

        }
    }

    User addSubscriber(String userId, String followingId) {
        Optional<User> optionalUser = userRepository.findById(followingId) 
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get() 
            existingUser.getSubscribedBy().add(userId)
            return userRepository.save(existingUser) 
        } else {
            throw new NotFoundException("User not found with Id "+followingId)
        }
    }

    User removeSubscriber(String userId, String followingId) {
        Optional<User> optionalUser = userRepository.findById(followingId) 
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get() 
            existingUser.getSubscribedBy().remove(userId)
            return userRepository.save(existingUser) 
        } else {

            throw new NotFoundException("User not found with Id "+followingId)
        }
    }

    User addFavouritePost(String userId, String favouritePostId) {
        Optional<User> optionalUser = userRepository.findById(userId) 
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get() 
            existingUser.getFavouritePosts().add(favouritePostId) 
            return userRepository.save(existingUser) 
        } else {
            throw new NotFoundException("User not found with Id"+userId)

        }
    }

    User removeFavouritePost(String userId, String favouritePostId) {
        Optional<User> optionalUser = userRepository.findById(userId) 
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get() 
            existingUser.getFavouritePosts().remove(favouritePostId) 
            return userRepository.save(existingUser) 
        } else {

            throw new NotFoundException("User not found with Id"+userId)

        }
    }
}
