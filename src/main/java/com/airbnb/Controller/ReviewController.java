package com.airbnb.Controller;


import com.airbnb.Entity.PropertyUser;
import com.airbnb.Entity.Reviews;
import com.airbnb.Payload.ReviewDto;
import com.airbnb.Service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/reviews")
@RestController
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{propertyId}")
    public ResponseEntity<String> addReview(@PathVariable long propertyId,
                                            @RequestBody ReviewDto reviewDto,
                                             @AuthenticationPrincipal PropertyUser user){

        String s = reviewService.addReview(propertyId, reviewDto, user);

        return new ResponseEntity<>(s, HttpStatus.CREATED);

    }
    @GetMapping("/reviews")
    public ResponseEntity<List<Reviews>> getUserReviews(@AuthenticationPrincipal PropertyUser user){
        List<Reviews> getreviews = reviewService.getreviews(user);

        return new ResponseEntity<>(getreviews,HttpStatus.OK);
    }
}
