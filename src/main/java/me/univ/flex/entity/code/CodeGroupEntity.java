package me.univ.flex.entity.code;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "code_group")
public class CodeGroupEntity {
    @Id
    @Column(nullable = false)
    private String codeGroupId;
    @Column(nullable = false)
    private String codeGroupName;
    @Column(nullable = false)
    private boolean del;
    @Column(nullable = false)
    private Timestamp registerTime;
}
