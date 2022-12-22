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
public class CodeGroupService {

    private final CodeGroupRepository codeGroupRepository;

    public List<CodeGroupEntity> findAll(UserDetailsImpl admin, CodeGroupEntity.SearchRequest request) {
        //List<CodeGroupEntity> list = codeGroupRepository.findAll();


        String codeGroupId = "%" + request.getCodeGroupId() + "%";
        String codeGroupName = "%" + request.getCodeGroupName() + "%";
        List<CodeGroupEntity> list = codeGroupRepository.findByCodeGroupIdLikeAndCodeGroupNameLikeAndDelOrderByRegisterTimeDesc(codeGroupId, codeGroupName, false);

        return list;
    }

    public CodeGroupEntity detail(UserDetailsImpl admin, String codeGroupId) {
        Optional<CodeGroupEntity> optionalCodeGroup = codeGroupRepository.findById(codeGroupId);
        if(!optionalCodeGroup.isPresent()) {
            return null;
        }
        return optionalCodeGroup.get();
    }

    public CodeGroupEntity.Response save(UserDetailsImpl admin, CodeGroupEntity.SaveRequest request) {
        Optional<CodeGroupEntity> optionalCodeGroup = codeGroupRepository.findById(request.getCodeGroupId());
        CodeGroupEntity entity = null;

        if(StringUtils.isEmpty(request.getCodeGroupId()) || StringUtils.isEmpty(request.getCodeGroupName())) {
            return CodeGroupEntity.Response.builder()
                .success(false)
                .message("필수 입력정보가 누락되었습니다.")
                .build();
        }

        if(!optionalCodeGroup.isPresent()) {
            // 등록
            entity = CodeGroupEntity.builder()
                .codeGroupId(request.getCodeGroupId())
                .codeGroupName(request.getCodeGroupName())
                .del(false)
                .registerTime(TimestampUtil.now())
                .registerId(admin.getUsername())
                .build();

        }else{
            // 수정
            entity = optionalCodeGroup.get();
            entity.setCodeGroupName(request.getCodeGroupName());
            entity.setLastUpdateTime(TimestampUtil.now());
            entity.setLastUpdateId(admin.getUsername());
        }
        codeGroupRepository.save(entity);

        return CodeGroupEntity.Response.builder()
            .success(true)
            .build();
    }

    public CodeGroupEntity.Response delete(UserDetailsImpl admin, String codeGroupId) {
        Optional<CodeGroupEntity> optionalCodeGroup = codeGroupRepository.findById(codeGroupId);

        if(!optionalCodeGroup.isPresent()){
            return CodeGroupEntity.Response.builder()
                .success(false)
                .message("삭제할 대상이 존재하지 않습니다.")
                .build();
        }

        CodeGroupEntity entity = optionalCodeGroup.get();
        entity.setDel(true);
        entity.setDeleteTime(TimestampUtil.now());
        entity.setDeleteId(admin.getUsername());

        codeGroupRepository.save(entity);

        return CodeGroupEntity.Response.builder()
            .success(true)
            .build();

    }
}
