package com.airbnb.repository;

import com.airbnb.Entity.Property;
import com.airbnb.Entity.PropertyUser;
import com.airbnb.Entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

    @Query("select r from Reviews r where r.property=:property and r.propertyUser=:propertyUser")
    Reviews findReviewsByUser(@Param("property") Property property , @Param("propertyUser") PropertyUser propertyUser);

    List<Reviews> findByPropertyUser(PropertyUser user);


}