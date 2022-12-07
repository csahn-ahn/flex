package me.univ.flex.admin.code;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.entity.code.CodeGroupEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeGroupService {

    private final CodeGroupRepository codeGroupRepository;

    public List<CodeGroupEntity> findAll() {
        List<CodeGroupEntity> list = codeGroupRepository.findAll();
        return list;
    }
}
