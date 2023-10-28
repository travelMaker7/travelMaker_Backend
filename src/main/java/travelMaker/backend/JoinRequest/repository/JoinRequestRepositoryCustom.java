package travelMaker.backend.JoinRequest.repository;

import travelMaker.backend.JoinRequest.dto.response.JoinRequestNotification;
import travelMaker.backend.JoinRequest.dto.response.NotificationsDto;

public interface JoinRequestRepositoryCustom {
    NotificationsDto searchNotifications(Long userId);
}
