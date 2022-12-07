package me.univ.flex.admin.manager;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.model.PageDataResponse;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService implements UserDetailsService {

    private final ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ManagerEntity> managerEntity = managerRepository.findById(username);
        if(!managerEntity.isPresent()) {
            throw new UsernameNotFoundException("Not found account");
        }
        return managerEntity.get();
    }

    public List<ManagerEntity> findAll() {
        List<ManagerEntity> list = managerRepository.findCustomAll();
        return list;
    }
}
