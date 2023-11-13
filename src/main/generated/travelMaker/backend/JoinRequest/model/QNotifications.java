package travelMaker.backend.JoinRequest.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotifications is a Querydsl query type for Notifications
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotifications extends EntityPathBase<Notifications> {

    private static final long serialVersionUID = -774045380L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotifications notifications = new QNotifications("notifications");

    public final StringPath destinationName = createString("destinationName");

    public final NumberPath<Long> joinId = createNumber("joinId", Long.class);

    public final EnumPath<JoinStatus> joinStatus = createEnum("joinStatus", JoinStatus.class);

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Long> notificationsId = createNumber("notificationsId", Long.class);

    public final StringPath scheduleName = createString("scheduleName");

    public final travelMaker.backend.user.model.QUser user;

    public QNotifications(String variable) {
        this(Notifications.class, forVariable(variable), INITS);
    }

    public QNotifications(Path<? extends Notifications> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotifications(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotifications(PathMetadata metadata, PathInits inits) {
        this(Notifications.class, metadata, inits);
    }

    public QNotifications(Class<? extends Notifications> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new travelMaker.backend.user.model.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

