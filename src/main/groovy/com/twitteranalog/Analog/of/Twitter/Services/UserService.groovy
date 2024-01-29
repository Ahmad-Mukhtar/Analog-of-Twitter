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
}
