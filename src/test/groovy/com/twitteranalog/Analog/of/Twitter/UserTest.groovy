package com.twitteranalog.Analog.of.Twitter

import com.twitteranalog.Analog.of.Twitter.Models.User
import com.twitteranalog.Analog.of.Twitter.Repositories.UserRepository
import com.twitteranalog.Analog.of.Twitter.Services.UserService
import com.twitteranalog.Analog.of.Twitter.dtos.UserDto

class UserTest extends AnalogOfTwitterApplicationTests {


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

}