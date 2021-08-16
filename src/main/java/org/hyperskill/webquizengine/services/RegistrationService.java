package org.hyperskill.webquizengine.services;

import org.hyperskill.webquizengine.domain.User;
import org.hyperskill.webquizengine.entities.UserEntity;
import org.hyperskill.webquizengine.mappers.UserMapper;
import org.hyperskill.webquizengine.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void register(final User user) {
        UserEntity userEntity = userMapper.toUserEntity(user, passwordEncoder);
        userRepository.save(userEntity);
    }
}
