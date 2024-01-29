package com.twitteranalog.Analog.of.Twitter.Repositories

import com.twitteranalog.Analog.of.Twitter.Models.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username)
}