package travelMaker.backend.tripPlan.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTripPlan is a Querydsl query type for TripPlan
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTripPlan extends EntityPathBase<TripPlan> {

    private static final long serialVersionUID = 116123957L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTripPlan tripPlan = new QTripPlan("tripPlan");

    public final StringPath address = createString("address");

    public final TimePath<java.time.LocalTime> arriveTime = createTime("arriveTime", java.time.LocalTime.class);

    public final travelMaker.backend.schedule.model.QDate date;

    public final StringPath destinationName = createString("destinationName");

    public final NumberPath<Double> destinationX = createNumber("destinationX", Double.class);

    public final NumberPath<Double> destinationY = createNumber("destinationY", Double.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final TimePath<java.time.LocalTime> leaveTime = createTime("leaveTime", java.time.LocalTime.class);

    public final StringPath region = createString("region");

    public final NumberPath<Long> tripPlanId = createNumber("tripPlanId", Long.class);

    public final NumberPath<Integer> wishCnt = createNumber("wishCnt", Integer.class);

    public final BooleanPath wishJoin = createBoolean("wishJoin");

    public QTripPlan(String variable) {
        this(TripPlan.class, forVariable(variable), INITS);
    }

    public QTripPlan(Path<? extends TripPlan> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTripPlan(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTripPlan(PathMetadata metadata, PathInits inits) {
        this(TripPlan.class, metadata, inits);
    }

    public QTripPlan(Class<? extends TripPlan> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.date = inits.isInitialized("date") ? new travelMaker.backend.schedule.model.QDate(forProperty("date"), inits.get("date")) : null;
    }

}

