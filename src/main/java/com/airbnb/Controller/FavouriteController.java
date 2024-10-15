package com.airbnb.Controller;

import com.airbnb.Entity.Favourite;
import com.airbnb.Entity.PropertyUser;
import com.airbnb.repository.FavouriteRespository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {

    private FavouriteRespository favouriteRespository;

    public FavouriteController(FavouriteRespository favouriteRespository) {
        this.favouriteRespository = favouriteRespository;
    }
@PostMapping("/addfavourite")
    public ResponseEntity<Favourite> addFavourite(@RequestBody Favourite favourite,
    @AuthenticationPrincipal PropertyUser user
    ){   favourite.setPropertyUser(user);
        Favourite save = favouriteRespository.save(favourite);
        return new ResponseEntity<>(save , HttpStatus.CREATED);
    }
}
