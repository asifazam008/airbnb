package com.airbnb.repository;

import com.airbnb.Entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRespository extends JpaRepository< Favourite,Long> {
}
