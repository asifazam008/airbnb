package com.airbnb.Controller;

import com.airbnb.Entity.PropertyUser;
import com.airbnb.Payload.LoginDto;
import com.airbnb.Payload.PropertyUserDto;
import com.airbnb.Payload.TokenResponce;
import com.airbnb.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/addUser")
    public ResponseEntity<String> createUser(@RequestBody PropertyUserDto propertyUserDto){
        PropertyUser propertyUser = userService.createUser(propertyUserDto);
        if(propertyUser!=null){
            return new ResponseEntity<>("SingUP successful",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("SingUP failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        String token = userService.verifyLogin(loginDto);
        if(token!=null){
            TokenResponce tokenResponce=new TokenResponce();
            tokenResponce.setToken(token);
            return new ResponseEntity<>(tokenResponce ,HttpStatus.OK);
        }
        return new ResponseEntity<>("login Fail",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/profile")
    public PropertyUser getProfiles(@AuthenticationPrincipal PropertyUser propertyUser){
        return propertyUser;
    }
}
