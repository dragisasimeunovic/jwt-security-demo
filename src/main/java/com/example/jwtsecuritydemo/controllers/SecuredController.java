package com.example.jwtsecuritydemo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This will be our secure controller to test that the authentication was
 * successful and that we get a response that we normally would not have
 * access to without the correct JWT
 */
@RestController
@RequestMapping("/api/secure")
public class SecuredController {


    @GetMapping
    public ResponseEntity reachSecureEndpoint() {

        return new ResponseEntity("If you are reading this you reached a secure endpoint", HttpStatus.OK);

    }

}
