package me.univ.flex.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "error")
public class ErrorEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int errorId;
    private int statusCode;
    private String reason;
    private boolean solution;
    private String info1;
    private String info2;
    private String info3;
    private String info4;
    private String info5;
    @Column(columnDefinition = "nvarchar(max)")
    private String trace;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp errorTime;
}
