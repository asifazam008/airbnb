package com.airbnb.Controller;

import com.airbnb.Entity.Property;
import com.airbnb.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
    private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
// http://localhost:8091/api/v1/property/location
    @GetMapping("/{location}")
    public ResponseEntity<List<Property>> findPropertyBYLocation( @PathVariable String location){
        List<Property> property = propertyRepository.findPropertyBylocation(location);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }
}
