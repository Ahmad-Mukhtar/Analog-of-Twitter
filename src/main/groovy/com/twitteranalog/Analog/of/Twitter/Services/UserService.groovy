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
}
