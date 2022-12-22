package me.univ.flex.admin.adminGroup;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.manager.ManagerService;
import me.univ.flex.common.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminGroupService {

    private final AdminGroupRepository adminGroupRepository;
    private final ManagerService managerService;

    public List<AdminGroupEntity> findAll() {
        List<AdminGroupEntity> list = adminGroupRepository.findAll();
        list = list.stream().filter(group -> group.isDel() == false).collect(Collectors.toList());

        if(list != null && !list.isEmpty()) {
            for(AdminGroupEntity adminGroupEntity : list) {
                adminGroupEntity.setManagerCount(managerService.countManagerInGroup(adminGroupEntity.getGroupId()));
            }
        }

        return list;
    }

    public AdminGroupEntity detail(UserDetailsImpl admin, int groupId) {
        Optional<AdminGroupEntity> optionalAdminGroup = adminGroupRepository.findById(groupId);
        if(optionalAdminGroup.isPresent()) {
            return optionalAdminGroup.get();
        }
        return AdminGroupEntity.builder().build();
    }

    public AdminGroupEntity save(UserDetailsImpl admin, AdminGroupEntity.SaveRequest request) {
        AdminGroupEntity adminGroupEntity = null;

        if(request.getGroupId() == 0) {
            // 등록
            adminGroupEntity = AdminGroupEntity.builder()
                .groupName(request.getGroupName())
                .del(false)
                .registerTime(Timestamp.from(Instant.now()))
                .registerId(admin.getUsername())
                .build();

        } else {
            // 수정
            adminGroupEntity = adminGroupRepository.findById(request.getGroupId()).orElseThrow();
            adminGroupEntity.setGroupName(request.getGroupName());
            adminGroupEntity.setLastUpdateTime(Timestamp.from(Instant.now()));
            adminGroupEntity.setLastUpdateId(admin.getUsername());
        }

        adminGroupEntity = adminGroupRepository.save(adminGroupEntity);
        return adminGroupEntity;
    }

    public AdminGroupEntity.DeleteResponse delete(UserDetailsImpl admin, int groupId) {
        Optional<AdminGroupEntity> optionalAdminGroupEntity = adminGroupRepository.findById(groupId);
        if(!optionalAdminGroupEntity.isPresent()) {
            return AdminGroupEntity.DeleteResponse.builder()
                .success(false)
                .message("삭제할 대상이 않습니다.")
                .build();
        }

        AdminGroupEntity adminGroupEntity = optionalAdminGroupEntity.get();

        if(adminGroupEntity.isDel()) {
            return AdminGroupEntity.DeleteResponse.builder()
                .success(false)
                .message("이미 삭제된 대상입니다.")
                .build();
        }

        // 해당 그룹에 속한 운영자가 있을경우
        if(managerService.existManagerInGroup(groupId)){
            return AdminGroupEntity.DeleteResponse.builder()
                .success(false)
                .message("해당 그룹에 속한 운영자가 존재하여 삭제할 수 없습니다.")
                .build();
        }

        // 해당 그룹 삭제처리.
        adminGroupEntity.setDel(true);;
        adminGroupEntity.setDeleteTime(Timestamp.from(Instant.now()));
        adminGroupEntity.setDeleteId(admin.getUsername());
        adminGroupRepository.save(adminGroupEntity);

        return AdminGroupEntity.DeleteResponse.builder()
            .success(true)
            .build();
    }
}
