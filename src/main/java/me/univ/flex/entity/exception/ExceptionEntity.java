package me.univ.flex.entity.exception;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "exception")
public class ExceptionEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int errorId;
    private String exceptionType;
    private String message;
    private String stackTrace;
    private Timestamp errorTime;
}
