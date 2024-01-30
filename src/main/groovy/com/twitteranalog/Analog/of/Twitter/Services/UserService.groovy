package com.twitteranalog.Analog.of.Twitter.Services


import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Repositories.UserRepository
import com.twitteranalog.Analog.of.Twitter.dtos.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    private UserRepository userRepository

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    UserDto createUser(UserDto dto) {
        Optional<User> checkUser = userRepository.findByEmail(dto.getEmail())
        if(checkUser.isPresent()){
            throw new ResourceException("User with associate email is already exist.")
        }
        if(dto.getEmail()!=null && dto.getUsername()!=null && dto.getPassword()!=null) {
            return new UserDto(userRepository.save(new User(dto)))
        } else {
            throw new IllegalArgumentException("Incomplete or invalid data. Please provide all required data.");
        }
    }

    UserDto updateUser(String userId, UserDto updatedUser) {
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
            return new UserDto(userRepository.save(existingUser))
        } else {
            throw new Exception("User not found with Id "+userId)
        }
    }

    void deleteUser(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId) 
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get() 
            userRepository.delete(existingUser)
        } else {
            throw new Exception("User not found with Id "+userId)
        }
    }

    UserDto addSubscriber(String userId, String followingId) {
        Optional<User> optionalUser = userRepository.findById(userId)
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get() 
            existingUser.getSubscribedTo().add(followingId)
            return new UserDto(userRepository.save(existingUser))
        } else {
            throw new Exception("User not found with Id "+userId)
        }
    }

    UserDto removeSubscriber(String userId, String followingId) {
        Optional<User> optionalUser = userRepository.findById(followingId)

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get() 
            existingUser.getSubscribedTo().remove(userId)
            return new UserDto(userRepository.save(existingUser))
        } else {
            throw new Exception("User not found with Id "+followingId)
        }
    }

    UserDto addFavouritePost(String userId, String favouritePostId) {
        Optional<User> optionalUser = userRepository.findById(userId)

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get() 
            existingUser.getFavouritePosts().add(favouritePostId) 
            return new UserDto(userRepository.save(existingUser))
        } else {
            throw new Exception("User not found with Id"+userId)
        }
    }

    UserDto removeFavouritePost(String userId, String favouritePostId) {
        Optional<User> optionalUser = userRepository.findById(userId)

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get() 
            existingUser.getFavouritePosts().remove(favouritePostId) 
            return new UserDto(userRepository.save(existingUser))
        } else {

            throw new Exception("User not found with Id"+userId)
        }
    }
}
