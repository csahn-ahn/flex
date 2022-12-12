package me.univ.flex.entity.user;

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
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(columnDefinition = "int", nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int userId;
    private String name;
    private String hp;
    private String password;
}
