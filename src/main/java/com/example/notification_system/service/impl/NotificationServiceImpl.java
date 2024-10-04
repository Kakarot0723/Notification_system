package com.example.notification_system.service.impl;

import com.example.notification_system.entity.Channel;
import com.example.notification_system.entity.Notification;
import com.example.notification_system.enums.ChannelStatus;
import com.example.notification_system.enums.NotificationStatus;
import com.example.notification_system.enums.NotificationType;
import com.example.notification_system.repository.ChannelRepository;
import com.example.notification_system.repository.NotificationRepository;
import com.example.notification_system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private TwilioSmsSender smsSender;

    @Autowired
    private SendGridEmailSender emailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public Notification sendNotification(Notification notification) {
        // Fetch the channel
        Optional<Channel> channelOpt = channelRepository.findById(notification.getChannel().getId());
        if (!channelOpt.isPresent()) {
            notification.setNotificationStatus(NotificationStatus.FAILED);
            notificationRepository.save(notification);
            throw new RuntimeException("Channel not found");
        }

        Channel channel = channelOpt.get();

        // Check if the channel is enabled and supports the notification type
        if (!ChannelStatus.ENABLED.equals(channel.getChannelStatus()) ) {
            notification.setNotificationStatus(NotificationStatus.FAILED);
            notificationRepository.save(notification);
            throw new RuntimeException("Channel is not enabled or does not support this notification type");
        }

        // Set status to IN_PROGRESS
        notification.setNotificationStatus(NotificationStatus.IN_PROGRESS);
        notification.setNotificationType(channel.getNotificationType());
        notificationRepository.save(notification);

        try {
            if (NotificationType.SMS.equals(notification.getNotificationType())) {
                smsSender.sendSms(notification.getRecipient(), notification.getMessage());
            } else if (NotificationType.EMAIL.equals(notification.getNotificationType())) {
                emailSender.sendEmail(notification.getRecipient(), "Notification", notification.getMessage());
            }

            // Update status to SUCCESS
            notification.setNotificationStatus(NotificationStatus.SUCCESS);
        } catch (Exception e) {
            // Update status to FAILED
            notification.setNotificationStatus(NotificationStatus.FAILED);
        }

        notificationRepository.save(notification);
        return notification;
    }
}
