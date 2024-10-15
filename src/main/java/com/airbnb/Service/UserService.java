package com.airbnb.Service;


import com.airbnb.Entity.PropertyUser;
import com.airbnb.Payload.LoginDto;
import com.airbnb.Payload.PropertyUserDto;
import com.airbnb.repository.PropertyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
 private PropertyUserRepository propertyUserRepository;
    @Autowired
    private JWTservice jwTservice;


    public PropertyUser createUser(PropertyUserDto propertyUserDto) {
        PropertyUser propertyUser=new PropertyUser();
        propertyUser.setFirstName(propertyUserDto.getFirstName());
        propertyUser.setLastName(propertyUserDto.getLastName());
        propertyUser.setEmail(propertyUserDto.getEmail());
        propertyUser.setUserName(propertyUserDto.getUserName());
        propertyUser.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(10)));
        propertyUser.setUserRole(propertyUserDto.getUserName());
        PropertyUser save = propertyUserRepository.save(propertyUser);
        return save;

    }

    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> user = propertyUserRepository.
                findByUserName(loginDto.getUserName());
        if(user.isPresent()){
            PropertyUser propertyUser = user.get();

            if(BCrypt.checkpw( loginDto.getPassword(),propertyUser.getPassword())){
               return jwTservice.generateToken(propertyUser);
            }

        }
        return null;
    }

}
