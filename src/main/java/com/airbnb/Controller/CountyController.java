package com.airbnb.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
public class CountyController {
@PostMapping("/addCountry")
    public void addCountry(){
        System.out.println("hello");
    }
}
