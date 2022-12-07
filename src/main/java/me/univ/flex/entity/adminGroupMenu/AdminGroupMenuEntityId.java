package me.univ.flex.entity.adminGroupMenu;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGroupMenuEntityId implements Serializable {
    private int groupId;
    private int menuId;
}
