package me.univ.flex.user.user;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.entity.user.UserEntity;
import me.univ.flex.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    public Optional<UserEntity> findById(int userId) {
        return this.userRepository.findById(userId);
    }

    public UserEntity save(UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    public boolean delete(int userId) {
        if(!this.userRepository.existsById(userId))
            return false;

        this.userRepository.deleteById(userId);
        return true;
    }
}
