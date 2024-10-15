package com.airbnb.repository;

import com.airbnb.Entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    public Images findByIdAndPropertyId(long imageId,long propertyId);
}