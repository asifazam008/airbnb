package com.airbnb.Controller;

import com.airbnb.Entity.Booking;
import com.airbnb.Entity.Property;
import com.airbnb.Entity.PropertyUser;
import com.airbnb.Payload.BookingDto;
import com.airbnb.Service.BookingService;
import com.airbnb.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
   private  BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/createBooking/{propertyId}")
public ResponseEntity<?> createBooking(@RequestBody BookingDto bookingDto,
                                       @PathVariable long propertyId,
                                       @AuthenticationPrincipal PropertyUser user
                                       ){
        Booking booking = bookingService.createBooking(bookingDto, propertyId, user);

    return new ResponseEntity<>(booking, HttpStatus.CREATED);

    }
    
}
