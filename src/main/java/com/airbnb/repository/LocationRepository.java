package com.airbnb.repository;

import com.airbnb.Entity.Location;
import com.airbnb.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {


}