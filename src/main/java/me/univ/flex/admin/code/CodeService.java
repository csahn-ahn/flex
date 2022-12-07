package me.univ.flex.admin.code;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.entity.code.CodeEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;

    public List<CodeEntity> findAll() {
        List<CodeEntity> list = codeRepository.findAll();
        return list;
    }
}
