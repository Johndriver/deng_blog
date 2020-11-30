package com.just.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreated;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private String type;
}
