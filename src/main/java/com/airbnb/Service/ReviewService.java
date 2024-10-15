package com.airbnb.Service;

import com.airbnb.Entity.Property;
import com.airbnb.Entity.PropertyUser;
import com.airbnb.Entity.Reviews;
import com.airbnb.Payload.ReviewDto;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewsRepository reviewsRepository;
    private PropertyRepository propertyRepository;

    public ReviewService(ReviewsRepository reviewsRepository, PropertyRepository propertyRepository) {
        this.reviewsRepository = reviewsRepository;
        this.propertyRepository = propertyRepository;
    }

    public String addReview(long propertyId, ReviewDto reviewDto, PropertyUser user) {
        Property property = propertyRepository.findById(propertyId).get();
        Reviews r= reviewsRepository.findReviewsByUser(property, user);
        if(r!=null){
            return "you already add revive for his property" ;
        }
        Reviews reviews=new Reviews();
        reviews.setContent(reviewDto.getContent());
        reviews.setProperty(property);
        reviews.setPropertyUser(user);
        reviewsRepository.save(reviews);
        return "review added";
    }

    public List<Reviews> getreviews(PropertyUser user){
        List<Reviews> reviews= reviewsRepository.findByPropertyUser(user);
        return reviews;
    }
}
