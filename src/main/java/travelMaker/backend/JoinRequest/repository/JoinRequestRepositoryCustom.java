package travelMaker.backend.JoinRequest.repository;

import travelMaker.backend.JoinRequest.dto.response.JoinRequestNotification;
import travelMaker.backend.JoinRequest.dto.response.NotificationsDto;
import travelMaker.backend.JoinRequest.model.JoinRequest;

public interface JoinRequestRepositoryCustom {
    NotificationsDto searchNotifications(LoginUser loginUser);

}
