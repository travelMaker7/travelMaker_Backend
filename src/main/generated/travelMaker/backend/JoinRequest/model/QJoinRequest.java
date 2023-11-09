package travelMaker.backend.JoinRequest.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJoinRequest is a Querydsl query type for JoinRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJoinRequest extends EntityPathBase<JoinRequest> {

    private static final long serialVersionUID = -1585325223L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJoinRequest joinRequest = new QJoinRequest("joinRequest");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final NumberPath<Long> joinId = createNumber("joinId", Long.class);

    public final EnumPath<JoinStatus> joinStatus = createEnum("joinStatus", JoinStatus.class);

    public final travelMaker.backend.tripPlan.model.QTripPlan tripPlan;

    public final travelMaker.backend.user.model.QUser user;

    public QJoinRequest(String variable) {
        this(JoinRequest.class, forVariable(variable), INITS);
    }

    public QJoinRequest(Path<? extends JoinRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJoinRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJoinRequest(PathMetadata metadata, PathInits inits) {
        this(JoinRequest.class, metadata, inits);
    }

    public QJoinRequest(Class<? extends JoinRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tripPlan = inits.isInitialized("tripPlan") ? new travelMaker.backend.tripPlan.model.QTripPlan(forProperty("tripPlan"), inits.get("tripPlan")) : null;
        this.user = inits.isInitialized("user") ? new travelMaker.backend.user.model.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

