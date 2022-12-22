package me.univ.flex.admin.stats;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "user_stats")
public class UserStatsEntity {
    @Id
    @Column(nullable = false)
    private String yearMonth;
    @Column(nullable = false)
    private long newCount;
    @Column(nullable = false)
    private long totalCount;
    @Column(nullable = false)
    private long leaveCount;
    @Column(nullable = false)
    private long inactiveCount;
}
