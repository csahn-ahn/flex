package me.univ.flex.code;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.common.utils.TimestampUtil;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;

    public List<CodeEntity> findAll(String codeGroupId) {
        List<CodeEntity> list = codeRepository.findByCodeGroupIdAndDisplayAndDelOrderByRegisterTimeDesc(codeGroupId, true, false);

        return list;
    }

    public CodeEntity detail(String codeGroupId, String codeId) {
        Optional<CodeEntity> optionalCode = codeRepository.findByCodeGroupIdAndCodeId(codeGroupId, codeId);
        if(!optionalCode.isPresent()) {
            return null;
        }
        return optionalCode.get();
    }

    public CodeEntity.Response save(UserDetailsImpl userDetails, String codeGroupId, CodeEntity.SaveRequest request) {
        Optional<CodeEntity> optionalCode = codeRepository.findByCodeGroupIdAndCodeId(codeGroupId, request.getCodeId());
        CodeEntity entity = null;

        if(StringUtils.isEmpty(request.getCodeId()) || StringUtils.isEmpty(request.getCodeValue())) {
            return CodeEntity.Response.builder()
                .success(false)
                .message("필수 입력정보가 누락되었습니다.")
                .build();
        }

        if(!optionalCode.isPresent()) {
            // 등록
            entity = CodeEntity.builder()
                .codeGroupId(codeGroupId)
                .codeId(request.getCodeId())
                .codeValue(request.getCodeValue())
                .codeName(request.getCodeName())
                .description(request.getDescription())
                .etc1(request.getEtc1())
                .etc2(request.getEtc2())
                .etc3(request.getEtc3())
                .display(true)
                .del(false)
                .registerTime(TimestampUtil.now())
                .registerId(userDetails.getUsername())
                .build();

        }else{
            // 수정
            entity = optionalCode.get();
            entity.setCodeValue(request.getCodeValue());
            entity.setCodeName(request.getCodeName());
            entity.setDescription(request.getDescription());
            entity.setEtc1(request.getEtc1());
            entity.setEtc2(request.getEtc2());
            entity.setEtc3(request.getEtc3());
            entity.setLastUpdateTime(TimestampUtil.now());
            entity.setLastUpdateId(userDetails.getUsername());
        }
        codeRepository.save(entity);

        return CodeEntity.Response.builder()
            .success(true)
            .build();
    }

    public CodeEntity.Response display(UserDetailsImpl userDetails, String codeGroupId, String codeId, boolean display) {
        Optional<CodeEntity> optionalCode = codeRepository.findByCodeGroupIdAndCodeId(codeGroupId, codeId);

        if(!optionalCode.isPresent()){
            return CodeEntity.Response.builder()
                .success(false)
                .message("대상이 존재하지 않습니다.")
                .build();
        }

        CodeEntity entity = optionalCode.get();
        entity.setDisplay(display);
        entity.setLastUpdateTime(TimestampUtil.now());
        entity.setLastUpdateId(userDetails.getUsername());

        codeRepository.save(entity);

        return CodeEntity.Response.builder()
            .success(true)
            .build();

    }

    public CodeEntity.Response delete(UserDetailsImpl userDetails, String codeGroupId, String codeId) {
        Optional<CodeEntity> optionalCode = codeRepository.findByCodeGroupIdAndCodeId(codeGroupId, codeId);

        if(!optionalCode.isPresent()){
            return CodeEntity.Response.builder()
                .success(false)
                .message("삭제할 대상이 존재하지 않습니다.")
                .build();
        }

        CodeEntity entity = optionalCode.get();
        if(entity.isDel() == true) {
            return CodeEntity.Response.builder()
                .success(false)
                .message("이미 삭제되어 있습니다.")
                .build();
        }

        entity.setDel(true);
        entity.setDeleteTime(TimestampUtil.now());
        entity.setDeleteId(userDetails.getUsername());

        codeRepository.save(entity);

        return CodeEntity.Response.builder()
            .success(true)
            .build();

    }
}
