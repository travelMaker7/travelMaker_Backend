package travelMaker.backend.schedule.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDate is a Querydsl query type for Date
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDate extends EntityPathBase<Date> {

    private static final long serialVersionUID = 166071852L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDate date = new QDate("date");

    public final NumberPath<Long> dateId = createNumber("dateId", Long.class);

    public final QSchedule schedule;

    public final DatePath<java.time.LocalDate> scheduledDate = createDate("scheduledDate", java.time.LocalDate.class);

    public final ListPath<travelMaker.backend.tripPlan.model.TripPlan, travelMaker.backend.tripPlan.model.QTripPlan> tripPlans = this.<travelMaker.backend.tripPlan.model.TripPlan, travelMaker.backend.tripPlan.model.QTripPlan>createList("tripPlans", travelMaker.backend.tripPlan.model.TripPlan.class, travelMaker.backend.tripPlan.model.QTripPlan.class, PathInits.DIRECT2);

    public QDate(String variable) {
        this(Date.class, forVariable(variable), INITS);
    }

    public QDate(Path<? extends Date> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDate(PathMetadata metadata, PathInits inits) {
        this(Date.class, metadata, inits);
    }

    public QDate(Class<? extends Date> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.schedule = inits.isInitialized("schedule") ? new QSchedule(forProperty("schedule"), inits.get("schedule")) : null;
    }

}

