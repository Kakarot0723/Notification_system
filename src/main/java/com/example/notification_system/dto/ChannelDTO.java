package com.example.notification_system.dto;

import com.example.notification_system.enums.ChannelStatus;
import com.example.notification_system.enums.NotificationStatus;
import com.example.notification_system.enums.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChannelDTO {

    private Integer id;
    private String name;
    private LocalDateTime expiryTime;
    private ChannelStatus channelStatus;
    private String channelKey;
    private NotificationType notificationType;
    private NotificationStatus notificationStatus;

}
