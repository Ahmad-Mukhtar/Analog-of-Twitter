package com.twitteranalog.Analog.of.Twitter.Controllers

import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Services.UserService
import com.twitteranalog.Analog.of.Twitter.dtos.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    UserService userService


    @PostMapping("/addUser")
    ResponseEntity<?> createUser(@RequestBody UserDto user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(user))
    }

    @PatchMapping("/updateUser/{userId}")
    ResponseEntity<?> updateUser(@PathVariable(name = "userId") String userId, @RequestBody UserDto updatedData) {
           return ResponseEntity
                   .status(HttpStatus.OK)
                   .body(userService.updateUser(userId, updatedData))
    }

    @DeleteMapping("deleteUser/{userId}")
    ResponseEntity<?> deleteUser(@PathVariable(name = "userId") String userId) {
        userService.deleteUser(userId)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    //The User followed Someone Else
    @PostMapping("/addSubscriber/{userId}/{followingId}")
    ResponseEntity<?> addSubscriber(@PathVariable(name = "userId") String userId, @PathVariable(name = "followingId") String followingId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.addSubscriber(userId, followingId))
    }

    @DeleteMapping("/removeSubscriber/{userId}/{followingId}")
    ResponseEntity<?> removeSubscriber(@PathVariable(name = "userId") String userId, @PathVariable(name = "followingId") String followingId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.removeSubscriber(userId, followingId))
    }

    @PostMapping("/addFavouritePost/{userId}/{postId}")
    ResponseEntity<?> addPost(@PathVariable(name = "userId") String userId,@PathVariable(name = "postId") String postId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.addFavouritePost(userId, postId))
    }

    @DeleteMapping("/removeFavouritePost/{userId}/{postId}")
    ResponseEntity<?> removePost(@PathVariable(name = "userId") String userId,@PathVariable(name = "postId") String postId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.removeFavouritePost(userId, postId))
    }

}
