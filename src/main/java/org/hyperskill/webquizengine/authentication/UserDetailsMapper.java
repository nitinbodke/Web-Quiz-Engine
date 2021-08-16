package org.hyperskill.webquizengine.authentication;

import org.hyperskill.webquizengine.entities.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserDetailsMapper {

    public UserDetails toUserDetails(final UserEntity userEntity) {
        return new User(userEntity.getName(), userEntity.getPassword(), Set.of());
    }

}
