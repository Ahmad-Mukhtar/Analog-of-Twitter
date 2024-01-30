package com.twitteranalog.Analog.of.Twitter.Services


import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private UserRepository userRepository

    User createUser(User user) {
        user.setSubscribedTo(new ArrayList<String>())
        user.setFavouritePosts(new ArrayList<String>())
        return userRepository.save(user)
    }

    User updateUser(String userId, User updatedUser)
    {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            if (updatedUser.getUsername() != null) {
                existingUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getPassword() != null) {
                existingUser.setPassword(updatedUser.getPassword());
            }

            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            return userRepository.save(existingUser);

        } else {
            return null
        }
    }

    User deleteUser(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            userRepository.delete(existingUser);
            return existingUser;
        } else {
            return null
        }
    }

    User addSubscriber(String userId, String followingId) {
        Optional<User> optionalUser = userRepository.findById(followingId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.getSubscribedTo().add(userId);
            return userRepository.save(existingUser);
        } else {
            return null
        }
    }

    User removeSubscriber(String userId, String followingId) {
        Optional<User> optionalUser = userRepository.findById(followingId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.getSubscribedTo().remove(userId);
            return userRepository.save(existingUser);
        } else {
            return null
        }
    }

    User addFavouritePost(String userId, String favouritePostId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.getFavouritePosts().add(favouritePostId);
            return userRepository.save(existingUser);
        } else {
            return null
        }
    }

    User removeFavouritePost(String userId, String favouritePostId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.getFavouritePosts().remove(favouritePostId);
            return userRepository.save(existingUser);
        } else {
            return null
        }
    }
}
