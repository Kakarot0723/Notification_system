package com.example.notification_system.mapper;

import com.example.notification_system.dto.ChannelDTO;
import com.example.notification_system.dto.NotificationDTO;
import com.example.notification_system.entity.Channel;
import com.example.notification_system.entity.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    @Autowired
    private ModelMapper modelMapper;


    public NotificationDTO convertToDto(Notification notification) {
        return modelMapper.map(notification, NotificationDTO.class);
    }

    public Notification convertToEntity(NotificationDTO notificationDTO) {
        return modelMapper.map(notificationDTO, Notification.class);
    }
}
