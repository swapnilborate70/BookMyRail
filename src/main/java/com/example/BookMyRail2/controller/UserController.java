package com.example.BookMyRail2.controller;

import com.example.BookMyRail2.entity.User;
import com.example.BookMyRail2.response.ApiResponse;
import com.example.BookMyRail2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

        @Autowired
        private UserService userService;

        @PostMapping("/user/signup")
        public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody User user)
        {
                userService.createUser(user);
                return new ResponseEntity<>(new ApiResponse("success","User Added successfully"), HttpStatus.CREATED);
        }




}
