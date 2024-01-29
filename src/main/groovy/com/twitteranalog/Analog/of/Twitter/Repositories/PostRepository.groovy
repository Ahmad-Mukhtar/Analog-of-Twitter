package com.twitteranalog.Analog.of.Twitter.Repositories

import com.twitteranalog.Analog.of.Twitter.Models.Post
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findByUserId(String userId)

}