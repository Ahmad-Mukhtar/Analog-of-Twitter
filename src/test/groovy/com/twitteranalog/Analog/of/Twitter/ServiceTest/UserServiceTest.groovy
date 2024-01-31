package com.twitteranalog.Analog.of.Twitter.ServiceTest

import com.twitteranalog.Analog.of.Twitter.AnalogOfTwitterApplicationTests
import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Repositories.UserRepository
import com.twitteranalog.Analog.of.Twitter.Services.UserService
import com.twitteranalog.Analog.of.Twitter.dtos.UserDto

class UserServiceTest extends AnalogOfTwitterApplicationTests {


    UserRepository userRepository=Mock()

    def userService=new UserService(userRepository)


    def "createUser should successfully create a user"() {
        given:
        UserDto user = new UserDto(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
        )

        User expecteduser = new User(
                username: "testUser",
                password: "testPassword",
                email: "test@example.com",
        )

        userRepository.findByEmail(_) >> Optional.empty()
        userRepository.save(_) >> expecteduser

        when:
        def createdUser = userService.createUser(user)

        then:
        createdUser != null
        createdUser.username == user.username
        createdUser.password == user.password
        createdUser.email == user.email

    }

    //update user
    def "updateUser should successfully update a user"() {
        given:
        String userId = "existingUserId"
        UserDto updatedUserDto = new UserDto(
                username: "updatedUsername",
                password: "updatedPassword",
                email: "updated@example.com",
        )

        User existingUser = new User(
                id: userId,
                username: "existingUsername",
                password: "existingPassword",
                email: "existing@example.com",
        )

        userRepository.findById(_) >> Optional.of(existingUser)
        userRepository.save(_) >> existingUser

        when:
        def updatedUser = userService.updateUser(userId, updatedUserDto)

        then:
        updatedUser != null
        updatedUser.username == updatedUserDto.username
        updatedUser.password == updatedUserDto.password
        updatedUser.email == updatedUserDto.email
    }

    //Test case for delete
    def "deleteUser should successfully delete a user"() {
        given:
        String userId = "123";
        User existingUser = new User(
                username: "existingUser"
        );

        userRepository.findById(userId) >> Optional.of(existingUser);

        when:
        userService.deleteUser(userId);

        then:
        1 * userRepository.delete(existingUser)
    }

    //Add Subscriber
    def "addSubscriber should successfully add a subscriber to a user"() {
        given:
        String userId = "123";
        String followingId = "456";
        User existingUser = new User(
                username: "existingUser",
                subscribedTo: ["111", "222"]
        );
        UserDto expectedUserDto = new UserDto(existingUser);
        expectedUserDto.getSubscribedTo().add(followingId);

        userRepository.findById(userId) >> Optional.of(existingUser);
        userRepository.save(existingUser) >> existingUser;

        when:
        UserDto updatedUserDto = userService.addSubscriber(userId, followingId);

        then:
        updatedUserDto != null
        updatedUserDto.username == existingUser.username
        updatedUserDto.subscribedTo == expectedUserDto.subscribedTo
    }

    //Remove Subscriber
    def "removeSubscriber should successfully remove a subscriber from a user"() {
        given:
        String userId = "123";
        String followingId = "456";
        User existingUser = new User(
                username: "existingUser",
                subscribedTo: ["111", "222", userId]
        );
        UserDto expectedUserDto = new UserDto(existingUser);
        expectedUserDto.getSubscribedTo().remove(userId);

        userRepository.findById(followingId) >> Optional.of(existingUser);
        userRepository.save(existingUser) >> existingUser;

        when:
        UserDto updatedUserDto = userService.removeSubscriber(userId, followingId);

        then:
        updatedUserDto != null
        updatedUserDto.username == existingUser.username
        updatedUserDto.subscribedTo == expectedUserDto.subscribedTo
    }

    //Add Favourite Post

    def "addFavouritePost should successfully add a favourite post to a user"() {
        given:
        String userId = "123";
        String favouritePostId = "789";
        User existingUser = new User(
                username: "existingUser",
                favouritePosts: ["456", "678"]
        );
        UserDto expectedUserDto = new UserDto(existingUser);
        expectedUserDto.getFavouritePosts().add(favouritePostId);

        userRepository.findById(userId) >> Optional.of(existingUser);
        userRepository.save(existingUser) >> existingUser;

        when:
        UserDto updatedUserDto = userService.addFavouritePost(userId, favouritePostId);

        then:
        updatedUserDto != null
        updatedUserDto.username == existingUser.username
        updatedUserDto.favouritePosts == expectedUserDto.favouritePosts
    }

    //Remove Favourite Post
    def "removeFavouritePost should successfully remove a favourite post from a user"() {
        given:
        String userId = "123";
        String favouritePostId = "456";
        User existingUser = new User(
                username: "existingUser",
                favouritePosts: ["456", "678"]
        );
        UserDto expectedUserDto = new UserDto(existingUser);
        expectedUserDto.getFavouritePosts().remove(favouritePostId);

        userRepository.findById(userId) >> Optional.of(existingUser);
        userRepository.save(existingUser) >> existingUser;

        when:
        UserDto updatedUserDto = userService.removeFavouritePost(userId, favouritePostId);

        then:
        updatedUserDto != null
        updatedUserDto.username == existingUser.username
        updatedUserDto.favouritePosts == expectedUserDto.favouritePosts
    }
}