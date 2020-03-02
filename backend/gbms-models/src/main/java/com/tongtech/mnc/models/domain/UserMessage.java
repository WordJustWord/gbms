package com.tongtech.mnc.models.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "mnc_user_message")
public class UserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "message_id", referencedColumnName = "id", nullable = false)
    private Message message;

    @ApiModelProperty("阅读状态，0：未读，1：已读")
    @Column(name = "status")
    private int status;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    @ApiModelProperty("是否删除")
    @Column(name = "is_delete")
    private boolean isDelete;
}
