package com.mrkenobii.ecommerceapp.controller;

import com.mrkenobii.ecommerceapp.dto.user.SignupResponseDto;
import com.mrkenobii.ecommerceapp.dto.user.SignInResponseDto;
import com.mrkenobii.ecommerceapp.dto.user.SignupDto;
import com.mrkenobii.ecommerceapp.dto.user.SignInDto;
import com.mrkenobii.ecommerceapp.model.User;
import com.mrkenobii.ecommerceapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupDto signupDto){
        return new ResponseEntity<>(userService.signup(signupDto), HttpStatus.OK);
    }
    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInDto signInDto){
        return new ResponseEntity<>(userService.signIn(signInDto), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<User> findUserByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUserDetails(@RequestParam("email") String email, @RequestBody SignupDto details) {
        return new ResponseEntity<>(userService.updateUserAddresses(email, details), HttpStatus.OK);
    }
}
