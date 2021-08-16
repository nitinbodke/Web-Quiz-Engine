package org.hyperskill.webquizengine.mappers;

import org.hyperskill.webquizengine.domain.User;
import org.hyperskill.webquizengine.entities.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toUserEntity(final User user, final PasswordEncoder passwordEncoder) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        return userEntity;
    }

}
