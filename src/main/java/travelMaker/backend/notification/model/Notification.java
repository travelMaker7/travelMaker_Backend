package travelMaker.backend.notification.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelMaker.backend.user.model.User;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name="Notifacation",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "notiReceiveSend",
                        columnNames = {"notiReceiveId","notiSendId"}
                )
        }
)
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String notiContent;
    private LocalDateTime notiDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notiReceiveId")
    private User notiReceive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notiSendId")
    private User notiSend;
}
