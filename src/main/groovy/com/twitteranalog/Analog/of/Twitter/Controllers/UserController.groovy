package com.twitteranalog.Analog.of.Twitter.Controllers


import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    UserService userService


    @PostMapping("/addUser")
    ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PatchMapping("/updateUser/{userId}")
    ResponseEntity<?> updateUser(@PathVariable(name = "userId") String userId, @RequestBody User updatedData) {

           User updatedUser = userService.updateUser(userId, updatedData);
           if (updatedUser == null)
           {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
           }
           return ResponseEntity.status(HttpStatus.OK).body(updatedUser);


    }

    @DeleteMapping("deleteUser/{userId}")
    ResponseEntity<?> deleteUser(@PathVariable(name = "userId") String userId) {
        if (userService.deleteUser(userId)==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Id not found: " + userId);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //The User followed Someone Else
    @PostMapping("/addSubscriber/{userId}/{followingId}")
    ResponseEntity<?> addSubscriber(@PathVariable(name = "userId") String userId, @PathVariable(name = "followingId") String followingId) {
        User updatedUser = userService.addSubscriber(userId, followingId);
        if (updatedUser == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/removeSubscriber/{userId}/{followingId}")
    ResponseEntity<?> removeSubscriber(@PathVariable(name = "userId") String userId, @PathVariable(name = "followingId") String followingId) {
        User updatedUser = userService.removeSubscriber(userId, followingId);
        if (updatedUser == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PostMapping("/addFavouritePost/{userId}/{postId}")
    ResponseEntity<?> addPost(@PathVariable(name = "userId") String userId,@PathVariable(name = "postId") String postId) {
        User updatedUser = userService.addFavouritePost(userId, postId);
        if (updatedUser == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/removeFavouritePost/{userId}/{postId}")
    ResponseEntity<?> removePost(@PathVariable(name = "userId") String userId,@PathVariable(name = "postId") String postId) {
        User updatedUser = userService.removeFavouritePost(userId, postId);
        if (updatedUser == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

}
