package com.example.easyapply.controllers;

import com.example.easyapply.entities.UserDetailsEntity;
import com.example.easyapply.models.JobProfileModel;
import com.example.easyapply.models.Response;
import com.example.easyapply.models.UserModel;
import com.example.easyapply.services.UserService;
import com.example.easyapply.utilities.ApplicationLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

/**
 * Controller which deals with user operations
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Creates user for given user model
     * @param userModel
     * @return
     */
    @RequestMapping(value = "/user")
    public ResponseEntity<Response> createUser(@RequestBody UserModel userModel) {
        Optional<Integer> userId = userService.createUser(userModel);
        return getResponseResponseEntity(userModel, userId);
    }

    /**
     * Returns user DTO for user id
     * @param userId
     * @return
     */
    @RequestMapping("/user/{id}")
    public ResponseEntity<Response> getUser(@PathVariable("id") int userId){
        Optional<UserModel> userModel = userService.getUser(userId);
        if(userModel.isPresent()){
            return new ResponseEntity<Response>(new Response(HttpStatus.OK, userModel), HttpStatus.OK);
        }

        return new ResponseEntity<Response>(new Response(HttpStatus.BAD_REQUEST, "User not found"), HttpStatus.OK);
    }

    /**
     * Login user
     * @param userModel
     * @return
     */
    @RequestMapping(value = "/user/login")
    public ResponseEntity<Response> loginUser(@RequestBody UserModel userModel) {
        Optional<Integer> userId = userService.loginUser(userModel);
        return getResponseResponseEntity(userModel, userId);
    }

    /**
     * Returns response for login and create API calls
     * @param userModel
     * @param userId
     * @return
     */
    private ResponseEntity<Response> getResponseResponseEntity(@RequestBody UserModel userModel, Optional<Integer> userId) {
        if(userId.isPresent()){
            userModel.setUserId(userId.get());
            return new ResponseEntity<Response>(new Response(HttpStatus.OK, userModel), HttpStatus.OK);
        }

        return new ResponseEntity<Response>(new Response(HttpStatus.BAD_REQUEST, "Creation of user failed"), HttpStatus.OK);
    }
}
